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

public class KHidDelegate extends KReaderDelegate {
    private static final String GET_HID_AUTO_LOCK_TIME = "getHIDAutoLockTime";
    private static final String SET_HID_AUTO_LOCK_TIME = "setHIDAutoLockTime";

    private static final String GET_HID_KEYBOARD = "getHIDKeyboard";
    private static final String SET_HID_KEYBOARD = "setHIDKeyboard";

    private static final String GET_HID_INITIAL_DELAY = "getHIDInitialDelay";
    private static final String SET_HID_INITIAL_DELAY = "setHIDInitialDelay";

    private static final String GET_HID_INTER_DELAY = "getHIDInterDelay";
    private static final String SET_HID_INTER_DELAY = "setHIDInterDelay";

    private static final String GET_HID_CONTROL_CHARACTER = "getHIDControlCharacter";
    private static final String SET_HID_CONTROL_CHARACTER = "setHIDControlCharacter";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            GET_HID_AUTO_LOCK_TIME,
            SET_HID_AUTO_LOCK_TIME,

            GET_HID_KEYBOARD,
            SET_HID_KEYBOARD,

            GET_HID_INITIAL_DELAY,
            SET_HID_INITIAL_DELAY,

            GET_HID_INTER_DELAY,
            SET_HID_INTER_DELAY,

            GET_HID_CONTROL_CHARACTER,
            SET_HID_CONTROL_CHARACTER
    );

    public KHidDelegate(KDCReader reader) {
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
            case GET_HID_AUTO_LOCK_TIME:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getHIDAutoLockTime(cb);
                    }
                });
                break;

            case SET_HID_AUTO_LOCK_TIME:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setHIDAutoLockTime(paramInt, cb);
                    }
                });
                break;

            case GET_HID_KEYBOARD:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getHIDKeyboard(cb);
                    }
                });
                break;

            case SET_HID_KEYBOARD:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setHIDKeyboard(paramInt, cb);
                    }
                });
                break;

            case GET_HID_INITIAL_DELAY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getHIDInitialDelay(cb);
                    }
                });
                break;

            case SET_HID_INITIAL_DELAY:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setHIDInitialDelay(paramInt, cb);
                    }
                });
                break;

            case GET_HID_INTER_DELAY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getHIDInterDelay(cb);
                    }
                });
                break;

            case SET_HID_INTER_DELAY:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setHIDInterDelay(paramInt, cb);
                    }
                });
                break;

            case GET_HID_CONTROL_CHARACTER:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getHIDControlCharacter(cb);
                    }
                });
                break;

            case SET_HID_CONTROL_CHARACTER:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setHIDControlCharacter(paramInt, cb);
                    }
                });
                break;
        }

        return true;
    }

    private void getHIDAutoLockTime(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.HIDAutoLockTime timeout = reader.GetHIDAutoLockTime();

        if (timeout != null) {
            result = new PluginResult(PluginResult.Status.OK, timeout.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setHIDAutoLockTime(int timeout, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.HIDAutoLockTime t : KDCConstants.HIDAutoLockTime.values()) {
            if(t.ordinal() == timeout) {
                bRet = reader.SetHIDAutoLockTime(t);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getHIDKeyboard(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.HIDKeyboard keyboard = reader.GetHIDKeyboard();

        if (keyboard != null) {
            result = new PluginResult(PluginResult.Status.OK, keyboard.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setHIDKeyboard(int keyboard, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.HIDKeyboard k : KDCConstants.HIDKeyboard.values()) {
            if(k.ordinal() == keyboard) {
                bRet = reader.SetHIDKeyboard(k);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getHIDInitialDelay(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.HIDInitialDelay delay = reader.GetHIDInitialDelay();

        if (delay != null) {
            result = new PluginResult(PluginResult.Status.OK, delay.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setHIDInitialDelay(int delay, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.HIDInitialDelay d : KDCConstants.HIDInitialDelay.values()) {
            if(d.ordinal() == delay) {
                bRet = reader.SetHIDInitialDelay(d);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getHIDInterDelay(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.HIDInterDelay delay = reader.GetHIDInterDelay();

        if (delay != null) {
            result = new PluginResult(PluginResult.Status.OK, delay.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setHIDInterDelay(int delay, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.HIDInterDelay d : KDCConstants.HIDInterDelay.values()) {
            if(d.ordinal() == delay) {
                bRet = reader.SetHIDInterDelay(d);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getHIDControlCharacter(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        PluginResult result = null;

        KDCConstants.HIDControlCharacter character = reader.GetHIDControlCharacter();

        if (character != null) {
            result = new PluginResult(PluginResult.Status.OK, character.ordinal());
        }

        if (result != null) {
            cb.sendPluginResult(result);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setHIDControlCharacter(int character, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.HIDControlCharacter c : KDCConstants.HIDControlCharacter.values()) {
            if(c.ordinal() == character) {
                bRet = reader.SetHIDControlCharacter(c);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }
}