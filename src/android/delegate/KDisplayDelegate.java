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

public class KDisplayDelegate extends KReaderDelegate {

    private static final String ENABLE_DISPLAY_CONNECTION_STATUS = "enableDisplayConnectionStatus";
    private static final String IS_DISPLAY_CONNECTION_STATUS_ENABLED = "isDisplayConnectionStatusEnabled";

    private static final String ENABLE_MENU_BARCODE_STATE = "enableMenuBarcodeState";
    private static final String IS_MENU_BARCODE_STATE_ENABLED = "isMenuBarcodeStateEnabled";

    private static final String ENABLE_DISPLAY_SCROLL = "enableDisplayScroll";
    private static final String IS_SCROLLING_ENABLED = "isScrollingEnabled";

    private static final String SET_DISPLAY_POSITION = "setDisplayPosition";

    private static final String CLEAR_DISPLAY = "clearDisplay";

    private static final String SET_DISPLAY_MESSAGE_FONT_SIZE = "setDisplayMessageFontSize";
    private static final String SET_DISPLAY_MESSAGE_DURATION = "setDisplayMessageDuration";
    private static final String SET_MESSAGE_TEXT_ATTRIBUTE = "setMessageTextAttribute";

    private static final String SET_DISPLAY_MESSAGE = "setDisplayMessage";
    private static final String SET_DISPLAY_MESSAGE_AND_GET_USER_CONFIRM = "setDisplayMessageAndGetUserConfirm";

    private static final String ENABLE_AUTO_MENU_EXIT = "enableAutoMenuExit";
    private static final String IS_AUTO_MENU_EXIT_ENABLED = "isAutoMenuExitEnabled";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            ENABLE_DISPLAY_CONNECTION_STATUS,
            IS_DISPLAY_CONNECTION_STATUS_ENABLED,

            ENABLE_MENU_BARCODE_STATE,
            IS_MENU_BARCODE_STATE_ENABLED,

            ENABLE_DISPLAY_SCROLL,
            IS_SCROLLING_ENABLED,

            SET_DISPLAY_POSITION,

            CLEAR_DISPLAY,

            SET_DISPLAY_MESSAGE_FONT_SIZE,
            SET_DISPLAY_MESSAGE_DURATION,
            SET_MESSAGE_TEXT_ATTRIBUTE,

            SET_DISPLAY_MESSAGE,
            SET_DISPLAY_MESSAGE_AND_GET_USER_CONFIRM,

            ENABLE_AUTO_MENU_EXIT,
            IS_AUTO_MENU_EXIT_ENABLED
    );

    public KDisplayDelegate(KDCReader reader) {
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
            case ENABLE_DISPLAY_CONNECTION_STATUS:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableDisplayConnectionStatus(paramBool, cb);
                    }
                });
                break;

            case IS_DISPLAY_CONNECTION_STATUS_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isDisplayConnectionStatusEnabled(cb);
                    }
                });
                break;

            case ENABLE_MENU_BARCODE_STATE:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableMenuBarcodeState(paramBool, cb);
                    }
                });
                break;

            case IS_MENU_BARCODE_STATE_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isMenuBarcodeStateEnabled(cb);
                    }
                });
                break;

            case ENABLE_DISPLAY_SCROLL:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableDisplayScroll(paramBool, cb);
                    }
                });
                break;

            case IS_SCROLLING_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isScrollingEnabled(cb);
                    }
                });
                break;

            case SET_DISPLAY_POSITION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDisplayPosition(args, cb);
                    }
                });
                break;

            case CLEAR_DISPLAY:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        clearDisplay(cb);
                    }
                });
                break;

            case SET_DISPLAY_MESSAGE_FONT_SIZE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDisplayMessageFontSize(paramInt, cb);
                    }
                });
                break;

            case SET_DISPLAY_MESSAGE_DURATION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDisplayMessageDuration(paramInt, cb);
                    }
                });
                break;

            case SET_MESSAGE_TEXT_ATTRIBUTE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMessageTextAttribute(paramInt, cb);
                    }
                });
                break;

            case SET_DISPLAY_MESSAGE:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDisplayMessage(paramStr, cb);
                    }
                });
                break;

            case SET_DISPLAY_MESSAGE_AND_GET_USER_CONFIRM:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setDisplayMessageAndGetUserConfirm(paramStr, cb);
                    }
                });
                break;

            case ENABLE_AUTO_MENU_EXIT:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableAutoMenuExit(paramBool, cb);
                    }
                });
                break;

            case IS_AUTO_MENU_EXIT_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isAutoMenuExitEnabled(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void enableDisplayConnectionStatus(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableDisplayConnectionStatus(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isDisplayConnectionStatusEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsDisplayConnectionStatusEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableMenuBarcodeState(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableMenuBarcodeState(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isMenuBarcodeStateEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsMenuBarcodeStateEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableDisplayScroll(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableDisplayScroll(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isScrollingEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsScrollingEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void setDisplayPosition(JSONArray jsonArray, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        try {
            int row = jsonArray.getInt(0);
            int column = jsonArray.getInt(1);

            bRet = reader.SetDisplayPosition(row, column);
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

    private void clearDisplay(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.ClearDisplay();

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setDisplayMessageFontSize(int size, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.FontSize fontSize = null;
        boolean bRet = false;

        for (KDCConstants.FontSize f : KDCConstants.FontSize.values()) {
            if (f.ordinal() == size) {
                fontSize = f;
                break;
            }
        }

        if (fontSize != null) {
            bRet = reader.SetDisplayMessageFontSize(fontSize);
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setDisplayMessageDuration(int duration, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetDisplayMessageDuration(duration);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMessageTextAttribute(int attr, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.MessageTextAttribute attribute = null;
        boolean bRet = false;

        for (KDCConstants.MessageTextAttribute a : KDCConstants.MessageTextAttribute.values()) {
            if (a.ordinal() == attr) {
                attribute = a;
                break;
            }
        }

        if (attribute != null) {
            bRet = reader.SetMessageTextAttribute(attribute);
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }


    private void setDisplayMessage(String message, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetDisplayMessage(message);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setDisplayMessageAndGetUserConfirm(String message, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String confirm = reader.SetDisplayMessageAndGetUserConfirmation(message);

        cb.success(confirm != null ? confirm : "");
    }

    private void enableAutoMenuExit(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableAutoMenuExit(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isAutoMenuExitEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsAutoMenuExitEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }
}
