package com.dev.ops.defa.services;

import android.os.AsyncTask;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;
import com.dev.ops.employee.service.domain.Employee;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Rohan Jayraj Mohite on 22/01/2016.
 */
public class EmployeeService extends AsyncTask<String, Void, String> {

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
                case CommonPreferences.Operation.EMPLOYEE_INFORMATION:
                    getEmployeeDetails();
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

    protected void getEmployeeDetails() {
        String url = device.getEmployeeServiceURL()  + device.getEmployeeId();

        final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(CommonPreferences.ApplicationConstants.APPLICATION_NAME, CommonPreferences.Operation.IS_SESSION_VALID));
        Employee employee = this.restTemplate.exchange(url, HttpMethod.GET, entity, Employee.class).getBody();

        device.setEmployeeName(employee.getFirstName() + employee.getLastName());
    }
}
