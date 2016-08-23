package com.dev.ops.exceptionmanager.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.impl.DefaultExceptionManager;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.impl.DefaultWrappedException;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionManagerTest {

	@Mock
	private static ExceptionManager exceptionManager = null;

	@Test
	public void testSingletonImplementation() {
		final ExceptionManager newExceptionManager = DefaultExceptionManager.getExceptionManager();
		final ExceptionManager newExceptionManager1 = DefaultExceptionManager.getExceptionManager();
		Assert.assertEquals(newExceptionManager1, newExceptionManager);
	}

	//@Test
	public void testLogErrorEventWithOutStacktrace() throws Exception {
		final boolean withStacktrace = false;
		exceptionManager.logErrorEvent(Mockito.any(DefaultWrappedException.class), Mockito.any(ContextInfo.class), Severity.ERROR, withStacktrace);
	}

	//@Test
	public void testLogErrorEventWithStacktrace() throws Exception {
		final boolean withStacktrace = true;
		exceptionManager.logErrorEvent(Mockito.any(DefaultWrappedException.class), Mockito.any(ContextInfo.class), Severity.ERROR, withStacktrace);
	}

	//@Test
	public void testLogFatalEventWithoutStacktrace() throws Exception {
		final boolean withStacktrace = false;
		exceptionManager.logErrorEvent(Mockito.any(DefaultWrappedException.class), Mockito.any(ContextInfo.class), Severity.FATAL, withStacktrace);
	}

	//@Test
	public void testLogFatalEventWithStacktrace() throws Exception {
		final boolean withStacktrace = true;
		exceptionManager.logErrorEvent(Mockito.any(DefaultWrappedException.class), Mockito.any(ContextInfo.class), Severity.FATAL, withStacktrace);
	}

	//@Test
	public void testLogErrorEvent() throws Exception {
		exceptionManager.logErrorEvent(Mockito.any(DefaultWrappedException.class));
	}

	//@Test
	public void testLogFatalEventWithStacktrace_Failure() throws Exception {
		final boolean withStacktrace = true;
		exceptionManager.logErrorEvent(Mockito.any(DefaultWrappedException.class), Mockito.any(ContextInfo.class), Severity.FATAL, withStacktrace);
	}
}