package com.dev.ops.defa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.ops.defa.domain.Device;
import com.dev.ops.defa.services.DeviceRegistrationService;

import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.R;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getName();

    private  ResponseReceiver receiver;
    private ProgressBar progressBar;
    private TextView progresstextView;
    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registration);
        setContentView(R.layout.activity_registration);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progresstextView = (TextView) findViewById(R.id.progresstext);
        progresstextView.setVisibility(View.INVISIBLE);
        device = Device.getInstance();

        invokeRegistrationService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void onRegistrationButton(View view) {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        EditText registrationId = (EditText) findViewById(R.id.registrationID_editText);
        EditText challenge = (EditText) findViewById(R.id.challenge_editText);

        device.setRegistrationId(registrationId.getText().toString());
        device.setSecretChallenge(challenge.getText().toString());
        device.setAndroidDeviceId(androidId);
        device.setBroadcastAction(CommonPreferences.ServiceKeys.Registration.REGISTRATION_BROADCAST_ACTION);

        Intent registrationServiceIntent = new Intent(getApplicationContext(), DeviceRegistrationService.class);

        progressBar.setVisibility(View.VISIBLE);
        progresstextView.setVisibility(View.VISIBLE);

        startService(registrationServiceIntent);
    }*/


    public void invokeRegistrationService(){
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        device.setAndroidDeviceId(androidId);
        device.setBroadcastAction(CommonPreferences.ServiceKeys.Registration.REGISTRATION_BROADCAST_ACTION);

        Intent registrationServiceIntent = new Intent(getApplicationContext(), DeviceRegistrationService.class);

        progressBar.setVisibility(View.VISIBLE);
        progresstextView.setVisibility(View.VISIBLE);

        startService(registrationServiceIntent);
    }

    // Broadcast receiver for receiving status updates from the IntentService
    private class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra(CommonPreferences.Notifiers.EXTENDED_DATA_STATUS, CommonPreferences.ProgressStatus.Registration.SERVICE_COMPLETED)) {

                case CommonPreferences.ProgressStatus.Registration.SERVICE_STARTED:
                    progressBar.setProgress(05);
                    break;

                case CommonPreferences.ProgressStatus.Registration.GENERATE_KEYPAIRS:
                    progresstextView.setText("Generating key pairs");
                    progressBar.setProgress(30);
                    break;

                case CommonPreferences.ProgressStatus.Registration.GENERATE_CSR:
                    progresstextView.setText("Generating CSR");
                    progressBar.setProgress(45);
                    break;

                case CommonPreferences.ProgressStatus.Registration.SUBMIT_ENROLLMENT_REQUEST:
                    progresstextView.setText("Registering device with the service");
                    progressBar.setProgress(60);
                    break;

                case CommonPreferences.ProgressStatus.Registration.RECEIVED_ENROLLMENT_REQUEST:
                    progresstextView.setText("Received response from service");
                    progressBar.setProgress(75);
                    break;

                case CommonPreferences.ProgressStatus.Registration.STORE_CERTIFICATE:
                    progresstextView.setText("Storing certificate");
                    progresstextView.setTextColor(Color.parseColor("#015a01"));
                    progressBar.setProgress(90);
                    break;

                case CommonPreferences.ProgressStatus.Registration.SERVICE_COMPLETED:
                    progresstextView.setText("Device registration completed!!!");
                    progressBar.setProgress(100);
                    StartLoginActivity();
                    break;

                case CommonPreferences.ProgressStatus.Registration.SERVICE_ERROR:
                    String errorStatus = intent.getStringExtra(CommonPreferences.Notifiers.EXTENDED_DATA_RESULT);
                    progressBar.setVisibility(View.INVISIBLE);
                    progresstextView.setText("Error: " + errorStatus);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(CommonPreferences.ServiceKeys.Registration.REGISTRATION_BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void StartLoginActivity(){
        try {
            Thread.sleep(2000);
            Intent intent = null;
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } catch (InterruptedException e) {
        e.printStackTrace();
         }
    }
}