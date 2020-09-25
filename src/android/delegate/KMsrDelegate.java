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
import koamtac.kdc.sdk.KDCMSRTrack;
import koamtac.kdc.sdk.KDCReader;

public class KMsrDelegate extends KReaderDelegate {
    private static final String ENABLE_MSR_ERROR_BEEP = "enableMSRErrorBeep";
    private static final String IS_MSR_ERROR_BEEP_ENABLED = "isMSRErrorBeepEnabled";

    private static final String ENABLE_MSR_SENTINEL = "enableMSRSentinel";
    private static final String IS_MSR_SENTINEL_ENABLED = "isMSRSentinelEnabled";

    private static final String GET_MSR_CARD_TYPE = "getMSRCardType";
    private static final String SET_MSR_CARD_TYPE = "setMSRCardType";

    private static final String GET_MSR_DATA_TYPE = "getMSRDataType";
    private static final String SET_MSR_DATA_TYPE = "setMSRDataType";

    private static final String GET_MSR_DATA_ENCRYPTION = "getMSRDataEncryption";
    private static final String SET_MSR_DATA_ENCRYPTION = "setMSRDataEncryption";

    private static final String GET_MSR_TRACKSEPARATOR = "getMSRTrackSeparator";
    private static final String SET_MSR_TRACKSEPARATOR = "setMSRTrackSeparator";

    private static final String GET_MSR_TRACK_SELECTION = "getMSRTrackSelection";
    private static final String SET_MSR_TRACK_SELECTION = "setMSRTrackSelection";

    private static final String GET_PARTIAL_DATA_MSR_START_POSITION = "getPartialDataMSRStartPosition";
    private static final String SET_PARTIAL_DATA_MSR_START_POSITION = "setPartialDataMSRStartPosition";

    private static final String GET_PARTIAL_DATA_MSR_LENGTH = "getPartialDataMSRLength";
    private static final String SET_PARTIAL_DATA_MSR_LENGTH = "setPartialDataMSRLength";

    private static final String GET_PARTIAL_DATA_MSR_ACTION = "getPartialDataMSRAction";
    private static final String SET_PARTIAL_DATA_MSR_ACTION = "setPartialDataMSRAction";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            ENABLE_MSR_ERROR_BEEP,
            IS_MSR_ERROR_BEEP_ENABLED,

            ENABLE_MSR_SENTINEL,
            IS_MSR_SENTINEL_ENABLED,

            GET_MSR_CARD_TYPE,
            SET_MSR_CARD_TYPE,

            GET_MSR_DATA_TYPE,
            SET_MSR_DATA_TYPE,

            GET_MSR_DATA_ENCRYPTION,
            SET_MSR_DATA_ENCRYPTION,

            GET_MSR_TRACKSEPARATOR,
            SET_MSR_TRACKSEPARATOR,

            GET_MSR_TRACK_SELECTION,
            SET_MSR_TRACK_SELECTION,

            GET_PARTIAL_DATA_MSR_START_POSITION,
            SET_PARTIAL_DATA_MSR_START_POSITION,

            GET_PARTIAL_DATA_MSR_LENGTH,
            SET_PARTIAL_DATA_MSR_LENGTH,

            GET_PARTIAL_DATA_MSR_ACTION,
            SET_PARTIAL_DATA_MSR_ACTION
    );

    enum TRACK {
        TRACK1(1),
        TRACK2(2),
        TRACK3(4);

        private int value;

        TRACK(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    public KMsrDelegate(KDCReader reader) {
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
            case ENABLE_MSR_ERROR_BEEP:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableMSRErrorBeep(paramBool, cb);
                    }
                });
                break;

            case IS_MSR_ERROR_BEEP_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isMSRErrorBeepEnabled(cb);
                    }
                });
                break;

            case ENABLE_MSR_SENTINEL:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableMSRSentinel(paramBool, cb);
                    }
                });
                break;

            case IS_MSR_SENTINEL_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isMSRSentinelEnabled(cb);
                    }
                });
                break;

            case GET_MSR_CARD_TYPE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMSRCardType(cb);
                    }
                });
                break;

            case SET_MSR_CARD_TYPE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMSRCardType(paramInt, cb);
                    }
                });
                break;

            case GET_MSR_DATA_TYPE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMSRDataType(cb);
                    }
                });
                break;

            case SET_MSR_DATA_TYPE:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMSRDataType(paramInt, cb);
                    }
                });
                break;

            case GET_MSR_DATA_ENCRYPTION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMSRDataEncryption(cb);
                    }
                });
                break;

            case SET_MSR_DATA_ENCRYPTION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMSRDataEncryption(paramInt, cb);
                    }
                });
                break;

            case GET_MSR_TRACKSEPARATOR:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMSRTrackSeparator(cb);
                    }
                });
                break;

            case SET_MSR_TRACKSEPARATOR:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMSRTrackSeparator(paramInt, cb);
                    }
                });
                break;

            case GET_MSR_TRACK_SELECTION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getMSRTrackSelection(cb);
                    }
                });
                break;

            case SET_MSR_TRACK_SELECTION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setMSRTrackSelection(paramInt, cb);
                    }
                });
                break;

            case GET_PARTIAL_DATA_MSR_START_POSITION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getPartialDataMSRStartPosition(cb);
                    }
                });
                break;

            case SET_PARTIAL_DATA_MSR_START_POSITION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setPartialDataMSRStartPosition(paramInt, cb);
                    }
                });
                break;

            case GET_PARTIAL_DATA_MSR_LENGTH:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getPartialDataMSRLength(cb);
                    }
                });
                break;

            case SET_PARTIAL_DATA_MSR_LENGTH:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setPartialDataMSRLength(paramInt, cb);
                    }
                });
                break;

            case GET_PARTIAL_DATA_MSR_ACTION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getPartialDataMSRAction(cb);
                    }
                });
                break;

            case SET_PARTIAL_DATA_MSR_ACTION:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setPartialDataMSRAction(paramInt, cb);
                    }
                });
                break;
        }

        return true;
    }

    private void enableMSRErrorBeep(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableMSRErrorBeep(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isMSRErrorBeepEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsMSRErrorBeepEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableMSRSentinel(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.EnableMSRSentinel(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isMSRSentinelEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsMSRSentinelEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void getMSRCardType(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.MSRCardType type = reader.GetMSRCardType();

        if (type != null) {
            cb.success(type.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMSRCardType(int type, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.MSRCardType t : KDCConstants.MSRCardType.values()) {
            if (type == t.ordinal()) {
                bRet = reader.SetMSRCardType(t);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getMSRDataType(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.MSRDataType type = reader.GetMSRDataType();

        if (type != null) {
            cb.success(type.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMSRDataType(int type, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.MSRDataType t : KDCConstants.MSRDataType.values()) {
            if (type == t.ordinal()) {
                bRet = reader.SetMSRDataType(t);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getMSRDataEncryption(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.MSRDataEncryption encryption = reader.GetMSRDataEncryption();

        if (encryption != null) {
            cb.success(encryption.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMSRDataEncryption(int encryption, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.MSRDataEncryption e : KDCConstants.MSRDataEncryption.values()) {
            if (encryption == e.ordinal()) {
                bRet = reader.SetMSRDataEncryption(e);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getMSRTrackSeparator(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.MSRTrackSeparator separator = reader.GetMSRTrackSeparator();

        if (separator != null) {
            cb.success(separator.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMSRTrackSeparator(int separator, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.MSRTrackSeparator s : KDCConstants.MSRTrackSeparator.values()) {
            if (separator == s.ordinal()) {
                bRet = reader.SetMSRTrackSeparator(s);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getMSRTrackSelection(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCMSRTrack track = reader.GetMSRTrackSelection();

        if (track != null) {
            int selection = 0;

            if (track.IsTrack1Enabled()) {
                selection |= TRACK.TRACK1.getValue();
            }

            if (track.IsTrack2Enabled()) {
                selection |= TRACK.TRACK2.getValue();
            }

            if (track.IsTrack3Enabled()) {
                selection |= TRACK.TRACK3.getValue();
            }

            cb.success(selection);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setMSRTrackSelection(int selection, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCMSRTrack track = new KDCMSRTrack(null);

        if ((selection & TRACK.TRACK1.getValue()) > 0) {
            track.Enable(KDCConstants.MSRTrack.TRACK1, true);
        }

        if ((selection & TRACK.TRACK2.getValue()) > 0) {
            track.Enable(KDCConstants.MSRTrack.TRACK2, true);
        }

        if ((selection & TRACK.TRACK3.getValue()) > 0) {
            track.Enable(KDCConstants.MSRTrack.TRACK3, true);
        }

        boolean bRet = reader.SetMSRTrackSelection(track);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getPartialDataMSRStartPosition(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int position = reader.GetPartialDataMSRStartPosition();

        if (position > -1) {
            cb.success(position);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setPartialDataMSRStartPosition(int position, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetPartialDataMSRStartPosition(position);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getPartialDataMSRLength(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int length = reader.GetPartialDataMSRLength();

        if (length > -1) {
            cb.success(length);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setPartialDataMSRLength(int length, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = reader.SetPartialDataMSRLength(length);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getPartialDataMSRAction(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.PartialDataAction action = reader.GetPartialDataMSRAction();

        if (action != null) {
            cb.success(action.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setPartialDataMSRAction(int action, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.PartialDataAction a : KDCConstants.PartialDataAction.values()) {
            if (action == a.ordinal()) {
                bRet = reader.SetPartialDataMSRAction(a);
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }
}
