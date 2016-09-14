package com.dev.ops.defa.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.defa.domain.Device;
import com.dev.ops.defa.services.DeviceAuthenticationService;

import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.R;
import com.dev.ops.defa.services.EmployeeService;
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.toString();
    private  ResponseReceiver receiver;
    private TextView authenticationResponseTextView;
    private Device device;
    private DrawerLayout drawerLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        authenticationResponseTextView = (TextView) findViewById(R.id.authResponse_text);
        authenticationResponseTextView.setVisibility(View.INVISIBLE);
        device = Device.getInstance();

        OnNavigationItemsSelected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Login button */
    public void onLoginButton(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        EditText employeeId = (EditText) findViewById(R.id.employeeID_editText);
        EditText otpValue = (EditText) findViewById(R.id.otp_editText2);
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        device.setOtpValue(otpValue.getText().toString());
        device.setEmployeeId(employeeId.getText().toString());
        device.setAndroidDeviceId(androidId);
        device.setBroadcastAction(CommonPreferences.ServiceKeys.Login.BROADCAST_ACTION);

        Intent authenticationServiceIntent = new Intent(getApplicationContext(), DeviceAuthenticationService.class);

        authenticationResponseTextView.setVisibility(View.VISIBLE);

        startService(authenticationServiceIntent);

        progressBar.setVisibility(View.VISIBLE);

    }

    // Broadcast receiver for receiving status updates from the IntentService
    private class ResponseReceiver extends BroadcastReceiver
    {
        // Called when the BroadcastReceiver gets an Intent it's registered to receive
        @Override
        public void onReceive(Context context, Intent intent) {
            String authenticationServiceResponse = null;
            //Handle Intents here

             /*
             * Gets the status from the Intent's extended data, and chooses the appropriate action
             */

            switch (intent.getIntExtra(CommonPreferences.Notifiers.EXTENDED_DATA_STATUS, CommonPreferences.ProgressStatus.Login.SERVICE_COMPLETED)) {

                // Logs "started" state
                case CommonPreferences.ProgressStatus.Login.SERVICE_STARTED:
                    break;

                case CommonPreferences.ProgressStatus.Login.AUTH_VALIDATION_SUCCESS:
                    authenticationServiceResponse = "SUCCESS";
                    progressBar.setVisibility(View.INVISIBLE);
                    openWebView();
                    break;

                case CommonPreferences.ProgressStatus.Login.AUTH_VALIDATION_FAILED:
                    authenticationServiceResponse = "FAILED TO AUTHENTICATE";
                    progressBar.setVisibility(View.INVISIBLE);
                    break;

                case CommonPreferences.ProgressStatus.Login.SERVICE_COMPLETED:
                    authenticationResponseTextView.setText(authenticationServiceResponse);
                    progressBar.setVisibility(View.INVISIBLE);
                    break;

                case CommonPreferences.ProgressStatus.Login.SERVICE_ERROR:
                    String errorStatus = intent.getStringExtra(CommonPreferences.Notifiers.EXTENDED_DATA_RESULT);
                    authenticationResponseTextView.setText("Error= " + errorStatus);
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(CommonPreferences.ServiceKeys.Login.BROADCAST_ACTION));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void openWebView(){
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }

    private void OnNavigationItemsSelected() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                String title = menuItem.getTitle().toString();

                switch (title) {
                    case CommonPreferences.NavigationItems.REGISTARTION:
                        ShowAlertDialogBox(CommonPreferences.Operation.RE_REGISTARTION, "Re-Register this device?");
                        break;
                }
                return true;
            }
        });
    }

    private boolean ShowAlertDialogBox(final String Operation, String alertText) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        TextView textView = (TextView) promptView.findViewById(R.id.alertMessagetext);
        textView.setText(alertText);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                InvokeDeviceOperationService service = new InvokeDeviceOperationService();
                service.execute(new String[] {Operation});
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        // create an alert dialog
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

        return true;
    }

    private class InvokeDeviceOperationService extends AsyncTask<String, Void, String> {

        private RestTemplate restTemplate = null;
        private ProgressDialog ringProgressDialog = null;

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Please wait ...", "In Progress...", true);
            ringProgressDialog.setCancelable(false);

            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        }

        @Override
        protected String doInBackground(String... operations) {

            try {
                String operation = operations[0];
                Thread.sleep(3000);

                switch (operation) {
                    case CommonPreferences.Operation.RE_REGISTARTION:
                        OnReRegisterDevice();
                        StartNewActivity(operation);
                        break;
                }
            }catch (Exception ex){
                return ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ringProgressDialog.dismiss();
        }

        private void OnReRegisterDevice() throws Exception {
            DeleteCertificateFromKeyStore();
            DeleteSessionId();
        }
        private void DeleteCertificateFromKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
            KeyStore keyStore = KeyStore.getInstance(CommonPreferences.KeyStore.NAME);

            if (keyStore != null)
            {
                keyStore.load(null);
                keyStore.deleteEntry(CommonPreferences.KeyStore.DEFA_ALIAS);
            }
        }

        private void OnSignOut(){
            DeleteSessionId();
        }

        private void DeleteSessionId() {
            device.setSessionId(null);
            SharedPreferences.Editor editor = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE).edit();
            editor.remove(CommonPreferences.SharedPreferences.Key.SESSION_ID);
            editor.remove(CommonPreferences.SharedPreferences.Key.EMPLOYEE_NAME);
            editor.commit();
        }
    }

    public void StartNewActivity(String Activity){
        Intent intent = null;
        if(Activity.matches(CommonPreferences.Operation.RE_REGISTARTION)) {
            intent = new Intent(this, RegistrationSetup.class);
        }
        startActivity(intent);
    }
}