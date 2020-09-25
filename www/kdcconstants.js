/**
 * @module KDCConstants
 */
module.exports = {
    /**
     * @enum {string}
     */
    DeviceListType: {
        /** Device List bonded(paired) using Bluetooth - Android only */
        BONDED_BLUETOOTH_LIST: "BondedBluetooth",

        /** Device List attached with using OTG - Android only  */
        ATTACHED_USB_LIST: "AttachedUsbDevice",

        /** Device List scanned through BLE */
        SCANNED_BLUETOOTH_LIST: "ScannedBluetooth",

        /** Device List connected as External Accessory - iOS only */
        EXTERNAL_ACCESSORY_LIST: "ExternalAccessory",

        /** Device List connected by other application  - iOS only */
        CONNECTED_PERIPHERAL_LIST: "ConnectedPeripheral"
    },

    /**
     * @enum {number}
     */
    ConnectionState: {
        /** Initial state or disconnected. */
        CONNECTION_STATE_NONE: 0,

        /** Bluetooth Classic Listen. */
        CONNECTION_STATE_LISTEN: 1,

        /** Connecting. */
        CONNECTION_STATE_CONNECTING: 2,

        /** Connection is established and initializing KDCReader is done. */
        CONNECTION_STATE_CONNECTED: 3,

        /** Connection is lost. */
        CONNECTION_STATE_LOST: 4,
        
        /** Connection is failed. */
        CONNECTION_STATE_FAILED: 5,
        
        CONNECTION_STATE_CONNECTED_XP67: 6,

        /** KDCReader is initializing. It is possible to use KDCReader after initialization is done. */
        CONNECTION_STATE_INITIALIZING: 7,

        /** Initializing KDCReader is failed. The connection will be disconnected. */
        CONNECTION_STATE_INITALIZING_FAILED: 8
    },    
    
    /**
     * @enum {number}
     */    
    DataType: {
        UNKNOWN: 0,

        BARCODE: 1,

        MSR: 2,

        NFC: 3,

        GPS: 4,

        KEY_EVENT: 5,

        UHF_LIST: 6,

        POS_MSR: 7,

        POS_PINBLOCK: 8,

        POS_EMV: 9,

        APP_DATA: 10
    },

    /**
     * @enum {number}
     */
    DataDelimiter: {
        NONE: 0,
        TAB: 1,
        SPACE: 2,
        COMMA: 3,
        SEMICOLON: 4
    },
    
    /**
     * @enum {number}
     */
    RecordDelimiter: {
        NONE: 0,
        LF: 1,
        CR: 2,
        TAB: 3,
        CRnLF: 4
    },    

    /**
     * @enum {number}
     */
    SleepTimeout: {
        DISABLED: 0,

        SECOND_1: 1,
        SECOND_2: 2,
        SECOND_3: 3,
        SECOND_4: 4,        
        SECOND_5: 5,        
        SECOND_10: 6,
        SECOND_20: 7,
        SECOND_30: 8,

        MINUTE_1: 9,
        MINUTE_2: 10,
        MINUTE_5: 11,
        MINUTE_10: 12
    }, 

    /**
     * @enum {number}
     */
    DataTerminator: {
        NONE: 0,
        CR: 1,
        LF: 2,
        CRLF: 3,
        TAB: 4,
        RIGHT_ARROW: 5,
        LEFT_ARROW: 6,
        DOWN_ARROW: 7,
        UP_ARROW: 8
    },
    
    /**
     * @enum {number}
     */
    DataFormat: {
        BARCODE_ONLY: 0,
        PACKET_DATA: 1,
    },

    /**
     * @enum {number}
     */
    DataProcess: {
        WEDGE_ONLY: 0,
        WEDGE_STORE_ALWAYS: 1,
        STORE_ONLY: 2,
        STORE_IF_SENT: 3,
        STORE_IF_NOT_SENT: 4
    },
    
    /**
     * @enum {number}
     */
    DeviceProfile: {
        SPP: 0,
        HID_IOS: 1,
        IPHONE: 2,
        SPP2_0: 3,
        HID_NORMAL: 4,        
    }, 

    /**
     * @enum {number}
     */
    BluetoothAutoPowerOnDelay: {
        DISABLED: 0,

        SECOND_1: 1,
        SECOND_2: 2,
        SECOND_3: 3,
        SECOND_4: 4, 
        SECOND_5: 5,
        SECOND_6: 6,
        SECOND_7: 7,
        SECOND_8: 8,
        SECOND_9: 9,
        SECOND_10: 10       
    }, 

    /**
     * @enum {number}
     */
    BluetoothAutoPowerOffDelay: {
        MINUTE_1: 1,
        MINUTE_2: 2,
        MINUTE_3: 3,
        MINUTE_4: 4,
        MINUTE_5: 5,
        MINUTE_6: 6,
        MINUTE_7: 7,
        MINUTE_8: 8,
        MINUTE_9: 9,
        MINUTE_10: 10,

        MINUTE_11: 11,
        MINUTE_12: 12,
        MINUTE_13: 13,
        MINUTE_14: 14,
        MINUTE_15: 15,
        MINUTE_16: 16,
        MINUTE_17: 17,
        MINUTE_18: 18,
        MINUTE_19: 19,
        MINUTE_20: 20,
        
        MINUTE_21: 21,
        MINUTE_22: 22,
        MINUTE_23: 23,
        MINUTE_24: 24,
        MINUTE_25: 25,
        MINUTE_26: 26,
        MINUTE_27: 27,
        MINUTE_28: 28,
        MINUTE_29: 29,
        MINUTE_30: 30        
    }, 

    /**
     * @enum {number}
     */
    PartialDataAction: {
        ERASE: 0,
        SELECT: 1,
    },

    /**
     * @enum {number}
     */
    AIMIDStatus: {
        NONE: 0,
        PREFIX: 1,
        SUFFIX: 2
    },

    /**
     * @enum {number}
     */
    RereadDelay: {
        CONTINUOUS: 0,
        SHORT: 1,
        MEDIUM: 2,
        LONG: 3,
        EXTRA_LONG: 4
    },
    
    /**
     * @enum {number}
     */
    NFCDataFormat: {
        PACKET: 0,
        DATA_ONLY: 1
    },    

    /**
     * @enum {number}
     */
    NFCTag: {
        NDEF_TYPE1: 0,
        NDEF_TYPE2: 1,
        RFID: 2,
        CALYPSO: 3,
        MIFARE_4K: 4,
        TYPE_A: 5,
        TYPE_B: 6,
        FELICA: 7,
        JEWEL: 8,
        MIFARE_1K: 9,
        MIFARE_UL_C: 10,
        MIFARE_UL: 11,
        MIFARE_DESFIRE: 12,
        ISO15693: 13,
        UNDEFINED: 14
    },    

    /**
     * @enum {number}
     */
    MSRTrack: {
        TRACK1: 1,
        TRACK2: 2,
        TRACK3: 4
    },     
    
    /**
     * @enum {number}
     */
    MSRTrackSeparator: {
        NONE: 0,
        SPACE: 1,
        COMMA: 2,
        SEMICOLON: 3,
        CR: 4,
        LF: 5,
        CRLF: 6,
        TAB: 7
    }, 

    /**
     * @enum {number}
     */
    MSRDataType: {
        PAYLOAD: 0,
        PACKET: 1,
    },  

    /**
     * @enum {number}
     */
    MSRDataEncryption: {
        NONE: 0,
        AES: 1,
    },  

    /**
     * @enum {number}
     */
    MSRCardType: {
        ISO: 0,
        OTHER_1: 1,
        AAMVA: 2
    }, 
    
    /**
     * @enum {number}
     */
    UHFReadMode: {
        NFC_RFID: 0,
        BARCODE: 1,
    }, 

    /**
     * @enum {number}
     */
    UHFReadTagMode: {
        SINGLE: 0,
        MULTIPLE: 1,
        ACTIVE: 2
    }, 

    /**
     * @enum {number}
     */
    UHFDataType: {
        EPC: 0,
        PC_EPC: 1,
        RSSI_EPC: 2,
        RSSI_PC_EPC: 3
    },     

    /**
     * @enum {number}
     */
    ResultCode: {
        SUCCESS: 0,
        FAILED: 1,
        NULL: 2,
        NOT_CONNECTED: 3
    },

};