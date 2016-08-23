package com.dev.ops.organisation.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.organisation.web.constants.WebConstants;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController {

	@Autowired
	@Value(value = "${employee.service.url}")
	private String employeeServiceURL;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(EmployeeController.class);

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showEmployees(final ModelAndView modelAndView, final HttpServletRequest request) {
		modelAndView.setViewName("employee/employees");
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/employeesJSON", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> getEmployees(final HttpServletRequest request) throws DefaultWrappedException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Employee.READ_ALL, null);
		return this.restTemplate.exchange(this.employeeServiceURL, HttpMethod.GET, entity, List.class).getBody();
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showForm(@ModelAttribute final Employee requestEmployee, final ModelAndView modelAndView, final HttpServletRequest request) throws DefaultWrappedException {
		Employee employee = null;
		if(requestEmployee.getEmployeeId() != null) {
			final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Employee.READ, null);
			employee = this.restTemplate.exchange(this.employeeServiceURL + requestEmployee.getEmployeeId(), HttpMethod.GET, entity, Employee.class).getBody();
		}

		modelAndView.addObject("employee", null != employee && null != employee.getEmployeeId() ? employee : new Employee());
		modelAndView.setViewName("employee/employee");
		return modelAndView;
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getEmployee(@PathVariable final String employeeId, final ModelAndView modelAndView, final HttpServletRequest request) throws DefaultWrappedException, IOException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Employee.READ, null);
		final Employee employee = this.restTemplate.exchange(this.employeeServiceURL + employeeId, HttpMethod.GET, entity, Employee.class).getBody();
		modelAndView.addObject("employee", employee);
		modelAndView.setViewName("employee/viewEmployee");
		return modelAndView;
	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public ModelAndView saveEmployee(@ModelAttribute final Employee employee, final ModelAndView modelAndView, final RedirectAttributes redirectAttributes, final HttpServletRequest request) throws DefaultWrappedException {
		modelAndView.setViewName("redirect:employees");
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.Employee.SAVE, employee);
		final Employee savedEmployee = this.restTemplate.exchange(this.employeeServiceURL, HttpMethod.POST, entity, Employee.class).getBody();
		redirectAttributes.addFlashAttribute("savedEmployee", savedEmployee);
		DIAGNOSTIC_LOGGER.info("The saved employee details: " + savedEmployee);
		return modelAndView;
	}
}