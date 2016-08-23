package com.dev.ops.project.service.orika.mapper.mappings;

import ma.glasnost.orika.MapperFactory;

import org.springframework.stereotype.Component;

import com.dev.ops.common.orika.mapper.config.MappingConfigurer;
import com.dev.ops.project.service.domain.Project;
import com.dev.ops.project.service.entities.ProjectMaster;

@Component
public class ProjectMappingConfigurer implements MappingConfigurer {

	@Override
	public void configure(final MapperFactory factory) {
		factory.classMap(Project.class, ProjectMaster.class).byDefault().register();
	}
}