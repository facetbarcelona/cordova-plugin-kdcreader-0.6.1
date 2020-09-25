/********* KReader.m Cordova Plugin Implementation *******/

#import <CoreBluetooth/CoreBluetooth.h>

#import "KReader.h"

#import "KConstants.h"

#import "KCommonDelegate.h"
#import "KBluetoothDelegate.h"
#import "KDataProcessDelegate.h"
#import "KSystemDelegate.h"
#import "KDisplayDelegate.h"
#import "KKeypadDelegate.h"
#import "KVibratorDelegate.h"
#import "KHidDelegate.h"
#import "KWiFiDelegate.h"
#import "KNfcDelegate.h"
#import "KMsrDelegate.h"
#import "KUhfDelegate.h"
#import "KPosDelegate.h"

@implementation KReader

//static FirebasePlugin *firebasePlugin;
//
//+ (FirebasePlugin *) firebasePlugin {
//    return firebasePlugin;
//}

- (void)pluginInitialize {
    NSLog(@"Starting KDCReader plugin");
//    firebasePlugin = self;
    
    // init reader
    self.reader = [[KDCReader alloc] init];
    [self.reader EnablePostNewDeviceArrived:YES];

    // init converter
    self.converter = [[KConverter alloc] init];
    
    // init lock
    self.lock = [[NSLock alloc] init];

    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(kdcConnectionChanged:) name:kdcConnectionChangedNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(kdcBarcodeDataArrived:) name:kdcBarcodeDataArrivedNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(kdcMSRDataArrived:) name:kdcMSRDataArrivedNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(kdcNFCDataArrived:) name:kdcNFCDataArrivedNotification object:nil];

    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(kdcDataArrived:) name:kdcDataArrivedNotification object:nil];
    
    // init delegate
    self.DELEGATE = [[NSMutableArray alloc] init];
    
    [self.DELEGATE addObject:[[KCommonDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];

    [self.DELEGATE addObject:[[KBluetoothDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    [self.DELEGATE addObject:[[KDataProcessDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    [self.DELEGATE addObject:[[KSystemDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    
    [self.DELEGATE addObject:[[KDisplayDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    
    [self.DELEGATE addObject:[[KKeypadDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    [self.DELEGATE addObject:[[KVibratorDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    
    [self.DELEGATE addObject:[[KHidDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    
    [self.DELEGATE addObject:[[KWiFiDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    
    [self.DELEGATE addObject:[[KNfcDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    [self.DELEGATE addObject:[[KMsrDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];

    [self.DELEGATE addObject:[[KUhfDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
    
    [self.DELEGATE addObject:[[KPosDelegate alloc] initWithKDCReader:self.reader lock:self.lock converter:self.converter]];
}

- (void) dispose {
    [super dispose];
    if ([self.reader IsConnected]) {
        [self.reader Disconnect];
    }
}

//************************************************************************
//
//  Notification from KDCReader when connection has been changed
//
//************************************************************************
- (void)kdcConnectionChanged:(NSNotification *)notification
{
    NSLog(@"%s",__FUNCTION__);
    
    if (self.connectionCallbackId != nil) {
        NSMutableDictionary *message = [[NSMutableDictionary alloc] init];
        
        if ( [notification userInfo] != nil ) {
            NSNumber *n = [[notification userInfo] objectForKey:keyConnectionState];
            
            if (n != nil) {
                [message setObject:n forKey:kState];
            }
            
            NSString *deviceName = nil;
            
            EAAccessory *accessory = [[notification userInfo] objectForKey:keyAccessory];
            CBPeripheral *peripheral = [[notification userInfo] objectForKey:keyPeripheral];

            if (accessory != nil) {
                @try {
                    deviceName = [[NSString alloc] initWithFormat:@"%@[%@]", accessory.modelNumber, [accessory.serialNumber substringFromIndex:4]];
                } @catch (NSException *e) {
                    deviceName = nil;
                }
            } else if (peripheral != nil) {
                deviceName = peripheral.name;
            }
            
            if (deviceName != nil) {
                NSMutableDictionary *device = [[NSMutableDictionary alloc] init];
                [device setObject:deviceName forKey:kDeviceName];
                [message setObject:device forKey:kDevice];
            }
        }
        
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];

        [pluginResult setKeepCallbackAsBool:YES];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.connectionCallbackId];
    }
}

//************************************************************************
//
//  Notification from KDCReader when data has been arrived from KDC
//
//************************************************************************
- (void)kdcDataArrived:(NSNotification *)notification
{
    NSLog(@"%s",__FUNCTION__);
    
    if (self.dataCallbackId != nil && [notification userInfo] != nil) {
        KDCData *data = [[notification userInfo] objectForKey:keyKDCData];

        NSDictionary *message = [self.converter fromKDCData:data];
        
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];
        
        [pluginResult setKeepCallbackAsBool:YES];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.dataCallbackId];
    }
}

//************************************************************************
//
//  Notification from KDCReader when barcode data has been arrived from KDC
//
//************************************************************************
- (void)kdcBarcodeDataArrived:(NSNotification *)notification
{
    NSLog(@"%s",__FUNCTION__);
    
    if (self.barcodeCallbackId != nil && [notification userInfo] != nil) {
        KDCData *data = [[notification userInfo] objectForKey:keyKDCData];

        NSDictionary *message = [self.converter fromKDCData:data];

        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];
        
        [pluginResult setKeepCallbackAsBool:YES];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.barcodeCallbackId];
    }
}

//************************************************************************
//
//  Notification from KDCReader when NFC data has been arrived from KDC
//
//************************************************************************
- (void)kdcNFCDataArrived:(NSNotification *)notification
{
    NSLog(@"%s",__FUNCTION__);
    
    if (self.nfcCallbackId != nil && [notification userInfo] != nil) {
        KDCData *data = [[notification userInfo] objectForKey:keyKDCData];

        NSDictionary *message = [self.converter fromKDCData:data];
        
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];
        
        [pluginResult setKeepCallbackAsBool:YES];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.nfcCallbackId];
    }
}

//************************************************************************
//
//  Notification from KDCReader when MSR data has been arrived from KDC
//
//************************************************************************
- (void)kdcMSRDataArrived:(NSNotification *)notification {
    NSLog(@"%s",__FUNCTION__);
    
    if (self.msrCallbackId != nil && [notification userInfo] != nil) {
        KDCData *data = [[notification userInfo] objectForKey:keyKDCData];

        NSDictionary *message = [self.converter fromKDCData:data];
        
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];
        
        [pluginResult setKeepCallbackAsBool:YES];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.msrCallbackId];
    }
}

- (id<KDelegate>) getDelegateByAction:(NSString *)name {
    for (id<KDelegate> delegate in self.DELEGATE) {
        if ([delegate isSupported:name]) {
            return delegate;
        }
    }
    
    return nil;
}

- (void)isConnected:(CDVInvokedUrlCommand*)command {
    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_OK
                                     messageAsBool:[self.reader IsConnected]];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setConnectionListener:(CDVInvokedUrlCommand*)command {
    self.connectionCallbackId = command.callbackId;
}

- (void)setBarcodeListener:(CDVInvokedUrlCommand*)command {
    self.barcodeCallbackId = command.callbackId;
}

- (void)setNFCListener:(CDVInvokedUrlCommand*)command {
    self.nfcCallbackId = command.callbackId;
}

- (void)setMSRListener:(CDVInvokedUrlCommand*)command {
    self.msrCallbackId = command.callbackId;
}

- (void)setDataListener:(CDVInvokedUrlCommand*)command {
    self.dataCallbackId = command.callbackId;
}

- (void)setKPOSListener:(CDVInvokedUrlCommand*)command {
    self.kposCallbackId = command.callbackId;
}

- (void)getAvailDeviceList:(CDVInvokedUrlCommand*)command {
    NSString *type = [command.arguments objectAtIndex:0];
    NSMutableArray *retDevices = [[NSMutableArray alloc] init];

    if (type != nil) {
        enum DeviceListType t = -1;
        
        if ([type isEqualToString:kExternalAccessory]) {
            t = EXTERNAL_ACCESSORY_LIST;
        } else if ([type isEqualToString:kScannedPeripheral]) {
            t = SCANNED_PERIPHERAL_LIST;
        } else if ([type isEqualToString:kConnectedPeripheral]) {
            t = CONNECTED_PERIPHERAL_LIST;
        }
        
        NSArray *list = [self.reader GetAvailableDeviceListEx:t options:nil];
        
        for (KDCDevice *device in list) {
            NSDictionary *dictionary = [self.converter fromKDCDevice:device];
            
            [retDevices addObject:dictionary];
        }
    }
    
    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_OK
                                     messageAsArray:retDevices];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)enableAttachType:(CDVInvokedUrlCommand*)command {
    BOOL isEnable = [[command.arguments objectAtIndex:0] boolValue];

    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_NO_RESULT];

    [self.reader EnableAttachType:isEnable];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)enableAttachSerialNumber:(CDVInvokedUrlCommand*)command {
    BOOL isEnable = [[command.arguments objectAtIndex:0] boolValue];
    
    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_NO_RESULT];
    
    [self.reader EnableAttachSerialNumber:isEnable];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)enableAttachTimestamp:(CDVInvokedUrlCommand*)command {
    BOOL isEnable = [[command.arguments objectAtIndex:0] boolValue];

    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_NO_RESULT];
    
    [self.reader EnableAttachTimestamp:isEnable];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setDataDelimiter:(CDVInvokedUrlCommand*)command {
    enum DataDelimiter delimiter = [[command.arguments objectAtIndex:0] integerValue];
    
    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_NO_RESULT];
    
    [self.reader SetDataDelimiter:delimiter];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setRecordDelimiter:(CDVInvokedUrlCommand*)command {
    enum RecordDelimiter delimiter = [[command.arguments objectAtIndex:0] integerValue];
    
    CDVPluginResult *pluginResult = [CDVPluginResult
                                     resultWithStatus:CDVCommandStatus_NO_RESULT];
    
    [self.reader SetRecordDelimiter:delimiter];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

// KCommonDelegate
- (void)connect:(CDVInvokedUrlCommand*)command {
    NSString *const action = kConnect;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }

    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)disconnect:(CDVInvokedUrlCommand*)command {
    NSString *const action = kDisconnect;

    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)softwareTrigger:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSoftwareTrigger;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

// KDataProcessDelegate
- (void)getDataPrefix:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetDataPrefix;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setDataPrefix:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetDataPrefix;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getDataSuffix:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetDataSuffix;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setDataSuffix:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetDataSuffix;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getSymbology:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetSymbology;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setSymbology:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetSymbology;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)enableAllSymbology:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableAllSymbology;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)disableAllSymbology:(CDVInvokedUrlCommand*)command {
    NSString *const action = kDisableAllSymbology;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getBarcodeOption:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetBarcodeOption;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setBarcodeOption:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetBarcodeOption;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)enableAllOptions:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableAllOptions;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)disableAllOptions:(CDVInvokedUrlCommand*)command {
    NSString *const action = kDisableAllOptions;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getNumberOfStoredBarcode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetNumberOfStoredBarcode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getStoredDataSingle:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetStoredDataSingle;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getDataProcessMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetDataProcessMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setDataProcessMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetDataProcessMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

// KDisplayDelegate
- (void)setDisplayMessage:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetDisplayMessage;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

// KSystemDelegate
- (void)enableButtonLock:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableButtonLock;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)enableScanButtonLock:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableScanButtonLock;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)eraseMemory:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEraseMemory;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getMemoryLeft:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetMemoryLeft;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getSleepTimeout:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetSleepTimeout;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setSleepTimeout:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetSleepTimeout;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getScanTimeout:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetScanTimeout;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setScanTimeout:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetScanTimeout;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getBatteryLevel:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetBatteryLevel;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)syncSystemTime:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSyncSystemTime;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)resetSystemTime:(CDVInvokedUrlCommand*)command {
    NSString *const action = kResetSystemTime;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setFactoryDefault:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetFactoryDefault;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

// KUhfDelegate
- (void)isUHFPowerEnabled:(CDVInvokedUrlCommand*)command {
    NSString *const action = kIsUHFPowerEnabled;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)enableUHFPower:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableUHFPower;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getUHFPowerLevel:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetUHFPowerLevel;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setUHFPowerLevel:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetUHFPowerLevel;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getUHFReadMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetUHFReadMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setUHFReadMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetUHFReadMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getUHFReadTagMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetUHFReadTagMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setUHFReadTagMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetUHFReadTagMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getUHFDataType:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetUHFDataType;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setUHFDataType:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetUHFDataType;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)isUHFBurstModeEnabled:(CDVInvokedUrlCommand*)command {
    NSString *const action = kIsUHFBurstModeEnabled;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)enableUHFBurstMode:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableUHFBurstMode;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)isUHFKeyEventEnabled:(CDVInvokedUrlCommand*)command {
    NSString *const action = kIsUHFKeyEventEnabled;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)enableUHFKeyEvent:(CDVInvokedUrlCommand*)command {
    NSString *const action = kEnableUHFKeyEvent;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)cancelUHFReading:(CDVInvokedUrlCommand*)command {
    NSString *const action = kCancelUHFReading;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)getUHFTagList:(CDVInvokedUrlCommand*)command {
    NSString *const action = kGetUHFTagList;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)selectUHFTag:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSelectUHFTag;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)readUHFTagMemory:(CDVInvokedUrlCommand*)command {
    NSString *const action = kReadUHFTagMemory;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)writeUHFTagMemory:(CDVInvokedUrlCommand*)command {
    NSString *const action = kWriteUHFTagMemory;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)setUHFTagLock:(CDVInvokedUrlCommand*)command {
    NSString *const action = kSetUHFTagLock;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

- (void)killUHFTag:(CDVInvokedUrlCommand*)command {
    NSString *const action = kKillUHFTag;
    
    id<KDelegate> delegate = [self getDelegateByAction:action];
    
    if (delegate == nil) {
        NSLog(@"Invalid Action: %@", action);
        return;
    }
    
    [delegate execute:action delegate:self.commandDelegate command:command];
}

@end
