package com.dev.ops.logger.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.logger.constants.LoggingConstants;
import com.dev.ops.logger.types.LoggingPoint;

// TODO:[Krishna]: Need to find out a solution for finding out performence in minute, seconds and milliseconds.
public class PerformanceLogger {
	private final Logger logger;

	public PerformanceLogger(final Logger logger) {
		this.logger = logger;
	}

	/**
	 * @deprecated Instead use {@link #log(String)} or {@link #log(String, ContextInfo)} methods and pass your
	 *             message.
	 */
	@Deprecated
	public void log(final LoggingPoint loggingPoint, final String serviceComponentId, final String serviceProcessId, final String businessActivityId, final String message) {
		final StringBuilder constructedMessage = new StringBuilder(loggingPoint.toString());
		constructedMessage.append(LoggingConstants.WhitespaceLiterals.BLANK_SPACE);
		constructedMessage.append(serviceComponentId);
		constructedMessage.append(LoggingConstants.WhitespaceLiterals.BLANK_SPACE);
		constructedMessage.append(serviceProcessId);
		constructedMessage.append(LoggingConstants.WhitespaceLiterals.BLANK_SPACE);
		constructedMessage.append(businessActivityId);
		constructedMessage.append(LoggingConstants.WhitespaceLiterals.BLANK_SPACE);
		constructedMessage.append(message);
		constructedMessage.append(LoggingConstants.WhitespaceLiterals.BLANK_SPACE);
		this.logger.log(StringUtils.EMPTY, Level.OFF, constructedMessage, null);
	}

	public void log(final LoggingPoint loggingPoint, final ContextInfo contextInfo) {
		final StringBuilder constructedMessage = new StringBuilder(loggingPoint.toString());
		if(null != contextInfo) {
			constructedMessage.append(contextInfo.toString());
		}
		this.logger.info(constructedMessage.toString());
	}

	public void log(final String message) {
		this.logger.info(message);
	}

	public void log(final String message, final ContextInfo contextInfo) {
		this.logger.info(message + contextInfo);
	}
}