//
//  KUhfDelegate.h
//  KUhfDelegate
//
//  Created by Koamtac on 4/01/19.
//  Copyright (c) 2019 AISolution. All rights reserved.
//

#import "KDelegate.h"
#import "KDCReader.h"
#import "KConverter.h"

extern NSString * const kIsUHFPowerEnabled;
extern NSString * const kEnableUHFPower;

extern NSString * const kGetUHFPowerLevel;
extern NSString * const kSetUHFPowerLevel;

extern NSString * const kGetUHFReadMode;
extern NSString * const kSetUHFReadMode;

extern NSString * const kGetUHFReadTagMode;
extern NSString * const kSetUHFReadTagMode;

extern NSString * const kGetUHFDataType;
extern NSString * const kSetUHFDataType;

extern NSString * const kIsUHFBurstModeEnabled;
extern NSString * const kEnableUHFBurstMode;

extern NSString * const kIsUHFKeyEventEnabled;
extern NSString * const kEnableUHFKeyEvent;

extern NSString * const kCancelUHFReading;

extern NSString * const kGetUHFTagList;

extern NSString * const kSelectUHFTag;

extern NSString * const kReadUHFTagMemory;
extern NSString * const kWriteUHFTagMemory;

extern NSString * const kSetUHFTagLock;

extern NSString * const kKillUHFTag;

@interface KUhfDelegate : NSObject <KDelegate>

@property (nonatomic, weak) KDCReader *reader;
@property (nonatomic, weak) NSLock *lock;
@property (nonatomic, weak) KConverter *converter;

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *)converter;

- (BOOL)isSupported: (NSString *)name;
- (void)execute: (NSString *)name delegate: (id <CDVCommandDelegate>)delegate command: (CDVInvokedUrlCommand*)command;

@end
