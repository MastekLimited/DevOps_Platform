package com.dev.ops.project.assignment.service.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.thread.local.ContextThreadLocal;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.project.assignment.service.domain.EmployeeDetails;
import com.dev.ops.project.assignment.service.domain.ProjectAssignment;
import com.dev.ops.project.assignment.service.domain.ProjectDetails;
import com.dev.ops.project.assignment.service.services.ProjectAssignmentService;

@Controller
@RequestMapping("/projectAssignment")
public class ProjectAssignmentServiceController {

	@Autowired
	private ProjectAssignmentService projectAssignmentService;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(ProjectAssignmentServiceController.class);

	@RequestMapping(value = "/{projectAssignmentId}", method = RequestMethod.GET)
	@ResponseBody
	public ProjectAssignment getProjectAssignmentDetails(@PathVariable final BigDecimal projectAssignmentId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get ProjectAssignment details for:" + projectAssignmentId);
		final ProjectAssignment projectAssignment = this.projectAssignmentService.getProjectAssignmentDetails(projectAssignmentId);
		DIAGNOSTIC_LOGGER.info("The ProjectAssignment details: " + projectAssignment);
		return projectAssignment;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ProjectAssignment saveProjectAssignmentDetails(@RequestBody @Valid final ProjectAssignment projectAssignment, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final ProjectAssignment projectAssignmentDetails = this.projectAssignmentService.saveProjectAssignmentDetails(projectAssignment);
		DIAGNOSTIC_LOGGER.info("The saved ProjectAssignment details: " + projectAssignmentDetails);
		return projectAssignmentDetails;
	}

	@RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
	@ResponseBody
	public ProjectDetails getProjectDetails(@PathVariable final BigDecimal projectId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get Project details for:" + projectId);
		final ProjectDetails projectDetails = this.projectAssignmentService.getProjectDetails(projectId);
		DIAGNOSTIC_LOGGER.info("The Project details: " + projectDetails);
		return projectDetails;
	}

	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
	@ResponseBody
	public EmployeeDetails getEmployeeDetails(@PathVariable final BigDecimal employeeId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get Employee details for:" + employeeId);
		final EmployeeDetails employeeDetails = this.projectAssignmentService.getEmployeeDetails(employeeId);
		DIAGNOSTIC_LOGGER.info("The Employee details: " + employeeDetails);
		return employeeDetails;
	}

	@RequestMapping(value = "/project/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ProjectDetails saveProjectDetails(@RequestBody @Valid final ProjectDetails projectDetails, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final ProjectDetails savedProjectDetails = this.projectAssignmentService.saveProjectDetails(projectDetails);
		DIAGNOSTIC_LOGGER.info("The saved Project Assignment details: " + projectDetails);
		return savedProjectDetails;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectAssignment> getAllProjectDetails(@RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final List<ProjectAssignment> projectAssignments = this.projectAssignmentService.getAllProjectAssignmentDetails();
		DIAGNOSTIC_LOGGER.info("The ProjectAssignment details: " + projectAssignments);
		return projectAssignments;
	}
}