package com.dev.ops.defa.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;
import com.dev.ops.defa.notifiers.BroadcastNotifier;
import com.dev.ops.defa.utils.DeviceUtility;

import java.security.KeyStore;
import java.security.Security;

/**
 * Created by Rohan Jayraj Mohite on 07/01/2016.
 */
public class DeviceInitializationService extends IntentService {

    protected static final String TAG = DeviceInitializationService.class.getName();
    private BroadcastNotifier broadcastNotifier = null;
    private String intentServiceErrorStatus = null;

    public Device device;

    public DeviceInitializationService() {
        super(TAG);
        device = Device.getInstance();
        broadcastNotifier = new BroadcastNotifier(this);

        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        device.setBroadcastAction(intent.getStringExtra(CommonPreferences.ServiceKeys.DeviceConstants.BROADCAST_ACTION));

        try {
            broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.SERVICE_STARTED, device.getBroadcastAction(), intentServiceErrorStatus);

            if(isTokenExists()){
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.TOKEN_EXISTS, device.getBroadcastAction(), intentServiceErrorStatus);
            }else {
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.TOKEN_NOT_EXISTS, device.getBroadcastAction(), intentServiceErrorStatus);
            }

            Thread.sleep(1000);

            KeyStore.PrivateKeyEntry keyEntry = DeviceUtility.getKeyEntry();

            if (keyEntry != null && DeviceUtility.isCertificateAvailableInKeystore(keyEntry)) {
                DeviceUtility.loadCertificates(keyEntry, device);
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.CERTIFICATE_EXISTS, device.getBroadcastAction(), intentServiceErrorStatus);
            }else {
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.CERTIFICATE_NOT_EXISTS, device.getBroadcastAction(), intentServiceErrorStatus);
            }

            broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.SERVICE_COMPLETED, device.getBroadcastAction(), intentServiceErrorStatus);
            Thread.sleep(4000);
            broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.READY_TO_LOAD_ACTIVITY, device.getBroadcastAction(), intentServiceErrorStatus);

        } catch (Exception e) {
            intentServiceErrorStatus = e.getMessage();
            Log.e(TAG, e.getMessage());
            broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.DeviceInitialisation.SERVICE_ERROR, device.getBroadcastAction(), intentServiceErrorStatus);
        }
    }

    private boolean isTokenExists() {
        boolean isTokenExists = false;
        readSharedPreferences();
        if(device.getRegistrationId()!=null && device.getSessionId()!=null){
            isTokenExists = true;
        }
        return isTokenExists;
    }

    private void readSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE);
        device.setRegistrationId(preferences.getString(CommonPreferences.SharedPreferences.Key.REGISTRATION_ID, null));
        device.setSessionId(preferences.getString(CommonPreferences.SharedPreferences.Key.SESSION_ID, null));
        device.setEmployeeName(preferences.getString(CommonPreferences.SharedPreferences.Key.EMPLOYEE_NAME, null));
        device.setOrganisationWebURL(preferences.getString(CommonPreferences.SharedPreferences.Key.ORGANISATION_WEB_URL, null));
        device.setAuthenticationServiceURL(preferences.getString(CommonPreferences.SharedPreferences.Key.AUTHENTICATION_SERVICE_URL, null));
        device.setRegistrationServiceURL(preferences.getString(CommonPreferences.SharedPreferences.Key.DEVICE_REGISTRATION_SERVICE_URL, null));
        device.setEmployeeServiceURL(preferences.getString(CommonPreferences.SharedPreferences.Key.EMPLOYEE_SERVICE_URL, null));

    }
}