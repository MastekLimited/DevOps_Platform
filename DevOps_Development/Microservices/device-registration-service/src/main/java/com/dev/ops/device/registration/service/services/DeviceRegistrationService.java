package com.dev.ops.device.registration.service.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dev.ops.device.registration.service.constants.DeviceRegistrationConstants;
import com.dev.ops.device.registration.service.dao.DeviceRegistrationDAO;
import com.dev.ops.device.registration.service.domain.DeviceRegistration;
import com.dev.ops.device.registration.service.entities.DeviceRegistrationMaster;
import com.dev.ops.exceptions.impl.DefaultWrappedException;

@Component
public class DeviceRegistrationService {

	@Autowired
	private MapperFacade mapperFacade;

	@Autowired
	private DeviceRegistrationDAO deviceRegistrationDAO;

	@Autowired
	private CertificateEnrollmentService certificateEnrollmentService;

	@Transactional(rollbackFor = {Exception.class})
	public DeviceRegistration getDeviceRegistrationDetails(final BigDecimal deviceRegistrationId) throws DefaultWrappedException {
		final DeviceRegistrationMaster deviceRegistrationMaster = this.deviceRegistrationDAO.findByPrimaryKey(deviceRegistrationId);
		DeviceRegistration deviceRegistrationDetails = null;
		if(null != deviceRegistrationMaster) {
			deviceRegistrationDetails = this.mapperFacade.map(deviceRegistrationMaster, DeviceRegistration.class);
		} else {
			throw new DefaultWrappedException("DEVICE_REGISTRATION_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {deviceRegistrationId});
		}
		return deviceRegistrationDetails;
	}

	@Transactional(rollbackFor = {Exception.class})
	public DeviceRegistration saveDeviceRegistrationDetails(final DeviceRegistration deviceRegistration) throws Exception {
		DeviceRegistrationMaster deviceRegistrationMaster = this.mapperFacade.map(deviceRegistration, DeviceRegistrationMaster.class);

		//Set only audit columns if the object already exists.
		if(deviceRegistrationMaster.getDeviceRegistrationId() != null) {
			final DeviceRegistrationMaster existingDeviceRegistration = this.deviceRegistrationDAO.findByPrimaryKey(deviceRegistrationMaster.getDeviceRegistrationId());
			if(existingDeviceRegistration != null) {
				deviceRegistrationMaster.setAuditColumns(existingDeviceRegistration.getAuditColumns());
			}
		}

		deviceRegistrationMaster = this.deviceRegistrationDAO.update(deviceRegistrationMaster);
		return this.mapperFacade.map(deviceRegistrationMaster, DeviceRegistration.class);
	}

	@Transactional(rollbackFor = {Exception.class})
	public DeviceRegistration saveDeviceRegistrationCSRDetails(final DeviceRegistration deviceRegistration) throws Exception {
		DeviceRegistrationMaster deviceRegistrationMaster = this.deviceRegistrationDAO.findByRegistrationId(deviceRegistration.getRegistrationId());

		if(null != deviceRegistrationMaster) {
			final DeviceRegistration existingDeviceRegistration = this.mapperFacade.map(deviceRegistrationMaster, DeviceRegistration.class);
			existingDeviceRegistration.setCsr(deviceRegistration.getCsr());
			existingDeviceRegistration.setDeviceId(deviceRegistration.getDeviceId());
			if(this.certificateEnrollmentService.validateCSR(existingDeviceRegistration.getCsr(), existingDeviceRegistration.getChallenge())) {
				existingDeviceRegistration.setCertificate(this.certificateEnrollmentService.getCertificate(existingDeviceRegistration.getCsr()));
			} else {
				throw new DefaultWrappedException(DeviceRegistrationConstants.ExceptionCodes.CSR_VALIDATION_EXCEPTION);
			}
			deviceRegistrationMaster = this.mapperFacade.map(existingDeviceRegistration, DeviceRegistrationMaster.class);
			deviceRegistrationMaster = this.deviceRegistrationDAO.update(deviceRegistrationMaster);
		} else {
			throw new DefaultWrappedException("DEVICE_REGISTRATION_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {deviceRegistration.getRegistrationId()});
		}
		return this.mapperFacade.map(deviceRegistrationMaster, DeviceRegistration.class);
	}

	@Transactional(rollbackFor = {Exception.class})
	public List<DeviceRegistration> getAllDeviceRegistrationDetails() throws DefaultWrappedException {
		final List<DeviceRegistration> deviceRegistrations = new ArrayList<DeviceRegistration>();
		final List<DeviceRegistrationMaster> deviceRegistrationMasters = this.deviceRegistrationDAO.fetchAllDeviceRegistrations();
		for(final DeviceRegistrationMaster deviceRegistrationMaster : deviceRegistrationMasters) {
			final DeviceRegistration deviceRegistration = this.mapperFacade.map(deviceRegistrationMaster, DeviceRegistration.class);
			deviceRegistrations.add(deviceRegistration);
		}
		return deviceRegistrations;
	}

	public boolean isValidDevice(final String registrationId, final String deviceId, final String applicationId) throws DefaultWrappedException {
		boolean isValidDevice = false;

		final DeviceRegistrationMaster deviceRegistrationMaster = this.deviceRegistrationDAO.findByRegistrationId(registrationId);
		if(null != deviceRegistrationMaster) {
			final DeviceRegistrationMaster inputDeviceRegistrationDetails = new DeviceRegistrationMaster(registrationId, deviceId, applicationId);
			if(inputDeviceRegistrationDetails.equals(deviceRegistrationMaster)) {
				isValidDevice = true;
			} else {
				throw new DefaultWrappedException("REGISTERED_DEVICE_VALIDATION_EXCEPTION", null, new Object[] {registrationId, deviceId, applicationId});
			}
		} else {
			throw new DefaultWrappedException("DEVICE_REGISTRATION_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {registrationId});
		}
		return isValidDevice;
	}
}