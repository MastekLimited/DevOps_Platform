package com.dev.ops.logger.constants;

public interface LoggingConstants {
	String ERROR_MESSAGE = "errorMessage";

	interface MessageBundles {
		String DEFAULT_MESSAGE_RESOURCE_BUNDLE = "messages";
		String DIAGNOSTIC_LOG_MESSAGE_RESOURCE_BUNDLE = "log-messages-store";
		String EXCEPTION_LOG_MESSAGE_RESOURCE_BUNDLE = "log-messages-store";
	}

	interface WhitespaceLiterals {
		String ATTRIBUTES_SEPERATOR = " | ";
		String NAME_VALUE_SEPERATOR = " : ";
		String BLANK_SPACE = " ";
		String EQUAL_TO = "==";
		String HYPHEN = "-";
	}

	interface ErrorMessages {

	}
}