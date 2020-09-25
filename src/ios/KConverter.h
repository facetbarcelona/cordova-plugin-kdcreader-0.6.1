//
//  KConverter.h
//  KConverter
//
//  Created by Koamtac on 4/01/19.
//  Copyright (c) 2019 AISolution. All rights reserved.
//

#import <Cordova/CDV.h>
#import "KDCReader.h"

@interface KConverter : NSObject

@property (nonatomic, strong) NSDictionary * eSuccess;
@property (nonatomic, strong) NSDictionary * eFailed;
@property (nonatomic, strong) NSDictionary * eNull;
@property (nonatomic, strong) NSDictionary * eNotConnected;

-(NSDictionary *)fromKDCDevice:(KDCDevice *) device;
-(NSDictionary *)fromKDCData: (KDCData *) data;
@end
