package com.dev.ops.logger.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dev.ops.common.domain.ContextInfo;

// TODO:[Krishna]:Remove assertTrue(true) statements.
public class ErrorLoggerTest {
	private static ErrorLogger errorLogger = LoggerFactory.getErrorLogger(ErrorLogger.ERROR_LOGGER_NAME);
	private static ErrorLogger errorLogger1 = LoggerFactory.getErrorLogger(ErrorLogger.ERROR_LOGGER_NAME);
	private static ContextInfo contextInfo;
	private Object[] parameters;

	@Before
	public void setUp() throws Exception {
		contextInfo = new ContextInfo("Sample", "Sample");
		this.parameters = new String[2];
		this.parameters[0] = new String("Dev0001");
		this.parameters[1] = new String("REQ11134556");
	}

	@Test
	public void testLoggingWithResourceBundle() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logFATALException("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_004", myExp, contextInfo, null);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLoggingWithCustomResourceBundle() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logFATALException("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_004", myExp, contextInfo, null);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogExceptionMsgIDERROR() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logERRORException("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_004", myExp, contextInfo, null);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogExceptionMsgIDNullContextInfo() {
		try {
			final ContextInfo contextInfo1 = null;
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logERRORException("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_004", myExp, contextInfo1, null);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogExceptionMsgIDNullException() {
		try {
			final Exception myExp = null;
			errorLogger.logERRORException("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_004", myExp, contextInfo, null);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogExceptionERROR() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logERRORException(myExp, contextInfo);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogExceptionERRORNull() {
		try {
			final Exception myExp = null;
			errorLogger.logERRORException(myExp, contextInfo);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogExceptionERROREmptyContextInfo() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logERRORException(myExp, new ContextInfo());
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogMessageFATAL() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger.logFATALMessage("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_005", myExp, contextInfo, this.parameters);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testLogMessageERROR() {
		try {
			final Exception myExp = new Exception("myTest--Exception");
			errorLogger1.logERRORMessage("TEST_SYSTEM_EXCEPTION_LOG_MESSAGE_004", myExp, contextInfo, null);
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}
}