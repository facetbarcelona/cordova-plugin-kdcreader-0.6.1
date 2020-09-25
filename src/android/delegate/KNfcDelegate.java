package koamtac.kdc.sdk.plugin.delegate;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import koamtac.kdc.sdk.KDCConstants;
import koamtac.kdc.sdk.KDCReader;

public class KNfcDelegate extends KReaderDelegate  {

    private static final String IS_NFC_INSTALLED = "isNFCInstalled";

    private static final String ENABLE_NFC_POWER = "enableNFCPower";
    private static final String IS_NFC_POWER_ENABLED = "isNFCPowerEnabled";

    private static final String SET_NFC_DATA_FORMAT = "setNFCDataFormat";
    private static final String GET_NFC_DATA_FORMAT = "getNFCDataFormat";

    private static final String ENABE_NFC_UID_ONLY = "enableNFCUIDOnly";
    private static final String IS_NFC_UID_ONLY_ENABLED = "isNFCUIDOnlyEnabled";

    private static final String ENABLE_NFC_EXTENDED_FORMAT = "enableNFCExtendedFormat";
    private static final String IS_NFC_EXTENDED_FORMAT_ENABLED = "isNFCExtendedFormatEnabled";


    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
        IS_NFC_INSTALLED,

        ENABLE_NFC_POWER,
        IS_NFC_POWER_ENABLED,

        SET_NFC_DATA_FORMAT,
        GET_NFC_DATA_FORMAT,

        ENABE_NFC_UID_ONLY,
        IS_NFC_UID_ONLY_ENABLED,

        ENABLE_NFC_EXTENDED_FORMAT,
        IS_NFC_EXTENDED_FORMAT_ENABLED
    );

    public KNfcDelegate(KDCReader reader) {
        wrReader = new WeakReference<>(reader);
    }

    @Override
    public boolean isSupported(String action) {
        return SUPPORTED_ACTIONS.contains(action);
    }

    @Override
    public boolean run(CordovaInterface cordova, String action, JSONArray args, CallbackContext cb) throws JSONException {
        boolean paramBool;
        int paramInt;
        String paramStr;
        JSONObject paramObject;

        switch (action) {
            case IS_NFC_INSTALLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isNFCInstalled(cb);
                    }
                });
                break;

            case ENABLE_NFC_POWER:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableNFCPower(paramBool, cb);
                    }
                });
                break;

            case IS_NFC_POWER_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isNFCPowerEnabled(cb);
                    }
                });
                break;

            case SET_NFC_DATA_FORMAT:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setNFCDataFormat(paramInt, cb);
                    }
                });
                break;

            case GET_NFC_DATA_FORMAT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getNFCDataFormat(cb);
                    }
                });
                break;

            case ENABE_NFC_UID_ONLY:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableNFCUIDOnly(paramBool, cb);
                    }
                });
                break;

            case IS_NFC_UID_ONLY_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isNFCUIDOnlyEnabled(cb);
                    }
                });
                break;

            case ENABLE_NFC_EXTENDED_FORMAT:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableNFCExtendedFormat(paramBool, cb);
                    }
                });
                break;

            case IS_NFC_EXTENDED_FORMAT_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isNFCExtendedFormatEnabled(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void isNFCInstalled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsNFCInstalled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableNFCPower(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableNFCPower(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isNFCPowerEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsNFCPowerEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void setNFCDataFormat(int format, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.NFCDataFormat f : KDCConstants.NFCDataFormat.values()) {
            if (format == f.ordinal()) {
                bRet = reader.SetNFCDataFormat(f);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getNFCDataFormat(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.NFCDataFormat format = reader.GetNFCDataFormat();

        if (format != null) {
            cb.success(format.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableNFCUIDOnly(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableNFCUIDOnly(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isNFCUIDOnlyEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsNFCUIDOnlyEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableNFCExtendedFormat(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableNFCExtendedFormat(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isNFCExtendedFormatEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsNFCExtendedFormatEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }
}

