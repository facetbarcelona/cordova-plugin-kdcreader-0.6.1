#import "KCommonDelegate.h"
#import "KConstants.h"

NSString * const kConnect = @"connect";
NSString * const kDisconnect = @"disconnect";
NSString * const kSoftwareTrigger = @"softwareTrigger";

@implementation KCommonDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(connect:command:)) forKey:kConnect];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(disconnect:command:)) forKey:kDisconnect];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(softwareTrigger:command:)) forKey:kSoftwareTrigger];
    }
    
    return self;
}

- (BOOL)isSupported: (NSString *)name {
    return [SUPPORTED_COMMAND objectForKey:name] != nil;
}

- (void)execute: (NSString *)name delegate: (id <CDVCommandDelegate>)delegate command: (CDVInvokedUrlCommand*)command {
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

- (void)connect: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    NSDictionary *device = [command.arguments objectAtIndex:0];
    NSString *name = device != nil ? [device objectForKey:kDeviceName] : @"";
    
    [delegate runInBackground:^{
        BOOL bRet = NO;
        BOOL isFound = NO;

        [self.lock lock];
        
        @try {
            if (name.length == 0) {
                bRet = [self.reader ConnectEx];
            } else {
                NSMutableArray *lists = [[NSMutableArray alloc] init];
                
                [lists addObject:[self.reader GetAvailableDeviceListEx:EXTERNAL_ACCESSORY_LIST options:nil]];
                [lists addObject:[self.reader GetAvailableDeviceListEx:CONNECTED_PERIPHERAL_LIST options:nil]];
                [lists addObject:[self.reader GetAvailableDeviceListEx:SCANNED_PERIPHERAL_LIST options:nil]];
                
                for (NSArray *list in lists) {
                    for (KDCDevice *d in list) {
                        if ( d.name != nil && [name isEqualToString:d.name]) {
                            bRet = [self.reader ConnectEx:d];
                            isFound = YES;
                            break;
                        }
                    }
                    
                    if (isFound) {
                        break;
                    }
                }
            }
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

- (void)disconnect: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        [self.lock lock];
        
        @try {
            [self.reader Disconnect];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)softwareTrigger: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            [self.reader SoftwareTrigger];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)dealloc {
    SUPPORTED_COMMAND = nil;
}

@end
