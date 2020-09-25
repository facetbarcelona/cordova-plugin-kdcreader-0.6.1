//
//  KDelegate.h
//  KDelegate
//
//  Created by Koamtac on 4/01/19.
//  Copyright (c) 2019 AISolution. All rights reserved.
//

#import <Cordova/CDV.h>

@protocol KDelegate
@required

- (BOOL)isSupported: (NSString *)name;
- (void)execute: (NSString *)name delegate: (id <CDVCommandDelegate>)delegate command: (CDVInvokedUrlCommand*)command;
@end
