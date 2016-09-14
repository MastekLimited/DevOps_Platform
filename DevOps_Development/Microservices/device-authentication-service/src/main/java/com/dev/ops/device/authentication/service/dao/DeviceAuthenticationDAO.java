package com.dev.ops.device.authentication.service.dao;

import java.util.List;

import com.dev.ops.common.dao.generic.GenericDAO;
import com.dev.ops.device.authentication.service.entities.DeviceAuthenticationMaster;

public interface DeviceAuthenticationDAO extends GenericDAO<DeviceAuthenticationMaster> {
	List<DeviceAuthenticationMaster> fetchAllDeviceAuthentications();

	DeviceAuthenticationMaster findBySessionId(String sessionId);
}
