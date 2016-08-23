package com.dev.ops.exceptionmanager.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import junit.framework.Assert;

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
public class ExceptionManagerPowerMockingTest3 {

	@Mock
	private DiagnosticLogger DIAGNOSTIC_LOGGER;

	@Mock
	private ErrorLogger ERROR_LOGGER;

	//@Test
	public void testCreateDefaultExceptionManager_NoMessageBundle() throws FileNotFoundException, IOException {
		PowerMockito.mockStatic(LoggerFactory.class);
		PowerMockito.mockStatic(ResourceBundle.class);
		PowerMockito.when(LoggerFactory.getErrorLogger(ErrorLogger.ERROR_LOGGER_NAME)).thenReturn(this.ERROR_LOGGER);
		PowerMockito.when(LoggerFactory.getDiagnosticLogger(Mockito.eq(DefaultExceptionManager.class))).thenReturn(this.DIAGNOSTIC_LOGGER);
		final ResourceBundle exceptionResourceBundle = new PropertyResourceBundle(ExceptionManagerPowerMockingTest3.class.getResourceAsStream("exceptionmanager.properties"));
		PowerMockito.when(exceptionResourceBundle.getString(ExceptionManagerConstants.MessageBundles.MESSAGE_RESOURCE_BUNDLE_FILE_KEY)).thenReturn(null);
		final ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();
		Assert.assertNotNull(exceptionManager);
	}
}