//
//  KPosDelegate.h
//  KPosDelegate
//
//  Created by Koamtac on 4/01/19.
//  Copyright (c) 2014 AISolution. All rights reserved.
//

#import "KDelegate.h"
#import "KDCReader.h"
#import "KConverter.h"

@interface KPosDelegate : NSObject <KDelegate>

@property (nonatomic, weak) KDCReader *reader;
@property (nonatomic, weak) NSLock *lock;
@property (nonatomic, weak) KConverter *converter;

- (id)initWithKDCReader:(KDCReader *)reader lock:(NSLock *) lock converter:(KConverter *)converter;

- (BOOL)isSupported: (NSString *)name;
- (void)execute: (NSString *)name delegate: (id <CDVCommandDelegate>)delegate command: (CDVInvokedUrlCommand*)command;

@end

