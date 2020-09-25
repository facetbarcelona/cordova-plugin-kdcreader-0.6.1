package koamtac.kdc.sdk.plugin.delegate;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import koamtac.kdc.sdk.KDCBarcodeOption;
import koamtac.kdc.sdk.KDCConstants;
import koamtac.kdc.sdk.KDCReader;
import koamtac.kdc.sdk.KDCSymbology;
import koamtac.kdc.sdk.plugin.KConstants;
import koamtac.kdc.sdk.plugin.KConverter;

public class KDataProcessDelegate extends KReaderDelegate {
    private static final String GET_DATA_PREFIX = "getDataPrefix";
    private static final String SET_DATA_PREFIX = "setDataPrefix";

    private static final String GET_DATA_SUFFIX = "getDataSuffix";
    private static final String SET_DATA_SUFFIX = "setDataSuffix";

    private static final String GET_SYMBOLOGY = "getSymbology";
    private static final String SET_SYMBOLOGY = "setSymbology";
    private static final String ENABLE_ALL_SYMBOLOGY = "enableAllSymbology";
    private static final String DISABLE_ALL_SYMBOLOGY = "disableAllSymbology";

    private static final String GET_BARCODE_OPTION = "getBarcodeOption";
    private static final String SET_BARCODE_OPTION = "setBarcodeOption";
    private static final String ENABLE_ALL_OPTIONS = "enableAllOptions";
    private static final String DISABLE_ALL_OPTIONS = "disableAllOptions";

    private static final String GET_MINIMUM_BARCODE_LENGTH = "getMinimumBarcodeLength";
    private static final String SET_MINIMUM_BARCODE_LENGTH = "setMinimumBarcodeLength";

    private static final String GET_NUMBER_OF_STORED_BARCODE = "getNumberOfStoredBarcode";

    private static final String GET_STORED_DATA_SINGLE = "getStoredDataSingle";

    private static final String GET_DATA_PROCESS_MODE = "getDataProcessMode";
    private static final String SET_DATA_PROCESS_MODE = "setDataProcessMode";

    private static final String GET_SECURITY_LEVEL = "getSecurityLevel";
    private static final String SET_SECURITY_LEVEL = "setSecurityLevel";

    private static final String ENABLE_AGE_VERIFY = "enableAgeVerify";
    private static final String IS_AGE_VERIFY_ENABLED = "isAgeVerifyEnabled";

    private static final String GET_AIMID_SETTING = "getAIMIDSetting";
    private static final String SET_AIMID_SETTING = "setAIMIDSetting";

    private static final String GET_PARTIAL_DATA_START_POSITION = "getPartialDataStartPosition";
    private static final String SET_PARTIAL_DATA_START_POSITION = "setPartialDataStartPosition";

    private static final String GET_PARTIAL_DATA_LENGTH = "getPartialDataLength";
    private static final String SET_PARTIAL_DATA_LENGTH = "setPartialDatalength";

    private static final String GET_PARTIAL_DATA_ACTION = "getPartialDataAction";
    private static final String SET_PARTIAL_DATA_ACTION = "setPartialDataAction";

    private static final String START_SYNCHRONIZATION = "startSynchronization";
    private static final String FINISH_SYNCHRONIZATION = "finishSynchronization";

    private static final String GET_DATA_TERMINATOR = "getDataTerminator";
    private static final String SET_DATA_TERMINATOR = "setDataTeminator";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            GET_DATA_PREFIX,
            SET_DATA_PREFIX,

            GET_DATA_SUFFIX,
            SET_DATA_SUFFIX,

            GET_SYMBOLOGY,
            SET_SYMBOLOGY,
            ENABLE_ALL_SYMBOLOGY,
            DISABLE_ALL_SYMBOLOGY,

            GET_BARCODE_OPTION,
            SET_BARCODE_OPTION,
            ENABLE_ALL_OPTIONS,
            DISABLE_ALL_OPTIONS,

            GET_MINIMUM_BARCODE_LENGTH,
            SET_MINIMUM_BARCODE_LENGTH,

            GET_NUMBER_OF_STORED_BARCODE,

            GET_STORED_DATA_SINGLE,

            GET_DATA_PROCESS_MODE,
            SET_DATA_PROCESS_MODE,

            GET_SECURITY_LEVEL,
            SET_SECURITY_LEVEL,

            ENABLE_AGE_VERIFY,
            IS_AGE_VERIFY_ENABLED,

            GET_AIMID_SETTING,
            SET_AIMID_SETTING,

            GET_PARTIAL_DATA_START_POSITION,
            SET_PARTIAL_DATA_START_POSITION,

            GET_PARTIAL_DATA_LENGTH,
            SET_PARTIAL_DATA_LENGTH,

            GET_PARTIAL_DATA_ACTION,
            SET_PARTIAL_DATA_ACTION,

            START_SYNCHRONIZATION,
            FINISH_SYNCHRONIZATION,

            GET_DATA_TERMINATOR,
            SET_DATA_TERMINATOR
    );

    public KDataProcessDelegate(KDCReader reader) {
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
            case GET_DATA_PREFIX:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getDataPrefix(cb);
                    }
                });
                break;

            case SET_DATA_PREFIX:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDataPrefix(paramStr, cb);
                    }
                });
                break;

            case GET_DATA_SUFFIX:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getDataSuffix(cb);
                    }
                });
                break;

            case SET_DATA_SUFFIX:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDataSuffix(paramStr, cb);
                    }
                });
                break;

            case GET_SYMBOLOGY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getSymbology(cb);
                    }
                });
                break;

            case SET_SYMBOLOGY:
                paramObject = args.getJSONObject(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setSymbology(paramObject, cb);
                    }
                });
                break;

            case ENABLE_ALL_SYMBOLOGY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableAllSymbology(cb);
                    }
                });
                break;

            case DISABLE_ALL_SYMBOLOGY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        disableAllSymbology(cb);
                    }
                });
                break;

            case GET_BARCODE_OPTION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getBarcodeOption(cb);
                    }
                });
                break;

            case SET_BARCODE_OPTION:
                paramObject = args.getJSONObject(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setBarcodeOption(paramObject, cb);
                    }
                });
                break;

            case ENABLE_ALL_OPTIONS:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableAllOptions(cb);
                    }
                });
                break;

            case DISABLE_ALL_OPTIONS:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        disableAllOptions(cb);
                    }
                });
                break;

            case GET_MINIMUM_BARCODE_LENGTH:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMinimumBarcodeLength(cb);
                    }
                });
                break;

            case SET_MINIMUM_BARCODE_LENGTH:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMinimumBarcodeLength(paramInt, cb);
                    }
                });
                break;

            case GET_NUMBER_OF_STORED_BARCODE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getNumberOfStoredBarcode(cb);
                    }
                });
                break;

            case GET_STORED_DATA_SINGLE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getStoredDataSingle(cb);
                    }
                });
                break;

            case GET_DATA_PROCESS_MODE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getDataProcessMode(cb);
                    }
                });
                break;

            case SET_DATA_PROCESS_MODE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDataProcessMode(paramInt, cb);
                    }
                });
                break;

            case GET_SECURITY_LEVEL:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getSecurityLevel(cb);
                    }
                });
                break;

            case SET_SECURITY_LEVEL:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setSecurityLevel(paramInt, cb);
                    }
                });
                break;

            case ENABLE_AGE_VERIFY:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableAgeVerify(paramBool, cb);
                    }
                });
                break;

            case IS_AGE_VERIFY_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isAgeVerifyEnabled(cb);
                    }
                });
                break;

            case GET_AIMID_SETTING:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getAIMIDSetting(cb);
                    }
                });
                break;

            case SET_AIMID_SETTING:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setAIMIDSetting(paramInt, cb);
                    }
                });
                break;

            case GET_PARTIAL_DATA_START_POSITION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getPartialDataStartPosition(cb);
                    }
                });
                break;

            case SET_PARTIAL_DATA_START_POSITION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setPartialDataStartPosition(paramInt, cb);
                    }
                });
                break;

            case GET_PARTIAL_DATA_LENGTH:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getPartialDataLength(cb);
                    }
                });
                break;

            case SET_PARTIAL_DATA_LENGTH:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setPartialDatalength(paramInt, cb);
                    }
                });
                break;

            case GET_PARTIAL_DATA_ACTION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getPartialDataAction(cb);
                    }
                });
                break;

            case SET_PARTIAL_DATA_ACTION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setPartialDataAction(paramInt, cb);
                    }
                });
                break;

            case START_SYNCHRONIZATION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        startSynchronization(cb);
                    }
                });
                break;

            case FINISH_SYNCHRONIZATION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        finishSynchronization(cb);
                    }
                });
                break;

            case GET_DATA_TERMINATOR:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getDataTerminator(cb);
                    }
                });
                break;

            case SET_DATA_TERMINATOR:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDataTeminator(paramInt, cb);
                    }
                });
                break;
        }

        return true;
    }

    private void getDataPrefix(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String prefix = reader.GetDataPrefix();

        PluginResult result = new PluginResult(PluginResult.Status.OK, prefix != null? prefix : "");

        cb.sendPluginResult(result);
    }

    private void setDataPrefix(String prefix, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if (reader.SetDataPrefix(prefix)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getDataSuffix(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String suffix = reader.GetDataSuffix();

        PluginResult result = new PluginResult(PluginResult.Status.OK, suffix != null ? suffix : "");

        cb.sendPluginResult(result);
    }

    private void setDataSuffix(String suffix, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if (reader.SetDataSuffix(suffix)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getSymbology(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;
        KDCSymbology symbol = reader.GetSymbology();

        if (symbol != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(symbol));
            } catch (JSONException e) {
                e.printStackTrace();
                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setSymbology(JSONObject json, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        Map<String, Boolean> map = new HashMap<>();

        try {
            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                map.put(key, json.getBoolean(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            cb.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
            return;
        }

        boolean bRet = false;
        KDCSymbology symbol = reader.GetSymbology();

        if (symbol != null) {
            Set<String> keys = map.keySet();

            for (String k : keys) {
                try {
                    KDCConstants.Symbology s = KDCConstants.Symbology.valueOf(k);
                    symbol.Enable(s, map.get(k));
                } catch (IllegalArgumentException e) {
                    // do nothing
                }
            }

            bRet = reader.SetSymbology(symbol, reader.GetKDCDeviceInfo());
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableAllSymbology(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if (reader.EnableAllSymbologies()) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void disableAllSymbology(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if (reader.DisableAllSymbologies()) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getBarcodeOption(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;
        KDCBarcodeOption option = reader.GetBarcodeOption();

        if (option != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(option));
            } catch (JSONException e) {
                e.printStackTrace();
                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setBarcodeOption(JSONObject json, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        Map<String, Boolean> map = new HashMap<>();

        try {
            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                map.put(key, json.getBoolean(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            cb.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
            return;
        }

        boolean bRet = false;
        KDCBarcodeOption option = reader.GetBarcodeOption();

        if (option != null) {
            Set<String> keys = map.keySet();

            for (String k : keys) {
                try {
                    KDCConstants.BarcodeOption o = KDCConstants.BarcodeOption.valueOf(k);
                    option.Enable(o, map.get(k));
                } catch (IllegalArgumentException e) {
                    // do nothing
                }
            }

            bRet = reader.SetBarcodeOption(option, reader.GetKDCDeviceInfo());
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableAllOptions(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if (reader.EnableAllOptions()) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void disableAllOptions(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if (reader.DisableAllOptions()) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getMinimumBarcodeLength(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int length = reader.GetMinimumBarcodeLength();

        if (length != -1) {
            cb.success(length);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMinimumBarcodeLength(int length, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetMinimumBarcodeLength(length);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getNumberOfStoredBarcode(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int size = reader.GetNumberOfStoredBarcode();

        PluginResult result = new PluginResult(PluginResult.Status.OK, size);

        cb.sendPluginResult(result);
    }

    private void getStoredDataSingle(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        reader.GetStoredDataSingle();

        cb.success(error.SUCCESS);
    }

    private void getDataProcessMode(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.DataProcess mode = reader.GetDataProcessMode();
        PluginResult result = (mode != null) ? new PluginResult(PluginResult.Status.OK, mode.ordinal()) : null;

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setDataProcessMode(int mode, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for(KDCConstants.DataProcess process : KDCConstants.DataProcess.values()) {
            if (mode == process.ordinal()) {
                bRet = reader.SetDataProcessMode(process);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getSecurityLevel(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int level = reader.GetSecurityLevel();

        if (level != -1) {
            cb.success(level);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setSecurityLevel(int level, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetSecurityLevel(level);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableAgeVerify(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableAgeVerify(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isAgeVerifyEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsAgeVerifyEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void getAIMIDSetting(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.AIMIDStatus status = reader.GetAIMIDSetting();

        PluginResult result = (status != null) ? new PluginResult(PluginResult.Status.OK, status.ordinal()) : null;

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setAIMIDSetting(int status, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for(KDCConstants.AIMIDStatus s : KDCConstants.AIMIDStatus.values()) {
            if (status == s.ordinal()) {
                bRet = reader.SetAIMIDSetting(s);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getPartialDataStartPosition(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int length = reader.GetPartialDataStartPosition();

        if (length != -1) {
            cb.success(length);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setPartialDataStartPosition(int position, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetPartialDataStartPosition(position);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getPartialDataLength(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int length = reader.GetPartialDataLength();

        if (length != -1) {
            cb.success(length);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setPartialDatalength(int length, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetPartialDataLength(length);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getPartialDataAction(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.PartialDataAction action = reader.GetPartialDataAction();

        PluginResult result = (action != null) ? new PluginResult(PluginResult.Status.OK, action.ordinal()) : null;

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setPartialDataAction(int action, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for(KDCConstants.PartialDataAction a : KDCConstants.PartialDataAction.values()) {
            if (action == a.ordinal()) {
                bRet = reader.SetPartialDataAction(a);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void startSynchronization(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.StartSynchronization();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void finishSynchronization(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.FinishSynchronization();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getDataTerminator(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.DataTerminator terminator = reader.GetDataTerminator();

        if (terminator != null) {
            cb.success(terminator.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setDataTeminator(int terminator, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for(KDCConstants.DataTerminator t : KDCConstants.DataTerminator.values()) {
            if (terminator == t.ordinal()) {
                bRet = reader.SetDataTerminator(t);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }
}
