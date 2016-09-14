package com.dev.ops.logger.service;

import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.logger.constants.LoggingConstants;

public class ErrorLogger {
	// TODO:[Krishna]: Need to look at the variables for fetching message
	// bundles.
	private final Logger logger;

	public static final String ERROR_LOGGER_NAME = "error";
	public static final String CRASHDUMP_LOGGER_NAME = "crashdump";

	public ErrorLogger(final Logger logger) {
		this.logger = logger;
		logger.setResourceBundle(ResourceBundle.getBundle(LoggingConstants.MessageBundles.EXCEPTION_LOG_MESSAGE_RESOURCE_BUNDLE));
	}

	public void logFATALMessage(final String messageId, final Throwable exception, final ContextInfo contextInfo, final Object[] param) {
		final Object[] contextParam = this.createParameters(exception, contextInfo, param);
		this.logger.l7dlog(Level.FATAL, messageId, contextParam, null);
	}

	public void logFATALException(final String messageId, final Throwable throwable, final ContextInfo contextInfo, final Object[] param) {
		final Object[] contextParam = this.createParameters(throwable, contextInfo, param);
		this.logger.l7dlog(Level.FATAL, messageId, contextParam, throwable);
	}

	public void logERRORMessage(final String messageId, final Throwable exception, final ContextInfo contextInfo, final Object[] param) {
		final Object[] contextParam = this.createParameters(exception, contextInfo, param);
		this.logger.l7dlog(Level.ERROR, messageId, contextParam, null);
	}

	public void logERRORException(final String messageId, final Throwable throwable, final ContextInfo contextInfo, final Object[] param) {
		final Object[] contextParam = this.createParameters(throwable, contextInfo, param);
		this.logger.l7dlog(Level.ERROR, messageId, contextParam, throwable);
	}

	public void logERRORException(final Throwable exception, final ContextInfo contextInfo) {
		if(exception != null) {
			final Object[] param = this.createParameters(exception, contextInfo, null);
			this.logger.error(param + LoggingConstants.WhitespaceLiterals.ATTRIBUTES_SEPERATOR + exception.getMessage(), exception);
		}
	}

	private Object[] createParameters(final Throwable exception, final ContextInfo contextInfo, final Object[] runtimeValues) {
		Object[] returnValues = null;
		int returnValueSize = 0;
		final StringBuilder stringContextInfo = new StringBuilder();
		if(contextInfo != null) {
			stringContextInfo.append(contextInfo.toString());
		}
		if(exception != null) {
			stringContextInfo.append(LoggingConstants.ERROR_MESSAGE).append(LoggingConstants.WhitespaceLiterals.NAME_VALUE_SEPERATOR).append(exception.getMessage());
		}

		if(runtimeValues != null) {
			returnValueSize = runtimeValues.length + 1;
			returnValues = new Object[returnValueSize];
			returnValues[0] = stringContextInfo.toString();
			for(int counter = 1; counter <= runtimeValues.length; counter++) {
				returnValues[counter] = runtimeValues[counter - 1];
			}
		} else {
			returnValueSize = 1;
			returnValues = new Object[returnValueSize];
			returnValues[0] = stringContextInfo.toString();
		}
		return returnValues;
	}
}