package com.dev.ops.device.authentication.service.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.dev.ops.common.dao.generic.GenericDAOImpl;
import com.dev.ops.device.authentication.service.dao.DeviceAuthenticationDAO;
import com.dev.ops.device.authentication.service.entities.DeviceAuthenticationMaster;

@Service
public class DeviceAuthenticationDAOImpl extends GenericDAOImpl<DeviceAuthenticationMaster> implements DeviceAuthenticationDAO {

	@Override
	public List<DeviceAuthenticationMaster> fetchAllDeviceAuthentications() {
		return this.getEntityManager().createNamedQuery("DeviceAuthenticationMaster.fetchAllDeviceAuthentications", DeviceAuthenticationMaster.class).getResultList();
	}

	@Override
	public DeviceAuthenticationMaster findBySessionId(final String sessionId) {
		final Query query = this.getEntityManager().createNamedQuery("DeviceAuthenticationMaster.findBySessionId", DeviceAuthenticationMaster.class);
		query.setParameter("sessionId", sessionId);
		return (DeviceAuthenticationMaster) query.getSingleResult();
	}
}