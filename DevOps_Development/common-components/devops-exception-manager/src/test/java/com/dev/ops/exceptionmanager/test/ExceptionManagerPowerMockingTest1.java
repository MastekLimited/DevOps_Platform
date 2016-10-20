package com.dev.ops.exceptionmanager.test;

import java.util.ResourceBundle;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.constants.ExceptionManagerConstants;
import com.dev.ops.exception.manager.impl.DefaultExceptionManager;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.ErrorLogger;
import com.dev.ops.logger.service.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {ResourceBundle.class, LoggerFactory.class})
public class ExceptionManagerPowerMockingTest1 {

	@Mock
	private DiagnosticLogger DIAGNOSTIC_LOGGER;

	@Mock
	private ErrorLogger ERROR_LOGGER;

	@Mock
	private ResourceBundle exceptionResourceBundle;

	@Test(expected = RuntimeException.class)
	public void testCreateDefaultExceptionManager_Failure() {
		PowerMockito.mockStatic(LoggerFactory.class);
		PowerMockito.mockStatic(ResourceBundle.class);
		PowerMockito.when(LoggerFactory.getErrorLogger(ErrorLogger.ERROR_LOGGER_NAME)).thenReturn(this.ERROR_LOGGER);
		PowerMockito.when(LoggerFactory.getDiagnosticLogger(Mockito.eq(DefaultExceptionManager.class))).thenReturn(this.DIAGNOSTIC_LOGGER);
		PowerMockito.doThrow(new RuntimeException()).when(this.DIAGNOSTIC_LOGGER).info("Loading Exception Resource Bundle :" + ExceptionManagerConstants.MessageBundles.MESSAGE_RESOURCE_BUNDLE_FILE_KEY);
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
	}
}