//
//  KDataProcessDelegate.h
//  KDataProcessDelegate
//
//  Created by Koamtac on 4/01/19.
//  Copyright (c) 2019 AISolution. All rights reserved.
//

#import "KDelegate.h"
#import "KDCReader.h"
#import "KConverter.h"

extern NSString * const kGetDataPrefix;
extern NSString * const kSetDataPrefix;

extern NSString * const kGetDataSuffix;
extern NSString * const kSetDataSuffix;

extern NSString * const kGetSymbology;
extern NSString * const kSetSymbology;
extern NSString * const kEnableAllSymbology;
extern NSString * const kDisableAllSymbology;

extern NSString * const kGetBarcodeOption;
extern NSString * const kSetBarcodeOption;
extern NSString * const kEnableAllOptions;
extern NSString * const kDisableAllOptions;

extern NSString * const kGetNumberOfStoredBarcode;

extern NSString * const kGetStoredDataSingle;

extern NSString * const kGetDataProcessMode;
extern NSString * const kSetDataProcessMode;

@interface KDataProcessDelegate : NSObject <KDelegate>

@property (nonatomic, weak) KDCReader *reader;
@property (nonatomic, weak) NSLock *lock;
@property (nonatomic, weak) KConverter *converter;

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *)converter;

- (BOOL)isSupported: (NSString *)name;
- (void)execute: (NSString *)name delegate: (id <CDVCommandDelegate>)delegate command: (CDVInvokedUrlCommand*)command;

@end

