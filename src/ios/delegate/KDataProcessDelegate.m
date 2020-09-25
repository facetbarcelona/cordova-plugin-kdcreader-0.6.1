#import "KDataProcessDelegate.h"

#import "KConstants.h"

NSString * const kGetDataPrefix = @"getDataPrefix";
NSString * const kSetDataPrefix = @"setDataPrefix";

NSString * const kGetDataSuffix = @"getDataSuffix";
NSString * const kSetDataSuffix = @"setDataSuffix";

NSString * const kGetSymbology = @"getSymbology";
NSString * const kSetSymbology = @"setSymbology";
NSString * const kEnableAllSymbology = @"enableAllSymbology";
NSString * const kDisableAllSymbology = @"disableAllSymbology";

NSString * const kGetBarcodeOption = @"getBarcodeOption";
NSString * const kSetBarcodeOption = @"setBarcodeOption";
NSString * const kEnableAllOptions = @"enableAllOptions";
NSString * const kDisableAllOptions = @"disableAllOptions";

NSString * const kGetNumberOfStoredBarcode = @"getNumberOfStoredBarcode";

NSString * const kGetStoredDataSingle = @"getStoredDataSingle";

NSString * const kGetDataProcessMode = @"getDataProcessMode";
NSString * const kSetDataProcessMode = @"setDataProcessMode";

@implementation KDataProcessDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getDataPrefix:command:)) forKey:kGetDataPrefix];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDataPrefix:command:)) forKey:kSetDataPrefix];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getDataSuffix:command:)) forKey:kGetDataSuffix];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDataSuffix:command:)) forKey:kSetDataSuffix];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getSymbology:command:)) forKey:kGetSymbology];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setSymbology:command:)) forKey:kSetSymbology];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableAllSymbology:command:)) forKey:kEnableAllSymbology];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(disableAllSymbology:command:)) forKey:kDisableAllSymbology];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getBarcodeOption:command:)) forKey:kGetBarcodeOption];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setBarcodeOption:command:)) forKey:kSetBarcodeOption];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableAllOptions:command:)) forKey:kEnableAllOptions];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(disableAllOptions:command:)) forKey:kDisableAllOptions];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getNumberOfStoredBarcode:command:)) forKey:kGetNumberOfStoredBarcode];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getStoredDataSingle:command:)) forKey:kGetStoredDataSingle];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getDataProcessMode:command:)) forKey:kGetDataProcessMode];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setDataProcessMode:command:)) forKey:kSetDataProcessMode];
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

- (void)setDataPrefix: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = false;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }

        [self.lock lock];
        
        @try {
            NSString *param = [command.arguments objectAtIndex:0];

            const char * prefix = [param UTF8String];
            
            if (prefix != NULL) {
                bRet = [self.reader SetDataPrefix: (char *)prefix];
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

- (void)getDataSuffix: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            char *s = [self.reader GetDataSuffix];
            NSString *suffix = [[NSString alloc] initWithFormat:@"%s", s != NULL ? s : ""];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:suffix];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setDataSuffix: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];

        @try {
            NSString *param = [command.arguments objectAtIndex:0];
            const char * suffix = [param UTF8String];
            
            if (suffix != NULL) {
                bRet = [self.reader SetDataSuffix: (char *)suffix];
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


- (void)getSymbology: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    if (![self isConnected:delegate command:command]) {
        return;
    }

}

- (void)setSymbology: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    if (![self isConnected:delegate command:command]) {
        return;
    }
    
}

- (void)enableAllSymbology: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }

        [self.lock lock];
        
        @try {
            bRet = [self.reader EnableAllSymbologies];
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

- (void)disableAllSymbology: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader DisableAllSymbologies];
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

- (void)getBarcodeOption: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    if (![self isConnected:delegate command:command]) {
        return;
    }
    
}

- (void)setBarcodeOption: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    if (![self isConnected:delegate command:command]) {
        return;
    }
    
}

- (void)enableAllOptions: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader EnableAllOptions];
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

- (void)disableAllOptions: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader DisableAllOptions];
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

- (void)getNumberOfStoredBarcode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            int size = [self.reader GetNumberOfStoredBarcode];

            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:size];
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)getStoredDataSingle: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            [self.reader GetStoredDataSingle];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)getDataProcessMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum WedgeMode mode = [self.reader GetDataProcessMode];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:mode];
        
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setDataProcessMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }

        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader SetDataProcessMode:param.intValue];
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
