package com.dev.ops.exception.manager.impl;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.helpers.LogLog;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.constants.ExceptionManagerConstants;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.WrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.ErrorLogger;
import com.dev.ops.logger.service.LoggerFactory;

public final class DefaultExceptionManager implements ExceptionManager {

	private static ExceptionManager exceptionManager = null;
	private static ErrorLogger ERROR_LOGGER;
	private static ErrorLogger CRASHDUMP_LOGGER;
	private static DiagnosticLogger DIAGNOSTIC_LOGGER;

	private ResourceBundle messageResourceBundle;

	private DefaultExceptionManager() {
		DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(DefaultExceptionManager.class);
		ERROR_LOGGER = LoggerFactory.getErrorLogger(ErrorLogger.ERROR_LOGGER_NAME);
		CRASHDUMP_LOGGER = LoggerFactory.getErrorLogger(ErrorLogger.CRASHDUMP_LOGGER_NAME);

		DIAGNOSTIC_LOGGER.info("Initialising the  Exception Manager Start.");
		try {
			DIAGNOSTIC_LOGGER.info("Loading Exception Resource Bundle :" + ExceptionManagerConstants.MessageBundles.MESSAGE_RESOURCE_BUNDLE_FILE_KEY);
			this.messageResourceBundle = ResourceBundle.getBundle(ExceptionManagerConstants.MessageBundles.MESSAGE_RESOURCE_BUNDLE_FILE_KEY);
		} catch(final Exception ex) {
			DIAGNOSTIC_LOGGER.fatal("Exception in intialisation of Exception Manager -- Failed to set Resoutce bundle.", ex);
			throw ex;
		}
		DIAGNOSTIC_LOGGER.debug("Exception resource bundle name:" + ExceptionManagerConstants.MessageBundles.MESSAGE_RESOURCE_BUNDLE_FILE_KEY);
		DIAGNOSTIC_LOGGER.info("Initialising the Exception Manager End.");
	}

	/**
	 * Singleton implementation for exception manager.
	 * @return the exception manager
	 */
	public static synchronized ExceptionManager getExceptionManager() {
		if(exceptionManager == null) {
			exceptionManager = new DefaultExceptionManager();
		}
		return exceptionManager;
	}

	@Override
	public void logErrorEvent(final WrappedException exception, final ContextInfo contextInfo, final Severity severity, final boolean printStacktrace) {
		if(Severity.FATAL.equals(severity)) {
			if(printStacktrace) {
				CRASHDUMP_LOGGER.logFATALException(ExceptionManagerConstants.AUDIT_MESSAGE, (Throwable) exception, contextInfo, null);
			}
			ERROR_LOGGER.logFATALMessage(ExceptionManagerConstants.AUDIT_MESSAGE, (Throwable) exception, contextInfo, null);
		} else if(Severity.ERROR.equals(severity)) {
			if(printStacktrace) {
				CRASHDUMP_LOGGER.logERRORException(ExceptionManagerConstants.AUDIT_MESSAGE, (Throwable) exception, contextInfo, null);
			}
			ERROR_LOGGER.logERRORMessage(ExceptionManagerConstants.AUDIT_MESSAGE, (Throwable) exception, contextInfo, null);
		} else {
			if(printStacktrace) {
				CRASHDUMP_LOGGER.logERRORException(ExceptionManagerConstants.AUDIT_MESSAGE, (Throwable) exception, contextInfo, null);
			}
			ERROR_LOGGER.logERRORMessage(ExceptionManagerConstants.AUDIT_MESSAGE, (Throwable) exception, contextInfo, null);
		}
	}

	@Override
	public void logErrorEvent(final WrappedException exception, final ContextInfo contextInfo, final Severity severity) {
		this.logErrorEvent(exception, contextInfo, severity, true);
	}

	@Override
	public void logErrorEvent(final WrappedException exception, final ContextInfo contextInfo) {
		this.logErrorEvent(exception, contextInfo, Severity.ERROR);
	}

	@Override
	public void logErrorEvent(final WrappedException exception) {
		this.logErrorEvent(exception, null);
	}

	@Override
	public ResourceBundle getMessageResourceBundle() {
		return this.messageResourceBundle;
	}

	public static String getExceptionDescription(final String exceptionId, final Object[] messageParameters) {
		final StringBuilder exceptionDescription = new StringBuilder(exceptionId + ExceptionManagerConstants.WhitespaceLiterals.BLANK_SPACE);
		final ResourceBundle messageResources = DefaultExceptionManager.getExceptionManager().getMessageResourceBundle();
		try {
			if(messageParameters != null) {
				exceptionDescription.append(MessageFormat.format(messageResources.getString(exceptionId), messageParameters));
			} else {
				exceptionDescription.append(messageResources.getString(exceptionId));
			}
		} catch(final MissingResourceException e) {
			LogLog.debug("Unable to find the resource with " + exceptionId, e);
			return exceptionId;
		}
		return exceptionDescription.toString();
	}
}