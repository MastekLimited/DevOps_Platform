package com.dev.ops.device.authentication.service.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
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
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;
import com.dev.ops.device.authentication.service.services.DeviceAuthenticationService;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;

@Controller
@RequestMapping("/deviceAuthentication")
public class DeviceAuthenticationServiceController {

	@Autowired
	private DeviceAuthenticationService deviceAuthenticationService;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(DeviceAuthenticationServiceController.class);

	@RequestMapping(value = "/{sessionId}", method = RequestMethod.GET)
	@ResponseBody
	public DeviceAuthentication getDeviceAuthenticationDetails(@PathVariable final String sessionId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		DIAGNOSTIC_LOGGER.info("Get DeviceAuthentication details for:" + sessionId);
		final DeviceAuthentication deviceAuthentication = this.deviceAuthenticationService.getDeviceAuthenticationDetails(sessionId);
		DIAGNOSTIC_LOGGER.info("The DeviceAuthentication details: " + deviceAuthentication);
		return deviceAuthentication;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	@ResponseBody
	public DeviceAuthentication saveDeviceAuthenticationDetails(@RequestBody @Valid final DeviceAuthentication deviceAuthentication, @RequestHeader("context") final String context) throws DefaultWrappedException, CertificateException, OperatorCreationException, CMSException, NoSuchAlgorithmException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final DeviceAuthentication deviceAuthenticationDetails = this.deviceAuthenticationService.saveDeviceAuthenticationDetails(deviceAuthentication);
		DIAGNOSTIC_LOGGER.info("The saved DeviceAuthentication details: " + deviceAuthenticationDetails);
		return deviceAuthenticationDetails;
	}

	@RequestMapping(value = "isValidSessionId/{sessionId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean isValidSessionId(@PathVariable final String sessionId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final boolean isValidSessionId = this.deviceAuthenticationService.isValidSessionId(sessionId);
		DIAGNOSTIC_LOGGER.info("Session id: " + sessionId + " is " + (isValidSessionId ? "valid" : "invalid") + ".");
		return isValidSessionId;
	}

	@RequestMapping(value = "inValidateSessionId/{sessionId}", method = RequestMethod.GET)
	@ResponseBody
	public DeviceAuthentication inValidateSessionId(@PathVariable final String sessionId, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final DeviceAuthentication deviceAuthentication = this.deviceAuthenticationService.inValidateSessionId(sessionId);
		DIAGNOSTIC_LOGGER.info("Invalidated device authentication session: " + deviceAuthentication);
		return deviceAuthentication;
	}
}