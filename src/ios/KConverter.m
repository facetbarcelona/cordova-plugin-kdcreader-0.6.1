
#import "KConverter.h"
#import "KConstants.h"

enum cDataType {
    CORDOVA_UNKNOWN,
    CORDOVA_BARCODE,
    CORDOVA_MSR,
    CORDOVA_NFC,
    CORDOVA_GPS,
    CORDOVA_KEY_EVENT,
    CORDOVA_UHF_LIST,
    CORDOVA_POS_MSR,
    CORDOVA_POS_PINBLOCK,
    CORDOVA_POS_EMV,
    CORDOVA_APP_DATA
};

@implementation KConverter
{
    NSMutableDictionary *dataTypeTable;
}

- (id) init {
    self = [super init];
    
    if (self) {
        dataTypeTable = [[NSMutableDictionary alloc] init];
        
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_UNKNOWN] forKey:[[NSNumber alloc] initWithInt:UNKNOWN]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_BARCODE] forKey:[[NSNumber alloc] initWithInt:BARCODE]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_MSR] forKey:[[NSNumber alloc] initWithInt:MSR]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_GPS] forKey:[[NSNumber alloc] initWithInt:GPS]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_NFC] forKey:[[NSNumber alloc] initWithInt:NFC_OLD]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_NFC] forKey:[[NSNumber alloc] initWithInt:NFC_NEW]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_APP_DATA] forKey:[[NSNumber alloc] initWithInt:APPLICATION_DATA]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_KEY_EVENT] forKey:[[NSNumber alloc] initWithInt:KEY_EVENT]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_NFC] forKey:[[NSNumber alloc] initWithInt:NFC]];
        [dataTypeTable setObject:[[NSNumber alloc] initWithInt:CORDOVA_UHF_LIST] forKey:[[NSNumber alloc] initWithInt:UHF_LIST]];
        
        self.eSuccess = @{ @"code": @0, @"message": @"The operation is succeeded." };
        self.eFailed = @{ @"code": @1, @"message": @"The operation is failed." };
        self.eNull = @{ @"code": @2, @"message": @"KDCReader is null." };
        self.eNotConnected = @{ @"code": @3, @"message": @"KDC Device is not connected." };
    }
    
    return self;
}

- (NSDictionary *)fromKDCDevice:(KDCDevice *)device {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];

    if (device != nil) {
        [dictionary setObject:device.type forKey:kDeviceType];
        [dictionary setObject:device.subtype forKey:kDeviceSubtype];

        [dictionary setObject:device.name forKey:kDeviceName];
        [dictionary setObject:device.name forKey:kKDCName];
    }
    
    return dictionary;
}

- (NSDictionary *)fromKDCData:(KDCData *)data {
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    
    if (data != nil) {
        // Data Type
        NSNumber *dataType = [dataTypeTable objectForKey:[[NSNumber alloc] initWithInt:[data GetDataType]]];
        [dictionary setObject:dataType forKey:kDataType];
        
        // Data
        [dictionary setObject:[data GetData] forKey:kData];
        
        { // as byte array
            NSMutableArray * array = [[NSMutableArray alloc] init];
            
            for (int i = 0 ; i < [data GetDataBytesLength] ; i++) {
                uint8_t * d = [data GetDataBytes];
                
                [array addObject: [[NSNumber alloc] initWithInt:*(d + i)]];
            }
            
            [dictionary setObject:array forKey:kDataBytes];
        }
        
        // Record
        [dictionary setObject:[data GetRecord] forKey:kRecord];
        
        switch( [dataType intValue]) {
            case CORDOVA_BARCODE:
                [dictionary setObject:[data GetBarcodeData] forKey:kBarcode];
                break;

            case CORDOVA_MSR:
                [dictionary setObject:[data GetMSRData] forKey:kMSR];
                
                if ([data GetMSRDataBytesLength] > 0) { // as byte array
                    NSMutableArray * array = [[NSMutableArray alloc] init];
                    
                    for (int i = 0 ; i < [data GetMSRDataBytesLength] ; i++) {
                        uint8_t * d = [data GetMSRDataBytes];
                        
                        [array addObject: [[NSNumber alloc] initWithInt:*(d + i)]];
                    }
                    
                    [dictionary setObject:array forKey:kDataBytes];
                }
                break;
                
            case CORDOVA_NFC:
                [dictionary setObject:[[NSNumber alloc] initWithInt:[data GetNFCTagType]] forKey:kNFCTagType];
                [dictionary setObject:[data GetNFCUID] forKey:kNFCUid];
                
                [dictionary setObject:[data GetNFCData] forKey:kNFC];
                
                if ([data GetNFCDataBytesLength] > 0) { // as byte array
                    NSMutableArray * array = [[NSMutableArray alloc] init];
                    
                    for (int i = 0 ; i < [data GetNFCDataBytesLength] ; i++) {
                        uint8_t * d = [data GetNFCDataBytes];
                        
                        [array addObject: [[NSNumber alloc] initWithInt:*(d + i)]];
                    }
                    
                    [dictionary setObject:array forKey:kDataBytes];
                }
                break;
                
            case CORDOVA_GPS:
                [dictionary setObject:[data GetGPSData] forKey:kGPS];
                break;

            case CORDOVA_KEY_EVENT:
                [dictionary setObject:[data GetKeyEvent] forKey:kKeyEvent];
                break;
                
            case CORDOVA_UHF_LIST: // UHF List - Burst Mode
                [dictionary setObject:[[NSNumber alloc] initWithInt:[data GetUHFListDataType]] forKey:kUHFListType];
                [dictionary setObject:[data GetUHFList] forKey:kUHFList];
                [dictionary setObject:[data GetUHFRssiList] forKey:kRSSIList];
                break;
        }
    }
    
    return dictionary;
}

- (void) dealloc {
    dataTypeTable = nil;
}

@end
