package com.dev.ops.project.service.dao;

import java.util.List;

import com.dev.ops.common.dao.generic.GenericDAO;
import com.dev.ops.project.service.entities.ProjectMaster;

public interface ProjectDAO extends GenericDAO<ProjectMaster> {
	List<ProjectMaster> fetchAllProjects();
}
