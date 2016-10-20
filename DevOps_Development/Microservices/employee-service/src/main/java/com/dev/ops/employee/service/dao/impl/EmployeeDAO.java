package com.dev.ops.employee.service.dao.impl;

import java.util.List;

import com.dev.ops.common.dao.generic.GenericDAO;
import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.employee.service.entities.EmployeeMaster;

public interface EmployeeDAO extends GenericDAO<EmployeeMaster> {
	List<EmployeeMaster> fetchAllEmployees(ContextInfo contextInfo);
}
