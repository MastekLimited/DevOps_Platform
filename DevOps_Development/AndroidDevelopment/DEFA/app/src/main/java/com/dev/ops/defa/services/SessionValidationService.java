package com.dev.ops.defa.services;

import android.os.AsyncTask;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Rohan Jayraj Mohite on 15/01/2016.
 */
public class SessionValidationService extends AsyncTask<String, Void, String> {

    private Device device;
    private RestTemplate restTemplate = null;

    @Override
    protected void onPreExecute() {
        device = Device.getInstance();
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    protected String doInBackground(String... operations) {
        try {
            String operation = operations[0];

            switch (operation) {
                case CommonPreferences.Operation.IS_SESSION_VALID:
                    IsValidSession();
                    break;
            }
        }catch (Exception ex){
            device.setIsSessionValid(false);
            return ex.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }

    protected void IsValidSession() {
        String url = device.getAuthenticationServiceURL() + CommonPreferences.ServiceURL.IS_VALID_SESSION_URL + device.getSessionId();

        final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(CommonPreferences.ApplicationConstants.APPLICATION_NAME, CommonPreferences.Operation.IS_SESSION_VALID));
        final boolean isValidSession = this.restTemplate.exchange(url, HttpMethod.GET, entity, boolean.class).getBody();

        device.setIsSessionValid(isValidSession);
    }

}
