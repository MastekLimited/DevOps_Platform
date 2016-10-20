package com.dev.ops.employee.service.controllers;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.employee.service.services.EmployeeService;
import com.dev.ops.exceptions.impl.DefaultWrappedException;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceControllerTest {

	@InjectMocks
	private final EmployeeServiceController employeeServiceController = new EmployeeServiceController();

	@Mock
	private EmployeeService employeeService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(EmployeeServiceController.class);
	}

	@Test
	public void testGetUserLoginDetails() throws DefaultWrappedException {
		final BigDecimal employeeId = new BigDecimal("1");
		final Employee mockedEmployee = this.getEmployee(employeeId);
		final ContextInfo contextInfo = new ContextInfo("moduleName", "operation");

		Mockito.doReturn(mockedEmployee).when(this.employeeService).getEmployeeDetails(Mockito.any(BigDecimal.class), Mockito.any(ContextInfo.class));
		final Employee employee = this.employeeServiceController.getEmployeeDetails(employeeId, contextInfo.toString());
		Assert.assertEquals("Mocked and returned objects should be equal.", mockedEmployee, employee);
		Assert.assertEquals("Mocked and returned EmployeeNumber should be equal.", mockedEmployee.getEmployeeNumber(), employee.getEmployeeNumber());
		Assert.assertEquals("Mocked and returned FirstName should be equal.", mockedEmployee.getFirstName(), employee.getFirstName());
		Assert.assertEquals("Mocked and returned LastName should be equal.", mockedEmployee.getLastName(), employee.getLastName());
		Assert.assertEquals("Mocked and returned Salary should be equal.", mockedEmployee.getSalary(), employee.getSalary());
		Assert.assertEquals("Mocked and returned MobileNumber should be equal.", mockedEmployee.getMobileNumber(), employee.getMobileNumber());
		Assert.assertEquals("Mocked and returned EmailAddress should be equal.", mockedEmployee.getEmailAddress(), employee.getEmailAddress());
		Assert.assertEquals("Mocked and returned Address should be equal.", mockedEmployee.getAddress(), employee.getAddress());

		Mockito.verify(this.employeeService, times(1)).getEmployeeDetails(Mockito.any(BigDecimal.class), Mockito.any(ContextInfo.class));

		Mockito.verifyNoMoreInteractions(this.employeeService);
	}

	private Employee getEmployee(final BigDecimal employeeId) {
		return new Employee(employeeId, "0001112656", "Adam", "Irish", new BigDecimal(767688886), "+448976874534", "mark.irish@devops.com", null, null);
	}

	@Test
	public void testSaveUserLoginDetails() throws DefaultWrappedException {
		final BigDecimal employeeId = new BigDecimal("1");
		final Employee mockedEmployee = this.getEmployee(employeeId);
		final ContextInfo contextInfo = new ContextInfo("moduleName", "operation");

		Mockito.doReturn(mockedEmployee).when(this.employeeService).saveEmployeeDetails(Mockito.any(Employee.class), Mockito.any(ContextInfo.class));
		final Employee employee = this.employeeServiceController.saveEmployeeDetails(mockedEmployee, contextInfo.toString());
		Assert.assertEquals("Mocked and returned objects should be equal.", mockedEmployee, employee);
		Assert.assertEquals("Mocked and returned EmployeeNumber should be equal.", mockedEmployee.getEmployeeNumber(), employee.getEmployeeNumber());
		Assert.assertEquals("Mocked and returned FirstName should be equal.", mockedEmployee.getFirstName(), employee.getFirstName());
		Assert.assertEquals("Mocked and returned LastName should be equal.", mockedEmployee.getLastName(), employee.getLastName());
		Assert.assertEquals("Mocked and returned Salary should be equal.", mockedEmployee.getSalary(), employee.getSalary());
		Assert.assertEquals("Mocked and returned MobileNumber should be equal.", mockedEmployee.getMobileNumber(), employee.getMobileNumber());
		Assert.assertEquals("Mocked and returned EmailAddress should be equal.", mockedEmployee.getEmailAddress(), employee.getEmailAddress());
		Assert.assertEquals("Mocked and returned Address should be equal.", mockedEmployee.getAddress(), employee.getAddress());

		Mockito.verify(this.employeeService, times(1)).saveEmployeeDetails(Mockito.any(Employee.class), Mockito.any(ContextInfo.class));

		Mockito.verifyNoMoreInteractions(this.employeeService);
	}
}