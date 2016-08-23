package com.dev.ops.device.authentication.service.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DeviceAuthentication {

	private BigDecimal deviceAuthenticationId;
	private BigDecimal employeeId;
	private String registrationId;
	private String deviceId;
	private String applicationId;
	private String otpValue;
	private String signedMessage;
	private String sessionId;
	private Timestamp expiryTimestamp;

	public DeviceAuthentication() {
	}

	public DeviceAuthentication(final BigDecimal deviceAuthenticationId, final BigDecimal employeeId, final String registrationId, final String deviceId, final String applicationId, final String otpValue, final String signedMessage, final String sessionId, final Timestamp expiryTimestamp) {
		this();
		this.deviceAuthenticationId = deviceAuthenticationId;
		this.employeeId = employeeId;
		this.registrationId = registrationId;
		this.deviceId = deviceId;
		this.applicationId = applicationId;
		this.otpValue = otpValue;
		this.signedMessage = signedMessage;
		this.sessionId = sessionId;
		this.expiryTimestamp = expiryTimestamp;
	}

	public DeviceAuthentication(final BigDecimal employeeId, final String deviceRegistrationId, final String deviceId, final String applicationId, final String otpValue, final String signedMessage, final String sessionId) {
		this(null, employeeId, deviceRegistrationId, deviceId, applicationId, otpValue, signedMessage, sessionId, null);
	}

	public DeviceAuthentication(final BigDecimal employeeId, final String deviceRegistrationId, final String deviceId, final String applicationId, final String otpValue, final String signedMessage) {
		this(employeeId, deviceRegistrationId, deviceId, applicationId, otpValue, signedMessage, null);
	}

	public DeviceAuthentication(final BigDecimal employeeId, final String deviceRegistrationId, final String deviceId, final String applicationId, final String otpValue) {
		this(employeeId, deviceRegistrationId, deviceId, applicationId, otpValue, null);
	}

	public DeviceAuthentication(final BigDecimal employeeId, final String deviceId, final String signedMessage) {
		this(employeeId, null, deviceId, null, null, signedMessage);
	}

	public DeviceAuthentication(final String deviceId, final String signedMessage) {
		this(null, null, deviceId, null, null, signedMessage);
	}

	public DeviceAuthentication(final String sessionId) {
		this(null, null, null, null, null, null, sessionId);
	}

	public BigDecimal getDeviceAuthenticationId() {
		return this.deviceAuthenticationId;
	}

	public void setDeviceAuthenticationId(final BigDecimal deviceAuthenticationId) {
		this.deviceAuthenticationId = deviceAuthenticationId;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(final BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getRegistrationId() {
		return this.registrationId;
	}

	public void setRegistrationId(final String registrationId) {
		this.registrationId = registrationId;
	}

	public String getSignedMessage() {
		return this.signedMessage;
	}

	public void setSignedMessage(final String signedMessage) {
		this.signedMessage = signedMessage;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	public Timestamp getExpiryTimestamp() {
		return this.expiryTimestamp;
	}

	public void setExpiryTimestamp(final Timestamp expiryTimestamp) {
		this.expiryTimestamp = expiryTimestamp;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(final String applicationId) {
		this.applicationId = applicationId;
	}

	public String getOtpValue() {
		return this.otpValue;
	}

	public void setOtpValue(final String otpValue) {
		this.otpValue = otpValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.deviceAuthenticationId == null ? 0 : this.deviceAuthenticationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		final DeviceAuthentication other = (DeviceAuthentication) obj;
		if(this.deviceAuthenticationId == null) {
			if(other.deviceAuthenticationId != null) {
				return false;
			}
		} else if(!this.deviceAuthenticationId.equals(other.deviceAuthenticationId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DeviceAuthentication [deviceAuthenticationId=" + this.deviceAuthenticationId + ", employeeId=" + this.employeeId + ", registrationId=" + this.registrationId + ", sessionId=" + this.sessionId + ", expiryTimestamp=" + this.expiryTimestamp + "]";
	}

}