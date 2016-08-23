package com.dev.ops.defa.constants;

import com.dev.ops.defa.activities.LoginActivity;
import com.dev.ops.defa.activities.MainActivity;
import com.dev.ops.defa.activities.RegistrationActivity;

/**
 * Created by Rohan Jayraj Mohite on 27/12/2015.
 */
public interface CommonPreferences {

    public interface ServiceURL {
        public static final String REGISTRATION_SERVER_URL = "submitCSR";
        public static final String IS_VALID_SESSION_URL = "isValidSessionId/";
        public static final String IN_VALIDATE_SESSION_ID_URL = "inValidateSessionId/";
    }

    public interface ApplicationConstants {
        String APPLICATION_NAME = "DEFA";
    }

    public interface ServiceKeys {

        public interface DeviceConstants {
           String ANDROID_DEVICE_ID = "androidDeviceId";
           String BROADCAST_ACTION = "broadcastAction";
           String MAIN_ACTIVITY_BROADCAST_ACTION = MainActivity.class.getCanonicalName();
        }

        public interface Registration {
            String REGISTRATION_BROADCAST_ACTION = RegistrationActivity.class.getCanonicalName();
        }

        public interface Login {
            String BROADCAST_ACTION = LoginActivity.class.getCanonicalName();
        }
    }

    public interface Notifiers {
        String EXTENDED_DATA_STATUS = "STATUS";
        String EXTENDED_DATA_RESULT = "RESULT";
    }

    public interface ProgressStatus {

        public interface Registration {
            int SERVICE_STARTED = 0;
            int GENERATE_KEYPAIRS = 1;
            int GENERATE_CSR = 2;
            int SUBMIT_ENROLLMENT_REQUEST = 3;
            int RECEIVED_ENROLLMENT_REQUEST = 4;
            int STORE_CERTIFICATE = 5;
            int SERVICE_COMPLETED = 6;
            int SERVICE_ERROR = 7;
        }

        public interface Login {
            int SERVICE_STARTED = 0;
            int SERVICE_COMPLETED = 1;
            int AUTH_VALIDATION_SUCCESS = 2;
            int AUTH_VALIDATION_FAILED = 3;
            int SERVICE_ERROR = 4;
        }

        public interface DeviceInitialisation {
            int SERVICE_STARTED = 0;
            int SERVICE_COMPLETED = 1;
            int CERTIFICATE_EXISTS = 2;
            int CERTIFICATE_NOT_EXISTS = 3;
            int TOKEN_EXISTS = 4;
            int TOKEN_NOT_EXISTS = 5;
            int READY_TO_LOAD_ACTIVITY = 6;
            int SERVICE_ERROR = 7;
        }
    }

    public interface KeypairType {
        String RSA = "RSA";
    }

    public interface SignatureAlgorithm {
        String SHA1_WITH_RSA = "SHA1WITHRSA";
    }

    public interface KeyStore {
        String NAME = "AndroidKeyStore";
        String DEFA_ALIAS = "DEFA";
    }

    public interface CertificateConstants {
        String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n";
        String END_CERTIFICATE =   "\n-----END CERTIFICATE-----";
        String COMMON_NAME = "CN=";
        String ORGANISATION_NAME = ", O=";
        String PEOPLE = "People";
    }

    public interface Provider{
        String ANDROID_OPEN_SSL = "AndroidOpenSSL";
        String SPONGY_CASTLE = "SC";
    }

    public interface Separators {
        String DOLLAR = "$";
        String URL_SEPARATOR = "/";
        String EQUAL = "=";
    }

    public interface SessionVariables {
        String SSO_TOKEN = "ssoToken";
    }

    public interface SharedPreferences {
        String NAME = "defaSharedPreference";

        public interface Key {
            String SESSION_ID = "sessionId";
            String EMPLOYEE_NAME = "employeeName";
            String REGISTRATION_ID = "registrationCode";
            String ORGANISATION_WEB_URL = "organisationWeb";
            String AUTHENTICATION_SERVICE_URL = "authenticationService";
            String DEVICE_REGISTRATION_SERVICE_URL = "deviceRegistrationService";
            String EMPLOYEE_SERVICE_URL = "employeeService";
        }
    }

    public interface NavigationItems{
        String REGISTARTION = "Registration";
        String SIGNOUT = "Sign out";
    }

    public interface Operation{
        String REGISTRATION = "DeviceRegistration";
        String AUTHENTICAtION = "DeviceAuthentication";
        String RE_REGISTARTION = "ReRegistartion";
        String SIGN_OUT = "Signout";
        String IS_SESSION_VALID = "IsSessionValid";
        String EMPLOYEE_INFORMATION = "EmployeeInformation";
    }

}
