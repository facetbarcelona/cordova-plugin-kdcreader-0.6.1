#import "KUhfDelegate.h"

#import "KConstants.h"

NSString * const kIsUHFPowerEnabled = @"isUHFPowerEnabled";
NSString * const kEnableUHFPower = @"enableUHFPower";

NSString * const kGetUHFPowerLevel = @"getUHFPowerLevel";
NSString * const kSetUHFPowerLevel = @"setUHFPowerLevel";

NSString * const kGetUHFReadMode = @"getUHFReadMode";
NSString * const kSetUHFReadMode = @"setUHFReadMode";

NSString * const kGetUHFReadTagMode = @"getUHFReadTagMode";
NSString * const kSetUHFReadTagMode = @"setUHFReadTagMode";

NSString * const kGetUHFDataType = @"getUHFDataType";
NSString * const kSetUHFDataType = @"setUHFDataType";

NSString * const kIsUHFBurstModeEnabled = @"isUHFBurstModeEnabled";
NSString * const kEnableUHFBurstMode = @"enableUHFBurstMode";

NSString * const kIsUHFKeyEventEnabled = @"isUHFKeyEventEnabled";
NSString * const kEnableUHFKeyEvent = @"enableUHFKeyEvent";

NSString * const kCancelUHFReading = @"cancelUHFReading";

NSString * const kGetUHFTagList = @"getUHFTagList";

NSString * const kSelectUHFTag = @"selectUHFTag";

NSString * const kReadUHFTagMemory = @"readUHFTagMemory";
NSString * const kWriteUHFTagMemory = @"writeUHFTagMemory";

NSString * const kSetUHFTagLock = @"setUHFTagLock";

NSString * const kKillUHFTag = @"killUHFTag";

@implementation KUhfDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(isUHFPowerEnabled:command:)) forKey:kIsUHFPowerEnabled];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableUHFPower:command:)) forKey:kEnableUHFPower];

        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFPowerLevel:command:)) forKey:kGetUHFPowerLevel];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFPowerLevel:command:)) forKey:kSetUHFPowerLevel];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFReadMode:command:)) forKey:kGetUHFReadMode];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFReadMode:command:)) forKey:kSetUHFReadMode];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFReadTagMode:command:)) forKey:kGetUHFReadTagMode];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFReadTagMode:command:)) forKey:kSetUHFReadTagMode];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFDataType:command:)) forKey:kGetUHFDataType];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFDataType:command:)) forKey:kSetUHFDataType];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(isUHFBurstModeEnabled:command:)) forKey:kIsUHFBurstModeEnabled];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableUHFBurstMode:command:)) forKey:kEnableUHFBurstMode];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(isUHFKeyEventEnabled:command:)) forKey:kIsUHFKeyEventEnabled];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableUHFKeyEvent:command:)) forKey:kEnableUHFKeyEvent];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(cancelUHFReading:command:)) forKey:kCancelUHFReading];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFTagList:command:)) forKey:kGetUHFTagList];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(selectUHFTag:command:)) forKey:kSelectUHFTag];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(readUHFTagMemory:command:)) forKey:kReadUHFTagMemory];
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(writeUHFTagMemory:command:)) forKey:kWriteUHFTagMemory];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFTagLock:command:)) forKey:kSetUHFTagLock];
        
        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(killUHFTag:command:)) forKey:kKillUHFTag];
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

- (NSString *)convertHexString:(NSData *)data {
    int length = 0;
    uint8_t* bytes = nil;
    
    if (data == nil || data.length == 0) {
        return nil;
    }
    
    length = (int) data.length;
    bytes = (uint8_t *)data.bytes;
    
    NSMutableString *hexString = [NSMutableString stringWithCapacity:length * 2];
    
    for (int i=0; i<length; i++) {
        [hexString appendFormat:@"%02X", (unsigned int)bytes[i]];
    }
    
    return [NSString stringWithString:hexString];
}

- (void)isUHFPowerEnabled: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            BOOL isEnabled = [self.reader IsUHFPowerEnabled];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:isEnabled];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
    
}

- (void)enableUHFPower: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader EnableUHFPower:param.boolValue];
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

- (void)getUHFPowerLevel: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum UHFPowerLevel level = [self.reader GetUHFPowerLevel];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:level];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
    
}

- (void)setUHFPowerLevel: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader SetUHFPowerLevel:param.intValue];
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

- (void)getUHFReadMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum UHFReadMode mode = [self.reader GetUHFReadMode];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:mode];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setUHFReadMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader SetUHFReadMode:param.intValue];
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

- (void)getUHFReadTagMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum UHFReadTagMode mode = [self.reader GetUHFReadTagMode];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:mode];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setUHFReadTagMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader SetUHFReadMode:param.intValue];
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

- (void)getUHFDataType: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            enum UHFDataType type = [self.reader GetUHFDataType];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:type];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)setUHFDataType: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader SetUHFDataType:param.intValue];
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

- (void)isUHFBurstModeEnabled: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            BOOL isEnabled = [self.reader IsUHFBurstModeEnabled];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:isEnabled];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
    
}

- (void)enableUHFBurstMode: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader EnableUHFBurstMode:param.boolValue];
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

- (void)isUHFKeyEventEnabled: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            BOOL isEnabled = [self.reader IsUHFKeyEventEnabled];
            
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:isEnabled];
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } @finally {
            [self.lock unlock];
        }
    }];
}

- (void)enableUHFKeyEvent: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        BOOL bRet = NO;
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0];
            bRet = [self.reader EnableUHFKeyEventEnabled:param.boolValue];
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

- (void)cancelUHFReading: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            [self.reader CancelUHFReading];
        } @finally {
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
}

- (void)getUHFTagList: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        NSMutableArray *tagList = [[NSMutableArray alloc] init];
        
        struct UHFStatus status = { UHF_DATA_HEX_DECIMAL, 0};

        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSNumber *param = [command.arguments objectAtIndex:0]; // timeout
            
            NSArray *list = [self.reader GetUHFTagList:param.intValue status:&status];
            
            // convert NSData to NSString
            for (NSData *tag in list) {
                [tagList addObject:[[NSString alloc] initWithData:tag encoding:NSUTF8StringEncoding]];
            }
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (status.errorCode == UHF_SUCCESS) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:tagList];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
}

- (void)selectUHFTag: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        struct UHFStatus status = { UHF_DATA_HEX_DECIMAL, 0};

        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSString *param = [command.arguments objectAtIndex:0]; // EPC
            NSData *epc = [param dataUsingEncoding:NSUTF8StringEncoding];
            
            [self.reader SelectUHFTag:epc status:&status];
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (status.errorCode == UHF_SUCCESS) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
    
}

- (void)readUHFTagMemory: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        struct UHFStatus status = { UHF_DATA_HEX_DECIMAL, 0};
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSString *param1 = [command.arguments objectAtIndex:0]; // Access Password
            NSData *pwd = [param1 dataUsingEncoding:NSUTF8StringEncoding];
            
            NSNumber *param2 = [command.arguments objectAtIndex:1]; // Memory Bank
            NSNumber *param3 = [command.arguments objectAtIndex:2]; // Start Address
            NSNumber *param4 = [command.arguments objectAtIndex:3]; // Length
            
            [self.reader ReadUHFTagMemory:pwd bank:param2.intValue
                                    start:param3.intValue length:param4.intValue status:&status];
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (status.errorCode == UHF_SUCCESS) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
    
}

- (void)writeUHFTagMemory: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        struct UHFStatus status = { UHF_DATA_HEX_DECIMAL, 0};
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSString *param1 = [command.arguments objectAtIndex:0]; // Access Password
            NSData *pwd = [param1 dataUsingEncoding:NSUTF8StringEncoding];
            
            NSNumber *param2 = [command.arguments objectAtIndex:1]; // Memory Bank
            NSNumber *param3 = [command.arguments objectAtIndex:2]; // Start Address
            NSNumber *param4 = [command.arguments objectAtIndex:3]; // Length

            NSString *param5 = [command.arguments objectAtIndex:3]; // data
            NSData *data = [param5 dataUsingEncoding:NSUTF8StringEncoding];

            [self.reader WriteUHFTagMemory:pwd bank:param2.intValue
                                     start:param3.intValue length:param4.intValue data:data status:&status];
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (status.errorCode == UHF_SUCCESS) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
}

- (void)setUHFTagLock: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    //pwd, mask,
    [delegate runInBackground:^{
        struct UHFStatus status = { UHF_DATA_HEX_DECIMAL, 0};
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSString *param1 = [command.arguments objectAtIndex:0]; // Access Password
            NSData *pwd = [param1 dataUsingEncoding:NSUTF8StringEncoding];
            
            NSNumber *param2 = [command.arguments objectAtIndex:1]; // Memory Bank
            
            [self.reader SetUHFTagLock:pwd mask:param2.intValue status:&status];
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (status.errorCode == UHF_SUCCESS) {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:self.converter.eSuccess];
            } else {
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:self.converter.eFailed];
            }
            
            [delegate sendPluginResult:pluginResult callbackId:command.callbackId];
            
            [self.lock unlock];
        }
    }];
    
}

- (void)killUHFTag: (id <CDVCommandDelegate>)delegate command:(CDVInvokedUrlCommand*)command {
    [delegate runInBackground:^{
        struct UHFStatus status = { UHF_DATA_HEX_DECIMAL, 0};
        
        if (![self isConnected:delegate command:command]) {
            return;
        }
        
        [self.lock lock];
        
        @try {
            NSString *param = [command.arguments objectAtIndex:0]; // Kill Password
            NSData *pwd = [param dataUsingEncoding:NSUTF8StringEncoding];
            
            [self.reader KillUHFTag:pwd status:&status];
        } @catch (NSException *e) {
            NSLog(@"%@ %@", e.name, e.reason);
        } @finally {
            CDVPluginResult *pluginResult = nil;
            
            if (status.errorCode == UHF_SUCCESS) {
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
