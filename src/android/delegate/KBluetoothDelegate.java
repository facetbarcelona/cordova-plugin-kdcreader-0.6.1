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

public class KBluetoothDelegate extends KReaderDelegate {

    private static final String ENABLE_BLUETOOTH_AUTO_CONNECT = "enableBluetoothAutoConnect";
    private static final String IS_AUTO_CONNECT_ENABLED = "isAutoConnectEnabled";

    private static final String ENABLE_BLUETOOTH_AUTO_POWER_ON = "enableBluetoothAutoPowerOn";
    private static final String IS_AUTO_POWER_ON_ENABLED = "isAutoPowerOnEnabled";

    private static final String ENABLE_BLUETOOTH_AUTO_POWER_OFF = "enableBluetoothAutoPowerOff";
    private static final String IS_BLUETOOTH_AUTO_POWER_OFF_ENABLED = "isBluetoothAutoPowerOffEnabled";

    private static final String ENABLE_BLUETOOTH_BEEP_WARNING = "enableBluetoothBeepWarning";
    private static final String IS_BLUETOOTH_BEEP_WARNING_ENABLED = "isBluetoothBeepWarningEnabled";

    private static final String GET_DEVICE_PROFILE = "getDeviceProfile";
    private static final String SET_DEVICE_PROFILE = "setDeviceProfile";

    private static final String GET_BLUETOOTH_AUTO_POWER_OFF_TIMEOUT = "getBluetoothAutoPowerOffTimeout";
    private static final String SET_BLUETOOTH_AUTO_POWER_OFF_TIMEOUT = "setBluetoothAutoPowerOffTimeout";

    private static final String ENABLE_BLUETOOTH_POWER_OFF_MESSAGE = "enableBluetoothPowerOffMessage";
    private static final String IS_POWER_OFF_MESSAGE_ENABLED = "isPowerOffMessageEnabled";

    private static final String GET_BLUETOOTH_MAC_ADDRESS = "getBluetoothMACAddress";

    private static final String GET_BLUETOOTH_AUTO_POWER_ON_DELAY = "getBluetoothAutoPowerOnDelay";
    private static final String SET_BLUETOOTH_AUTO_POWER_ON_DELAY = "setBluetoothAutoPowerOnDelay";

    private static final String GET_BLUETOOTH_FIRMWARE_VERSION = "getBluetoothFirmwareVersion";

    private static final String ENABLE_BLUETOOTH_WAKEUP_NULL = "enableBluetoothWakeupNull";
    private static final String IS_WAKEUP_NULLS_ENABLED = "isWakeupNullsEnabled";

    private static final String ENABLE_BLUETOOTH_TOGGLE = "enableBluetoothToggle";
    private static final String IS_BLUETOOTH_TOGGLE_ENABLED = "isBluetoothToggleEnabled";

    private static final String ENABLE_BLUETOOTH_DISCONNECT_BUTTON = "enableBluetoothDisconnectButton";
    private static final String IS_BLUETOOTH_DISCONNECT_BUTTON_ENABLED = "isBluetoothDisconnectButtonEnabled";


    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            ENABLE_BLUETOOTH_AUTO_CONNECT,
            IS_AUTO_CONNECT_ENABLED,

            ENABLE_BLUETOOTH_AUTO_POWER_ON,
            IS_AUTO_POWER_ON_ENABLED,

            ENABLE_BLUETOOTH_AUTO_POWER_OFF,
            IS_BLUETOOTH_AUTO_POWER_OFF_ENABLED,

            ENABLE_BLUETOOTH_BEEP_WARNING,
            IS_BLUETOOTH_BEEP_WARNING_ENABLED,

            GET_DEVICE_PROFILE,
            SET_DEVICE_PROFILE,

            GET_BLUETOOTH_AUTO_POWER_OFF_TIMEOUT,
            SET_BLUETOOTH_AUTO_POWER_OFF_TIMEOUT,

            ENABLE_BLUETOOTH_POWER_OFF_MESSAGE,
            IS_POWER_OFF_MESSAGE_ENABLED,

            GET_BLUETOOTH_MAC_ADDRESS,

            GET_BLUETOOTH_AUTO_POWER_ON_DELAY,
            SET_BLUETOOTH_AUTO_POWER_ON_DELAY,

            GET_BLUETOOTH_FIRMWARE_VERSION,

            ENABLE_BLUETOOTH_WAKEUP_NULL,
            IS_WAKEUP_NULLS_ENABLED,

            ENABLE_BLUETOOTH_TOGGLE,
            IS_BLUETOOTH_TOGGLE_ENABLED,

            ENABLE_BLUETOOTH_DISCONNECT_BUTTON,
            IS_BLUETOOTH_DISCONNECT_BUTTON_ENABLED
    );

    public KBluetoothDelegate(KDCReader reader) {
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
            case ENABLE_BLUETOOTH_AUTO_CONNECT:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothAutoConnect(paramBool, cb);
                    }
                });
                break;

            case IS_AUTO_CONNECT_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isAutoConnectEnabled(cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_AUTO_POWER_ON:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothAutoPowerOn(paramBool, cb);
                    }
                });
                break;

            case IS_AUTO_POWER_ON_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isAutoPowerOnEnabled(cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_AUTO_POWER_OFF:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothAutoPowerOff(paramBool, cb);
                    }
                });
                break;

            case IS_BLUETOOTH_AUTO_POWER_OFF_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBluetoothAutoPowerOffEnabled(cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_BEEP_WARNING:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothBeepWarning(paramBool, cb);
                    }
                });
                break;

            case IS_BLUETOOTH_BEEP_WARNING_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBluetoothBeepWarningEnabled(cb);
                    }
                });
                break;

            case GET_DEVICE_PROFILE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getDeviceProfile(cb);
                    }
                });
                break;

            case SET_DEVICE_PROFILE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDeviceProfile(paramInt, cb);
                    }
                });
                break;

            case GET_BLUETOOTH_AUTO_POWER_OFF_TIMEOUT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getBluetoothAutoPowerOffTimeout(cb);
                    }
                });
                break;

            case SET_BLUETOOTH_AUTO_POWER_OFF_TIMEOUT:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setBluetoothAutoPowerOffTimeout(paramInt, cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_POWER_OFF_MESSAGE:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothPowerOffMessage(paramBool, cb);
                    }
                });
                break;

            case IS_POWER_OFF_MESSAGE_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isPowerOffMessageEnabled(cb);
                    }
                });
                break;

            case GET_BLUETOOTH_MAC_ADDRESS:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getBluetoothMACAddress(cb);
                    }
                });
                break;

            case GET_BLUETOOTH_AUTO_POWER_ON_DELAY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getBluetoothAutoPowerOnDelay(cb);
                    }
                });
                break;

            case SET_BLUETOOTH_AUTO_POWER_ON_DELAY:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setBluetoothAutoPowerOnDelay(paramInt, cb);
                    }
                });
                break;

            case GET_BLUETOOTH_FIRMWARE_VERSION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getBluetoothFirmwareVersion(cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_WAKEUP_NULL:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothWakeupNull(paramBool, cb);
                    }
                });
                break;

            case IS_WAKEUP_NULLS_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isWakeupNullsEnabled(cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_TOGGLE:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothToggle(paramBool, cb);
                    }
                });
                break;

            case IS_BLUETOOTH_TOGGLE_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBluetoothToggleEnabled(cb);
                    }
                });
                break;

            case ENABLE_BLUETOOTH_DISCONNECT_BUTTON:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableBluetoothDisconnectButton(paramBool, cb);
                    }
                });
                break;

            case IS_BLUETOOTH_DISCONNECT_BUTTON_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isBluetoothDisconnectButtonEnabled(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void enableBluetoothAutoConnect(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothAutoConnect(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isAutoConnectEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsAutoConnectEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }


    private void enableBluetoothAutoPowerOn(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothAutoPowerOn(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isAutoPowerOnEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsAutoPowerOnEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBluetoothAutoPowerOff(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothAutoPowerOff(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBluetoothAutoPowerOffEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBluetoothAutoPowerOffEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBluetoothBeepWarning(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothBeepWarning(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBluetoothBeepWarningEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBluetoothBeepWarningEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void getDeviceProfile(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.DeviceProfile profile = reader.GetDeviceProfile();

        if (profile != null) {
            cb.success(profile.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setDeviceProfile(int profile, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.DeviceProfile p : KDCConstants.DeviceProfile.values()) {
            if (profile == p.ordinal()) {
                bRet = reader.SetDeviceProfile(p);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getBluetoothAutoPowerOffTimeout(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.BluetoothAutoPowerOffDelay delay = reader.GetBluetoothAutoPowerOffTimeout();

        if (delay != null) {
            cb.success(delay.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setBluetoothAutoPowerOffTimeout(int delay, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.BluetoothAutoPowerOffDelay d : KDCConstants.BluetoothAutoPowerOffDelay.values()) {
            if (delay == d.ordinal()) {
                bRet = reader.SetBluetoothAutoPowerOffTimeout(d);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableBluetoothPowerOffMessage(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothPowerOffMessage(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isPowerOffMessageEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsPowerOffMessageEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void getBluetoothMACAddress(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String address = reader.GetBluetoothMACAddress();

        if (address != null) {
            cb.success(address);
        } else {
            cb.error(error.FAILED);
        }
    }
;
    private void getBluetoothAutoPowerOnDelay(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.BluetoothAutoPowerOnDelay delay = reader.GetBluetoothAutoPowerOnDelay();

        if (delay != null) {
            cb.success(delay.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setBluetoothAutoPowerOnDelay(int delay, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.BluetoothAutoPowerOnDelay d : KDCConstants.BluetoothAutoPowerOnDelay.values()) {
            if (delay == d.ordinal()) {
                bRet = reader.SetBluetoothAutoPowerOnDelay(d);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getBluetoothFirmwareVersion(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String version = reader.GetBluetoothFirmwareVersion();

        if (version != null) {
            cb.success(version);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableBluetoothWakeupNull(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothWakeupNull(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isWakeupNullsEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsWakeupNullsEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBluetoothToggle(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothToggle(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBluetoothToggleEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBluetoothToggleEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableBluetoothDisconnectButton(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableBluetoothDisconnectButton(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isBluetoothDisconnectButtonEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsBluetoothDisconnectButtonEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

}
