package com.dev.ops.project.assignment.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dev.ops.project", "com.dev.ops.common"})
public class ProjectAssignmentServiceInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(ProjectAssignmentServiceInitializer.class, args);
	}
}