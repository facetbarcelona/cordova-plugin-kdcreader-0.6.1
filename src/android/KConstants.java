package koamtac.kdc.sdk.plugin;

public final class KConstants {
    static class ConnectionKey {
        static final String State = "state";
    }

    public static class DeviceInfoKey {
        static final String Name = "name";

        static final String Firmware = "firmware";

        static final String SerialNumber = "serial";

        static final String IsFlash = "isFlash";
        static final String IsBluetooth = "isBluetooth";
        static final String IsBarcode = "isBarcode";
        static final String Is2D = "is2D";
        static final String IsMSR = "isMSR";
        static final String IsNFC = "isNFC";
        static final String ISGPS = "isGPS";
        static final String IsUHF = "isUHF";
        static final String IsWiFi = "isWiFi";
        static final String IsKeypad = "isKeypad";
        static final String IsVibrator = "isVibrator";
        static final String IsDisplay = "isDisplay";
        static final String IsPassportReader = "isPassportReader";
        static final String IsFingerPrint = "isFingerPrint";

        static final String IsPos = "isPOS";
    }

    public static class DeviceKey {
        public static final String DeviceName = "deviceName";

        static final String Device = "device";

        static final String Type = "type";
        static final String SubType = "subType";
        static final String KDCName = "kdcName";
    }

    static class DataKey {
        static final String Type = "type";

        static final String Data = "data";
        static final String DataBytes = "dataBytes";

        static final String Barcode = "barcode";

        static final String GPS = "gps";

        static final String MSR = "msr";
        static final String MSRBytes = "msrBytes";

        static final String NFCTagType = "nfcType";
        static final String NFCUid = "nfcUid";
        static final String NFC = "nfc";
        static final String NFCBytes = "nfcBytes";

        static final String KeyEvent = "key";

        static final String UHFListType = "uhfListType";
        static final String UHFList = "uhfList";
        static final String UHFRssiList = "uhfRssiList";

        static final String Record = "record";
    }

    static class PosDataKey {
        static final String CommandCode = "commandCode";
        static final String EventCode = "eventCode";
        static final String ErrorCode = "errorCode";

        static final String SerialNumber = "serial";

        static final String BatteryStatus = "battery";

        static final String KeyEvent = "key";

        static final String Data = "data";

        static final String Barcode = "barcode";

        static final String NFC = "nfc";
        static final String NFCUid = "uid";

        static final String PinBlock = "pinBlock";

        static final String KSNCardData = "ksnData";
        static final String KSNPinBlock = "ksnPinBlock";

        static final String Track1 = "track1";
        static final String Track2 = "track2";
        static final String Track3 = "track3";

        static final String EncryptSpec = "encryptSpec";
        static final String EncryptType = "encryptType";

        static final String EncryptedDataSize = "encryptedDataLength";

        static final String maskedTrack1 = "maskedTrack1";
        static final String maskedTrack2 = "maskedTrack2";
        static final String maskedTrack3 = "maskedTrack3";

        static final String EncryptedTrack1 = "encryptedTrack1";
        static final String EncryptedTrack2 = "encryptedTrack2";
        static final String EncryptedTrack3 = "encryptedTrack3";

        static final String DigestType = "digestType";

        static final String DigestTrack1 = "digestTrack1";
        static final String DigestTrack2 = "digestTrack2";
        static final String DigestTrack3 = "digestTrack3";

        static final String IsAutoAppSelection = "isAutoAppSelection";

        static final String EMVApplicationList = "emvApps";

        static final String EMVTagList = "emvTagList";

        static final String EMVResultCode = "emvResultCode";

        static final String EMVFallbackType = "emvFallbackType";

        static final String PosEntryMode = "posEntryMode";

        static final String EncryptedPAN = "encryptedPAN";
        static final String DigestPAN = "digestPAN";

        public static class EMVTagKey {
            static final String TLV = "tlv";
            static final String Brand = "brand";
        }

        public static class EMVAppKey {
            static final String Index = "index";
            static final String Priority = "priority";
            static final String Name = "name";
        }
    }

//    public static class ProcessKey {
//        public static final String Prefix = "prefix";
//        public static final String Suffix = "suffix";
//    }
//
//    public static class SystemKey {
//        public static final String Memory = "memory";
//        public static final String Timeout = "timeout";
//    }

    public static class UHFKey {
        public static final String Level = "level";
        public static final String Mode = "mode";
        public static final String Type = "type";
        public static final String Format = "format";
        public static final String Enalbe = "enable";
    }
}
