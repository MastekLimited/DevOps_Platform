package com.dev.ops.uuid.service.configurations;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dev.ops.uuid.service.factory.UUIDGeneratorFactory;

@Configuration
public class ApplicationConfig {

	@Bean
	public ServiceLocatorFactoryBean myFactoryServiceLocatorFactoryBean() {
		final ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
		bean.setServiceLocatorInterface(UUIDGeneratorFactory.class);
		return bean;
	}

	@Bean
	public UUIDGeneratorFactory uuidGeneratorFactory() {
		return (UUIDGeneratorFactory) this.myFactoryServiceLocatorFactoryBean().getObject();
	}
}