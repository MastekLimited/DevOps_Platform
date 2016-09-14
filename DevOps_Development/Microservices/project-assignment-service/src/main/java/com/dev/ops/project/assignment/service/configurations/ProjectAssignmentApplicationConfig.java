package com.dev.ops.project.assignment.service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectAssignmentApplicationConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}