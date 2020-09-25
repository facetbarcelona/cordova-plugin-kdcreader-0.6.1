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

public class KPosDelegate extends KReaderDelegate {
    private static final String SOFTWARE_TRIGGER_POS = "softwareTriggerPos";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            SOFTWARE_TRIGGER_POS
    );

    public KPosDelegate(KDCReader reader) {
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
            case SOFTWARE_TRIGGER_POS:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        softwareTriggerPos(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void softwareTriggerPos(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int retCode = reader.SoftwareTrigger_POS();
        cb.success(retCode);
    }
}

