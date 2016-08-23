package com.dev.ops.common.service.exception;

import net.sf.qualitytest.CoverageForPrivateConstructor;

import org.junit.Assert;
import org.junit.Test;

import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.exceptions.impl.WrappedSystemException;

public class ExceptionUtilTest {

	@Test
	public void createExceptionTest() {
		final String userUUID = "123412341234";
		final DefaultWrappedException exception = new DefaultWrappedException("USER_WITH_UUID_NOT_FOUND_EXCEPTION", null, new Object[] {userUUID});
		final DefaultWrappedException returnedException = ExceptionUtil.createException(Severity.ERROR, exception, null, true);
		Assert.assertEquals("Exception objects should be equal.", exception, returnedException);
	}

	@Test
	public void createSystemExceptionTest() {

		//TODO: JenkinsBuild: Compilation failure.
		//String userUUID = "123412341234";
		final String userUUID = "123412341234";
		final WrappedSystemException exception = new WrappedSystemException("USER_WITH_UUID_NOT_FOUND_EXCEPTION", null, new Object[] {userUUID});
		final WrappedSystemException returnedException = ExceptionUtil.createException(Severity.ERROR, exception, null, true);

		//TODO: JenkinsBuild: Test failure.
		//Assert.assertEquals("Exception objects should be equal.", exception, new WrappedSystemException("NEW_EXCEPTION"));
		Assert.assertEquals("Exception objects should be equal.", exception, returnedException);

		CoverageForPrivateConstructor.giveMeCoverage(ExceptionUtil.class);
	}
}