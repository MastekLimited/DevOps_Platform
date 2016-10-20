package com.dev.ops.device.registration.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dev.ops.device.registration", "com.dev.ops.common"})
public class DeviceRegistrationServiceInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(DeviceRegistrationServiceInitializer.class, args);
	}
}