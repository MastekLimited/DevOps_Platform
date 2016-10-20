package com.dev.ops.exceptionmanager.test;

import java.util.ResourceBundle;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.impl.DefaultExceptionManager;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.ErrorLogger;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionManagerMockingTest {

	@Mock
	private DiagnosticLogger DIAGNOSTIC_LOGGER;

	@Mock
	private ErrorLogger ERROR_LOGGER;

	@Mock
	private ResourceBundle exceptionResourceBundle;

	@Test
	public void testLogErrorEvent_FATAL_NoStacktrace() {
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
		exceptionManager.logErrorEvent(new DefaultWrappedException(), null, Severity.FATAL, false);
	}

	@Test
	public void testLogErrorEvent_FATAL_Stacktrace() {
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
		exceptionManager.logErrorEvent(new DefaultWrappedException(), null, Severity.FATAL, true);
	}

	@Test
	public void testLogErrorEvent_ERROR_NoStacktrace() {
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
		exceptionManager.logErrorEvent(new DefaultWrappedException(), null, Severity.ERROR, false);
	}

	@Test
	public void testLogErrorEvent_ERROR_Stacktrace() {
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
		exceptionManager.logErrorEvent(new DefaultWrappedException(), null, Severity.ERROR, true);
	}

	@Test
	public void testLogErrorEvent_NULL_Severity_NoStacktrace() {
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
		exceptionManager.logErrorEvent(new DefaultWrappedException(), null, null, false);
	}

	@Test
	public void testLogErrorEvent_NULL_Severity_Stacktrace() {
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
		exceptionManager.logErrorEvent(new DefaultWrappedException(), null, null, true);
	}
}