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
import koamtac.kdc.sdk.plugin.KConverter;

public class KWiFiDelegate extends KReaderDelegate {
    private static final String IS_WIFI_INSTALLED = "isWiFiInstalled";

    private static final String ENABLE_WIFI_POWER = "enableWiFiPower";
    private static final String IS_WIFI_POWER_ENABLED = "isWiFiPowerEnabled";

    private static final String ENABLE_WIFI_AUTO_CONNECT = "enableWiFiAutoConnect";
    private static final String IS_WIFI_AUTO_CONNECT_ENABLED = "isWiFiAutoConnectEnabled";

    private static final String SET_WIFI_SERVER_IP_ADDRESS = "setWiFiServerIPAddress";
    private static final String GET_WIFI_SERVER_IP_ADDRESS = "getWiFiServerIPAddress";

    private static final String SET_WIFI_SERVER_URL_ADDRESS = "setWiFiServerURLAddress";
    private static final String GET_WIFI_SERVER_URL_ADDRESS = "getWiFiServerURLAddress";

    private static final String SET_WIFI_SERVER_PORT_NUMBER = "setWiFiServerPortNumber";
    private static final String GET_WIFI_SERVER_PORT_NUMBER = "getWiFiServerPortNumber";

    private static final String SET_WIFI_PROTOCOL = "setWiFiProtocol";
    private static final String GET_WIFI_PROTOCOL = "getWiFiProtocol";

    private static final String ENABLE_WIFI_SSL = "enableWiFiSSL";
    private static final String IS_WIFI_SSL_ENABLED = "isWiFiSSLEnabled";

    private static final String SET_WIFI_SERVER_PAGE = "setWiFiServerPage";
    private static final String GET_WIFI_SERVER_PAGE = "getWiFiServerPage";

    private static final String SET_WIFI_CERTIFICATION = "setWiFiCertification";
    private static final String GET_WIFI_CERTIFICATION = "getWiFiCertification";

    private static final String SET_WIFI_AP_SSID = "setWiFiApSSID";
    private static final String GET_WIFI_AP_SSID = "getWiFiApSSID";

    private static final String SET_WIFI_AP_PASSCODE = "setWiFiApPasscode";
    private static final String GET_WIFI_AP_PASSCODE = "getWiFiApPasscode";


    private static final List<String> SUPPORTED_ACTIONS = Arrays.asList(
        IS_WIFI_INSTALLED,

        ENABLE_WIFI_POWER,
        IS_WIFI_POWER_ENABLED,

        ENABLE_WIFI_AUTO_CONNECT,
        IS_WIFI_AUTO_CONNECT_ENABLED,

        SET_WIFI_SERVER_IP_ADDRESS,
        GET_WIFI_SERVER_IP_ADDRESS,

        SET_WIFI_SERVER_URL_ADDRESS,
        GET_WIFI_SERVER_URL_ADDRESS,

        SET_WIFI_SERVER_PORT_NUMBER,
        GET_WIFI_SERVER_PORT_NUMBER,

        SET_WIFI_PROTOCOL,
        GET_WIFI_PROTOCOL,

        ENABLE_WIFI_SSL,
        IS_WIFI_SSL_ENABLED,

        SET_WIFI_SERVER_PAGE,
        GET_WIFI_SERVER_PAGE,

        SET_WIFI_CERTIFICATION,
        GET_WIFI_CERTIFICATION,

        SET_WIFI_AP_SSID,
        GET_WIFI_AP_SSID,

        SET_WIFI_AP_PASSCODE,
        GET_WIFI_AP_PASSCODE
    );

    public KWiFiDelegate(KDCReader reader) {
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
        JSONArray paramArray;

        switch (action) {
            case IS_WIFI_INSTALLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isWiFiInstalled(cb);
                    }
                });
                break;

            case ENABLE_WIFI_POWER:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableWiFiPower(paramBool, cb);
                    }
                });
                break;

            case IS_WIFI_POWER_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isWiFiPowerEnabled(cb);
                    }
                });
                break;

            case ENABLE_WIFI_AUTO_CONNECT:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableWiFiAutoConnect(paramBool, cb);
                    }
                });
                break;

            case IS_WIFI_AUTO_CONNECT_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isWiFiAutoConnectEnabled(cb);
                    }
                });
                break;

            case SET_WIFI_SERVER_IP_ADDRESS:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiServerIPAddress(paramStr, cb);
                    }
                });
                break;

            case GET_WIFI_SERVER_IP_ADDRESS:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiServerIPAddress(cb);
                    }
                });
                break;

            case SET_WIFI_SERVER_URL_ADDRESS:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiServerURLAddress(paramStr, cb);
                    }
                });
                break;

            case GET_WIFI_SERVER_URL_ADDRESS:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiServerURLAddress(cb);
                    }
                });
                break;

            case SET_WIFI_SERVER_PORT_NUMBER:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiServerPortNumber(paramInt, cb);
                    }
                });
                break;

            case GET_WIFI_SERVER_PORT_NUMBER:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiServerPortNumber(cb);
                    }
                });
                break;

            case SET_WIFI_PROTOCOL:
                paramInt = args.getInt(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiProtocol(paramInt, cb);
                    }
                });
                break;

            case GET_WIFI_PROTOCOL:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiProtocol(cb);
                    }
                });
                break;

            case ENABLE_WIFI_SSL:
                paramBool = args.getBoolean(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        enableWiFiSSL(paramBool, cb);
                    }
                });
                break;

            case IS_WIFI_SSL_ENABLED:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        isWiFiSSLEnabled(cb);
                    }
                });
                break;

            case SET_WIFI_SERVER_PAGE:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiServerPage(paramStr, cb);
                    }
                });
                break;

            case GET_WIFI_SERVER_PAGE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiServerPage(cb);
                    }
                });
                break;

            case SET_WIFI_CERTIFICATION:
                paramArray = args.getJSONArray(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiCertification(paramArray, cb);
                    }
                });
                break;

            case GET_WIFI_CERTIFICATION:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiCertification(cb);
                    }
                });
                break;

            case SET_WIFI_AP_SSID:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiApSSID(paramStr, cb);
                    }
                });
                break;

            case GET_WIFI_AP_SSID:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiApSSID(cb);
                    }
                });
                break;

            case SET_WIFI_AP_PASSCODE:
                paramStr = args.getString(0);

                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        setWiFiApPasscode(paramStr, cb);
                    }
                });
                break;

            case GET_WIFI_AP_PASSCODE:
                cordova.getThreadPool().execute(() -> {
                    synchronized (mLock) {
                        getWiFiApPasscode(cb);
                    }
                });
                break;
        }

        return true;
    }

    private void isWiFiInstalled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsWiFiInstalled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableWiFiPower(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableWiFiPower(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isWiFiPowerEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsWiFiPowerEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void enableWiFiAutoConnect(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableWiFiAutoConnect(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isWiFiAutoConnectEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsWiFiAutoConnectEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void setWiFiServerIPAddress(String ip, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.SetWiFiServerIPAddress(ip);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiServerIPAddress(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String ip = reader.GetWiFiServerIPAddress();

        if (ip != null) {
            cb.success(ip);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setWiFiServerURLAddress(String url, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.SetWiFiServerURLAddress(url);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiServerURLAddress(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String url = reader.GetWiFiServerURLAddress();

        if (url != null) {
            cb.success(url);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setWiFiServerPortNumber(int port, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.SetWiFiServerPortNumber(port);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiServerPortNumber(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        int port = reader.GetWiFiServerPortNumber();


        if (port != -1) {
            cb.success(port);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setWiFiProtocol(int protocol, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = false;

        for (KDCConstants.WiFiProtocol p : KDCConstants.WiFiProtocol.values()) {
            if (protocol == p.ordinal()) {
                bRet = reader.SetWiFiProtocol(p);
                break;
            }
        }

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiProtocol(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        KDCConstants.WiFiProtocol p  = reader.GetWiFiProtocol();

        if (p != null) {
            cb.success(p.ordinal());
        } else {
            cb.error(error.FAILED);
        }
    }

    private void enableWiFiSSL(boolean e, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.EnableWiFiSSL(e);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void isWiFiSSLEnabled(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        boolean isEnabled = reader.IsWiFiSSLEnabled();

        PluginResult result = new PluginResult(PluginResult.Status.OK, isEnabled);

        cb.sendPluginResult(result);
    }

    private void setWiFiServerPage(String page, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.SetWiFiServerPage(page);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiServerPage(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String page = reader.GetWiFiServerPage();

        if (page != null) {
            cb.success(page);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setWiFiCertification(JSONArray array, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }


        boolean bRet = false;

        try {
            byte [] cert = KConverter.toBytes(array);

            if (cert != null) {
                bRet = reader.SetWiFiCertification(cert);
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

    private void getWiFiCertification(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        byte [] cert = reader.GetWiFiCertification();

        if (cert != null) {
            cb.success(cert);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setWiFiApSSID(String ssid, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.SetWiFiApSSID(ssid);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiApSSID(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String ssid = reader.GetWiFiApSSID();

        if (ssid != null) {
            cb.success(ssid);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void setWiFiApPasscode(String passcode, CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if (isNull(reader)) {
            cb.error(error.NULL);
            return;
        }

        boolean bRet = reader.SetWiFiApPasscode(passcode);

        if (bRet) {
            cb.success(error.SUCCESS);
        } else {
            cb.error(error.FAILED);
        }
    }

    private void getWiFiApPasscode(CallbackContext cb) {
        final KDCReader reader = wrReader.get();

        if(!isConnected(reader)) {
            cb.error(error.NOT_CONNECTED);
            return;
        }

        String passcode = reader.GetWiFiApPasscode();

        if (passcode != null) {
            cb.success(passcode);
        } else {
            cb.error(error.FAILED);
        }
    }

}