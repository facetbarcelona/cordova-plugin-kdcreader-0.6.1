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

public class KVibratorDelegate extends KReaderDelegate {
    private static final String ENABLE_VIBRATOR = "enableVibrator";
    private static final String IS_VIBRATOR_ENABLED = "isVibratorEnabled";

    private static final String SET_CUSTOM_VIBRATION = "setCustomVibration";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            ENABLE_VIBRATOR,
            IS_VIBRATOR_ENABLED,

            SET_CUSTOM_VIBRATION
    );

    public KVibratorDelegate(KDCReader reader) {
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
            case ENABLE_VIBRATOR:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableVibrator(paramBool, cb);
                    }
                });
                break;

            case IS_VIBRATOR_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isVibratorEnabled(cb);
                    }
                });
                break;

            case SET_CUSTOM_VIBRATION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setCustomVibration(args, cb);
                    }
                });
                break;
        }

        return true;
    }

    private void enableVibrator(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableVibrator(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isVibratorEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsVibratorEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void setCustomVibration(JSONArray jsonArray, CallbackContext cb) {
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

            bRet = reader.SetCustomVibration(onTime, offTime, repeat);
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

}
