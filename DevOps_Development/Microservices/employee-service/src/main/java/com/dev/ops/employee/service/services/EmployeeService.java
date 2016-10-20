package com.dev.ops.employee.service.services;

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

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.services.GoogleAuthenticatorService;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.employee.service.dao.impl.EmployeeDAO;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.employee.service.entities.AddressMaster;
import com.dev.ops.employee.service.entities.EmployeeMaster;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.uuid.service.domain.EntityType;

@Component
public class EmployeeService {

	@Autowired
	private MapperFacade mapperFacade;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Autowired
	private GoogleAuthenticatorService googleAuthenticatorService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Value("${uuid.service.url}")
	private String uuidServiceURL;

	@Transactional(rollbackFor = {Exception.class})
	public Employee getEmployeeDetails(final BigDecimal employeeId, final ContextInfo contextInfo) throws DefaultWrappedException {
		final EmployeeMaster employeeMaster = this.employeeDAO.findByPrimaryKey(employeeId, contextInfo);
		Employee employeeDetails = null;
		if(null != employeeMaster) {
			employeeDetails = this.mapperFacade.map(employeeMaster, Employee.class);
		} else {
			throw new DefaultWrappedException("EMPLOYEE_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {employeeId});
		}
		return employeeDetails;
	}

	@Transactional(rollbackFor = {Exception.class})
	public Employee saveEmployeeDetails(final Employee employee, final ContextInfo contextInfo) throws DefaultWrappedException {
		EmployeeMaster employeeMaster = this.mapperFacade.map(employee, EmployeeMaster.class);

		//Add google authenticator key and employee number only if new employee is newly getting created.
		if(employeeMaster.getEmployeeId() == null) {
			employeeMaster.setGoogleAuthenticatorKey(this.googleAuthenticatorService.generateAuthenticationKey());

			final HttpEntity<?> entity = HttpUtil.getHeaders();
			final String employeeNumber = this.restTemplate.exchange(this.uuidServiceURL + EntityType.EMPLOYEE.name(), HttpMethod.GET, entity, String.class).getBody();
			employeeMaster.setEmployeeNumber(employeeNumber);
		} else {
			//Set only audit columns if the object already exists.
			final EmployeeMaster existingEmployee = this.employeeDAO.findByPrimaryKey(employeeMaster.getEmployeeId());

			if(existingEmployee != null) {
				employeeMaster.setAuditColumns(existingEmployee.getAuditColumns());
				final AddressMaster existingAddress = existingEmployee.getAddress();
				employeeMaster.getAddress().setAuditColumns(existingAddress.getAuditColumns());
			}
		}

		employeeMaster = this.employeeDAO.update(employeeMaster, contextInfo);
		return this.mapperFacade.map(employeeMaster, Employee.class);
	}

	@Transactional(rollbackFor = {Exception.class})
	public List<Employee> getAllEmployeeDetails(final ContextInfo contextInfo) throws DefaultWrappedException {
		final List<Employee> employees = new ArrayList<Employee>();
		final List<EmployeeMaster> employeeMasters = this.employeeDAO.fetchAllEmployees(contextInfo);
		for(final EmployeeMaster employeeMaster : employeeMasters) {
			final Employee employee = this.mapperFacade.map(employeeMaster, Employee.class);
			employees.add(employee);
		}
		return employees;
	}

	public boolean isValidEmployee(final BigDecimal employeeId, final String otpValue) throws DefaultWrappedException {
		boolean isValidEmployee = false;
		final EmployeeMaster employeeMaster = this.employeeDAO.findByPrimaryKey(employeeId);
		if(null != employeeMaster) {
			final String googleAuthenticatorKey = employeeMaster.getGoogleAuthenticatorKey();
			isValidEmployee = this.googleAuthenticatorService.authenticateCredentials(googleAuthenticatorKey, Integer.valueOf(otpValue));
			if(!isValidEmployee) {
				throw new DefaultWrappedException("EMPLOYEE_OTP_VERIFICATION_EXCEPTION", null, new Object[] {employeeId, otpValue});
			}
		} else {
			throw new DefaultWrappedException("EMPLOYEE_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {employeeId});
		}
		return isValidEmployee;
	}
}