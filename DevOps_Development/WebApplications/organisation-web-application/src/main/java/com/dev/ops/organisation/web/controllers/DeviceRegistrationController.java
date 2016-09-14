package com.dev.ops.organisation.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.device.registration.service.domain.DeviceRegistration;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.organisation.web.constants.WebConstants;

@Controller
@RequestMapping("/deviceRegistration")
public class DeviceRegistrationController extends BaseController {

	@Autowired
	@Value(value = "${device.registration.service.url}")
	private String deviceRegistrationServiceURL;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(DeviceRegistrationController.class);

	@RequestMapping(value = "/deviceRegistrations", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showDeviceRegistrations(final ModelAndView modelAndView) {
		modelAndView.setViewName("device-registration/deviceRegistrations");
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deviceRegistrationsJSON", method = RequestMethod.GET)
	@ResponseBody
	public List<DeviceRegistration> getDeviceRegistrations() throws DefaultWrappedException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.DeviceRegistration.READ_ALL, null);
		return this.restTemplate.exchange(this.deviceRegistrationServiceURL, HttpMethod.GET, entity, List.class).getBody();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showForm(final ModelAndView modelAndView) throws DefaultWrappedException {
		final DeviceRegistration deviceRegistration = new DeviceRegistration();

		//Get unique device Id
		deviceRegistration.setRegistrationId(this.getDeviceRegistrionId());

		modelAndView.addObject("deviceRegistration", deviceRegistration);
		modelAndView.setViewName("device-registration/deviceRegistration");
		return modelAndView;
	}

	@RequestMapping(value = "/{deviceRegistrationId}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getDeviceRegistration(@PathVariable final String deviceRegistrationId, final ModelAndView modelAndView, final HttpServletRequest request) throws DefaultWrappedException, IOException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.DeviceRegistration.READ, null);
		final DeviceRegistration deviceRegistration = this.restTemplate.exchange(this.deviceRegistrationServiceURL + deviceRegistrationId, HttpMethod.GET, entity, DeviceRegistration.class).getBody();
		modelAndView.addObject("deviceRegistration", deviceRegistration);
		modelAndView.setViewName("device-registration/viewDeviceRegistration");
		return modelAndView;
	}

	@RequestMapping(value = "/saveDeviceRegistration", method = RequestMethod.POST)
	public ModelAndView saveDeviceRegistration(@ModelAttribute final DeviceRegistration deviceRegistration, final ModelAndView modelAndView, final RedirectAttributes redirectAttributes) throws DefaultWrappedException {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(WebConstants.Operations.DeviceRegistration.SAVE, deviceRegistration);
		final DeviceRegistration savedDeviceRegistration = this.restTemplate.exchange(this.deviceRegistrationServiceURL, HttpMethod.POST, entity, DeviceRegistration.class).getBody();
		redirectAttributes.addFlashAttribute("savedDeviceRegistration", savedDeviceRegistration);
		modelAndView.setViewName("redirect:/deviceRegistration/" + savedDeviceRegistration.getDeviceRegistrationId());
		DIAGNOSTIC_LOGGER.info("The saved deviceRegistration details: " + savedDeviceRegistration);
		return modelAndView;
	}

	public String getDeviceRegistrionId() {
		final String deviceRegistrationId = UUID.randomUUID().toString();
		return deviceRegistrationId.toUpperCase();
	}
}