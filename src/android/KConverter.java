package koamtac.kdc.sdk.plugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import koamtac.kdc.sdk.KDCBarcodeOption;
import koamtac.kdc.sdk.KDCConstants;
import koamtac.kdc.sdk.KDCData;
import koamtac.kdc.sdk.KDCDevice;
import koamtac.kdc.sdk.KDCDeviceInfo;
import koamtac.kdc.sdk.KDCSymbology;
import koamtac.kdc.sdk.KPOSData;
import koamtac.kdc.sdk.KPOSEMVApplication;
import koamtac.kdc.sdk.KPOSEMVTagList;

public final class KConverter {

    public static JSONObject toJson(KDCDeviceInfo info) throws JSONException {
        JSONObject json = new JSONObject();

        if (info != null) {
            json.put(KConstants.DeviceInfoKey.Name, info.GetModelName());

            json.put(KConstants.DeviceInfoKey.Firmware, info.GetFirmwareVersion());

            json.put(KConstants.DeviceInfoKey.SerialNumber, info.GetSerialNumber());

            json.put(KConstants.DeviceInfoKey.IsFlash, info.IsFlash());
            json.put(KConstants.DeviceInfoKey.IsBluetooth, info.IsBluetooth());
            json.put(KConstants.DeviceInfoKey.IsBarcode, info.IsBarcode());
            json.put(KConstants.DeviceInfoKey.Is2D, info.IsModel2D());
            json.put(KConstants.DeviceInfoKey.IsMSR, info.IsMSR());
            json.put(KConstants.DeviceInfoKey.IsNFC, info.IsNFC());
            json.put(KConstants.DeviceInfoKey.ISGPS, info.IsGPS());
            json.put(KConstants.DeviceInfoKey.IsUHF, info.IsUHF());
            json.put(KConstants.DeviceInfoKey.IsWiFi, info.IsWiFi());
            json.put(KConstants.DeviceInfoKey.IsKeypad, info.IsKeypad());
            json.put(KConstants.DeviceInfoKey.IsVibrator, info.IsVib());
            json.put(KConstants.DeviceInfoKey.IsDisplay, info.IsDisp());
            json.put(KConstants.DeviceInfoKey.IsPassportReader, info.IsPassportReader());
            json.put(KConstants.DeviceInfoKey.IsFingerPrint, info.IsFingerPrint());

            json.put(KConstants.DeviceInfoKey.IsPos, info.IsPOS());
        }

        return json;
    }

    public static JSONObject toJson(KDCDevice device) throws JSONException {
        JSONObject json = new JSONObject();

        if (device != null) {
            json.put(KConstants.DeviceKey.Type, device.GetType());
            json.put(KConstants.DeviceKey.SubType, device.GetSubType());

            json.put(KConstants.DeviceKey.DeviceName, device.GetDeviceName());

            json.put(KConstants.DeviceKey.KDCName, device.GetKdcName());
        }

        return json;
    }

    public static JSONObject toJson(KDCData data) throws JSONException {
        JSONObject json = new JSONObject();

        if (data != null) {
            json.put(KConstants.DataKey.Type, data.GetDataType().ordinal());

            // Data
            json.put(KConstants.DataKey.Data, data.GetData());
            json.put(KConstants.DataKey.DataBytes, toJsonArray(data.GetDataBytes())); // as byte array

            // Record
            json.put(KConstants.DataKey.Record, data.GetRecord());

            switch (data.GetDataType()) {
                case BARCODE:
                    json.put(KConstants.DataKey.Barcode, data.GetBarcodeData());
                    break;

                case MSR:
                    json.put(KConstants.DataKey.MSR, data.GetMSRData());
                    json.put(KConstants.DataKey.MSRBytes, toJsonArray(data.GetMSRDataBytes())); // as byte array
                    break;

                case NFC:
                    json.put(KConstants.DataKey.NFCTagType, data.GetNFCTagType().ordinal());
                    json.put(KConstants.DataKey.NFCUid, data.GetNFCUID());
                    json.put(KConstants.DataKey.NFC, data.GetNFCData());
                    json.put(KConstants.DataKey.NFCBytes, toJsonArray(data.GetNFCDataBytes())); // as byte array
                    break;

                case GPS:
                    json.put(KConstants.DataKey.GPS, data.GetGPSData());
                    break;

                case KEY_EVENT:
                    json.put(KConstants.DataKey.KeyEvent, data.GetKeyEvent());
                    break;

                case UHF_LIST:
                    json.put(KConstants.DataKey.UHFListType, data.GetUHFListDataType().ordinal());
                    json.put(KConstants.DataKey.UHFList, toJsonArray(data.GetUHFList()));
                    json.put(KConstants.DataKey.UHFRssiList, toJsonArray(data.GetUHFRssiList()));
                    break;
            }
        }

        return json;
    }

    public static JSONObject toJson(KPOSData data) throws JSONException {
        JSONObject json = new JSONObject();

        if (data != null) {
            json.put(KConstants.PosDataKey.CommandCode, data.GetCommandCode());
            json.put(KConstants.PosDataKey.EventCode, data.GetEventCode());
            json.put(KConstants.PosDataKey.ErrorCode, data.GetErrorCode());

            json.put(KConstants.PosDataKey.SerialNumber, data.GetDeviceSerialNumber());
            json.put(KConstants.PosDataKey.BatteryStatus, data.GetBatteryStatus());
            json.put(KConstants.PosDataKey.KeyEvent, (int)data.GetPressedKey());

            // Hex
            json.put(KConstants.PosDataKey.Data, toHexString(data.GetDataBytes()));

            json.put(KConstants.PosDataKey.Barcode, new String(data.GetBarcodeBytes()));

            json.put(KConstants.PosDataKey.NFC, data.GetNFCData());
            json.put(KConstants.PosDataKey.NFCUid, data.GetNFCUID());

            json.put(KConstants.PosDataKey.PinBlock, new String(data.GetPinBlockBytes()));

            json.put(KConstants.PosDataKey.KSNCardData, data.GetCardDataKSN());
            json.put(KConstants.PosDataKey.KSNPinBlock, data.GetPinBlockKSN());

            json.put(KConstants.PosDataKey.Track1, data.GetTrack1());
            json.put(KConstants.PosDataKey.Track2, data.GetTrack2());
            json.put(KConstants.PosDataKey.Track3, data.GetTrack3());

            json.put(KConstants.PosDataKey.EncryptSpec, data.GetEncryptionSpec());
            json.put(KConstants.PosDataKey.EncryptType, data.GetEncryptionType());

            json.put(KConstants.PosDataKey.EncryptedDataSize, data.GetEncryptedDataSize());

            json.put(KConstants.PosDataKey.maskedTrack1, data.GetMaskedTrack1());
            json.put(KConstants.PosDataKey.maskedTrack2, data.GetMaskedTrack2());
            json.put(KConstants.PosDataKey.maskedTrack3, data.GetMaskedTrack3());

            // Hex
            json.put(KConstants.PosDataKey.EncryptedTrack1, toHexString(data.GetEncryptedTrack1Bytes()));
            json.put(KConstants.PosDataKey.EncryptedTrack2, toHexString(data.GetEncryptedTrack2Bytes()));
            json.put(KConstants.PosDataKey.EncryptedTrack3, toHexString(data.GetEncryptedTrack3Bytes()));

            json.put(KConstants.PosDataKey.DigestType, data.GetDigestType());

            json.put(KConstants.PosDataKey.DigestTrack1, toHexString(data.GetTrack1DigestBytes()));
            json.put(KConstants.PosDataKey.DigestTrack2, toHexString(data.GetTrack2DigestBytes()));
            json.put(KConstants.PosDataKey.DigestTrack3, toHexString(data.GetTrack3DigestBytes()));

            json.put(KConstants.PosDataKey.IsAutoAppSelection, data.IsAutoAppSelection());

            json.put(KConstants.PosDataKey.EMVApplicationList, toJsonArray(data.GetEMVApplicationList()));

            json.put(KConstants.PosDataKey.EMVTagList, toJson(data.GetEMVTagList()));

            json.put(KConstants.PosDataKey.EMVResultCode, data.GetEMVResultCode());

            json.put(KConstants.PosDataKey.EMVFallbackType, data.GetEMVFallbackType());

            json.put(KConstants.PosDataKey.PosEntryMode, data.GetPOSEntryMode());

            json.put(KConstants.PosDataKey.EncryptedPAN, toHexString(data.GetEncryptedPANBytes()));

            json.put(KConstants.PosDataKey.DigestPAN, toHexString(data.GetPANDigestBytes()));
        }

        return json;
    }

    public static JSONObject toJson(KDCSymbology kdcSymbol) throws JSONException {
        JSONObject json = new JSONObject();

        if (kdcSymbol != null) {
            List<KDCConstants.Symbology> symbolList = kdcSymbol.GetAvailableSymbologies();

            for (KDCConstants.Symbology s : symbolList) {
                json.put(s.name(), kdcSymbol.IsEnabled(s));
            }
        }

        return json;
    }

    public static JSONObject toJson(KDCBarcodeOption kdcOption) throws JSONException {
        JSONObject json = new JSONObject();

        if (kdcOption != null) {
            List<KDCConstants.BarcodeOption> optionList = kdcOption.GetAvailableBarcodeOptions();

            for (KDCConstants.BarcodeOption o : optionList) {
                json.put(o.name(), kdcOption.IsEnabled(o));
            }
        }

        return json;
    }

    // put key,value into json object
    public static JSONObject put(JSONObject json, String key, boolean value) throws  JSONException {
        if (json == null) {
            json = new JSONObject();
        }

        json.put(key, value);

        return json;
    }

    public static JSONObject put(JSONObject json, String key, int value) throws  JSONException {
        if (json == null) {
            json = new JSONObject();
        }

        json.put(key, value);

        return json;
    }

    public static JSONObject put(JSONObject json, String key, String value) throws  JSONException{
        if (json == null) {
            json = new JSONObject();
        }

        json.put(key, value);

        return json;
    }

    public static JSONObject put(JSONObject json, String key, JSONObject value) throws  JSONException {
        if (json == null) {
            json = new JSONObject();
        }

        json.put(key, value);

        return json;
    }

    // put json object into json array
    public static <T> JSONArray put(JSONArray json, JSONObject object) {
        if (json == null) {
            json = new JSONArray();
        }

        json.put(object);

        return json;
    }


    // List to JSONArray
    public static <T> JSONArray toJsonArray(List<T> list) throws JSONException {
        JSONArray json = new JSONArray();

        if (list != null) {
            for (T item : list) {
                if (item instanceof KDCDevice) {
                    json.put(toJson((KDCDevice)item));
                } else {
                    json.put(item);
                }
            }
        }

        return json;
    }

    // Array to JSONArray
    private static <T> JSONArray toJsonArray(T [] array) throws JSONException {
        JSONArray json = new JSONArray();

        if (array != null) {
            for (T item : array) {
                if (item instanceof KPOSEMVApplication) {
                    json.put(toJson((KPOSEMVApplication) item));
                } else {
                    json.put(item);
                }
            }
        }

        return json;
    }

    private static JSONArray toJsonArray(byte [] array) {
        JSONArray json = new JSONArray();

        if (array != null) {
            for (byte b : array) {
                json.put(b & 0xFF);
            }
        }

        return json;
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        if (bytes != null) {
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF);

                if (hex.length() % 2 > 0) {
                    sb.append("0");
                    sb.append(hex);
                } else {
                    sb.append(hex);
                }
            }
        }

        return sb.toString();
    }

    private static JSONObject toJson(KPOSEMVApplication app) throws JSONException {
        JSONObject json = new JSONObject();

        if (app != null) {
            json.put(KConstants.PosDataKey.EMVAppKey.Index, app.GetIndex());
            json.put(KConstants.PosDataKey.EMVAppKey.Priority, app.GetPriority());
            json.put(KConstants.PosDataKey.EMVAppKey.Name, app.GetName());
        }

        return json;
    }

    private static JSONObject toJson(KPOSEMVTagList list) throws JSONException {
        JSONObject json = new JSONObject();

        if (list != null) {
            json.put(KConstants.PosDataKey.EMVTagKey.TLV, toHexString(list.GetTLVs()));
            json.put(KConstants.PosDataKey.EMVTagKey.Brand, list.GetICCardBrand());
        }

        return json;
    }

    public static byte[] toBytes(JSONArray array) throws JSONException {
        byte [] bytes = null;

        if (array != null && array.length() > 0) {
            bytes = new byte[array.length()];

            for (int i = 0 ; i < array.length() ; i++) {
                bytes[i] = (byte)(array.getInt(0) & 0xFF);
            }
        }

        return bytes;
    }
}
