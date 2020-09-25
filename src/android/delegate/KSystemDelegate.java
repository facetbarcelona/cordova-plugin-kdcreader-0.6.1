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
import koamtac.kdc.sdk.plugin.KConstants;
import koamtac.kdc.sdk.plugin.KConverter;

public class KSystemDelegate extends KReaderDelegate {
    private static final String ENABLE_BUTTON_LOCK = "enableButtonLock";
    private static final String ENABLE_SCAN_BUTTON_LOCK = "enableScanButtonLock";

    private static final String ERASE_MEMORY = "eraseMemory";
    private static final String ERASE_LAST_DATA = "eraseLastData";

    private static final String GET_MEMORY_LEFT = "getMemoryLeft";

    private static final String GET_SLEEP_TIMEOUT = "getSleepTimeout";
    private static final String SET_SLEEP_TIMEOUT = "setSleepTimeout";

    private static final String GET_SCAN_TIMEOUT = "getScanTimeout";
    private static final String SET_SCAN_TIMEOUT = "setScanTimeout";

    private static final String GET_BATTERY_LEVEL = "getBatteryLevel";
    private static final String GET_EXTENDED_BATTERY_LEVEL = "getExtendedBatteryLevel";

    private static final String SYNC_SYSTEM_TIME = "syncSystemTime";
    private static final String RESET_SYSTEM_TIME = "resetSystemTime";

    private static final String SET_FACTORY_DEFAULT = "setFactoryDefault";

    private static final String ENABLE_HIGH_BEEP_VOLUME = "enableHighBeepVolume";
    private static final String IS_HIGH_BEEP_VOLUME_ENABLED = "isHighBeepVolumeEnabled";

    private static final String ENABLE_BEEP_SOUND = "enableBeepSound";
    private static final String IS_BEEP_SOUND_ENABLED = "isBeepSoundEnabled";

    private static final String ENABLE_POWER_ON_BEEP = "enablePowerOnBeep";
    private static final String IS_POWER_ON_BEEP_ENABLED = "isPowerOnBeepEnabled";

    private static final String ENABLE_BEEP_ON_SCAN = "enableBeepOnScan";
    private static final String IS_BEEP_ON_SCAN_ENABLED = "isBeepOnScanEnabled";

    private static final String ENABLE_BEEP_ON_CONNECT = "enableBeepOnConnect";
    private static final String IS_BEEP_ON_CONNECT_ENABLED = "isBeepOnConnectEnabled";

    private static final String SET_FAILURE_ALERT_BEEP = "setFailureAlertBeep";

    private static final String SET_SUCCESS_ALERT_BEEP = "setSuccessAlertBeep";

    private static final String SET_CUSTOM_BEEP_TONE = "setCustomBeepTone";

    private static final String ENABLE_MFI_MODE = "enableMFiMode";
    private static final String IS_MFI_ENABLED = "isMFiEnabled";

    private static final String IS_MFI_AUTH_CHIP_INSTALLED = "isMFiAuthChipInstalled";

    private static final String ENABLE_DUPLICATE_CHECK = "enableDuplicateCheck";
    private static final String IS_DUPLICATE_CHECK_ENABLED = "isDuplicateCheckEnabled";

    private static final String ENABLE_AUTO_ERASE = "enableAutoErase";
    private static final String IS_AUTO_ERASE_ENABLED = "isAutoEraseEnabled";

    private static final String ENABLE_SCAN_IF_CONNECTED = "enableScanIfConnected";
    private static final String IS_SCAN_IF_CONNECTED_ENABLED = "isScanIfConnectedEnabled";

    private static final String GET_AUTO_TRIGGER_REREAD_DELAY = "getAutoTriggerRereadDelay";
    private static final String SET_AUTO_TRIGGER_REREAD_DELAY = "setAutoTriggerRereadDelay";

    private static final String ENABLE_AUTO_TRIGGER = "enableAutoTrigger";
    private static final String IS_AUTO_TRIGGER_ENABLED = "isAutoTriggerEnabled";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            ENABLE_BUTTON_LOCK,
            ENABLE_SCAN_BUTTON_LOCK,

            ERASE_MEMORY,
            ERASE_LAST_DATA,

            GET_MEMORY_LEFT,

            GET_SLEEP_TIMEOUT,
            SET_SLEEP_TIMEOUT,

            GET_SCAN_TIMEOUT,
            SET_SCAN_TIMEOUT,

            GET_BATTERY_LEVEL,
            GET_EXTENDED_BATTERY_LEVEL,

            SYNC_SYSTEM_TIME,
            RESET_SYSTEM_TIME,

            SET_FACTORY_DEFAULT,

            ENABLE_HIGH_BEEP_VOLUME,
            IS_HIGH_BEEP_VOLUME_ENABLED,


            ENABLE_BEEP_SOUND,
            IS_BEEP_SOUND_ENABLED,

            ENABLE_POWER_ON_BEEP,
            IS_POWER_ON_BEEP_ENABLED,

            ENABLE_BEEP_ON_SCAN,
            IS_BEEP_ON_SCAN_ENABLED,

            ENABLE_BEEP_ON_CONNECT,
            IS_BEEP_ON_CONNECT_ENABLED,

            SET_FAILURE_ALERT_BEEP,

            SET_SUCCESS_ALERT_BEEP,

            SET_CUSTOM_BEEP_TONE,

            ENABLE_MFI_MODE,
            IS_MFI_ENABLED,

            IS_MFI_AUTH_CHIP_INSTALLED,

            ENABLE_DUPLICATE_CHECK,
            IS_DUPLICATE_CHECK_ENABLED,

            ENABLE_AUTO_ERASE,
            IS_AUTO_ERASE_ENABLED,

            ENABLE_SCAN_IF_CONNECTED,
            IS_SCAN_IF_CONNECTED_ENABLED,

            GET_AUTO_TRIGGER_REREAD_DELAY,
            SET_AUTO_TRIGGER_REREAD_DELAY,

            ENABLE_AUTO_TRIGGER,
            IS_AUTO_TRIGGER_ENABLED
    );

    public KSystemDelegate(KDCReader reader) {
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
            case ENABLE_BUTTON_LOCK:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableButtonLock(paramBool, cb);
                    }
                });
                break;

            case ENABLE_SCAN_BUTTON_LOCK:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableScanButtonLock(paramBool, cb);
                    }
                });
                break;

            case ERASE_MEMORY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        eraseMemory(cb);
                    }
                });
                break;

            case ERASE_LAST_DATA:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        eraseLastData(cb);
                    }
                });
                break;

            case GET_MEMORY_LEFT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMemoryLeft(cb);
                    }
                });
                break;

            case GET_SLEEP_TIMEOUT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getSleepTimeout(cb);
                    }
                });
                break;


            case SET_SLEEP_TIMEOUT:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setSleepTimeout(paramInt, cb);
                    }
                });
                break;

            case GET_SCAN_TIMEOUT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getScanTimeout(cb);
                    }
                });
                break;

            case SET_SCAN_TIMEOUT:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setScanTimeout(paramInt, cb);
                    }
                });
                break;

            case GET_BATTERY_LEVEL:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getBatteryLevel(cb);
                    }
                });
                break;

            case GET_EXTENDED_BATTERY_LEVEL:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getExtendedBatteryLevel(cb);
                    }
                });
                break;

            case SYNC_SYSTEM_TIME:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        syncSystemTime(cb);
                    }
                });
                break;

            case RESET_SYSTEM_TIME:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        resetSystemTime(cb);
                    }
                });
                break;

            case SET_FACTORY_DEFAULT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setFactoryDefault(cb);
                    }
                });
                break;

            case ENABLE_HIGH_BEEP_VOLUME:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableHighBeepVolume(paramBool, cb);
                    }
                });
                break;

            case IS_HIGH_BEEP_VOLUME_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isHighBeepVolumeEnabled(cb);
                    }
                });
                break;


            case ENABLE_BEEP_SOUND:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBeepSound(paramBool, cb);
                    }
                });
                break;

            case IS_BEEP_SOUND_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBeepSoundEnabled(cb);
                    }
                });
                break;

            case ENABLE_POWER_ON_BEEP:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enablePowerOnBeep(paramBool, cb);
                    }
                });
                break;

            case IS_POWER_ON_BEEP_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isPowerOnBeepEnabled(cb);
                    }
                });
                break;

            case ENABLE_BEEP_ON_SCAN:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBeepOnScan(paramBool, cb);
                    }
                });
                break;

            case IS_BEEP_ON_SCAN_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBeepOnScanEnabled(cb);
                    }
                });
                break;

            case ENABLE_BEEP_ON_CONNECT:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBeepOnConnect(paramBool, cb);
                    }
                });
                break;

            case IS_BEEP_ON_CONNECT_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBeepOnConnectEnabled(cb);
                    }
                });
                break;

            case SET_FAILURE_ALERT_BEEP:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setFailureAlertBeep(cb);
                    }
                });
                break;

            case SET_SUCCESS_ALERT_BEEP:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setSuccessAlertBeep(cb);
                    }
                });
                break;

            case SET_CUSTOM_BEEP_TONE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setCustomBeepTone(args, cb);
                    }
                });
                break;

            case ENABLE_MFI_MODE:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableMFiMode(paramBool, cb);
                    }
                });
                break;

            case IS_MFI_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isMFiEnabled(cb);
                    }
                });
                break;

            case IS_MFI_AUTH_CHIP_INSTALLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isMFiAuthChipInstalled(cb);
                    }
                });
                break;

            case GET_AUTO_TRIGGER_REREAD_DELAY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getAutoTriggerRereadDelay(cb);
                    }
                });
                break;

            case SET_AUTO_TRIGGER_REREAD_DELAY:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setAutoTriggerRereadDelay(paramInt, cb);
                    }
                });
                break;

            case ENABLE_AUTO_TRIGGER:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableAutoTrigger(paramBool, cb);
                    }
                });
                break;

            case IS_AUTO_TRIGGER_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isAutoTriggerEnabled(cb);
                    }
                });
                break;

        }

        return true;
    }

    private void enableButtonLock(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableButtonLock(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableScanButtonLock(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableScanButtonLock(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void eraseMemory(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EraseMemory()) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void eraseLastData(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EraseLastData();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getMemoryLeft(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        int memory = reader.GetMemoryLeft();

        if (memory != -1) {
            result = new PluginResult(PluginResult.Status.OK, memory);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getSleepTimeout(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.SleepTimeout timeout = reader.GetSleepTimeout();

        if (timeout != null) {
            result = new PluginResult(PluginResult.Status.OK, timeout.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setSleepTimeout(int timeout, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.SleepTimeout s : KDCConstants.SleepTimeout.values()) {
            if(s.ordinal() == timeout) {
                bRet = reader.SetSleepTimeout(s);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getScanTimeout(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        int timeout = reader.GetScanTimeout();

        if (timeout != -1) {
            result = new PluginResult(PluginResult.Status.OK, timeout);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setScanTimeout(int timeout, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = timeout >= 0 && reader.SetScanTimeout(timeout);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getBatteryLevel(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        int level = reader.GetBatteryLevel();

        if (level != -1) {
            result = new PluginResult(PluginResult.Status.OK, level);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getExtendedBatteryLevel(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        int level = reader.GetExtendedBatteryLevel();

        if (level != -1) {
            result = new PluginResult(PluginResult.Status.OK, level);
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void syncSystemTime(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SyncSystemTime();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void resetSystemTime(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.ResetSystemTime();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setFactoryDefault(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetFactoryDefault();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableHighBeepVolume(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableHighBeepVolume(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isHighBeepVolumeEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsHighBeepVolumeEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBeepSound(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableBeepSound(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBeepSoundEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBeepSoundEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enablePowerOnBeep(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnablePowerOnBeep(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isPowerOnBeepEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsPowerOnBeepEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBeepOnScan(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableBeepOnScan(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBeepOnScanEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBeepOnScanEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBeepOnConnect(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableBeepOnConnect(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBeepOnConnectEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBeepOnConnectEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void setFailureAlertBeep(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetFailureAlertBeep();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setSuccessAlertBeep(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetSuccessAlertBeep();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setCustomBeepTone(JSONArray jsonArray, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        try {
            int onTime = jsonArray.getInt(0);
            int offTime = jsonArray.getInt(1);
            int repeat = jsonArray.getInt(2);

            bRet = reader.SetCustomBeepTone(onTime, offTime, repeat);
        } catch (JSONException e) {
            e.printStackTrace();

            cb.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
            return;
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableMFiMode(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        if(reader.EnableMFiMode(e)) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isMFiEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsMFiEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void isMFiAuthChipInstalled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsMFiAuthChipInstalled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void getAutoTriggerRereadDelay(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.RereadDelay delay = reader.GetAutoTriggerRereadDelay();

        if (delay != null) {
            cb.success(delay.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setAutoTriggerRereadDelay(int delay, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.RereadDelay d : KDCConstants.RereadDelay.values()) {
            if (delay == d.ordinal()) {
                bRet = reader.SetAutoTriggerRereadDelay(d);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableAutoTrigger(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableAutoTrigger(e);

        if(bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isAutoTriggerEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int isEnabled = reader.IsAutoTriggerEnabled();

        PluginResult result = (isEnabled != -1) ? new PluginResult(PluginResult.Status.OK, isEnabled > 1) : null;

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

}
