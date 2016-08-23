package com.dev.ops.employee.service.mapper.config;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import ma.glasnost.orika.MapperFacade;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dev.ops.common.orika.mapper.config.MapperFacadeFactoryBean;
import com.dev.ops.common.orika.mapper.config.MappingConfigurer;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.employee.service.entities.EmployeeMaster;
import com.dev.ops.employee.service.orika.mapper.mappings.EmployeeMappingConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MapperFacadeFactoryBean.class, EmployeeMappingConfigurer.class})
public class MapperFacadeFactoryBeanTest {

	@Autowired
	private MapperFacade mapperFacade;

	@Test
	public void testObjectConversion() {
		final BigDecimal employeeId = new BigDecimal("1");
		final Employee employee = this.getEmployee(employeeId);

		final EmployeeMaster employeeMaster = this.mapperFacade.map(employee, EmployeeMaster.class);
		Assert.assertNotNull(employeeMaster);
	}

	@Test
	public void testForParameterisedConstructor() {
		final Set<MappingConfigurer> configurers = new HashSet<MappingConfigurer>();
		configurers.add(new EmployeeMappingConfigurer());

		final MapperFacadeFactoryBean mapperFactory = new MapperFacadeFactoryBean(configurers);
		Assert.assertNotNull("Mapper facade object is not null.", mapperFactory);
	}

	private Employee getEmployee(final BigDecimal employeeId) {
		return new Employee(employeeId, "0001112656", "Adam", "Irish", new BigDecimal(767688886), "+448976874534", "mark.irish@devops.com", null, null);
	}
}
