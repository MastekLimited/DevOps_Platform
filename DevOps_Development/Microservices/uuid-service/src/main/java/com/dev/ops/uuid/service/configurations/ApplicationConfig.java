package com.dev.ops.uuid.service.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.dev.ops.uuid.service.factory.UUIDGeneratorFactory;

@Configuration
@EnableSwagger2
public class ApplicationConfig {

	@Value("${swagger.app.title}")
	private String title;

	@Value("${swagger.app.description}")
	private String description;

	@Value("${swagger.app.version}")
	private String version;

	@Value("${swagger.app.termsOfServiceUrl}")
	private String termsOfServiceUrl;

	@Value("${swagger.app.contact.name}")
	private String contactName;

	@Value("${swagger.app.contact.url}")
	private String contactURL;

	@Value("${swagger.app.contact.email}")
	private String contactEmail;

	@Value("${swagger.app.license}")
	private String license;

	@Value("${swagger.app.licenseUrl}")
	private String licenseURL;

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

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(title, description, version, termsOfServiceUrl, new Contact(contactName, contactURL, contactEmail), license, licenseURL);
	}
}