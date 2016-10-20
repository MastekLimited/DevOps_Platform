package com.dev.ops.exceptionmanager.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.constants.ExceptionManagerConstants;
import com.dev.ops.exception.manager.impl.DefaultExceptionManager;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.impl.WrappedSystemException;

// TODO:[Krishna]: Check the last logged statement.
public class WrappedSystemExceptionTest {

	private static ExceptionManager exceptionManager;

	@BeforeClass
	public static void setUp() {
		exceptionManager = DefaultExceptionManager.getExceptionManager();
	}

	@Test(expected = NullPointerException.class)
	public void testDefaultExceptionWithoutExceptionID() throws WrappedSystemException {
		throw new WrappedSystemException(null, new Throwable());
	}

	@Test
	public void testWrappedSystemException() {
		WrappedSystemException exception = null;
		try {
			Class.forName("DummyUnknowClass");
		} catch(final Exception e) {
			exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS, e, new String[] {"param-1"});
			exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, true);
		} finally {
			Assert.assertNotNull(exception);
			Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE_WITH_PARAMETERS + "param-1", exception.getMessage());
		}
	}

	@Test(expected = WrappedSystemException.class)
	public void testWrappedSystemExceptionWithoutId() throws WrappedSystemException {
		throw new WrappedSystemException();
	}

	@Test
	public void testCreateWrappedSystemException() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE);
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE, exception.getMessage());
	}

	@Test
	public void testWrappedSystemExceptionWithThrowable() {
		final WrappedSystemException exception = new WrappedSystemException(new Throwable());
		Assert.assertNotNull(exception);
	}

	@Test
	public void testWrappedSystemExceptionWithThrowableAndExceptionId() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable());
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE, exception.getMessage());
	}

	@Test
	public void testWrappedSystemExceptionWithThrowableAndExceptionIdAndParameters() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS, new Exception(), new String[] {"param-1"});
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE_WITH_PARAMETERS + "param-1", exception.getMessage());
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS, exception.getExceptionId());
	}

	@Test
	public void testWrappedSystemExceptionEvent() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception);
	}

	@Test
	public void testWrappedSystemExceptionErrorWithStackTrace() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.ERROR, true);
	}

	@Test
	public void testWrappedSystemExceptionErrorWithOutStackTrace() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.ERROR, false);
	}

	@Test
	public void testWrappedSystemExceptionFatalWithOutStackTrace() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, false);
	}

	@Test
	public void testWrappedSystemExceptionFatalWithOutStackTraceWithNullContext() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, false);
	}

	@Test
	public void testWrappedSystemExceptionFatalWithStackTrace() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, true);
	}

	@Test
	public void testWrappedSystemExceptionFatalWithStackTraceWithNullContext() {
		final WrappedSystemException exception = new WrappedSystemException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, true);
	}
}