package com.dev.ops.uuid.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dev.ops.uuid", "com.dev.ops.common"})
public class UUIDServiceInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(UUIDServiceInitializer.class, args);
	}
}