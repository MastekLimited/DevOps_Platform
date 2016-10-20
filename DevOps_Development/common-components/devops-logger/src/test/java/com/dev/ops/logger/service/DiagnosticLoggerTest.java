package com.dev.ops.logger.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.dev.ops.common.domain.ContextInfo;

public class DiagnosticLoggerTest {
	private static DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(DiagnosticLoggerTest.class);
	private String messageId;
	private ContextInfo contextInfo;
	private Object[] parameters;
	private Object[] parametersNotNull;

	@Before
	public void setUp() throws Exception {
		this.messageId = "TEST_DIAGNOSTIC_LOG_MESSAGE";
		this.contextInfo = new ContextInfo("Sample", "Sample");
		this.parametersNotNull = new String[2];
		this.parametersNotNull[0] = new String("SID0001");
	}

	@Test
	public void testDebug() {
		DIAGNOSTIC_LOGGER.debug("test debug level");
	}

	@Test
	public void testWarn() {
		DIAGNOSTIC_LOGGER.warn("test warn level");
	}

	@Test
	public void testInfo() {
		DIAGNOSTIC_LOGGER.info("test info level");
	}

	@Test
	public void testError() {
		DIAGNOSTIC_LOGGER.error("test error level");
	}

	@Test
	public void testIsDebugEnabled() {
		Assert.assertTrue(DIAGNOSTIC_LOGGER.isDebugEnabled());
	}

	@Test
	public void testIsInfoEnabled() {
		Assert.assertTrue(DIAGNOSTIC_LOGGER.isInfoEnabled());
	}

	@Test
	public void testIsWarnEnabled() {
		Assert.assertTrue(DIAGNOSTIC_LOGGER.isWarnEnabled());
	}

	@Test
	public void testIsErrorEnabled() {
		Assert.assertTrue(DIAGNOSTIC_LOGGER.isErrorEnabled());
	}

	@Test
	public void testFatalString() {
		DIAGNOSTIC_LOGGER.fatal("test fatal level");
	}

	@Test
	public void testErrorString() {
		DIAGNOSTIC_LOGGER.error("test error level");
	}

	@Test
	public void testErrorStringThrowable() {
		DIAGNOSTIC_LOGGER.error("Hello world.", new Exception());
	}

	@Test
	public void testFatalStringThrowable() {
		DIAGNOSTIC_LOGGER.fatal("Hello world.", new Exception());
	}

	@Test
	public void testDebugContextInfo() {
		DIAGNOSTIC_LOGGER.debug(this.messageId, this.contextInfo, this.parameters);
		DIAGNOSTIC_LOGGER.debug(this.messageId, this.contextInfo);
	}

	@Test
	public void testWarnContextInfo() {
		DIAGNOSTIC_LOGGER.warn(this.messageId, this.contextInfo, this.parameters);
		DIAGNOSTIC_LOGGER.warn(this.messageId, this.contextInfo);
		DIAGNOSTIC_LOGGER.warn(this.messageId);
	}

	@Test
	public void testInfoContextInfo() {
		DIAGNOSTIC_LOGGER.info("TEST_DIAGNOSTIC_LOG_MESSAGE_2", this.contextInfo, this.parametersNotNull);
		DIAGNOSTIC_LOGGER.info("messageId", this.contextInfo);
	}

	@Test
	public void testInfoContextInfoEmpty() {
		DIAGNOSTIC_LOGGER.info("TEST_DIAGNOSTIC_LOG_MESSAGE_2", new ContextInfo());
	}

	@Test
	public void testErrorContextInfo() {
		DIAGNOSTIC_LOGGER.error("TEST_DIAGNOSTIC_LOG_MESSAGE", this.contextInfo, this.parameters);
		DIAGNOSTIC_LOGGER.error("TEST_DIAGNOSTIC_LOG_MESSAGE", this.contextInfo);
	}
}