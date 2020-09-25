#import "KKeypadDelegate.h"

#import "KConstants.h"

@implementation KKeypadDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getDataPrefix:command:)) forKey:kGetDataPrefix];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDataPrefix:command:)) forKey:kSetDataPrefix];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getDataSuffix:command:)) forKey:kGetDataSuffix];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDataSuffix:command:)) forKey:kSetDataSuffix];
//
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getSymbology:command:)) forKey:kGetSymbology];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setSymbology:command:)) forKey:kSetSymbology];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableAllSymbology:command:)) forKey:kEnableAllSymbology];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(disableAllSymbology:command:)) forKey:kDisableAllSymbology];
//
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getBarcodeOption:command:)) forKey:kGetBarcodeOption];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setBarcodeOption:command:)) forKey:kSetBarcodeOption];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableAllOptions:command:)) forKey:kEnableAllOptions];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(disableAllOptions:command:)) forKey:kDisableAllOptions];
//
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getNumberOfStoredBarcode:command:)) forKey:kGetNumberOfStoredBarcode];
//
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getStoredDataSingle:command:)) forKey:kGetStoredDataSingle];
//
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getDataProcessMode:command:)) forKey:kGetDataProcessMode];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDataProcessMode:command:)) forKey:kSetDataProcessMode];
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

- (void)getDataPrefix: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            char * p = [self.reader GetDataPrefix];
            
            NSString *prefix = [[NSString alloc] initWithFormat:@"%s", p != NULL ? p : ""];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:prefix];
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
