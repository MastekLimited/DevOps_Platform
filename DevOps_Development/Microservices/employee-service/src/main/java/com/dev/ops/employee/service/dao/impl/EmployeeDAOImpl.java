package com.dev.ops.employee.service.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.ops.common.dao.generic.GenericDAOImpl;
import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.employee.service.entities.EmployeeMaster;

@Service
public class EmployeeDAOImpl extends GenericDAOImpl<EmployeeMaster> implements EmployeeDAO {

	@Override
	public List<EmployeeMaster> fetchAllEmployees(final ContextInfo contextInfo) {
		return this.getEntityManager().createNamedQuery("EmployeeMaster.fetchAllEmployees", EmployeeMaster.class).getResultList();
	}
}