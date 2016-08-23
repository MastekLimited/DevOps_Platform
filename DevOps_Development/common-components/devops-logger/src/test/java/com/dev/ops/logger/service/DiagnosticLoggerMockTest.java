package com.dev.ops.logger.service;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.dev.ops.logger.constants.LoggingConstants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ResourceBundle.class})
public class DiagnosticLoggerMockTest {

	@Mock
	private ResourceBundle resourceBundle;

	@Mock
	private Logger logger;

	@InjectMocks
	private final DiagnosticLogger diagnosticLogger = new DiagnosticLogger(this.logger);

	@BeforeClass
	public static void testSetup() {
		PowerMockito.mockStatic(ResourceBundle.class);
		Mockito.when(ResourceBundle.getBundle(Mockito.anyString())).thenReturn(null);
	}

	//@Test
	public void testLoadResourceBundle_Success() {
		//PowerMockito.mockStatic(ResourceBundle.class);
		//Mockito.when(ResourceBundle.getBundle(Mockito.anyString())).thenReturn(resourceBundle);
		this.diagnosticLogger.loadResourceBundles();
		PowerMockito.verifyStatic();
		ResourceBundle.getBundle(Mockito.anyString());
	}

	//@Test
	public void testLoadResourceBundle_Success_Message() {
		PowerMockito.mockStatic(ResourceBundle.class);
		PowerMockito.spy(ResourceBundle.class);
		PowerMockito.when(ResourceBundle.getBundle(LoggingConstants.MessageBundles.DIAGNOSTIC_LOG_MESSAGE_RESOURCE_BUNDLE)).thenThrow(new RuntimeException());
		//PowerMockito.when(ResourceBundle.getBundle(Mockito.anyString())).thenReturn(resourceBundle);
		this.diagnosticLogger.loadResourceBundles();
	}

	//@Test(expected=RuntimeException.class)
	public void testLoadResourceBundle_Error() {
		PowerMockito.mockStatic(ResourceBundle.class);
		PowerMockito.when(ResourceBundle.getBundle(Mockito.anyString())).thenThrow(new RuntimeException());
		PowerMockito.when(ResourceBundle.getBundle(Mockito.anyString())).thenThrow(new RuntimeException());
		this.diagnosticLogger.loadResourceBundles();
	}
}