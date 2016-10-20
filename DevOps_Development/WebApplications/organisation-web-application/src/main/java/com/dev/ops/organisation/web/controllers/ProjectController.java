package com.dev.ops.organisation.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.organisation.web.constants.WebConstants;
import com.dev.ops.project.service.domain.Project;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

	@Autowired
	@Value(value = "${project.service.url}")
	private String projectServiceURL;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(ProjectController.class);

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showProjects(final ModelAndView modelAndView) throws DefaultWrappedException {
		modelAndView.setViewName("project/projects");
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/projectsJSON", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getProjects() throws DefaultWrappedException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Project.READ_ALL, null);
		return this.restTemplate.exchange(this.projectServiceURL, HttpMethod.GET, entity, List.class).getBody();
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showForm(@ModelAttribute final Project requestProject, final ModelAndView modelAndView) throws DefaultWrappedException {
		Project project = null;

		if(requestProject.getProjectId() != null) {
			final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Project.READ, null);
			project = this.restTemplate.exchange(this.projectServiceURL + requestProject.getProjectId(), HttpMethod.GET, entity, Project.class).getBody();
		}

		modelAndView.addObject("project", null != project && null != project.getProjectId() ? project : new Project());
		modelAndView.setViewName("project/project");
		return modelAndView;
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getEmployee(@PathVariable final String projectId, final ModelAndView modelAndView) throws DefaultWrappedException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Project.READ, null);
		final Project project = this.restTemplate.exchange(this.projectServiceURL + projectId, HttpMethod.GET, entity, Project.class).getBody();

		modelAndView.addObject("project", project);
		modelAndView.setViewName("project/viewProject");
		return modelAndView;
	}

	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public ModelAndView saveProject(@ModelAttribute final Project project, final ModelAndView modelAndView, final RedirectAttributes redirectAttributes) throws DefaultWrappedException {
		modelAndView.setViewName("redirect:projects");

		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Project.SAVE, project);
		final Project savedProject = this.restTemplate.exchange(this.projectServiceURL, HttpMethod.POST, entity, Project.class).getBody();

		redirectAttributes.addFlashAttribute("savedProject", savedProject);
		DIAGNOSTIC_LOGGER.info("The saved user login details: " + savedProject);
		return modelAndView;
	}
}