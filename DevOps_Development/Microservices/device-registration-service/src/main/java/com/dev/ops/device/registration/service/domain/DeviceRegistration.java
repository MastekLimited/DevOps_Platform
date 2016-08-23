package com.dev.ops.device.registration.service.domain;

import java.math.BigDecimal;

public class DeviceRegistration {

	private BigDecimal deviceRegistrationId;
	private String registrationId;
	private String challenge;
	private String deviceId;
	private String applicationId;
	private String csr;
	private String certificate;

	public DeviceRegistration() {
	}

	public BigDecimal getDeviceRegistrationId() {
		return this.deviceRegistrationId;
	}

	public void setDeviceRegistrationId(final BigDecimal deviceRegistrationId) {
		this.deviceRegistrationId = deviceRegistrationId;
	}

	public String getRegistrationId() {
		return this.registrationId;
	}

	public void setRegistrationId(final String registrationId) {
		this.registrationId = registrationId;
	}

	public String getChallenge() {
		return this.challenge;
	}

	public void setChallenge(final String challenge) {
		this.challenge = challenge;
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

	public String getCsr() {
		return this.csr;
	}

	public void setCsr(final String csr) {
		this.csr = csr;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(final String certificate) {
		this.certificate = certificate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.deviceId == null ? 0 : this.deviceId.hashCode());
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
		final DeviceRegistration other = (DeviceRegistration) obj;
		if(this.deviceId == null) {
			if(other.deviceId != null) {
				return false;
			}
		} else if(!this.deviceId.equals(other.deviceId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DeviceRegistration [deviceRegistrationId=" + this.deviceRegistrationId + ", deviceId=" + this.deviceId + ", applicationId=" + this.applicationId + "]";
	}
}