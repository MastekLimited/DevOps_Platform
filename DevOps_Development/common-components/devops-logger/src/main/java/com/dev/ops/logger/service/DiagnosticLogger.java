package com.dev.ops.logger.service;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.logger.constants.LoggingConstants;

public class DiagnosticLogger {
	// TODO:[Krishna]: Need to look at the variables for fetching message
	// bundles.
	private final Logger logger;
	private static ResourceBundle resourceBundle;

	public DiagnosticLogger(final org.apache.log4j.Logger logger) {
		this.logger = logger;
		this.loadResourceBundles();
	}

	public void loadResourceBundles() {
		try {
			resourceBundle = ResourceBundle.getBundle(LoggingConstants.MessageBundles.DIAGNOSTIC_LOG_MESSAGE_RESOURCE_BUNDLE);
		} catch(final Exception e) {
			LogLog.debug("Unable to find the bundle " + LoggingConstants.MessageBundles.DIAGNOSTIC_LOG_MESSAGE_RESOURCE_BUNDLE, e);
			resourceBundle = ResourceBundle.getBundle(LoggingConstants.MessageBundles.DEFAULT_MESSAGE_RESOURCE_BUNDLE);
		}
	}

	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return this.logger.isEnabledFor(Level.WARN);
	}

	public boolean isErrorEnabled() {
		return this.logger.isEnabledFor(Level.ERROR);
	}

	public void debug(final String message) {
		this.logger.debug(message);
	}

	public void debug(final String messageId, final ContextInfo contextInfo, final Object[] param) {
		final String message = getMessageDescription(messageId, contextInfo, param);
		this.debug(message);
	}

	public void debug(final String messageId, final ContextInfo contextInfo) {
		this.debug(messageId, contextInfo, null);
	}

	public void info(final String message) {
		this.logger.info(message);
	}

	public void info(final String messageId, final ContextInfo contextInfo, final Object[] param) {
		final String message = getMessageDescription(messageId, contextInfo, param);
		this.info(message);
	}

	public void info(final String messageId, final ContextInfo contextInfo) {
		this.info(messageId, contextInfo, null);
	}

	public void warn(final String message) {
		this.logger.warn(message);
	}

	public void warn(final String messageId, final ContextInfo contextInfo, final Object[] param) {
		final String message = getMessageDescription(messageId, contextInfo, param);
		this.warn(message);
	}

	public void warn(final String messageId, final ContextInfo contextInfo) {
		this.warn(messageId, contextInfo, null);
	}

	public void error(final String message, final Throwable trace) {
		this.logger.error(message, trace);
	}

	public void error(final String message) {
		this.logger.error(message);
	}

	public void error(final String messageId, final ContextInfo contextInfo, final Object[] param) {
		final String message = getMessageDescription(messageId, contextInfo, param);
		this.error(message);
	}

	public void error(final String messageId, final ContextInfo contextInfo) {
		this.error(messageId, contextInfo, null);
	}

	public void fatal(final String message) {
		this.logger.fatal(message);
	}

	public void fatal(final String message, final Throwable trace) {
		this.logger.fatal(message, trace);
	}

	private static String getMessageDescription(final String messageId, final ContextInfo contextInfo, final Object[] param) {
		String logMessage = null;
		final StringBuilder messageBuffer = new StringBuilder();
		if(contextInfo != null) {
			messageBuffer.append(contextInfo.toString());
		}

		if(resourceBundle != null) {
			try {
				if(param != null) {
					logMessage = MessageFormat.format(resourceBundle.getString(messageId), param);
					messageBuffer.append(logMessage);
				} else {
					logMessage = resourceBundle.getString(messageId);
					messageBuffer.append(logMessage);
				}
			} catch(final MissingResourceException e) {
				LogLog.debug("Unable to find the resource with " + messageId, e);
				messageBuffer.append(messageId);
			}
		} else {
			messageBuffer.append(messageId);
		}
		return messageBuffer.toString();
	}
}