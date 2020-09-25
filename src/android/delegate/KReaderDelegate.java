package koamtac.kdc.sdk.plugin.delegate;

import org.apache.cordova.CallbackContext;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import koamtac.kdc.sdk.KDCReader;

public abstract class KReaderDelegate implements KDelegate {
    static class ErrorCode {
        private static final String KEY_CODE = "code";
        private static final String KEY_MESSAGE = "message";

        private static final int CODE_SUCCESS = 0;
        private static final int CODE_FAILED = 1;
        private static final int CODE_NULL = 2;
        private static final int CODE_NOT_CONNECTED = 3;

        private static final String MESSAGE_SUCCESS = "The operation is succeeded.";
        private static final String MESSAGE_FAILED = "The operation is failed.";
        private static final String MESSAGE_NULL = "KDCReader is null.";
        private static final String MESSAGE_NOT_CONNECTED = "KDC Device is not connected.";

        JSONObject SUCCESS;
        JSONObject FAILED;
        JSONObject NULL;
        JSONObject NOT_CONNECTED;

        ErrorCode() {
            Map<String, Object> map = new HashMap<>();

            map.clear();
            map.put(KEY_CODE, CODE_SUCCESS);
            map.put(KEY_MESSAGE, MESSAGE_SUCCESS);

            SUCCESS = new JSONObject(map);

            map.clear();
            map.put(KEY_CODE, CODE_FAILED);
            map.put(KEY_MESSAGE, MESSAGE_FAILED);

            FAILED = new JSONObject(map);

            map.clear();
            map.put(KEY_CODE, CODE_NULL);
            map.put(KEY_MESSAGE, MESSAGE_NULL);

            NULL = new JSONObject(map);

            map.clear();
            map.put(KEY_CODE, CODE_NOT_CONNECTED);
            map.put(KEY_MESSAGE, MESSAGE_NOT_CONNECTED);

            NOT_CONNECTED = new JSONObject(map);
        }
    }

    static final Object mLock = new Object();

    static final ErrorCode error = new ErrorCode();

    WeakReference<KDCReader> wrReader;

    boolean isNull(KDCReader reader) {
        return reader == null;
    }

    boolean isConnected(KDCReader reader) {
        return reader != null && reader.IsConnected();
    }
}
