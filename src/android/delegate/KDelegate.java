package koamtac.kdc.sdk.plugin.delegate;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;

public interface KDelegate {
    boolean isSupported(String action);
    boolean run(CordovaInterface cordova, String action, JSONArray args, CallbackContext cb) throws JSONException;
}
