package com.dev.ops.organisation.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ops.common.constants.CommonConstants;
import com.dev.ops.common.utils.QRCodeUtil;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.organisation.web.constants.WebConstants;

@Controller
@RequestMapping("")
public class StaticMappingsController extends BaseController {

	@Autowired
	@Value(value = "${device.registration.service.url}")
	private String deviceRegistrationServiceURL;

	@Autowired
	@Value(value = "${authentication.service.url}")
	private String authenticationServiceURL;

	@Autowired
	@Value(value = "${employee.service.url}")
	private String employeeServiceURL;

	@Autowired
	@Value(value = "${organisation.web.url}")
	private String organisationWebURL;

	private static final String IMAGE_JPEG = "image/jpeg";

	//private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(StaticMappingsController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView homePage(final ModelAndView modelAndView, final HttpServletRequest request, final HttpServletResponse response) throws DefaultWrappedException {
		modelAndView.setViewName("misc/home");
		return modelAndView;
	}

	@RequestMapping(value = "about", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView aboutPage(final ModelAndView modelAndView) throws DefaultWrappedException {
		modelAndView.setViewName("misc/pageUnderConstruction");
		return modelAndView;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView logout(final ModelAndView modelAndView) throws DefaultWrappedException {
		modelAndView.setViewName("misc/logout");
		return modelAndView;
	}

	@RequestMapping(value = "getDeviceRegistrationQRCode/{registrationCode}", method = RequestMethod.GET, produces = IMAGE_JPEG)
	@ResponseBody
	public byte[] getDeviceRegistrationQRCode(@PathVariable final String registrationCode) throws DefaultWrappedException, IOException {
		final StringBuilder qrCode = new StringBuilder();

		qrCode.append(this.getQRCodeParameterString(WebConstants.DeviceRegistration.QRCode.REGISTRATION_CODE, registrationCode));
		qrCode.append(this.getQRCodeParameterString(WebConstants.DeviceRegistration.QRCode.DEVICE_REGISTRATION_SERVICE, this.deviceRegistrationServiceURL));
		qrCode.append(this.getQRCodeParameterString(WebConstants.DeviceRegistration.QRCode.AUTHENTICATION_SERVICE, this.authenticationServiceURL));
		qrCode.append(this.getQRCodeParameterString(WebConstants.DeviceRegistration.QRCode.EMPLOYEE_SERVICE, this.employeeServiceURL));
		qrCode.append(this.getQRCodeParameterString(WebConstants.DeviceRegistration.QRCode.ORGANISATION_WEB, this.organisationWebURL));

		return QRCodeUtil.getQRCodeImage(qrCode.substring(0, qrCode.length() - 1).toString());
	}

	@RequestMapping(value = "getGoogleAuthenticatorQRCode/{user}/{googleAuthenticatorKey}/{name}", method = RequestMethod.GET, produces = IMAGE_JPEG)
	@ResponseBody
	public byte[] getGoogleAuthenticatorQRCode(@PathVariable final String user, @PathVariable final String googleAuthenticatorKey, @PathVariable final String name) throws DefaultWrappedException, IOException {
		final String format = "otpauth://totp/" + name + ":" + user + "@devops?secret=" + googleAuthenticatorKey + "&issuer=" + name;
		return QRCodeUtil.getQRCodeImage(format);
	}

	private String getQRCodeParameterString(final String key, final String value) {
		final StringBuilder keyValue = new StringBuilder();
		keyValue.append(key);
		keyValue.append(value);
		keyValue.append(CommonConstants.Separators.DOLLAR_SEPARATOR);
		return keyValue.toString();
	}
}