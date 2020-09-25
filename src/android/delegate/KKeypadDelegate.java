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

import koamtac.kdc.sdk.KDCReader;

public class KKeypadDelegate extends KReaderDelegate {

    private static final String ENABLE_ENTER_KEY_FUNCTION = "enableEnterKeyFunction";
    private static final String IS_ENTER_KEY_FUNCTION_ENABLED = "isEnterKeyFunctionEnabled";

    private static final String ENABLE_EXTEND_KEYPAD = "enableExtendKeypad";
    private static final String IS_EXTEND_KEYPAD_ENABLED = "isExtendKeypadEnabled";

    private static final String ENABLE_KEYPAD = "enableKeypad";
    private static final String IS_KEYPAD_ENABLED = "isKeypadEnabled";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            ENABLE_ENTER_KEY_FUNCTION,
            IS_ENTER_KEY_FUNCTION_ENABLED,

            ENABLE_EXTEND_KEYPAD,
            IS_EXTEND_KEYPAD_ENABLED,

            ENABLE_KEYPAD,
            IS_KEYPAD_ENABLED
    );

    public KKeypadDelegate(KDCReader reader) {
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
            case ENABLE_ENTER_KEY_FUNCTION:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableEnterKeyFunction(paramBool, cb);
                    }
                });
                break;

            case IS_ENTER_KEY_FUNCTION_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isEnterKeyFunctionEnabled(cb);
                    }
                });
                break;

            case ENABLE_EXTEND_KEYPAD:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableExtendKeypad(paramBool, cb);
                    }
                });
                break;

            case IS_EXTEND_KEYPAD_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isExtendKeypadEnabled(cb);
                    }
                });
                break;

            case ENABLE_KEYPAD:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableKeypad(paramBool, cb);
                    }
                });
                break;

            case IS_KEYPAD_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isKeypadEnabled(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void enableEnterKeyFunction(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableEnterKeyFunction(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isEnterKeyFunctionEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsEnterKeyFunctionEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableExtendKeypad(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableExtendKeypad(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isExtendKeypadEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsExtendKeypadEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableKeypad(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableKeypad(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isKeypadEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsKeypadEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }
}
