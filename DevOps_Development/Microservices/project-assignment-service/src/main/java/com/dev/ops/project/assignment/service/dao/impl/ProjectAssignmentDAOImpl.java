package com.dev.ops.project.assignment.service.dao.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.dev.ops.common.dao.generic.GenericDAOImpl;
import com.dev.ops.project.assignment.service.dao.ProjectAssignmentDAO;
import com.dev.ops.project.assignment.service.entities.ProjectAssignmentMaster;

@Service
public class ProjectAssignmentDAOImpl extends GenericDAOImpl<ProjectAssignmentMaster> implements ProjectAssignmentDAO {

	@Override
	public List<ProjectAssignmentMaster> fetchAllProjectAssignments() {
		return this.getEntityManager().createNamedQuery("ProjectAssignmentMaster.fetchAllProjects", ProjectAssignmentMaster.class).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<ProjectAssignmentMaster> fetchAllProjectEmployees(final BigDecimal projectId) {
		final Query namedQuery = this.getEntityManager().createNamedQuery("ProjectAssignmentMaster.fetchAllProjectEmployees");
		namedQuery.setParameter("projectId", projectId);
		final Set<ProjectAssignmentMaster> projectAssignments = new HashSet<ProjectAssignmentMaster>();
		projectAssignments.addAll(namedQuery.getResultList());
		return projectAssignments;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<ProjectAssignmentMaster> fetchAllEmployeeProjects(final BigDecimal employeeId) {
		final Query namedQuery = this.getEntityManager().createNamedQuery("ProjectAssignmentMaster.fetchAllEmployeeProjects");
		namedQuery.setParameter("employeeId", employeeId);
		final Set<ProjectAssignmentMaster> projectAssignments = new HashSet<ProjectAssignmentMaster>();
		projectAssignments.addAll(namedQuery.getResultList());
		return projectAssignments;
	}
}