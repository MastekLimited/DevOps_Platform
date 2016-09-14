package com.dev.ops.project.assignment.service.orika.mapper.mappings;

import ma.glasnost.orika.MapperFactory;

import org.springframework.stereotype.Component;

import com.dev.ops.common.orika.mapper.config.MappingConfigurer;
import com.dev.ops.project.assignment.service.domain.ProjectAssignment;
import com.dev.ops.project.assignment.service.entities.ProjectAssignmentMaster;

@Component
public class ProjectAssignmentMappingConfigurer implements MappingConfigurer {

	@Override
	public void configure(final MapperFactory factory) {
		factory.classMap(ProjectAssignment.class, ProjectAssignmentMaster.class).byDefault().register();
	}
}