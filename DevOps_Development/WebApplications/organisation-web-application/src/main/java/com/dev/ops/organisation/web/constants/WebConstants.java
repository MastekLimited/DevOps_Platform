package com.dev.ops.organisation.web.constants;

public interface WebConstants {

	interface SessionVariables {
		String LOGGED_IN_USER = "loggedInUser";
	}

	interface AdminUser {
		String USER_ID = "000000000000";
		String USERNAME = "ADMIN";
	}

	interface Modules {
		String ORGANISATION_WEB_APPLICATION = "OrganisationWebApp";
	}

	interface Operations {
		String INITIALIZE_OPERATION = "initialize context info";
		String SAVE = "Save ";
		String READ = "Read ";
		String READ_ALL = "Read all ";
		String DELETE = "Delete ";

		interface Employee {
			String SAVE = WebConstants.Operations.SAVE + Employee.class.getSimpleName();
			String READ = WebConstants.Operations.READ + Employee.class.getSimpleName();
			String READ_ALL = WebConstants.Operations.READ_ALL + Employee.class.getSimpleName();
			String DELETE = WebConstants.Operations.DELETE + Employee.class.getSimpleName();
		}

		interface Project {
			String SAVE = WebConstants.Operations.SAVE + Project.class.getSimpleName();
			String READ = WebConstants.Operations.READ + Project.class.getSimpleName();
			String READ_ALL = WebConstants.Operations.READ_ALL + Employee.class.getSimpleName();
			String DELETE = WebConstants.Operations.DELETE + Project.class.getSimpleName();
		}

		interface DeviceRegistration {
			String SAVE = WebConstants.Operations.SAVE + DeviceRegistration.class.getSimpleName();
			String READ = WebConstants.Operations.READ + DeviceRegistration.class.getSimpleName();
			String READ_ALL = WebConstants.Operations.READ_ALL + DeviceRegistration.class.getSimpleName();
			String DELETE = WebConstants.Operations.DELETE + DeviceRegistration.class.getSimpleName();
		}

		interface DeviceAuthentication {
			String SAVE = WebConstants.Operations.SAVE + DeviceAuthentication.class.getSimpleName();
			String READ = WebConstants.Operations.READ + DeviceAuthentication.class.getSimpleName();
			String READ_ALL = WebConstants.Operations.READ_ALL + DeviceAuthentication.class.getSimpleName();
			String DELETE = WebConstants.Operations.DELETE + DeviceAuthentication.class.getSimpleName();
			String IS_VALID_SESSION = "isValidSession " + DeviceAuthentication.class.getSimpleName();
		}
	}

	interface DeviceRegistration {
		interface QRCode {
			String REGISTRATION_CODE = "registrationCode=";
			String DEVICE_REGISTRATION_SERVICE = "deviceRegistrationService=";
			String AUTHENTICATION_SERVICE = "authenticationService=";
			String EMPLOYEE_SERVICE = "employeeService=";
			String ORGANISATION_WEB = "organisationWeb=";
		}
	}
}
