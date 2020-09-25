package koamtac.kdc.sdk.plugin.delegate;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import koamtac.kdc.sdk.KDCConstants;
import koamtac.kdc.sdk.KDCDevice;
import koamtac.kdc.sdk.KDCDeviceInfo;
import koamtac.kdc.sdk.KDCReader;
import koamtac.kdc.sdk.plugin.KConstants;
import koamtac.kdc.sdk.plugin.KConverter;

public class KCommonDelegate extends KReaderDelegate {
    private static final String GET_AVAIL_DEVICE_LIST = "getAvailDeviceList";

    private static final String ENABLE_ATTACH_TYPE = "enableAttachType";
    private static final String ENABLE_ATTACH_SERIAL_NUMBER = "enableAttachSerialNumber";
    private static final String ENABLE_ATTACH_TIMESTAMP = "enableAttachTimestamp";

    private static final String SET_DATA_DELIMITER = "setDataDelimiter";
    private static final String SET_RECORD_DELIMITER = "setRecordDelimiter";

    private static final String GET_DEVICE_INFO = "getDeviceInfo";

    private static final String IS_CONNECTED = "isConnected";

    private static final String CONNECT = "connect";
    private static final String DISCONNECT = "disconnect";

    private static final String SOFTWARE_TRIGGER = "softwareTrigger";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            GET_AVAIL_DEVICE_LIST,

            ENABLE_ATTACH_TYPE,
            ENABLE_ATTACH_SERIAL_NUMBER,
            ENABLE_ATTACH_TIMESTAMP,

            SET_DATA_DELIMITER,
            SET_RECORD_DELIMITER,

            GET_DEVICE_INFO,

            IS_CONNECTED,

            CONNECT,
            DISCONNECT,

            SOFTWARE_TRIGGER
    );

    public KCommonDelegate(KDCReader reader) {
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
            case GET_AVAIL_DEVICE_LIST:
                getAvailDeviceList(args.getString(0), cb);
                break;

            case ENABLE_ATTACH_TYPE:
                enableAttachType(args.getBoolean(0), cb);
                break;

            case ENABLE_ATTACH_SERIAL_NUMBER:
                enableAttachSerialNumber(args.getBoolean(0), cb);
                break;

            case ENABLE_ATTACH_TIMESTAMP:
                enableAttachTimestamp(args.getBoolean(0), cb);
                break;

            case SET_DATA_DELIMITER:
                setDataDelimiter(args.getInt(0), cb);
                break;

            case SET_RECORD_DELIMITER:
                setRecordDelimiter(args.getInt(0), cb);
                break;

            case GET_DEVICE_INFO:
                getDeviceInfo(cb);
                break;

            case IS_CONNECTED:
                isConnected(cb);
                break;

            case CONNECT:
                paramObject = args.getJSONObject(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        connect(paramObject, cb);
                    }
                });
                break;

            case DISCONNECT:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        disconnect(cb);
                    }
                });
                break;

            case SOFTWARE_TRIGGER:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        softwareTrigger(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void getAvailDeviceList(String type, CallbackContext cb) throws JSONException {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        cb.success(KConverter.toJsonArray(reader.GetAvailableDeviceListEx(type)));
    }

    private void enableAttachType(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        reader.EnableAttachType(e);
        cb.sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
    }

    private void enableAttachSerialNumber(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        reader.EnableAttachSerialNumber(e);
        cb.sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
    }

    private void enableAttachTimestamp(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        reader.EnableAttachTimestamp(e);
        cb.sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
    }

    private void setDataDelimiter(int d, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        for (KDCConstants.DataDelimiter delimiter : KDCConstants.DataDelimiter.values()) {
            if (delimiter.ordinal() == d) {
                reader.SetDataDelimiter(delimiter);
            }
        }
        cb.sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
    }

    private void setRecordDelimiter(int d, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        for (KDCConstants.RecordDelimiter delimiter : KDCConstants.RecordDelimiter.values()) {
            if (delimiter.ordinal() == d) {
                reader.SetRecordDelimiter(delimiter);
            }
        }
        cb.sendPluginResult(new PluginResult(PluginResult.Status.NO_RESULT));
    }

    private void getDeviceInfo(CallbackContext cb) throws JSONException {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCDeviceInfo info = reader.GetKDCDeviceInfo();

        cb.success(KConverter.toJson(info));
    }

    private void isConnected(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        cb.sendPluginResult(new PluginResult(PluginResult.Status.OK, reader.IsConnected()));
    }

    private void connect(JSONObject object, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = false;

        try {
            if (!object.isNull(KConstants.DeviceKey.DeviceName)) {
                String name = object.getString(KConstants.DeviceKey.DeviceName);

                if (name != null) {
                    List<List<KDCDevice<?>>> lists = new ArrayList<>();

                    lists.add(reader.GetAvailableDeviceListEx(KDCConstants.DeviceListType.ATTACHED_USB_LIST));
                    lists.add(reader.GetAvailableDeviceListEx(KDCConstants.DeviceListType.BONDED_BLUETOOTH_LIST));
                    lists.add(reader.GetAvailableDeviceListEx(KDCConstants.DeviceListType.SCANNED_BLUETOOTH_LIST));

                    for (List<KDCDevice<?>> list : lists) {
                        for (KDCDevice d : list) {
                            String dName = d.GetDeviceName();

                            if (dName != null && dName.equals(name)) {
                                bRet = reader.ConnectEx(d);
                                break;
                            }
                        }
                    }
                }
            } else {
                bRet = reader.ConnectEx();
            }
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

    private void disconnect(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        reader.Disconnect();
        cb.success(error.SUCCESS);
    }

    private void softwareTrigger(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        reader.SoftwareTrigger();
        cb.success(error.SUCCESS);
    }
}
