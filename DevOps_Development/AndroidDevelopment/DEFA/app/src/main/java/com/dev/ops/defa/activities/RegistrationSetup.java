package com.dev.ops.defa.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.ops.R;
import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;

import java.util.Properties;
import java.util.StringTokenizer;

public class RegistrationSetup extends AppCompatActivity {

    private static String TAG = RegistrationSetup.class.getName();
    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_setup);

        device = Device.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onQRButtonClick(View view) {
        try {
            String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException ex) {
            showDialog(RegistrationSetup.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity activity, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);

        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Log.e(TAG, "onClick ");
                }
            }
        });

        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: Handle error if user click on No button
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                String qrCodeResult = intent.getStringExtra("SCAN_RESULT");
                Properties qrCodeProperties = getProperties(qrCodeResult);

                device.setRegistrationId(qrCodeProperties.getProperty(CommonPreferences.SharedPreferences.Key.REGISTRATION_ID));
                device.setOrganisationWebURL(qrCodeProperties.getProperty(CommonPreferences.SharedPreferences.Key.ORGANISATION_WEB_URL));
                device.setAuthenticationServiceURL(qrCodeProperties.getProperty(CommonPreferences.SharedPreferences.Key.AUTHENTICATION_SERVICE_URL));
                device.setRegistrationServiceURL(qrCodeProperties.getProperty(CommonPreferences.SharedPreferences.Key.DEVICE_REGISTRATION_SERVICE_URL));
                device.setEmployeeServiceURL(qrCodeProperties.getProperty(CommonPreferences.SharedPreferences.Key.EMPLOYEE_SERVICE_URL));

                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Registration Device Code:" + device.getRegistrationId(), Toast.LENGTH_LONG);
                toast.show();
                onChallengeDialogBox();
            }
        }
    }

    private Properties getProperties(String qrCodeResult) {
        Properties qrCodeProperties = new Properties();
        StringTokenizer token = new StringTokenizer(qrCodeResult, CommonPreferences.Separators.DOLLAR);

        while(token.hasMoreElements()) {
            String[] tokenValue = token.nextToken().split("=");
            qrCodeProperties.put(tokenValue[0], tokenValue[1]);
        }
        return qrCodeProperties;
    }

    private void onChallengeDialogBox() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText input = (EditText) promptView.findViewById(R.id.userSecreteChallenge);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                device.setSecretChallenge(input.getText().toString());
                if(device.getSecretChallenge()!=null) {

                    storedToSharedPreferences();

                    Toast toast = Toast.makeText(RegistrationSetup.this, "Registration process started...", Toast.LENGTH_LONG);
                    toast.show();

                    invokeRegistrationActivity();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                Toast toast = Toast.makeText(RegistrationSetup.this, "Please enter secret challenge!!! ", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        // create an alert dialog
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    public void invokeRegistrationActivity() {

        Intent intent = null;
        intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);

    }

    private void storedToSharedPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE).edit();
        editor.putString(CommonPreferences.SharedPreferences.Key.REGISTRATION_ID, device.getRegistrationId());
        editor.putString(CommonPreferences.SharedPreferences.Key.ORGANISATION_WEB_URL, device.getOrganisationWebURL());
        editor.putString(CommonPreferences.SharedPreferences.Key.AUTHENTICATION_SERVICE_URL, device.getAuthenticationServiceURL());
        editor.putString(CommonPreferences.SharedPreferences.Key.DEVICE_REGISTRATION_SERVICE_URL, device.getRegistrationServiceURL());
        editor.putString(CommonPreferences.SharedPreferences.Key.EMPLOYEE_SERVICE_URL, device.getEmployeeServiceURL());
        editor.commit();
    }
}

