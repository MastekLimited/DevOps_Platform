package com.dev.ops.device.registration.service.orika.mapper.mappings;

import ma.glasnost.orika.MapperFactory;

import org.springframework.stereotype.Component;

import com.dev.ops.common.orika.mapper.config.MappingConfigurer;
import com.dev.ops.device.registration.service.domain.DeviceRegistration;
import com.dev.ops.device.registration.service.entities.DeviceRegistrationMaster;

@Component
public class DeviceRegistrationMappingConfigurer implements MappingConfigurer {

	@Override
	public void configure(final MapperFactory factory) {
		factory.classMap(DeviceRegistration.class, DeviceRegistrationMaster.class).byDefault().register();
	}
}