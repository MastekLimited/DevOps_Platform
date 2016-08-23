package com.dev.ops.organisation.web.config;

import org.junit.Assert;
import org.junit.Test;

import com.dev.ops.organisation.web.configurations.ApplicationConfig;

public class ApplicationConfigTest {

	@Test
	public void testBeans() {
		final ApplicationConfig applicationConfig = new ApplicationConfig();
		Assert.assertNotNull("Object should not be null.", applicationConfig.restTemplate());
	}
}