package com.dev.ops.device.authentication.service.orika.mapper.mappings;

import ma.glasnost.orika.MapperFactory;

import org.springframework.stereotype.Component;

import com.dev.ops.common.orika.mapper.config.MappingConfigurer;
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;
import com.dev.ops.device.authentication.service.entities.DeviceAuthenticationMaster;

@Component
public class DeviceAuthenticationMappingConfigurer implements MappingConfigurer {

	@Override
	public void configure(final MapperFactory factory) {
		factory.classMap(DeviceAuthentication.class, DeviceAuthenticationMaster.class).byDefault().register();
	}
}