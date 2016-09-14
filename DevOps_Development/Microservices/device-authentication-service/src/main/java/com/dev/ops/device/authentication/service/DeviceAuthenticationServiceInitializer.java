package com.dev.ops.device.authentication.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dev.ops.device.authentication", "com.dev.ops.common"})
public class DeviceAuthenticationServiceInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(DeviceAuthenticationServiceInitializer.class, args);
	}
}