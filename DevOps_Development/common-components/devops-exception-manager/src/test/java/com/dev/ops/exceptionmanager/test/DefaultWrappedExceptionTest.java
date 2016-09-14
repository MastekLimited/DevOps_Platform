package com.dev.ops.exceptionmanager.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.constants.ExceptionManagerConstants;
import com.dev.ops.exception.manager.impl.DefaultExceptionManager;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.impl.DefaultWrappedException;

// TODO:[Krishna]: Check the last logged statement.
public class DefaultWrappedExceptionTest {

	private static ExceptionManager exceptionManager;

	@BeforeClass
	public static void setUp() {
		exceptionManager = DefaultExceptionManager.getExceptionManager();
	}

	/**
	 * Test default exception without exception id.
	 * @throws DefaultWrappedException
	 */
	@Test(expected = NullPointerException.class)
	public void testDefaultExceptionWithoutExceptionID() throws DefaultWrappedException {
		throw new DefaultWrappedException(null, new Throwable());
	}

	@Test
	public void testDefaultWrappedException() {
		DefaultWrappedException exception = null;
		try {
			Class.forName("DummyUnknowClass");
		} catch(final Exception e) {
			exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS, e, new String[] {"param-1"});
			exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, true);
		} finally {
			Assert.assertNotNull(exception);
			Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE_WITH_PARAMETERS + "param-1", exception.getMessage());
		}
	}

	@Test(expected = DefaultWrappedException.class)
	public void testDefaultWrappedExceptionWithoutId() throws DefaultWrappedException {
		throw new DefaultWrappedException();
	}

	@Test
	public void testCreateDefaultWrappedException() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE);
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE, exception.getMessage());
	}

	@Test
	public void testDefaultWrappedExceptionWithThrowable() {
		final DefaultWrappedException exception = new DefaultWrappedException(new Throwable());
		Assert.assertNotNull(exception);
	}

	@Test
	public void testDefaultWrappedExceptionWithThrowableAndExceptionId() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable());
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE, exception.getMessage());
	}

	@Test
	public void testDefaultWrappedExceptionWithThrowableAndExceptionIdAndParameters() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS, new Exception(), new String[] {"param-1"});
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageValues.DEFAULT_MESSAGE_WITH_PARAMETERS + "param-1", exception.getMessage());
		Assert.assertEquals(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE_WITH_PARAMETERS, exception.getExceptionId());
	}

	@Test
	public void testDefaultWrappedExceptionEvent() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception);
	}

	@Test
	public void testDefaultWrappedExceptionErrorWithStackTrace() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.ERROR, true);
	}

	@Test
	public void testDefaultWrappedExceptionErrorWithOutStackTrace() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.ERROR, false);
	}

	@Test
	public void testDefaultWrappedExceptionFatalWithOutStackTrace() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, false);
	}

	@Test
	public void testDefaultWrappedExceptionFatalWithOutStackTraceWithNullContext() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, false);
	}

	@Test
	public void testDefaultWrappedExceptionFatalWithStackTrace() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, true);
	}

	@Test
	public void testDefaultWrappedExceptionFatalWithStackTraceWithNullContext() {
		final DefaultWrappedException exception = new DefaultWrappedException(ExceptionManagerConstants.ErrorMessageKeys.DEFAULT_MESSAGE, new Throwable(), null);
		exceptionManager.logErrorEvent(exception, new ContextInfo("Sample", "Sample"), Severity.FATAL, true);
	}
}