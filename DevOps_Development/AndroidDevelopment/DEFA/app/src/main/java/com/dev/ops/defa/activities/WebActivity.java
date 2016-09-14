package com.dev.ops.defa.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
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
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;

import com.dev.ops.R;
import com.dev.ops.defa.services.SessionValidationService;
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

public class WebActivity extends AppCompatActivity {
    private Device device;
    private WebView webView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        device = Device.getInstance();

        setContentView(R.layout.activity_web_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        OnSetDrawerHeader();
        OnNavigationItemsSelected();

        webView = (WebView) findViewById(R.id.activity_main_webview);
        webView.setWebViewClient(new MyBrowser());

        CookieSyncManager syncManager = CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();

        String cookie = CommonPreferences.SessionVariables.SSO_TOKEN + CommonPreferences.Separators.EQUAL + device.getSessionId();
        cookieManager.setCookie(device.getOrganisationWebURL(), cookie);
        syncManager.sync();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);

        webView.loadUrl(device.getOrganisationWebURL());
    }

    private void OnSetDrawerHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View hView = navigationView.inflateHeaderView(R.layout.drawer_header);
        TextView tv = (TextView) hView.findViewById(R.id.drawerheader_txt);
        tv.setText(device.getEmployeeName());
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (device.isSessionValid()) {
                view.loadUrl(url);
            }else {
                Toast toast = Toast.makeText(WebActivity.this, "You are logoff...", Toast.LENGTH_LONG);
                toast.show();
                OnSignOut();
                StartNewActivity(CommonPreferences.Operation.SIGN_OUT);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //You can add some custom functionality here

            SessionValidationService service = new SessionValidationService();
            service.execute(new String[]{CommonPreferences.Operation.IS_SESSION_VALID});
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //You can add some custom functionality here
        }

        @Override
        public void onReceivedError (WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //You can add some custom functionality here
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_login, menu);
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
                    case CommonPreferences.NavigationItems.SIGNOUT:
                        ShowAlertDialogBox(CommonPreferences.Operation.SIGN_OUT, "Sign out from this device?");
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
            ringProgressDialog = ProgressDialog.show(WebActivity.this, "Please wait ...", "In Progress...", true);
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
                        InValidateSessionID();
                        OnReRegisterDevice();
                        StartNewActivity(operation);
                        break;
                    case CommonPreferences.Operation.SIGN_OUT:
                        InValidateSessionID();
                        OnSignOut();
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

        protected void InValidateSessionID() {
            String url = device.getAuthenticationServiceURL() + CommonPreferences.ServiceURL.IN_VALIDATE_SESSION_ID_URL + device.getSessionId();

            final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(CommonPreferences.ApplicationConstants.APPLICATION_NAME, CommonPreferences.Operation.AUTHENTICAtION));
            final DeviceAuthentication responseDeviceAuthentication = this.restTemplate.exchange(url, HttpMethod.GET, entity, DeviceAuthentication.class).getBody();
        }
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

    public void StartNewActivity(String Activity){
        Intent intent = null;
        if(Activity.matches(CommonPreferences.Operation.RE_REGISTARTION)) {
            intent = new Intent(this, RegistrationSetup.class);
        } else {
            if (Activity.matches(CommonPreferences.Operation.SIGN_OUT)) {
                intent = new Intent(this, LoginActivity.class);
            }
        }
        startActivity(intent);
    }

}