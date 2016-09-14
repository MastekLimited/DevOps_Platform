package com.dev.ops.organisation.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ops.exceptions.impl.DefaultWrappedException;

@Controller
@RequestMapping("/projectAssignments")
public class ProjectAssignmentController extends BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showProjects(final ModelAndView modelAndView) throws DefaultWrappedException {
		modelAndView.setViewName("misc/pageUnderConstruction");
		return modelAndView;
	}
}