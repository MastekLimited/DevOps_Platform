package com.dev.ops.device.registration.service.dao;

import java.util.List;

import com.dev.ops.common.dao.generic.GenericDAO;
import com.dev.ops.device.registration.service.entities.DeviceRegistrationMaster;

public interface DeviceRegistrationDAO extends GenericDAO<DeviceRegistrationMaster> {
	List<DeviceRegistrationMaster> fetchAllDeviceRegistrations();

	DeviceRegistrationMaster findByRegistrationId(String registrationId);
}
