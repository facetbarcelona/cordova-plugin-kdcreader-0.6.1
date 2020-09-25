#import "KDisplayDelegate.h"

#import "KConstants.h"

NSString * const kSetDisplayMessage = @"setDisplayMessage";

@implementation KDisplayDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDisplayMessage:command:)) forKey:kSetDisplayMessage];
    }
    
    return self;
}

- (BOOL)isSupported: (NSString *)name {
    return [SUPPORTED_COMMAND objectForKey:name] != nil;
}

-(void)execute: (NSString *)name delegate: (id <CDVCommandDelegate>)delegate command: (CDVInvokedUrlCommand*)command {
    NSString * value = [SUPPORTED_COMMAND objectForKey:name];
    
    SEL selector = (value != nil) ? NSSelectorFromString(value) : nil;
    
    if (selector == nil) {
        NSLog(@"%s",__FUNCTION__);
        return;
    }
    
    NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:[self methodSignatureForSelector:selector]];
    invocation.selector = selector;
    invocation.target = self;
    [invocation setArgument:&delegate atIndex:2];
    [invocation setArgument:&command atIndex:3];
    [invocation invoke];
}

- (BOOL) isConnected : (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    CDVPluginResult *pluginResult = nil;
    
    if (self.reader == nil || self.lock == nil) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eNull];
    } else if (![self.reader IsConnected]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eNotConnected];
    }
    
    if (pluginResult != nil) {
        [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
    
    return pluginResult == nil;
}

- (void)setDisplayMessage: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }

        [self.lock lock];
        
        @try {
            NSString *param = [command.arguments objectAtIndex:0];
            const char * message = [param UTF8String];

            if (message != NULL) {
                bRet = [self.reader SetDisplayMessage: (char *)message];
            }
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (bRet) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
}

- (void)dealloc {
    SUPPORTED_COMMAND = nil;
}

@end
