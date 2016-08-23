package com.dev.ops.device.authentication.service.services;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.persistence.NoResultException;

import ma.glasnost.orika.MapperFacade;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.dev.ops.common.constants.CommonConstants;
import com.dev.ops.common.services.RandomSequenceGeneratorService;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.common.utils.TimestampUtil;
import com.dev.ops.device.authentication.service.constants.DeviceAuthenticationConstants;
import com.dev.ops.device.authentication.service.dao.DeviceAuthenticationDAO;
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;
import com.dev.ops.device.authentication.service.entities.DeviceAuthenticationMaster;
import com.dev.ops.exceptions.impl.DefaultWrappedException;

@Component
public class DeviceAuthenticationService {

	@Autowired
	private MapperFacade mapperFacade;

	@Autowired
	private DeviceAuthenticationDAO deviceAuthenticationDAO;

	@Autowired
	private CertificateValidationService certificateValidationService;

	@Autowired
	private RandomSequenceGeneratorService randomSequenceGeneratorService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Value("${employee.service.url}")
	private String employeeServiceURL;

	@Autowired
	@Value("${device.registration.service.url}")
	private String deviceRegistrationServiceURL;

	private final long sessionDuration;

	@Autowired
	public DeviceAuthenticationService(@Value("${sso.token.valid.duration.hours}") final String sessionDurationHours) {
		this.sessionDuration = Long.valueOf(sessionDurationHours) * 60 * 60 * 1000;
	}

	@Transactional(rollbackFor = {Exception.class})
	public DeviceAuthentication getDeviceAuthenticationDetails(final String sessionId) throws DefaultWrappedException {
		final DeviceAuthenticationMaster deviceAuthenticationMaster = this.deviceAuthenticationDAO.findBySessionId(sessionId);
		DeviceAuthentication deviceAuthenticationDetails = null;
		if(null != deviceAuthenticationMaster) {
			deviceAuthenticationDetails = this.mapperFacade.map(deviceAuthenticationMaster, DeviceAuthentication.class);
		} else {
			throw new DefaultWrappedException("DEVICE_AUTHENTICATION_WITH_ID_NOT_FOUND_EXCEPTION", null, new Object[] {sessionId});
		}
		return deviceAuthenticationDetails;
	}

	@Transactional(rollbackFor = {Exception.class})
	public DeviceAuthentication saveDeviceAuthenticationDetails(final DeviceAuthentication deviceAuthentication) throws DefaultWrappedException, CertificateException, OperatorCreationException, CMSException, NoSuchAlgorithmException {

		final DeviceAuthentication decodedDeviceAuthentication = this.certificateValidationService.getDeviceAuthentication(deviceAuthentication.getSignedMessage());
		DeviceAuthentication savedDeviceAuthentication = null;

		//TODO: Added this check so that ssoToken will be created for web application through browser with credentials.
		if(!StringUtils.equals(DeviceAuthenticationConstants.SessionConstants.CREATE_WEB_SESSION, deviceAuthentication.getDeviceId())) {
			final HttpEntity<?> entity = HttpUtil.getHeaders();
			final boolean isValidEmployee = this.restTemplate.exchange(this.employeeServiceURL + "validateEmployee" + CommonConstants.Separators.URL_SEPARATOR + decodedDeviceAuthentication.getEmployeeId() + CommonConstants.Separators.URL_SEPARATOR + decodedDeviceAuthentication.getOtpValue(), HttpMethod.GET, entity, Boolean.class).getBody();
			if(isValidEmployee) {
				final boolean isValidDevice = this.restTemplate.exchange(this.deviceRegistrationServiceURL + "validateDevice" + CommonConstants.Separators.URL_SEPARATOR + decodedDeviceAuthentication.getRegistrationId() + CommonConstants.Separators.URL_SEPARATOR + decodedDeviceAuthentication.getDeviceId() + CommonConstants.Separators.URL_SEPARATOR + decodedDeviceAuthentication.getApplicationId(), HttpMethod.GET, entity, Boolean.class).getBody();
				if(isValidDevice) {
					savedDeviceAuthentication = this.saveDeviceAuthenticationWithSessionId(decodedDeviceAuthentication);
				}
			}
		} else {
			savedDeviceAuthentication = this.saveDeviceAuthenticationWithSessionId(decodedDeviceAuthentication);
		}
		return savedDeviceAuthentication;
	}

	private DeviceAuthentication saveDeviceAuthenticationWithSessionId(DeviceAuthentication decodedDeviceAuthentication) throws NoSuchAlgorithmException {
		//set random session id and its valid time
		decodedDeviceAuthentication.setSessionId(this.randomSequenceGeneratorService.getRandomSequence());
		decodedDeviceAuthentication.setExpiryTimestamp(TimestampUtil.getCurentPlusAdditionalTimestamp(this.sessionDuration));

		//convert VO to entity object to save. To transfer it back to calling service again convert it back to VO.
		DeviceAuthenticationMaster deviceAuthenticationMaster = this.mapperFacade.map(decodedDeviceAuthentication, DeviceAuthenticationMaster.class);
		deviceAuthenticationMaster = this.deviceAuthenticationDAO.update(deviceAuthenticationMaster);
		decodedDeviceAuthentication = this.mapperFacade.map(deviceAuthenticationMaster, DeviceAuthentication.class);
		return decodedDeviceAuthentication;
	}

	@Transactional(rollbackFor = {Exception.class})
	public boolean isValidSessionId(final String sessionId) throws DefaultWrappedException {
		boolean isValidSessionId = false;
		try {
			final DeviceAuthenticationMaster deviceAuthenticationMaster = this.deviceAuthenticationDAO.findBySessionId(sessionId);

			if(TimestampUtil.getCurentTimestamp().before(deviceAuthenticationMaster.getExpiryTimestamp())) {
				isValidSessionId = true;
			}
		} catch(final NoResultException e) {
			throw new DefaultWrappedException("SESSION_ID_NOT_FOUND_EXCEPTION", e, new Object[] {sessionId});
		}
		return isValidSessionId;
	}

	@Transactional(rollbackFor = {Exception.class})
	public DeviceAuthentication inValidateSessionId(final String sessionId) throws DefaultWrappedException {
		DeviceAuthentication deviceAuthentication = null;
		try {
			DeviceAuthenticationMaster deviceAuthenticationMaster = this.deviceAuthenticationDAO.findBySessionId(sessionId);

			if(TimestampUtil.getCurentTimestamp().before(deviceAuthenticationMaster.getExpiryTimestamp())) {
				deviceAuthenticationMaster.setExpiryTimestamp(TimestampUtil.getCurentTimestamp());
				deviceAuthenticationMaster = this.deviceAuthenticationDAO.update(deviceAuthenticationMaster);
			}
			deviceAuthentication = this.mapperFacade.map(deviceAuthenticationMaster, DeviceAuthentication.class);
		} catch(final NoResultException e) {
			throw new DefaultWrappedException("SESSION_ID_NOT_FOUND_EXCEPTION", e, new Object[] {sessionId});
		}
		return deviceAuthentication;
	}
}