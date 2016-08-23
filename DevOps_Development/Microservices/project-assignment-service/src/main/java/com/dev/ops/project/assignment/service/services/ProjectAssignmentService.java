package com.dev.ops.project.assignment.service.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ma.glasnost.orika.MapperFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.project.assignment.service.dao.ProjectAssignmentDAO;
import com.dev.ops.project.assignment.service.domain.EmployeeDetails;
import com.dev.ops.project.assignment.service.domain.ProjectAssignment;
import com.dev.ops.project.assignment.service.domain.ProjectDetails;
import com.dev.ops.project.assignment.service.entities.ProjectAssignmentMaster;
import com.dev.ops.project.service.domain.Project;

@Component
public class ProjectAssignmentService {

	@Autowired
	private MapperFacade mapperFacade;

	@Autowired
	private ProjectAssignmentDAO projectAssignmentDAO;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Value("${project.service.url}")
	private String projectServiceURL;

	@Autowired
	@Value("${employee.service.url}")
	private String employeeServiceURL;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(ProjectAssignmentService.class);

	@Transactional(rollbackFor = {Exception.class})
	public ProjectAssignment getProjectAssignmentDetails(final BigDecimal projectAssignmentId) throws DefaultWrappedException {
		final ProjectAssignmentMaster projectAssignmentMaster = this.projectAssignmentDAO.findByPrimaryKey(projectAssignmentId);
		ProjectAssignment projectAssignmentDetails = null;
		if(null != projectAssignmentMaster) {
			projectAssignmentDetails = this.mapperFacade.map(projectAssignmentMaster, ProjectAssignment.class);
		} else {
			throw new DefaultWrappedException("PROJECT_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {projectAssignmentId});
		}
		return projectAssignmentDetails;
	}

	@Transactional(rollbackFor = {Exception.class})
	public ProjectAssignment saveProjectAssignmentDetails(final ProjectAssignment projectAssignment) throws DefaultWrappedException {
		ProjectAssignmentMaster projectAssignmentMaster = this.mapperFacade.map(projectAssignment, ProjectAssignmentMaster.class);

		//Set only audit columns if the object already exists.
		if(projectAssignmentMaster.getProjectAssignmentId() != null) {
			final ProjectAssignmentMaster existingProjectAssignment = this.projectAssignmentDAO.findByPrimaryKey(projectAssignmentMaster.getProjectAssignmentId());

			if(existingProjectAssignment != null) {
				projectAssignmentMaster.setAuditColumns(existingProjectAssignment.getAuditColumns());
			}
		}

		projectAssignmentMaster = this.projectAssignmentDAO.update(projectAssignmentMaster);
		return this.mapperFacade.map(projectAssignmentMaster, ProjectAssignment.class);
	}

	@Transactional(rollbackFor = {Exception.class})
	public List<ProjectAssignment> getAllProjectAssignmentDetails() throws DefaultWrappedException {
		final List<ProjectAssignment> projectAssignments = new ArrayList<ProjectAssignment>();
		final List<ProjectAssignmentMaster> projectAssignmentMasters = this.projectAssignmentDAO.fetchAllProjectAssignments();
		for(final ProjectAssignmentMaster projectAssignmentMaster : projectAssignmentMasters) {
			final ProjectAssignment projectAssignment = this.mapperFacade.map(projectAssignmentMaster, ProjectAssignment.class);
			projectAssignments.add(projectAssignment);
		}
		return projectAssignments;
	}

	@Transactional(rollbackFor = {Exception.class})
	public ProjectDetails getProjectDetails(final BigDecimal projectId) throws DefaultWrappedException {
		DIAGNOSTIC_LOGGER.info("Getting project details from project service for project id:" + projectId);

		final HttpEntity<?> entity = HttpUtil.getHeaders();
		final Project project = this.restTemplate.exchange(this.projectServiceURL + projectId, HttpMethod.GET, entity, Project.class).getBody();

		DIAGNOSTIC_LOGGER.info("Project details from project service for project id:" + projectId + " are:" + project);
		final ProjectDetails projectDetails = new ProjectDetails(project);

		if(null != project) {
			final Set<ProjectAssignmentMaster> projectAssignments = this.projectAssignmentDAO.fetchAllProjectEmployees(projectId);

			for(final ProjectAssignmentMaster projectAssignment : projectAssignments) {
				DIAGNOSTIC_LOGGER.info("Getting employee details from employee service for employee id:" + projectAssignment.getEmployeeId());
				final Employee employee = this.restTemplate.exchange(this.employeeServiceURL + projectAssignment.getEmployeeId(), HttpMethod.GET, entity, Employee.class).getBody();

				DIAGNOSTIC_LOGGER.info("Employee details from employee service for employee id:" + projectAssignment.getEmployeeId() + " are:" + project);
				projectDetails.addEmployee(employee);
			}
		} else {
			throw new DefaultWrappedException("PROJECT_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {projectId});
		}
		return projectDetails;
	}

	//TODO: Test this with audit columns
	@Transactional(rollbackFor = {Exception.class})
	public ProjectDetails saveProjectDetails(final ProjectDetails projectDetails) throws DefaultWrappedException {
		final Project project = projectDetails.getProject();

		final Set<ProjectAssignmentMaster> projectAssignments = this.projectAssignmentDAO.fetchAllProjectEmployees(project.getProjectId());
		final Set<ProjectAssignmentMaster> removalProjectAssignments = new HashSet<ProjectAssignmentMaster>();
		final Set<ProjectAssignmentMaster> newProjectAssignments = new HashSet<ProjectAssignmentMaster>();

		ProjectAssignmentMaster projectAssignment;
		for(final Employee employee : projectDetails.getEmployees()) {
			projectAssignment = new ProjectAssignmentMaster(project.getProjectId(), employee.getEmployeeId());
			if(!projectAssignments.contains(projectAssignment)) {
				newProjectAssignments.add(projectAssignment);
			}
		}

		for(final ProjectAssignmentMaster projectAssignmentMaster : projectAssignments) {
			final Employee employee = new Employee(projectAssignmentMaster.getEmployeeId());
			if(!projectDetails.getEmployees().contains(employee)) {
				removalProjectAssignments.add(projectAssignmentMaster);
			}
		}
		projectAssignments.addAll(newProjectAssignments);
		projectAssignments.removeAll(removalProjectAssignments);
		this.projectAssignmentDAO.update(projectAssignments);
		return projectDetails;
	}

	@Transactional(rollbackFor = {Exception.class})
	public EmployeeDetails getEmployeeDetails(final BigDecimal employeeId) throws DefaultWrappedException {
		DIAGNOSTIC_LOGGER.info("Getting employee details from employee service for employee id:" + employeeId);

		final HttpEntity<?> entity = HttpUtil.getHeaders();
		final Employee employee = this.restTemplate.exchange(this.employeeServiceURL + employeeId, HttpMethod.GET, entity, Employee.class).getBody();
		DIAGNOSTIC_LOGGER.info("Employee details from employee service for employee id:" + employeeId + " are:" + employee);
		final EmployeeDetails employeeDetails = new EmployeeDetails(employee);

		if(null != employee) {
			final Set<ProjectAssignmentMaster> projectAssignments = this.projectAssignmentDAO.fetchAllEmployeeProjects(employeeId);

			for(final ProjectAssignmentMaster projectAssignment : projectAssignments) {
				DIAGNOSTIC_LOGGER.info("Getting project details from project service for project id:" + projectAssignment.getEmployeeId());

				final Project project = this.restTemplate.exchange(this.projectServiceURL + projectAssignment.getEmployeeId(), HttpMethod.GET, entity, Project.class).getBody();

				DIAGNOSTIC_LOGGER.info("Project details from project service for project id:" + projectAssignment.getEmployeeId() + " are:" + project);
				employeeDetails.addProject(project);
			}
		} else {
			throw new DefaultWrappedException("EMPLOYEE_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {employeeId});
		}
		return employeeDetails;
	}
}