package com.dev.ops.project.assignment.service.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.dev.ops.common.dao.generic.GenericDAO;
import com.dev.ops.project.assignment.service.entities.ProjectAssignmentMaster;

public interface ProjectAssignmentDAO extends GenericDAO<ProjectAssignmentMaster> {
	List<ProjectAssignmentMaster> fetchAllProjectAssignments();

	Set<ProjectAssignmentMaster> fetchAllProjectEmployees(BigDecimal projectId);

	Set<ProjectAssignmentMaster> fetchAllEmployeeProjects(BigDecimal employeeId);
}