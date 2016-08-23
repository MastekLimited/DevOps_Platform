package com.dev.ops.project.service.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.ops.common.dao.generic.GenericDAOImpl;
import com.dev.ops.project.service.dao.ProjectDAO;
import com.dev.ops.project.service.entities.ProjectMaster;

@Service
public class ProjectDAOImpl extends GenericDAOImpl<ProjectMaster> implements ProjectDAO {

	@Override
	public List<ProjectMaster> fetchAllProjects() {
		return this.getEntityManager().createNamedQuery("ProjectMaster.fetchAllProjects", ProjectMaster.class).getResultList();
	}
}