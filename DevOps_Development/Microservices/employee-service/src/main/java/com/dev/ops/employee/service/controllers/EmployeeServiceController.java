package com.dev.ops.employee.service.controllers;

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
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.employee.service.services.EmployeeService;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;

@Controller
@RequestMapping("/employee")
public class EmployeeServiceController {

	@Autowired
	private EmployeeService employeeService;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(EmployeeServiceController.class);

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	@ResponseBody
	public Employee getEmployeeDetails(@PathVariable final BigDecimal employeeId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get Employee details for:" + employeeId);
		final Employee employee = this.employeeService.getEmployeeDetails(employeeId, ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("The Employee details: " + employee);
		return employee;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Employee saveEmployeeDetails(@RequestBody @Valid final Employee employee, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final Employee employeeDetails = this.employeeService.saveEmployeeDetails(employee, ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("The saved Employee details: " + employeeDetails);
		return employeeDetails;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> getAllEmployeeDetails(@RequestHeader("context") final String context) throws DefaultWrappedException {
		/*public List<Employee> getAllEmployeeDetails(@RequestHeader("contextInfo") ContextInfo contextInfo) throws DefaultWrappedException {
		public List<Employee> getAllEmployeeDetails(@RequestHeader HttpHeaders headers) throws DefaultWrappedException {
			System.out.println(headers);
			System.out.println(headers.getFirst("contextInfo"));*/
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final List<Employee> employees = this.employeeService.getAllEmployeeDetails(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("The Employee details: " + employees);
		return employees;
	}

	@RequestMapping(value = "/validateEmployee/{employeeId}/{otpValue}", method = RequestMethod.GET)
	@ResponseBody
	public boolean validateEmployee(@PathVariable final BigDecimal employeeId, @PathVariable final String otpValue, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get Employee details for:" + employeeId);
		final boolean isValidEmployee = this.employeeService.isValidEmployee(employeeId, otpValue);
		DIAGNOSTIC_LOGGER.info("Is employee valid for employeeId:<" + employeeId + "> otpValue:<" + otpValue + " isValid:<" + isValidEmployee + ">");
		return isValidEmployee;
	}
}