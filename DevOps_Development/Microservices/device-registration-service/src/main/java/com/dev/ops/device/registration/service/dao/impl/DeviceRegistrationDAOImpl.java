package com.dev.ops.device.registration.service.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.dev.ops.common.dao.generic.GenericDAOImpl;
import com.dev.ops.device.registration.service.dao.DeviceRegistrationDAO;
import com.dev.ops.device.registration.service.entities.DeviceRegistrationMaster;

@Service
public class DeviceRegistrationDAOImpl extends GenericDAOImpl<DeviceRegistrationMaster> implements DeviceRegistrationDAO {

	@Override
	public List<DeviceRegistrationMaster> fetchAllDeviceRegistrations() {
		return this.getEntityManager().createNamedQuery("DeviceRegistrationMaster.fetchAllDeviceRegistrations", DeviceRegistrationMaster.class).getResultList();
	}

	@Override
	public DeviceRegistrationMaster findByRegistrationId(final String registrationId) {
		final Query query = this.getEntityManager().createNamedQuery("DeviceRegistrationMaster.findByRegistrationId", DeviceRegistrationMaster.class);
		query.setParameter("registrationId", registrationId);
		return (DeviceRegistrationMaster) query.getSingleResult();
	}
}