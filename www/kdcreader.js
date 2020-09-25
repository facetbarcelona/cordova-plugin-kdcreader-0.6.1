/**
 * @module KDCReader
 */
var exec = require('cordova/exec');

/**
 * The object that is passed as a parameter when the listener is called.
 * The validity of each data is determined by the data type.
 * @typedef {Object} KDCData
 * @property {module:KDCConstants.DataType} type - data type
 * @property {string} data - received data
 * @property {number[]} dataBytes - received data as byte array
 * @property {string} [barcode] - Barcode data
 * @property {string} [gps] - GPS data 
 * @property {string} [msr] - MSR data
 * @property {number[]} [msrBytes] - MSR data as byte array
 * @property {module:KDCConstants.NFCTag} [nfcType] - NFC tag type
 * @property {string} [nfcUid] - NFC UID
 * @property {string} [nfc] - NFC hex string
 * @property {number[]} [nfcBytes] - NFC data as byte array
 * @property {module:KDCConstants.UHFDataType} [uhfListType] - UHF data list type
 * @property {string[]} [uhfList] - UHF data list when burst mode is enabled
 * @property {number[]} [uhfRssiList] - UHF RSSI list when burst mode is enabled
 * @property {string} [key] - Key Event
 * @property {string} record - Record data
 */

 /**
  * Callback that pass received data object.
  * 
  * @callback dataListener
  * @param {KDCData} data - received data
  */

/**
 * The object that has KDC device information. This information is required to establish a connection.
 * @typedef {Object} KDCDevice
 * @property {string} [type] - connection main type
 * @property {string} [subType] - connection sub type
 * @property {string} deviceName - Remote device name to distinguish
 * @property {string} [kdcName] - KDC name of remote device 
 */
 
 /**
  * Callback that pass available device list.
  * 
  * @callback deviceCallback
  * @param {KDCDevice[]} devices - available device list.
  */

/**
 * The object that has connection information.
 * @typedef {Object} ConnectNotification
 * @property {KDCDevice} [device] - related device information.
 * @property {module:KDCConstants.ConnectionState} state - connection state
 */

 /**
  * Callback that pass connection information object when connection state is changed.
  * 
  * @callback connectListener
  * @param {ConnectNotification} notification - connection information.
  */ 

 /**
  * The object that has result information.
  * 
  * @typedef {Object} Result
  * @property {module:KDCConstants.ResultCode} code - result code
  * @property {string} message - result description message
  */

 /**
  * Callback that pass result information object.
  * 
  * @callback resultCallback
  * @param {Result} result - operation result.
  */

 /**
  * Callback that pass returned value.
  * The type of returned value is depened on which API is called.
  * 
  * @callback returnCallback
  * @param {number|boolean} result - result value.
  * Depending on the function called, the specific return value is one of the enumeration values.
  */

/**
 * @exports KDCReader
 */
var kreader = {
    /*
     * Register Listener
     */

    /**
     * Register connection state changed listener.
     *
     * @param {connectListener} callback fired when connection state is changed.
     */     
    setConnectionListener: function (callback) {
        exec(callback, e => { }, 'KReader', 'setConnectionListener', []);
    },

    /**
     * Register Barcode data received listener.
     *
     * @param {dataListener} callback fired when Barcode data is received.
     */      
    setBarcodeListener: function (callback) {
        exec(callback, e => { }, 'KReader', 'setBarcodeListener', []);
    },

    /**
     * Register NFC data received listener.
     *
     * @param {dataListener} callback fired when NFC data is received.
     */      
    setNFCListener: function (callback) {
        exec(callback, e => { }, 'KReader', 'setNFCListener', []);
    },

    /**
     * Register MSR data received listener.
     *
     * @param {dataListener} callback fired when MSR data is received.
     */      
    setMSRListener: function (callback) {
        exec(callback, e => { }, 'KReader', 'setMSRListener', []);
    },

    /**
     * Register generic data received listener.
     *
     * @param {dataListener} callback fired when generic data is received.
     */      
    setDataListener: function (callback) {
        exec(callback, e => { }, 'KReader', 'setDataListener', []);
    },

    /**
     * Register POS data received listener.
     *
     * @param {dataListener} callback fired when POS data is received.
     */      
    setKPOSListener: function (callback) {
        exec(callback, e => { }, 'KReader', 'setKPOSListener', []);
    },

    /*
     * Common APIs (Connection, KDCReader Configuration, SoftwareTrigger)
     */
    
    /**
     * Get available KDC device list.
     *
     * @param {module:KDCConstants.DeviceListType} type device list type to be obtained.
     * @param {deviceCallback} callback fired with available device list.
     */       
    getAvailDeviceList: function (type, callback) {
        exec(callback, e => { }, 'KReader', 'getAvailDeviceList', [type]);
    },

    /**
     * Decide whether to attach the type when creating a data record.
     *
     * @param {boolean} enable true if enable, otherwise false.
     */    
    enableAttachType: function (enable) {
        exec(r => { }, e => { }, 'KReader', 'enableAttachType', [enable]);
    },

    /**
     * Decide whether to attach the serial number when creating a data record.
     *
     * @param {boolean} enable true if enable, otherwise false.
     */    
    enableAttachSerialNumber: function (enable) {
        exec(r => { }, e => { }, 'KReader', 'enableAttachSerialNumber', [enable]);
    },

    /**
     * Decide whether to attach the timestamp when creating a data record.
     *
     * @param {boolean} enable true if enable, otherwise false.
     */    
    enableAttachTimestamp: function (enable) {
        exec(r => { }, e => { }, 'KReader', 'enableAttachTimestamp', [enable]);
    },

    /**
     * Select data delimiter when creating a data record.
     *
     * @param {module:KDCConstants.DataDelimiter} delimiter delimiter to be set.
     */    
    setDataDelimiter: function (delimiter) {
        exec(r => { }, e => { }, 'KReader', 'setDataDelimiter', [delimiter]);
    },

    /**
     * Select record delimiter when creating a data record.
     *
     * @param {module:KDCConstants.RecordDelimiter} delimiter delimiter to be set.
     */    
    setRecordDelimiter: function (delimiter) {
        exec(r => { }, e => { }, 'KReader', 'setRecordDelimiter', [delimiter]);
    },

    getDeviceInfo: function (success, error) {
        exec(success, error, 'KReader', 'getDeviceInfo', []);
    },

    /**
     * Connect to the target KDC device.
     * If the specific device is not provided, KDCReader will attempt to connect to all available devices.
     *
     * @param {resultCallback} success callback when connection request is accepted.
     * @param {resultCallback} error callback when error occurred.
     * @param {KDCDevice} [ device ] The KDC device to be connected.
     */      
    connect: function (success, error, device) {
        var d = device || {} ;
        exec(success, error, 'KReader', 'connect', [d]);
    },

    /**
     * Disconnect current connection.
     *
     * @param {resultCallback} success callback when successful.
     * @param {resultCallback} error callback when failed.
     */     
    disconnect: function (success, error) {
        exec(success, error, 'KReader', 'disconnect', []);
    },

    /**
     * Check if connection is established.
     *
     * @param {returnCallback} callback return true if connected, otherwise false.
     */      
    isConnected: function (callback) {
        exec(callback, e => { }, 'KReader', 'isConnected', []);
    },

    /**
     * Scan Barcode or Read UHF tag. This behavior is depended on configuration of the KDC device.
     *
     * @param {resultCallback} success callback when successful.
     * @param {resultCallback} error callback when failed.
     */     
    softwareTrigger: function (success, error) {
        exec(success, error, 'KReader', 'softwareTrigger', []);
    },

    /*
     * Data Process APIs (Connection, KDCReader Configuration, SoftwareTrigger)
     */
    getDataPrefix: function (success, error) {
        exec(success, error, 'KReader', 'getDataPrefix', []);
    },

    setDataPrefix: function (prefix, success, error) {
        exec(success, error, 'KReader', 'setDataPrefix', [prefix]);
    },

    getDataSuffix: function (success, error) {
        exec(success, error, 'KReader', 'getDataSuffix', []);
    },

    setDataSuffix: function (suffix, success, error) {
        exec(success, error, 'KReader', 'setDataSuffix', [suffix]);
    },  

    getSymbology: function (success, error) {
        exec(success, error, 'KReader', 'getSymbology', []);
    },

    setSymbology: function (symbol, success, error) {
        exec(success, error, 'KReader', 'setSymbology', [symbol]);
    },
    
    enableAllSymbology: function (success, error) {
        exec(success, error, 'KReader', 'enableAllSymbology', []);
    },

    disableAllSymbology: function (success, error) {
        exec(success, error, 'KReader', 'disableAllSymbology', []);
    },
    
    getBarcodeOption: function (success, error) {
        exec(success, error, 'KReader', 'getBarcodeOption', []);
    },

    setBarcodeOption: function (option, success, error) {
        exec(success, error, 'KReader', 'setBarcodeOption', [option]);
    },
    
    enableAllOptions: function (success, error) {
        exec(success, error, 'KReader', 'enableAllOptions', []);
    },

    disableAllOptions: function (success, error) {
        exec(success, error, 'KReader', 'disableAllOptions', []);
    },

    getMinimumBarcodeLength: function (success, error) {
        exec(success, error, 'KReader', 'getMinimumBarcodeLength', []);
    },

    setMinimumBarcodeLength: function (length, success, error) {
        exec(success, error, 'KReader', 'setMinimumBarcodeLength', [length]);
    },

    /**
     * Get number of stored barcodes in the KDC internal flash memory.
     * 
     * @param {returnCallback} success callback when successful with the number of stored barcodes.
     * @param {resultCallback} error callback when failed.
     */      
    getNumberOfStoredBarcode: function (success, error) {
        exec(success, error, 'KReader', 'getNumberOfStoredBarcode', []);
    },

    /**
     * Get the all sotred barcodes in the KDC internal flash memory.
     * The all stored barcode is passed to the application via listener one by one.
     * 
     * @param {resultCallback} success callback when successful.
     * @param {resultCallback} error callback when failed.
     */       
    getStoredDataSingle: function (success, error) {
        exec(success, error, 'KReader', 'getStoredDataSingle', []);
    },

    /**
     * Get current setting of data process mode of KDC device.
     * 
     * @param {returnCallback} success callback when successful with data process mode. {@link module:KDCConstants.DataProcess}
     * @param {resultCallback} error callback when failed.
     */       
    getDataProcessMode: function (success, error) {
        exec(success, error, 'KReader', 'getDataProcessMode', []);
    },

    /**
     * Set data process mode of the KDC device.
     *
     * @param {module:KDCConstants.DataProcess} mode data process mode to be set.
     * @param {resultCallback} success callback when successful.
     * @param {resultCallback} error callback when failed.
     */    
    setDataProcessMode: function (mode, success, error) {
        exec(success, error, 'KReader', 'setDataProcessMode', [mode]);
    },

    getSecurityLevel: function (success, error) {
        exec(success, error, 'KReader', 'getSecurityLevel', []);
    },

    setSecurityLevel: function (mode, success, error) {
        exec(success, error, 'KReader', 'setSecurityLevel', [mode]);
    },

    enableAgeVerify: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableAgeVerify', [enable]);
    },

    isAgeVerifyEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isAgeVerifyEnabled', []);
    },

    getAIMIDSetting: function (success, error) {
        exec(success, error, 'KReader', 'getAIMIDSetting', []);
    },

    setAIMIDSetting: function (status, success, error) {
        exec(success, error, 'KReader', 'setAIMIDSetting', [status]);
    },

    getPartialDataStartPosition: function (success, error) {
        exec(success, error, 'KReader', 'getPartialDataStartPosition', []);
    },

    setPartialDataStartPosition: function (status, success, error) {
        exec(success, error, 'KReader', 'setPartialDataStartPosition', [status]);
    },

    getPartialDataLength: function (success, error) {
        exec(success, error, 'KReader', 'getPartialDataLength', []);
    },

    setPartialDatalength: function (status, success, error) {
        exec(success, error, 'KReader', 'setPartialDatalength', [status]);
    },

    getPartialDataAction: function (success, error) {
        exec(success, error, 'KReader', 'getPartialDataAction', []);
    },

    setPartialDataAction: function (status, success, error) {
        exec(success, error, 'KReader', 'setPartialDataAction', [status]);
    },

    startSynchronization: function (success, error) {
        exec(success, error, 'KReader', 'startSynchronization', []);
    },

    finishSynchronization: function (success, error) {
        exec(success, error, 'KReader', 'finishSynchronization', []);
    },

    getDataTerminator: function (success, error) {
        exec(success, error, 'KReader', 'getDataTerminator', []);
    },

    setDataTeminator: function (terminator, success, error) {
        exec(success, error, 'KReader', 'setDataTeminator', [terminator]);
    },

    /*
     * System Configuration APIs - Lock, Battery Level
     */
    enableButtonLock: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableButtonLock', [enable]);
    },

    enableScanButtonLock: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableScanButtonLock', [enable]);
    },

    getBatteryLevel: function (success, error) {
        exec(success, error, 'KReader', 'getBatteryLevel', []);
    },

    getExtendedBatteryLevel(success, error) {
        exec(success, error, 'KReader', 'getExtendedBatteryLevel', [pwd]);
    },  

    /*
     * System Configuration APIs - Time
     */    
    syncSystemTime: function (success, error) {
        exec(success, error, 'KReader', 'syncSystemTime', []);
    },

    resetSystemTime: function (success, error) {
        exec(success, error, 'KReader', 'resetSystemTime', []);
    },

    /**
     * Get current KDC device sleep timeout duration.
     * 
     * @param {returnCallback} success callback when successful with timeout value. {@link module:KDCConstants.SleepTimeout}
     * @param {resultCallback} error callback when failed.
     */       
    getSleepTimeout: function (success, error) {
        exec(success, error, 'KReader', 'getSleepTimeout', []);
    },

    /**
     * Set KDC device sleep timeout duration.
     * 
     * @param {module:KDCConstants.SleepTimeout} timeout sleep timeout duration to be set.
     * @param {resultCallback} success callback when successful.
     * @param {resultCallback} error callback when failed.
     */       
    setSleepTimeout: function (timeout, success, error) {
        exec(success, error, 'KReader', 'setSleepTimeout', [timeout]);
    },

    /**
     * Get current barcode read operation timeout.
     * 
     * @param {returnCallback} success callback when successful with timeout value.
     * @param {resultCallback} error callback when failed.
     */      
    getScanTimeout: function (success, error) {
        exec(success, error, 'KReader', 'getScanTimeout', []);
    },

    /**
     * Set the timeout for barcode read operation.
     * 
     * @param {number} timeout barcode read timeout to be set. [100, 10000]
     * @param {resultCallback} success callback when successful.
     * @param {resultCallback} error callback when failed.
     */      
    setScanTimeout: function (timeout, success, error) {
        exec(success, error, 'KReader', 'setScanTimeout', [timeout]);
    },

    /*
     * System Configuration APIs - Memory
     */      
    eraseMemory: function (success, error) {
        exec(success, error, 'KReader', 'eraseMemory', []);
    },

    eraseLastData: function (success, error) {
        exec(success, error, 'KReader', 'eraseLastData', []);
    },

    getMemoryLeft: function (success, error) {
        exec(success, error, 'KReader', 'getMemoryLeft', []);
    },

    setFactoryDefault: function (success, error) {
        exec(success, error, 'KReader', 'setFactoryDefault', []);
    },

    /*
     * System Configuration APIs - Beep
     */
    enableHighBeepVolume: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableHighBeepVolume', [enable]);
    },

    isHighBeepVolumeEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isHighBeepVolumeEnabled', []);
    },

    enableBeepSound: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBeepSound', [enable]);
    },

    isBeepSoundEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBeepSoundEnabled', []);
    },

    enablePowerOnBeep: function (enable, success, error) {
        exec(success, error, 'KReader', 'enablePowerOnBeep', [enable]);
    },

    isPowerOnBeepEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isPowerOnBeepEnabled', []);
    },

    enableBeepOnScan: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBeepOnScan', [enable]);
    },

    isBeepOnScanEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBeepOnScanEnabled', []);
    },

    enableBeepOnConnect: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBeepOnConnect', [enable]);
    },

    isBeepOnConnectEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBeepOnConnectEnabled', []);
    },

    setFailureAlertBeep: function (success, error) {
        exec(success, error, 'KReader', 'setFailureAlertBeep', []);
    },

    setSuccessAlertBeep: function (success, error) {
        exec(success, error, 'KReader', 'setSuccessAlertBeep', []);
    },

    setCustomBeepTone: function (onTime, offTime, repeat, success, error) {
        exec(success, error, 'KReader', 'setCustomBeepTone', [onTime, offTime, repeat]);
    },

    /*
     * System Configuration APIs - MFi
     */
    enableMFiMode: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableMFiMode', [enable]);
    },

    isMFiEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isMFiEnabled', []);
    },

    isMFiAuthChipInstalled: function (success, error) {
        exec(success, error, 'KReader', 'isMFiAuthChipInstalled', []);
    },
    
    /*
     * System Configuration APIs - Bluetooth
     */
    enableBluetoothAutoConnect: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothAutoConnect', [enable]);
    },

    isAutoConnectEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isAutoConnectEnabled', []);
    },

    enableBluetoothAutoPowerOn: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothAutoPowerOn', [enable]);
    },

    isAutoPowerOnEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isAutoPowerOnEnabled', []);
    },

    enableBluetoothAutoPowerOff: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothAutoPowerOff', [enable]);
    },

    isBluetoothAutoPowerOffEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBluetoothAutoPowerOffEnabled', []);
    },
    
    enableBluetoothBeepWarning: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothBeepWarning', [enable]);
    },

    isBluetoothBeepWarningEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBluetoothBeepWarningEnabled', []);
    },
    
    getDeviceProfile: function (success, error) {
        exec(success, error, 'KReader', 'getDeviceProfile', []);
    },

    setDeviceProfile: function (profile, success, error) {
        exec(success, error, 'KReader', 'setDeviceProfile', [profile]);
    },
    
    getBluetoothAutoPowerOffTimeout: function (success, error) {
        exec(success, error, 'KReader', 'getBluetoothAutoPowerOffTimeout', []);
    },

    setBluetoothAutoPowerOffTimeout: function (timeout, success, error) {
        exec(success, error, 'KReader', 'setBluetoothAutoPowerOffTimeout', [timeout]);
    },
    
    enableBluetoothPowerOffMessage: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothPowerOffMessage', [enable]);
    },

    isPowerOffMessageEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isPowerOffMessageEnabled', []);
    },
    
    getBluetoothMACAddress: function (success, error) {
        exec(success, error, 'KReader', 'getBluetoothMACAddress', []);
    },

    getBluetoothAutoPowerOnDelay: function (success, error) {
        exec(success, error, 'KReader', 'getBluetoothAutoPowerOnDelay', []);
    },

    setBluetoothAutoPowerOnDelay: function (timeout, success, error) {
        exec(success, error, 'KReader', 'setBluetoothAutoPowerOnDelay', [timeout]);
    },

    getBluetoothFirmwareVersion: function (success, error) {
        exec(success, error, 'KReader', 'getBluetoothFirmwareVersion', []);
    },

    enableBluetoothWakeupNull: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothWakeupNull', [enable]);
    },

    isWakeupNullsEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isWakeupNullsEnabled', []);
    },

    enableBluetoothToggle: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothToggle', [enable]);
    },

    isBluetoothToggleEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBluetoothToggleEnabled', []);
    },

    enableBluetoothDisconnectButton: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableBluetoothDisconnectButton', [enable]);
    },

    isBluetoothDisconnectButtonEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isBluetoothDisconnectButtonEnabled', []);
    },
    
    /*
     * System Configuration APIs - Etc.
     */
    enableDuplicateCheck: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableDuplicateCheck', [enable]);
    },

    isDuplicateCheckEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isDuplicateCheckEnabled', []);
    },

    enableAutoErase: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableAutoErase', [enable]);
    },

    isAutoEraseEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isAutoEraseEnabled', []);
    },

    enableScanIfConnected: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableScanIfConnected', [enable]);
    },

    isScanIfConnectedEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isScanIfConnectedEnabled', []);
    },

    getAutoTriggerRereadDelay: function (success, error) {
        exec(success, error, 'KReader', 'getAutoTriggerRereadDelay', []);
    },

    setAutoTriggerRereadDelay: function (delay, success, error) {
        exec(success, error, 'KReader', 'setAutoTriggerRereadDelay', [delay]);
    },

    enableAutoTrigger: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableAutoTrigger', [enable]);
    },

    isAutoTriggerEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isAutoTriggerEnabled', []);
    },

    /*
     * Vibrate
     */    
    enableVibrator: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableVibrator', [enable]);
    },

    isVibratorEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isVibratorEnabled', []);
    },

    setCustomVibration: function (onTime, offTime, repeat, success, error) {
        exec(success, error, 'KReader', 'setCustomVibration', [onTime, offTime, repeat]);
    },

    /*
     * Display APIs
     */  
    enableDisplayConnectionStatus: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableDisplayConnectionStatus', [enable]);
    },

    isDisplayConnectionStatusEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isDisplayConnectionStatusEnabled', []);
    },

    enableMenuBarcodeState: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableMenuBarcodeState', [enable]);
    },

    isMenuBarcodeStateEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isMenuBarcodeStateEnabled', []);
    },

    enableDisplayScroll: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableDisplayScroll', [enable]);
    },

    isScrollingEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isScrollingEnabled', []);
    },

    setDisplayPosition: function (row, column, success, error) {
        exec(success, error, 'KReader', 'setDisplayPosition', [row, column]);
    },

    clearDisplay: function (success, error) {
        exec(success, error, 'KReader', 'clearDisplay', []);
    },

    setDisplayMessageFontSize: function (size, success, error) {
        exec(success, error, 'KReader', 'setDisplayMessageFontSize', [size]);
    },

    setDisplayMessageDuration: function (duration, success, error) {
        exec(success, error, 'KReader', 'setDisplayMessageDuration', [duration]);
    },

    setMessageTextAttribute: function (attr, success, error) {
        exec(success, error, 'KReader', 'setMessageTextAttribute', [attr]);
    },

    setDisplayMessage: function (message, success, error) {
        exec(success, error, 'KReader', 'setDisplayMessage', [message]);
    },

    setDisplayMessageAndGetUserConfirm: function (message, success, error) {
        exec(success, error, 'KReader', 'setDisplayMessageAndGetUserConfirm', [message]);
    },

    enableAutoMenuExit: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableAutoMenuExit', [enable]);
    },

    isAutoMenuExitEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isAutoMenuExitEnabled', []);
    },

    /*
     * Keypad APIs
     */  
    enableEnterKeyFunction: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableEnterKeyFunction', [enable]);
    },

    isEnterKeyFunctionEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isEnterKeyFunctionEnabled', []);
    },

    enableExtendKeypad: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableExtendKeypad', [enable]);
    },

    isExtendKeypadEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isExtendKeypadEnabled', []);
    },
    
    enableKeypad: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableKeypad', [enable]);
    },

    isKeypadEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isKeypadEnabled', []);
    },

    /*
     * HID APIs
     */    
    getHIDAutoLockTime: function (success, error) {
        exec(success, error, 'KReader', 'getHIDAutoLockTime', []);
    },

    setHIDAutoLockTime: function (timeout, success, error) {
        exec(success, error, 'KReader', 'setHIDAutoLockTime', [timeout]);
    },  

    getHIDKeyboard: function (success, error) {
        exec(success, error, 'KReader', 'getHIDKeyboard', []);
    },

    setHIDKeyboard: function (keyboard, success, error) {
        exec(success, error, 'KReader', 'setHIDKeyboard', [keyboard]);
    },  

    getHIDInitialDelay: function (success, error) {
        exec(success, error, 'KReader', 'getHIDInitialDelay', []);
    },

    setHIDInitialDelay: function (delay, success, error) {
        exec(success, error, 'KReader', 'setHIDInitialDelay', [delay]);
    },  

    getHIDInterDelay: function (success, error) {
        exec(success, error, 'KReader', 'getHIDInterDelay', []);
    },

    setHIDInterDelay: function (delay, success, error) {
        exec(success, error, 'KReader', 'setHIDInterDelay', [delay]);
    },  

    getHIDControlCharacter: function (success, error) {
        exec(success, error, 'KReader', 'getHIDControlCharacter', []);
    },

    setHIDControlCharacter: function (character, success, error) {
        exec(success, error, 'KReader', 'setHIDControlCharacter', [character]);
    },  

    /*
     * WiFi APIs
     */    
    isWiFiInstalled: function (success, error) {
        exec(success, error, 'KReader', 'isWiFiInstalled', []);
    },

    enableWiFiPower: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableWiFiPower', [enable]);
    },  

    isWiFiPowerEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isWiFiPowerEnabled', []);
    },

    enableWiFiAutoConnect: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableWiFiAutoConnect', [enable]);
    },  

    isWiFiAutoConnectEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isWiFiAutoConnectEnabled', []);
    },

    setWiFiServerIPAddress: function (ip, success, error) {
        exec(success, error, 'KReader', 'setWiFiServerIPAddress', [ip]);
    }, 

    getWiFiServerIPAddress: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiServerIPAddress', []);
    },

    setWiFiServerURLAddress: function (url, success, error) {
        exec(success, error, 'KReader', 'setWiFiServerURLAddress', [url]);
    }, 

    getWiFiServerURLAddress: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiServerURLAddress', []);
    },

    setWiFiServerPortNumber: function (port, success, error) {
        exec(success, error, 'KReader', 'setWiFiServerPortNumber', [port]);
    }, 

    getWiFiServerPortNumber: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiServerPortNumber', []);
    },

    setWiFiProtocol: function (protocol, success, error) {
        exec(success, error, 'KReader', 'setWiFiProtocol', [protocol]);
    }, 

    getWiFiProtocol: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiProtocol', []);
    },

    enableWiFiSSL: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableWiFiSSL', [enable]);
    },  

    isWiFiSSLEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isWiFiSSLEnabled', []);
    },

    setWiFiServerPage: function (page, success, error) {
        exec(success, error, 'KReader', 'setWiFiServerPage', [page]);
    }, 

    getWiFiServerPage: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiServerPage', []);
    },

    setWiFiCertification: function (cert, success, error) {
        exec(success, error, 'KReader', 'setWiFiCertification', [cert]);
    }, 

    getWiFiCertification: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiCertification', []);
    },

    setWiFiApSSID: function (ssid, success, error) {
        exec(success, error, 'KReader', 'setWiFiApSSID', [ssid]);
    }, 

    getWiFiApSSID: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiApSSID', []);
    },

    getWiFiApPasscode: function (success, error) {
        exec(success, error, 'KReader', 'getWiFiApPasscode', []);
    },

    setWiFiApPasscode: function (passcode, success, error) {
        exec(success, error, 'KReader', 'setWiFiApPasscode', [passcode]);
    }, 

    /*
     * NFC APIs
     */    
    isNFCInstalled: function (success, error) {
        exec(success, error, 'KReader', 'isNFCInstalled', []);
    },

    enableNFCPower: function (enable, success, errosr) {
        exec(success, error, 'KReader', 'enableNFCPower', [enable]);
    },  

    isNFCPowerEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isNFCPowerEnabled', []);
    },

    setNFCDataFormat: function (format, success, error) {
        exec(success, error, 'KReader', 'setNFCDataFormat', [format]);
    }, 

    getNFCDataFormat: function (success, error) {
        exec(success, error, 'KReader', 'getNFCDataFormat', []);
    },

    enableNFCUIDOnly: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableNFCUIDOnly', [enable]);
    },  

    isNFCUIDOnlyEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isNFCUIDOnlyEnabled', []);
    },

    enableNFCExtendedFormat: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableNFCExtendedFormat', [enable]);
    },  

    isNFCExtendedFormatEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isNFCExtendedFormatEnabled', []);
    },

    /*
     * MSR APIs
     */
    enableMSRErrorBeep: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableMSRErrorBeep', [enable]);
    },  

    isMSRErrorBeepEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isMSRErrorBeepEnabled', []);
    },

    enableMSRSentinel: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableMSRSentinel', [enable]);
    },  

    isMSRSentinelEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isMSRSentinelEnabled', []);
    },

    getMSRCardType: function (success, error) {
        exec(success, error, 'KReader', 'getMSRCardType', []);
    },

    setMSRCardType: function (type, success, error) {
        exec(success, error, 'KReader', 'setMSRCardType', [type]);
    }, 

    getMSRDataType: function (success, error) {
        exec(success, error, 'KReader', 'getMSRDataType', []);
    },

    setMSRDataType: function (type, success, error) {
        exec(success, error, 'KReader', 'setMSRDataType', [type]);
    }, 

    getMSRDataEncryption: function (success, error) {
        exec(success, error, 'KReader', 'getMSRDataEncryption', []);
    },

    setMSRDataEncryption: function (encryption, success, error) {
        exec(success, error, 'KReader', 'setMSRDataEncryption', [encryption]);
    }, 

    getMSRTrackSeparator: function (success, error) {
        exec(success, error, 'KReader', 'getMSRTrackSeparator', []);
    },

    setMSRTrackSeparator: function (separator, success, error) {
        exec(success, error, 'KReader', 'setMSRTrackSeparator', [separator]);
    }, 

    getMSRTrackSelection: function (success, error) {
        exec(success, error, 'KReader', 'getMSRTrackSelection', []);
    },

    setMSRTrackSelection: function (selection, success, error) {
        exec(success, error, 'KReader', 'setMSRTrackSelection', [selection]);
    }, 

    getPartialDataMSRStartPosition: function (success, error) {
        exec(success, error, 'KReader', 'getPartialDataMSRStartPosition', []);
    },

    setPartialDataMSRStartPosition: function (position, success, error) {
        exec(success, error, 'KReader', 'setPartialDataMSRStartPosition', [position]);
    }, 
    
    getPartialDataMSRLength: function (success, error) {
        exec(success, error, 'KReader', 'getPartialDataMSRLength', []);
    },

    setPartialDataMSRLength: function (length, success, error) {
        exec(success, error, 'KReader', 'setPartialDataMSRLength', [length]);
    }, 
    
    getPartialDataMSRAction: function (success, error) {
        exec(success, error, 'KReader', 'getPartialDataMSRAction', []);
    },

    setPartialDataMSRAction: function (action, success, error) {
        exec(success, error, 'KReader', 'setPartialDataMSRAction', [action]);
    },     


    /*
     * UHF APIs
     */
    isUHFModuleAttached: function (success, error) {
        exec(success, error, 'KReader', 'isUHFModuleAttached', []);
    },

    isUHFPowerEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isUHFPowerEnabled', []);
    },

    enableUHFPower: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableUHFPower', [enable]);
    },

    getUHFPowerLevel: function (success, error) {
        exec(success, error, 'KReader', 'getUHFPowerLevel', []);
    },

    setUHFPowerLevel: function (level, success, error) {
        exec(success, error, 'KReader', 'setUHFPowerLevel', [level]);
    },

    getUHFReadMode: function (success, error) {
        exec(success, error, 'KReader', 'getUHFReadMode', []);
    },

    setUHFReadMode: function (mode, success, error) {
        exec(success, error, 'KReader', 'setUHFReadMode', [mode]);
    },

    getUHFReadTagMode: function (success, error) {
        exec(success, error, 'KReader', 'getUHFReadTagMode', []);
    },

    setUHFReadTagMode: function (mode, success, error) {
        exec(success, error, 'KReader', 'setUHFReadTagMode', [mode]);
    },

    getUHFDataType: function (success, error) {
        exec(success, error, 'KReader', 'getUHFDataType', []);
    },

    setUHFDataType: function (type, success, error) {
        exec(success, error, 'KReader', 'setUHFDataType', [type]);
    },

    isUHFBurstModeEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isUHFBurstModeEnabled', []);
    },
    
    enableUHFBurstMode: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableUHFBurstMode', [enable]);
    },

    isUHFKeyEventEnabled: function (success, error) {
        exec(success, error, 'KReader', 'isUHFKeyEventEnabled', []);
    },

    enableUHFKeyEvent: function (enable, success, error) {
        exec(success, error, 'KReader', 'enableUHFKeyEvent', [enable]);
    },

    cancelUHFReading: function (success, error) {
        exec(success, error, 'KReader', 'cancelUHFReading', []);
    },

    getUHFTagList: function (timeout, success, error) {
        exec(success, error, 'KReader', 'getUHFTagList', [timeout]);
    },

    selectUHFTag(epc, success, error) {
        exec(success, error, 'KReader', 'selectUHFTag', [epc]);
    },

    readUHFTagMemory(pwd, bank, start, length, success, error) {
        exec(success, error, 'KReader', 'readUHFTagMemory', [pwd, bank, start, length]);
    },

    writeUHFTagMemory(pwd, bank, start, length, data, success, error) {
        exec(success, error, 'KReader', 'writeUHFTagMemory', [pwd, bank, start, length, data]);
    },

    setUHFTagLock(pwd, mask, success, error) {
        exec(success, error, 'KReader', 'setUHFTagLock', [pwd, mask]);
    },

    killUHFTag(pwd, success, error) {
        exec(success, error, 'KReader', 'killUHFTag', [pwd]);
    },


    /*
     * POS APIs
     */    
    softwareTriggerPos: function (success, error) {
        exec(success, error, 'KReader', 'softwareTriggerPos', []);
    },    

};

module.exports = kreader;
