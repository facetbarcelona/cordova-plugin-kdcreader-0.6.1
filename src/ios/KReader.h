//
//  KReader.h
//  KReader
//
//  Created by Koamtac on 4/01/19.
//  Copyright (c) 2019 AISolution. All rights reserved.
//

#import <Cordova/CDV.h>

#import "KDCReader.h"

#import "KConverter.h"
#import "KDelegate.h"

@interface KReader : CDVPlugin
{}

@property (nonatomic, strong) KDCReader *reader;

@property (nonatomic, strong) NSString *connectionCallbackId;
@property (nonatomic, strong) NSString *barcodeCallbackId;
@property (nonatomic, strong) NSString *nfcCallbackId;
@property (nonatomic, strong) NSString *msrCallbackId;
@property (nonatomic, strong) NSString *dataCallbackId;
@property (nonatomic, strong) NSString *kposCallbackId;

@property (nonatomic, strong) NSMutableArray<id<KDelegate>> *DELEGATE;

@property (nonatomic, strong) KConverter *converter;

@property (nonatomic, strong) NSLock *lock;

- (void)isConnected:(CDVInvokedUrlCommand*)command;

- (void)setConnectionListener:(CDVInvokedUrlCommand*)command;
- (void)setBarcodeListener:(CDVInvokedUrlCommand*)command;
- (void)setNFCListener:(CDVInvokedUrlCommand*)command;
- (void)setMSRListener:(CDVInvokedUrlCommand*)command;
- (void)setDataListener:(CDVInvokedUrlCommand*)command;
- (void)setKPOSListener:(CDVInvokedUrlCommand*)command;

- (void)getAvailDeviceList:(CDVInvokedUrlCommand*)command;

- (void)enableAttachType:(CDVInvokedUrlCommand*)command;
- (void)enableAttachSerialNumber:(CDVInvokedUrlCommand*)command;
- (void)enableAttachTimestamp:(CDVInvokedUrlCommand*)command;

- (void)setDataDelimiter:(CDVInvokedUrlCommand*)command;
- (void)setRecordDelimiter:(CDVInvokedUrlCommand*)command;

// KCommonDelegate
- (void)connect:(CDVInvokedUrlCommand*)command;
- (void)disconnect:(CDVInvokedUrlCommand*)command;

- (void)softwareTrigger:(CDVInvokedUrlCommand*)command;


// KDataProcessDelegate
- (void)getDataPrefix:(CDVInvokedUrlCommand*)command;
- (void)setDataPrefix:(CDVInvokedUrlCommand*)command;

- (void)getDataSuffix:(CDVInvokedUrlCommand*)command;
- (void)setDataSuffix:(CDVInvokedUrlCommand*)command;

- (void)getSymbology:(CDVInvokedUrlCommand*)command;
- (void)setSymbology:(CDVInvokedUrlCommand*)command;
- (void)enableAllSymbology:(CDVInvokedUrlCommand*)command;
- (void)disableAllSymbology:(CDVInvokedUrlCommand*)command;

- (void)getBarcodeOption:(CDVInvokedUrlCommand*)command;
- (void)setBarcodeOption:(CDVInvokedUrlCommand*)command;
- (void)enableAllOptions:(CDVInvokedUrlCommand*)command;
- (void)disableAllOptions:(CDVInvokedUrlCommand*)command;

- (void)getNumberOfStoredBarcode:(CDVInvokedUrlCommand*)command;

- (void)getStoredDataSingle:(CDVInvokedUrlCommand*)command;

- (void)getDataProcessMode:(CDVInvokedUrlCommand*)command;
- (void)setDataProcessMode:(CDVInvokedUrlCommand*)command;

// KDisplayDelegate
- (void)setDisplayMessage:(CDVInvokedUrlCommand*)command;


// KSystemDelegate
- (void)enableButtonLock:(CDVInvokedUrlCommand*)command;
- (void)enableScanButtonLock:(CDVInvokedUrlCommand*)command;

- (void)eraseMemory:(CDVInvokedUrlCommand*)command;

- (void)getMemoryLeft:(CDVInvokedUrlCommand*)command;

- (void)getSleepTimeout:(CDVInvokedUrlCommand*)command;
- (void)setSleepTimeout:(CDVInvokedUrlCommand*)command;

- (void)getScanTimeout:(CDVInvokedUrlCommand*)command;
- (void)setScanTimeout:(CDVInvokedUrlCommand*)command;

- (void)getBatteryLevel:(CDVInvokedUrlCommand*)command;

- (void)syncSystemTime:(CDVInvokedUrlCommand*)command;
- (void)resetSystemTime:(CDVInvokedUrlCommand*)command;

- (void)setFactoryDefault:(CDVInvokedUrlCommand*)command;

// KUhfDelegate
- (void)isUHFPowerEnabled:(CDVInvokedUrlCommand*)command;
- (void)enableUHFPower:(CDVInvokedUrlCommand*)command;

- (void)getUHFPowerLevel:(CDVInvokedUrlCommand*)command;
- (void)setUHFPowerLevel:(CDVInvokedUrlCommand*)command;

- (void)getUHFReadMode:(CDVInvokedUrlCommand*)command;
- (void)setUHFReadMode:(CDVInvokedUrlCommand*)command;

- (void)getUHFReadTagMode:(CDVInvokedUrlCommand*)command;
- (void)setUHFReadTagMode:(CDVInvokedUrlCommand*)command;

- (void)getUHFDataType:(CDVInvokedUrlCommand*)command;
- (void)setUHFDataType:(CDVInvokedUrlCommand*)command;

- (void)isUHFBurstModeEnabled:(CDVInvokedUrlCommand*)command;
- (void)enableUHFBurstMode:(CDVInvokedUrlCommand*)command;

- (void)isUHFKeyEventEnabled:(CDVInvokedUrlCommand*)command;
- (void)enableUHFKeyEvent:(CDVInvokedUrlCommand*)command;

- (void)cancelUHFReading:(CDVInvokedUrlCommand*)command;

- (void)getUHFTagList:(CDVInvokedUrlCommand*)command;

- (void)selectUHFTag:(CDVInvokedUrlCommand*)command;

- (void)readUHFTagMemory:(CDVInvokedUrlCommand*)command;
- (void)writeUHFTagMemory:(CDVInvokedUrlCommand*)command;

- (void)setUHFTagLock:(CDVInvokedUrlCommand*)command;

- (void)killUHFTag:(CDVInvokedUrlCommand*)command;

@end
