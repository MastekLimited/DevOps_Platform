package com.dev.ops.uuid.service.orika.mapper.mappings;

import ma.glasnost.orika.MapperFactory;

import org.springframework.stereotype.Component;

import com.dev.ops.common.orika.mapper.config.MappingConfigurer;

@Component
public class UUIDMappingConfigurer implements MappingConfigurer {

	@Override
	public void configure(final MapperFactory factory) {
	}
}