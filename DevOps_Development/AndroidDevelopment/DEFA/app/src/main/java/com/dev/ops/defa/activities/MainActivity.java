package com.dev.ops.defa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.services.DeviceInitializationService;

import com.dev.ops.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private  ResponseReceiver receiver;
    private TextView statusView;
    private boolean certificateExists = false;
    private boolean tokenExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusView = (TextView) findViewById(R.id.status);
        initialiseApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

      public void initialiseApplication() {
        Intent deviceInitialiseIntent = new Intent(getApplicationContext(), DeviceInitializationService.class);
        deviceInitialiseIntent.putExtra(CommonPreferences.ServiceKeys.DeviceConstants.BROADCAST_ACTION, CommonPreferences.ServiceKeys.DeviceConstants.MAIN_ACTIVITY_BROADCAST_ACTION);
        startService(deviceInitialiseIntent);
    }

    private class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra(CommonPreferences.Notifiers.EXTENDED_DATA_STATUS, CommonPreferences.ProgressStatus.DeviceInitialisation.SERVICE_COMPLETED)) {

                case CommonPreferences.ProgressStatus.DeviceInitialisation.SERVICE_STARTED:
                    break;
                case CommonPreferences.ProgressStatus.DeviceInitialisation.TOKEN_EXISTS:
                    tokenExists = true;
                    break;
                case CommonPreferences.ProgressStatus.DeviceInitialisation.TOKEN_NOT_EXISTS:
                    tokenExists = false;
                    break;
                case CommonPreferences.ProgressStatus.DeviceInitialisation.CERTIFICATE_EXISTS:
                    certificateExists = true;
                    break;
                case CommonPreferences.ProgressStatus.DeviceInitialisation.CERTIFICATE_NOT_EXISTS:
                    certificateExists = false;
                    break;
                case CommonPreferences.ProgressStatus.DeviceInitialisation.SERVICE_COMPLETED:
                    updateViewStatus(tokenExists, certificateExists);
                    break;
                case CommonPreferences.ProgressStatus.DeviceInitialisation.READY_TO_LOAD_ACTIVITY:
                    initializeApplication(tokenExists, certificateExists);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(CommonPreferences.ServiceKeys.DeviceConstants.MAIN_ACTIVITY_BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void initializeApplication(boolean tokenExists, boolean certificateExists){
        Intent intent = null;
        if(tokenExists) {
            intent = new Intent(this, WebActivity.class);
        } else {
            if (certificateExists) {
                intent = new Intent(this, LoginActivity.class);
            } else {
                intent = new Intent(this, RegistrationSetup.class);
            }
        }
        startActivity(intent);
    }

    private void updateViewStatus(boolean tokenExists, boolean certificateExists){
        if (tokenExists) {
            statusView.setText("Token available, loading web application... ");
        }else {
            if (certificateExists) {
                statusView.setText("Redirecting to login screen... ");
            } else {
                statusView.setText("loading device registration screen.");
            }
        }
    }
}