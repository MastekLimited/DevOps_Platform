package com.dev.ops.device.registration.service.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.thread.local.ContextThreadLocal;
import com.dev.ops.device.registration.service.domain.DeviceRegistration;
import com.dev.ops.device.registration.service.services.DeviceRegistrationService;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;

@Controller
@RequestMapping("/deviceRegistration")
public class DeviceRegistrationServiceController {

	@Autowired
	private DeviceRegistrationService deviceRegistrationService;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(DeviceRegistrationServiceController.class);

	@RequestMapping(value = "/{deviceRegistrationId}", method = RequestMethod.GET)
	@ResponseBody
	public DeviceRegistration getDeviceRegistrationDetails(@PathVariable final BigDecimal deviceRegistrationId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get DeviceRegistration details for:" + deviceRegistrationId);
		final DeviceRegistration deviceRegistration = this.deviceRegistrationService.getDeviceRegistrationDetails(deviceRegistrationId);
		DIAGNOSTIC_LOGGER.info("The DeviceRegistration details: " + deviceRegistration);
		return deviceRegistration;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public DeviceRegistration saveDeviceRegistrationDetails(@RequestBody @Valid final DeviceRegistration deviceRegistration, @RequestHeader("context") final String context) throws Exception {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final DeviceRegistration deviceRegistrationDetails = this.deviceRegistrationService.saveDeviceRegistrationDetails(deviceRegistration);
		DIAGNOSTIC_LOGGER.info("The saved DeviceRegistration details: " + deviceRegistrationDetails);
		return deviceRegistrationDetails;
	}

	@RequestMapping(value = "/submitCSR", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public DeviceRegistration saveDeviceRegistrationCSRDetails(@RequestBody @Valid final DeviceRegistration deviceRegistration, @RequestHeader("context") final String context) throws Exception {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final DeviceRegistration deviceRegistrationDetails = this.deviceRegistrationService.saveDeviceRegistrationCSRDetails(deviceRegistration);
		DIAGNOSTIC_LOGGER.info("The saved DeviceRegistration details: " + deviceRegistrationDetails);
		return deviceRegistrationDetails;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<DeviceRegistration> getAllDeviceRegistrationDetails(@RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final List<DeviceRegistration> deviceRegistrations = this.deviceRegistrationService.getAllDeviceRegistrationDetails();
		DIAGNOSTIC_LOGGER.info("The DeviceRegistration details: " + deviceRegistrations);
		return deviceRegistrations;
	}

	@RequestMapping(value = "/validateDevice/{registrationId}/{deviceId}/{applicationId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean validateDevice(@PathVariable final String registrationId, @PathVariable final String deviceId, @PathVariable final String applicationId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final boolean isValidDevice = this.deviceRegistrationService.isValidDevice(registrationId, deviceId, applicationId);
		DIAGNOSTIC_LOGGER.info("Is device valid for registrationId:<" + registrationId + "> deviceId:<" + deviceId + "> applicationId:<" + applicationId + "> isValid:<" + isValidDevice + ">");
		return isValidDevice;
	}
}