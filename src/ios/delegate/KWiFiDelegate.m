#import "KWiFiDelegate.h"

#import "KConstants.h"

@implementation KWiFiDelegate {
    NSMutableDictionary *SUPPORTED_COMMAND;
}

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *) converter {
    self = [super init];
    
    if (self) {
        self.reader = reader;
        self.lock = lock;
        self.converter = converter;
        
        SUPPORTED_COMMAND = [[NSMutableDictionary alloc] init];
        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(isUHFPowerEnabled:command:)) forKey:kIsUHFPowerEnabled];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableUHFPower:command:)) forKey:kEnableUHFPower];
//
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFPowerLevel:command:)) forKey:kGetUHFPowerLevel];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFPowerLevel:command:)) forKey:kSetUHFPowerLevel];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFReadMode:command:)) forKey:kGetUHFReadMode];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFReadMode:command:)) forKey:kSetUHFReadMode];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFReadTagMode:command:)) forKey:kGetUHFReadTagMode];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFReadTagMode:command:)) forKey:kSetUHFReadTagMode];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFDataType:command:)) forKey:kGetUHFDataType];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFDataType:command:)) forKey:kSetUHFDataType];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(isUHFBurstModeEnabled:command:)) forKey:kIsUHFBurstModeEnabled];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableUHFBurstMode:command:)) forKey:kEnableUHFBurstMode];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(isUHFKeyEventEnabled:command:)) forKey:kIsUHFKeyEventEnabled];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(enableUHFKeyEvent:command:)) forKey:kEnableUHFKeyEvent];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(cancelUHFReading:command:)) forKey:kCancelUHFReading];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(getUHFTagList:command:)) forKey:kGetUHFTagList];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(selectUHFTag:command:)) forKey:kSelectUHFTag];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(readUHFTagMemory:command:)) forKey:kReadUHFTagMemory];
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(writeUHFTagMemory:command:)) forKey:kWriteUHFTagMemory];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(setUHFTagLock:command:)) forKey:kSetUHFTagLock];
//        
//        [SUPPORTED_COMMAND setObject:NSStringFromSelector(@selector(killUHFTag:command:)) forKey:kKillUHFTag];
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

- (void)dealloc {
    SUPPORTED_COMMAND = nil;
}

@end
