package com.dev.ops.organisation.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dev.ops.organisation.*", "com.dev.ops.common.service.exception.", "com.dev.ops.common.logging.interceptors."})
//"com.dev.ops.common.orika",
public class OrganisationWebApplicationInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(OrganisationWebApplicationInitializer.class, args);
	}
}