package com.dev.ops.exception.manager.constants;

public interface ExceptionManagerConstants {

	String AUDIT_MESSAGE = "AUDIT_MESSAGE";
	String ERROR_MESSAGE = "Failed to log Exception";

	interface MessageBundles {
		String MESSAGE_RESOURCE_BUNDLE_FILE_KEY = "exception-message-store";
	}

	interface WhitespaceLiterals {
		String BLANK_SPACE = " ";
	}

	interface ErrorMessageKeys {
		String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";
		String DEFAULT_MESSAGE_WITH_PARAMETERS = "DEFAULT_MESSAGE_WITH_PARAMETERS";
	}

	interface ErrorMessageValues {
		String DEFAULT_MESSAGE = ErrorMessageKeys.DEFAULT_MESSAGE + ExceptionManagerConstants.WhitespaceLiterals.BLANK_SPACE + "Default exception occurred";
		String DEFAULT_MESSAGE_WITH_PARAMETERS = ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS + ExceptionManagerConstants.WhitespaceLiterals.BLANK_SPACE + "Default exception occurred with parameter1=";
	}
}
