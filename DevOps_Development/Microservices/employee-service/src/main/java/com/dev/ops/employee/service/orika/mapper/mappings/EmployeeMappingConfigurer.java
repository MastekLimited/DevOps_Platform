package com.dev.ops.employee.service.orika.mapper.mappings;

import ma.glasnost.orika.MapperFactory;

import org.springframework.stereotype.Component;

import com.dev.ops.common.orika.mapper.config.MappingConfigurer;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.employee.service.entities.EmployeeMaster;

@Component
public class EmployeeMappingConfigurer implements MappingConfigurer {

	@Override
	public void configure(final MapperFactory factory) {
		factory.classMap(Employee.class, EmployeeMaster.class).byDefault().register();
	}
}