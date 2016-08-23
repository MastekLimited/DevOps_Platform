package com.dev.ops.project.service.controllers;

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
import com.dev.ops.project.service.domain.Project;
import com.dev.ops.project.service.services.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectServiceController {

	@Autowired
	private ProjectService projectService;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(ProjectServiceController.class);

	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
	@ResponseBody
	public Project getProjectDetails(@PathVariable final BigDecimal projectId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get Project details for:" + projectId);
		final Project project = this.projectService.getProjectDetails(projectId);
		DIAGNOSTIC_LOGGER.info("The Project details: " + project);
		return project;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Project saveProjectDetails(@RequestBody @Valid final Project project, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final Project projectDetails = this.projectService.saveProjectDetails(project);
		DIAGNOSTIC_LOGGER.info("The saved Project details: " + projectDetails);
		return projectDetails;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getAllProjectDetails(@RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final List<Project> projects = this.projectService.getAllProjectDetails();
		DIAGNOSTIC_LOGGER.info("The Project details: " + projects);
		return projects;
	}
}