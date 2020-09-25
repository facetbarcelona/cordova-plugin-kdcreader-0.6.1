package koamtac.kdc.sdk.plugin.delegate;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import koamtac.kdc.sdk.KDCConstants;
import koamtac.kdc.sdk.KDCDevice;
import koamtac.kdc.sdk.KDCReader;
import koamtac.kdc.sdk.UHFStatus;
import koamtac.kdc.sdk.plugin.KConstants;
import koamtac.kdc.sdk.plugin.KConverter;

public class KUhfDelegate extends KReaderDelegate {
    private static final String IS_UHF_MODULE_ATTACHED = "isUHFModuleAttached";

    private static final String IS_UHF_POWER_ENABLED = "isUHFPowerEnabled";
    private static final String ENABLE_UHF_POWER = "enableUHFPower";

    private static final String GET_UHF_POWER_LEVEL = "getUHFPowerLevel";
    private static final String SET_UHF_POWER_LEVEL = "setUHFPowerLevel";

    private static final String GET_UHF_READ_MODE = "getUHFReadMode";
    private static final String SET_UHF_READ_MODE = "setUHFReadMode";

    private static final String GET_UHF_READ_TAG_MODE = "getUHFReadTagMode";
    private static final String SET_UHF_READ_TAG_MODE = "setUHFReadTagMode";

    private static final String GET_UHF_DATA_TYPE = "getUHFDataType";
    private static final String SET_UHF_DATA_TYPE = "setUHFDataType";

    private static final String IS_UHF_BURST_MODE_ENABLED = "isUHFBurstModeEnabled";
    private static final String ENABLE_UHF_BURST_MODE = "enableUHFBurstMode";

    private static final String IS_UHF_KEY_EVENT_ENABLED = "isUHFKeyEventEnabled";
    private static final String ENABLE_UHF_KEY_EVENT = "enableUHFKeyEvent";

    private static final String CANCEL_UHF_READING = "cancelUHFReading";

    private static final String GET_UHF_TAG_LIST = "getUHFTagList";

    private static final String SELECT_UHF_TAG = "selectUHFTag";

    private static final String READ_UHF_TAG_MEMORY = "readUHFTagMemory";
    private static final String WRITE_UHF_TAG_MEMORY = "writeUHFTagMemory";

    private static final String SET_UHF_TAG_LOCK = "setUHFTagLock";
    private static final String KILL_UHF_TAG = "'killUHFTag'";


    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            IS_UHF_MODULE_ATTACHED,

            IS_UHF_POWER_ENABLED,
            ENABLE_UHF_POWER,

            GET_UHF_POWER_LEVEL,
            SET_UHF_POWER_LEVEL,

            GET_UHF_READ_MODE,
            SET_UHF_READ_MODE,

            GET_UHF_READ_TAG_MODE,
            SET_UHF_READ_TAG_MODE,

            GET_UHF_DATA_TYPE,
            SET_UHF_DATA_TYPE,

            IS_UHF_BURST_MODE_ENABLED,
            ENABLE_UHF_BURST_MODE,

            IS_UHF_KEY_EVENT_ENABLED,
            ENABLE_UHF_KEY_EVENT,

            CANCEL_UHF_READING,

            GET_UHF_TAG_LIST,

            SELECT_UHF_TAG,

            READ_UHF_TAG_MEMORY,
            WRITE_UHF_TAG_MEMORY,

            SET_UHF_TAG_LOCK,
            KILL_UHF_TAG

    );

    public KUhfDelegate(KDCReader reader) {
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
            case IS_UHF_MODULE_ATTACHED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isUHFModuleAttached(cb);
                    }
                });
                break;

            case IS_UHF_POWER_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isUHFPowerEnabled(cb);
                    }
                });
                break;

            case ENABLE_UHF_POWER:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableUHFPower(paramBool, cb);
                    }
                });
                break;

            case GET_UHF_POWER_LEVEL:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getUHFPowerLevel(cb);
                    }
                });
                break;

            case SET_UHF_POWER_LEVEL:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setUHFPowerLevel(paramInt, cb);
                    }
                });
                break;

            case GET_UHF_READ_MODE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getUHFReadMode(cb);
                    }
                });
                break;

            case SET_UHF_READ_MODE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setUHFReadMode(paramInt, cb);
                    }
                });
                break;

            case GET_UHF_READ_TAG_MODE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getUHFReadTagMode(cb);
                    }
                });
                break;

            case SET_UHF_READ_TAG_MODE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setUHFReadTagMode(paramInt, cb);
                    }
                });
                break;

            case GET_UHF_DATA_TYPE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getUHFDataType(cb);
                    }
                });
                break;

            case SET_UHF_DATA_TYPE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setUHFDataType(paramInt, cb);
                    }
                });
                break;

            case IS_UHF_BURST_MODE_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isUHFBurstModeEnabled(cb);
                    }
                });
                break;

            case ENABLE_UHF_BURST_MODE:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableUHFBurstMode(paramBool, cb);
                    }
                });
                break;

            case IS_UHF_KEY_EVENT_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isUHFKeyEventEnabled(cb);
                    }
                });
                break;

            case ENABLE_UHF_KEY_EVENT:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableUHFKeyEvent(paramBool, cb);
                    }
                });
                break;

            case CANCEL_UHF_READING:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        cancelUHFReading(cb);
                    }
                });
                break;

            case GET_UHF_TAG_LIST:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getUHFTagList(paramInt, cb);
                    }
                });
                break;

            case SELECT_UHF_TAG:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        selectUHFTag(paramStr, cb);
                    }
                });
                break;

            case READ_UHF_TAG_MEMORY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        readUHFTagMemory(args, cb);
                    }
                });
                break;

            case WRITE_UHF_TAG_MEMORY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        writeUHFTagMemory(args, cb);
                    }
                });
                break;

            case SET_UHF_TAG_LOCK:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setUHFTagLock(args, cb);
                    }
                });
                break;

            case KILL_UHF_TAG:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        killUHFTag(paramStr, cb);
                    }
                });
                break;
        }

        return true;
    }

    private void isUHFModuleAttached(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isAttached = reader.IsUHFModuleAttached();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isAttached);

        cb.sendPluginResult(result);
    }

    private void isUHFPowerEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsUHFKeyEventEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableUHFPower(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableUHFPower(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getUHFPowerLevel(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.UHFPowerLevel level = reader.GetUHFPowerLevel();

        if (level != null) {
            result = new PluginResult(PluginResult.Status.OK, level.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setUHFPowerLevel(int level, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.UHFPowerLevel p : KDCConstants.UHFPowerLevel.values()) {
            if(p.ordinal() == level) {
                bRet = reader.SetUHFPowerLevel(p);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getUHFReadMode(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.UHFReadMode mode = reader.GetUHFReadMode();

        if (mode != null) {
            result = new PluginResult(PluginResult.Status.OK, mode.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setUHFReadMode(int mode, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.UHFReadMode m : KDCConstants.UHFReadMode.values()) {
            if(m.ordinal() == mode) {
                bRet = reader.SetUHFReadMode(m);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getUHFReadTagMode(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.UHFReadTagMode mode = reader.GetUHFReadTagMode();

        if (mode != null) {
            result = new PluginResult(PluginResult.Status.OK, mode.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setUHFReadTagMode(int mode, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.UHFReadTagMode m : KDCConstants.UHFReadTagMode.values()) {
            if(m.ordinal() == mode) {
                bRet = reader.SetUHFReadTagMode(m);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getUHFDataType(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.UHFDataType mode = reader.GetUHFDataType();

        if (mode != null) {
            result = new PluginResult(PluginResult.Status.OK, mode.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setUHFDataType(int type, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.UHFDataType t : KDCConstants.UHFDataType.values()) {
            if(t.ordinal() == type) {
                bRet = reader.SetUHFDataType(t);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isUHFBurstModeEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsUHFBurstModeEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableUHFBurstMode(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableUHFBurstMode(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isUHFKeyEventEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsUHFKeyEventEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableUHFKeyEvent(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableUHFKeyEvent(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void cancelUHFReading(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        reader.CancelUHFReading();

        cb.success(error.SUCCESS);
    }

    private void getUHFTagList(int timeout, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        UHFStatus status = new UHFStatus();
        status.SetUHFDataFormat(KDCConstants.UHFDataFormat.HEX_DECIMAL);

        ArrayList<byte[]> tList = reader.GetUHFTagList(timeout, status);

        if (status.GetErrorCode() == UHFStatus.UHF_SUCCESS) {
            ArrayList<String> tagList = new ArrayList<>();

            for (byte[] b : tList) {
                tagList.add(new String(b));
            }

            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJsonArray(tagList));
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

    private void selectUHFTag(String epc, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        UHFStatus status = new UHFStatus();
        status.SetUHFDataFormat(KDCConstants.UHFDataFormat.HEX_DECIMAL);

        reader.SelectUHFTag(epc.getBytes(), status);

        if (status.GetErrorCode() == UHFStatus.UHF_SUCCESS) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void readUHFTagMemory(JSONArray jsonArray, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.UHFMemoryBank memoryBank = null;

        UHFStatus status = new UHFStatus();
        status.SetUHFDataFormat(KDCConstants.UHFDataFormat.HEX_DECIMAL);

        try {
            String pwd = jsonArray.getString(0);
            int bank = jsonArray.getInt(1);

            for (KDCConstants.UHFMemoryBank b : KDCConstants.UHFMemoryBank.values()) {
                if (bank == b.ordinal()) {
                    memoryBank = b;
                    break;
                }
            }

            if (memoryBank != null) {
                int start = jsonArray.getInt(2);
                int length = jsonArray.getInt(3);


                reader.ReadUHFTagMemory(pwd.getBytes(), memoryBank, start, length, status);

                if (status.GetErrorCode() == UHFStatus.UHF_SUCCESS) {
                    result = new PluginResult(PluginResult.Status.OK, error.SUCCESS);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();

            result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void writeUHFTagMemory(JSONArray jsonArray, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.UHFMemoryBank memoryBank = null;

        UHFStatus status = new UHFStatus();
        status.SetUHFDataFormat(KDCConstants.UHFDataFormat.HEX_DECIMAL);

        try {
            String pwd = jsonArray.getString(0);
            int bank = jsonArray.getInt(1);

            for (KDCConstants.UHFMemoryBank b : KDCConstants.UHFMemoryBank.values()) {
                if (bank == b.ordinal()) {
                    memoryBank = b;
                    break;
                }
            }

            if (memoryBank != null) {
                int start = jsonArray.getInt(2);
                int length = jsonArray.getInt(3);
                String data = jsonArray.getString(4);


                reader.WriteUHFTagMemory(pwd.getBytes(), memoryBank, start, length, data.getBytes(), status);

                if (status.GetErrorCode() == UHFStatus.UHF_SUCCESS) {
                    result = new PluginResult(PluginResult.Status.OK, error.SUCCESS);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();

            result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setUHFTagLock(JSONArray jsonArray, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        UHFStatus status = new UHFStatus();
        status.SetUHFDataFormat(KDCConstants.UHFDataFormat.HEX_DECIMAL);

        try {
            String pwd = jsonArray.getString(0);
            int mask = jsonArray.getInt(1);


            reader.SetUHFTagLock(pwd.getBytes(), mask, status);

            if (status.GetErrorCode() == UHFStatus.UHF_SUCCESS) {
                result = new PluginResult(PluginResult.Status.OK, error.SUCCESS);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void killUHFTag(String pwd, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        UHFStatus status = new UHFStatus();
        status.SetUHFDataFormat(KDCConstants.UHFDataFormat.HEX_DECIMAL);

        reader.KillUHFTag(pwd.getBytes(), status);

        if (status.GetErrorCode() == UHFStatus.UHF_SUCCESS) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }
}
