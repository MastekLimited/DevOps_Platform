package com.dev.ops.employee.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dev.ops.employee", "com.dev.ops.common"})
public class EmployeeServiceInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(EmployeeServiceInitializer.class, args);
	}
}