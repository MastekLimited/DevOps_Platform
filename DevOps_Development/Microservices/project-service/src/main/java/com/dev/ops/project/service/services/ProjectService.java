package com.dev.ops.project.service.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.project.service.dao.ProjectDAO;
import com.dev.ops.project.service.domain.Project;
import com.dev.ops.project.service.entities.ProjectMaster;
import com.dev.ops.uuid.service.domain.EntityType;

@Component
public class ProjectService {

	@Autowired
	private MapperFacade mapperFacade;

	@Autowired
	private ProjectDAO projectDAO;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Value("${uuid.service.url}")
	private String uuidServiceURL;

	@Transactional(rollbackFor = {Exception.class})
	public Project getProjectDetails(final BigDecimal projectId) throws DefaultWrappedException {
		final ProjectMaster projectMaster = this.projectDAO.findByPrimaryKey(projectId);
		Project projectDetails = null;
		if(null != projectMaster) {
			projectDetails = this.mapperFacade.map(projectMaster, Project.class);
		} else {
			throw new DefaultWrappedException("PROJECT_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {projectId});
		}
		return projectDetails;
	}

	@Transactional(rollbackFor = {Exception.class})
	public Project saveProjectDetails(final Project project) throws DefaultWrappedException {
		ProjectMaster projectMaster = this.mapperFacade.map(project, ProjectMaster.class);

		//Add project code only if new project is newly getting created.
		if(projectMaster.getProjectId() == null) {
			final HttpEntity<?> entity = HttpUtil.getHeaders();
			final String projectCode = this.restTemplate.exchange(this.uuidServiceURL + EntityType.PROJECT.name(), HttpMethod.GET, entity, String.class).getBody();
			projectMaster.setProjectCode(projectCode);
		} else {
			//Set only audit columns if the object already exists.
			final ProjectMaster existingProject = this.projectDAO.findByPrimaryKey(projectMaster.getProjectId());

			if(existingProject != null) {
				projectMaster.setAuditColumns(existingProject.getAuditColumns());
			}
		}

		projectMaster = this.projectDAO.update(projectMaster);
		return this.mapperFacade.map(projectMaster, Project.class);
	}

	@Transactional(rollbackFor = {Exception.class})
	public List<Project> getAllProjectDetails() throws DefaultWrappedException {
		final List<Project> projects = new ArrayList<Project>();
		final List<ProjectMaster> projectMasters = this.projectDAO.fetchAllProjects();
		for(final ProjectMaster projectMaster : projectMasters) {
			final Project project = this.mapperFacade.map(projectMaster, Project.class);
			projects.add(project);
		}
		return projects;
	}
}