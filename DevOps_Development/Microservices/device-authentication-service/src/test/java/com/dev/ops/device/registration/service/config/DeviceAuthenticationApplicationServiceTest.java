package com.dev.ops.device.registration.service.config;

import net.sf.qualitytest.CoverageForPrivateConstructor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

import com.dev.ops.device.authentication.service.DeviceAuthenticationServiceInitializer;

/**
 * This is a power mock for test coverage. Not expecting any output out of this.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringApplication.class})
public class DeviceAuthenticationApplicationServiceTest {
	@Test
	public void testMain() {
		PowerMockito.mockStatic(SpringApplication.class);
		PowerMockito.when(SpringApplication.run(DeviceAuthenticationServiceInitializer.class, new String[] {})).thenReturn(null);
		DeviceAuthenticationServiceInitializer.main(new String[] {});

		//Verify whether the expected method has been called
		PowerMockito.verifyStatic();
		SpringApplication.run(DeviceAuthenticationServiceInitializer.class, new String[] {});

		//Verify there are no more interactions
		PowerMockito.verifyNoMoreInteractions(SpringApplication.class);

		CoverageForPrivateConstructor.giveMeCoverage(DeviceAuthenticationServiceInitializer.class);
	}
}