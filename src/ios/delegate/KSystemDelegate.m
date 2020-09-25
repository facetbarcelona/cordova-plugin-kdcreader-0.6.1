#import "KSystemDelegate.h"

#import "KConstants.h"

NSString * const kEnableButtonLock = @"enableButtonLock";
NSString * const kEnableScanButtonLock = @"enableScanButtonLock";

NSString * const kEraseMemory = @"eraseMemory";

NSString * const kGetMemoryLeft = @"getMemoryLeft";

NSString * const kGetSleepTimeout = @"getSleepTimeout";
NSString * const kSetSleepTimeout = @"setSleepTimeout";

NSString * const kGetScanTimeout = @"getScanTimeout";
NSString * const kSetScanTimeout = @"setScanTimeout";

NSString * const kGetBatteryLevel = @"getBatteryLevel";

NSString * const kSyncSystemTime = @"syncSystemTime";
NSString * const kResetSystemTime = @"resetSystemTime";

NSString * const kSetFactoryDefault = @"setFactoryDefault";

static enum SleepTimeout sleepTimeoutTable[] = {
    SLEEP_TIMEOUT_DISABLED,
    SLEEP_TIMEOUT_1_SECOND,
    SLEEP_TIMEOUT_2_SECONDS,
    SLEEP_TIMEOUT_3_SECONDS,
    SLEEP_TIMEOUT_4_SECONDS,
    SLEEP_TIMEOUT_5_SECONDS,
    SLEEP_TIMEOUT_10_SECONDS,
    SLEEP_TIMEOUT_20_SECONDS,
    SLEEP_TIMEOUT_30_SECONDS,
    SLEEP_TIMEOUT_60_SECONDS,
    SLEEP_TIMEOUT_120_SECONDS,
    SLEEP_TIMEOUT_300_SECONDS,
    SLEEP_TIMEOUT_600_SECONDS
};

@implementation KSystemDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableButtonLock:command:)) forKey:kEnableButtonLock];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableScanButtonLock:command:)) forKey:kEnableScanButtonLock];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(eraseMemory:command:)) forKey:kEraseMemory];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getMemoryLeft:command:)) forKey:kGetMemoryLeft];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getSleepTimeout:command:)) forKey:kGetSleepTimeout];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setSleepTimeout:command:)) forKey:kSetSleepTimeout];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getScanTimeout:command:)) forKey:kGetScanTimeout];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setScanTimeout:command:)) forKey:kSetScanTimeout];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getBatteryLevel:command:)) forKey:kGetBatteryLevel];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(syncSystemTime:command:)) forKey:kSyncSystemTime];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(resetSystemTime:command:)) forKey:kResetSystemTime];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setFactoryDefault:command:)) forKey:kSetFactoryDefault];
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

- (void)enableButtonLock: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader EnableButtonLock:param.boolValue];
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

- (void)enableScanButtonLock: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader EnableScanButtonLock:param.boolValue];
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

- (void)eraseMemory: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader EraseMemory];
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

- (void)getMemoryLeft: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            int size = [self.reader GetMemoryLeft];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:size];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)getSleepTimeout: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum SleepTimeout timeout = [self.reader GetSleepTimeout];
            int size = sizeof(sleepTimeoutTable) / sizeof(enum SleepTimeout);
            int index = 0;
            CDVPluginResult *pluginResult = nil;

            for (index = 0 ; index < size ; index++) {
                if (sleepTimeoutTable[index] == timeout) {
                    break;
                }
            }

            if (index < size) { // found
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:index];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }

            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setSleepTimeout: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;

        if (![self isConnected:delegate command:command]) {
            return;
        }

        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            enum SleepTimeout timeout = sleepTimeoutTable[param.intValue];
            
            bRet = [self.reader SetSleepTimeout:timeout];
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

- (void)getScanTimeout: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum ScanTimeout timeout = [self.reader GetScanTimeout];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:timeout];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setScanTimeout: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];

            bRet = [self.reader SetScanTimeout:param.intValue];
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

- (void)getBatteryLevel: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            int level = [self.reader GetBatteryLevel];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:level];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)syncSystemTime: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader SyncSystemTime];
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

- (void)resetSystemTime: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader ResetSystemTime];
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

- (void)setFactoryDefault: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            bRet = [self.reader SetFactoryDefault];
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
