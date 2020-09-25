package koamtac.kdc.sdk.plugin;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import koamtac.kdc.sdk.KDCBarcodeDataReceivedListener;
import koamtac.kdc.sdk.KDCConnectionListenerEx;
import koamtac.kdc.sdk.KDCConstants;
import koamtac.kdc.sdk.KDCData;
import koamtac.kdc.sdk.KDCDataReceivedListener;
import koamtac.kdc.sdk.KDCDevice;
import koamtac.kdc.sdk.KDCMSRDataReceivedListener;
import koamtac.kdc.sdk.KDCNFCDataReceivedListener;
import koamtac.kdc.sdk.KDCReader;
import koamtac.kdc.sdk.KPOSData;
import koamtac.kdc.sdk.KPOSDataReceivedListener;
import koamtac.kdc.sdk.plugin.delegate.KBluetoothDelegate;
import koamtac.kdc.sdk.plugin.delegate.KCommonDelegate;
import koamtac.kdc.sdk.plugin.delegate.KDataProcessDelegate;
import koamtac.kdc.sdk.plugin.delegate.KDisplayDelegate;
import koamtac.kdc.sdk.plugin.delegate.KHidDelegate;
import koamtac.kdc.sdk.plugin.delegate.KKeypadDelegate;
import koamtac.kdc.sdk.plugin.delegate.KMsrDelegate;
import koamtac.kdc.sdk.plugin.delegate.KNfcDelegate;
import koamtac.kdc.sdk.plugin.delegate.KPosDelegate;
import koamtac.kdc.sdk.plugin.delegate.KReaderDelegate;
import koamtac.kdc.sdk.plugin.delegate.KSystemDelegate;
import koamtac.kdc.sdk.plugin.delegate.KUhfDelegate;
import koamtac.kdc.sdk.plugin.delegate.KVibratorDelegate;
import koamtac.kdc.sdk.plugin.delegate.KWiFiDelegate;

/**
 * This class echoes a string called from JavaScript.
 */
public class KReader extends CordovaPlugin implements KDCConnectionListenerEx,
        KDCBarcodeDataReceivedListener,
        KDCNFCDataReceivedListener,
        KDCMSRDataReceivedListener,
        KDCDataReceivedListener,
        KPOSDataReceivedListener {
    private static final String TAG = KReader.class.getSimpleName();

    private static final String SET_CONNECTION_LISTENER = "setConnectionListener";
    private static final String SET_BARCODE_LISTENER = "setBarcodeListener";
    private static final String SET_NFC_LISTENER = "setNFCListener";
    private static final String SET_MSR_LISTENER = "setMSRListener";
    private static final String SET_DATA_LISTENER = "setDataListener";
    private static final String SET_KPOS_LISTENER = "setKPOSListener";

    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
            SET_CONNECTION_LISTENER,
            SET_BARCODE_LISTENER,
            SET_NFC_LISTENER,
            SET_MSR_LISTENER,
            SET_DATA_LISTENER,
            SET_KPOS_LISTENER
    );

    private KDCReader kdcReader;

    private List<KReaderDelegate> delegates;

    private CallbackContext connectionCallback;

    private CallbackContext barcodeCallback;
    private CallbackContext nfcCallback;
    private CallbackContext msrCallback;
    private CallbackContext dataCallback;

    private CallbackContext kposCallback;

    @Override
    protected void pluginInitialize() {
        Log.d(TAG, "pluginInitialize");

        super.pluginInitialize();

        kdcReader = new KDCReader();

        kdcReader.SetConnectionMode(KDCConstants.ConnectionMode.BLUETOOTH_CLASSIC);
        kdcReader.SetContext(cordova.getContext());

        kdcReader.SetKDCConnectionListenerEx(this);

        kdcReader.SetBarcodeDataReceivedListener(this);
        kdcReader.SetNFCDataReceivedListener(this);
        kdcReader.SetMSRDataReceivedListener(this);
        kdcReader.SetDataReceivedListener(this);

        kdcReader.SetKPOSDataReceivedListener(this);

        delegates = new ArrayList<>();

        delegates.add(new KCommonDelegate(kdcReader));

        delegates.add(new KDataProcessDelegate(kdcReader));
        delegates.add(new KSystemDelegate(kdcReader));
        delegates.add(new KBluetoothDelegate(kdcReader));

        delegates.add(new KDisplayDelegate(kdcReader));
        delegates.add(new KVibratorDelegate(kdcReader));
        delegates.add(new KKeypadDelegate(kdcReader));
        delegates.add(new KHidDelegate(kdcReader));
        delegates.add(new KWiFiDelegate(kdcReader));

        delegates.add(new KNfcDelegate(kdcReader));
        delegates.add(new KMsrDelegate(kdcReader));

        delegates.add(new KUhfDelegate(kdcReader));

        delegates.add(new KPosDelegate(kdcReader));
    }

    @Override
    public void onPause(boolean multitasking) {
        Log.d(TAG, "onPause");
        super.onPause(multitasking);
    }

    @Override
    public void onResume(boolean multitasking) {
        Log.d(TAG, "onResume");
        super.onResume(multitasking);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    public void onReset() {
        Log.d(TAG, "onReset");
        super.onReset();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        kdcReader.Dispose();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, action);

        for(KReaderDelegate delegate : delegates) {
            if (delegate.isSupported(action)) {
                return delegate.run(cordova, action, args, callbackContext);
            }
        }

        if (!SUPPORTED_ACTIONS.contains(action)) {
            Log.d(TAG, "Invalid Action: " + action);
            callbackContext.error("Invalid Action: " + action);
            return false;
        }

        switch (action) {
            case SET_CONNECTION_LISTENER:
            case SET_BARCODE_LISTENER:
            case SET_NFC_LISTENER:
            case SET_MSR_LISTENER:
            case SET_DATA_LISTENER:
            case SET_KPOS_LISTENER:
                setListener(action, callbackContext);
                break;
        }

        return true;
    }

    @Override
    public void ConnectionChangedEx(KDCDevice<?> kdcDevice, int i) {
        PluginResult result;
        JSONObject jsonObject = null;

        if (connectionCallback != null) {
            try {
                jsonObject = KConverter.put(jsonObject, KConstants.DeviceKey.Device, KConverter.toJson(kdcDevice));
                jsonObject = KConverter.put(jsonObject, KConstants.ConnectionKey.State, i);

                result = new PluginResult(PluginResult.Status.OK, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();

                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }

            result.setKeepCallback(true);
            connectionCallback.sendPluginResult(result);
        }
    }


    @Override
    public void BarcodeDataReceived(KDCData kdcData) {
        PluginResult result;

        if (barcodeCallback != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(kdcData));
            } catch (JSONException e) {
                e.printStackTrace();

                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }

            result.setKeepCallback(true);
            barcodeCallback.sendPluginResult(result);
        }
    }

    @Override
    public void NFCDataReceived(KDCData kdcData) {
        PluginResult result;

        if (nfcCallback != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(kdcData));
            } catch (JSONException e) {
                e.printStackTrace();

                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }

            result.setKeepCallback(true);
            nfcCallback.sendPluginResult(result);
        }
    }

    @Override
    public void MSRDataReceived(KDCData kdcData) {
        PluginResult result;

        if (msrCallback != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(kdcData));
            } catch (JSONException e) {
                e.printStackTrace();

                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }

            result.setKeepCallback(true);
            msrCallback.sendPluginResult(result);
        }
    }

    @Override
    public void DataReceived(KDCData kdcData) {
        PluginResult result;

        if (dataCallback != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(kdcData));
            } catch (JSONException e) {
                e.printStackTrace();

                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }

            result.setKeepCallback(true);
            dataCallback.sendPluginResult(result);
        }
    }

    @Override
    public void POSDataReceived(KPOSData kposData) {
        PluginResult result;

        if (kposCallback != null) {
            try {
                result = new PluginResult(PluginResult.Status.OK, KConverter.toJson(kposData));
            } catch (JSONException e) {
                e.printStackTrace();

                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }

            result.setKeepCallback(true);
            kposCallback.sendPluginResult(result);
        }
    }

    private void setListener(String action, CallbackContext cb) {
        switch (action) {
            case SET_CONNECTION_LISTENER:
                connectionCallback = cb;
                break;

            case SET_BARCODE_LISTENER:
                barcodeCallback = cb;
                break;

            case SET_NFC_LISTENER:
                nfcCallback = cb;
                break;

            case SET_MSR_LISTENER:
                msrCallback = cb;
                break;

            case SET_DATA_LISTENER:
                dataCallback = cb;
                break;

            case SET_KPOS_LISTENER:
                kposCallback = cb;
                break;
        }
    }
}
