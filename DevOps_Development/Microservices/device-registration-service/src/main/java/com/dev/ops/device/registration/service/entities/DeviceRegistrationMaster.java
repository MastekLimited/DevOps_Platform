package com.dev.ops.device.registration.service.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.dev.ops.common.audit.AuditColumns;
import com.dev.ops.common.audit.AuditableColumns;

@Entity
@Table(name = "device_registration")
//@EntityListeners(AuditColumnsListener.class)
@NamedQueries({@NamedQuery(name = "DeviceRegistrationMaster.fetchAllDeviceRegistrations", query = "SELECT deviceRegistrationMaster FROM DeviceRegistrationMaster deviceRegistrationMaster"), @NamedQuery(name = "DeviceRegistrationMaster.findByRegistrationId", query = "SELECT deviceRegistrationMaster FROM DeviceRegistrationMaster deviceRegistrationMaster where deviceRegistrationMaster.registrationId=:registrationId")})
public class DeviceRegistrationMaster implements AuditableColumns {

	@Id
	@SequenceGenerator(name = "DEVICE_REGISTRATION_ID_SEQUENCE_GENERATOR", sequenceName = "DEVICE_REGISTRATION_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVICE_REGISTRATION_ID_SEQUENCE_GENERATOR")
	@Column(name = "device_registration_id")
	private BigDecimal deviceRegistrationId;

	@Column(name = "registration_id")
	private String registrationId;

	@Column(name = "challenge")
	private String challenge;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "csr")
	private String csr;

	@Column(name = "certificate")
	private String certificate;

	private AuditColumns auditColumns;

	public DeviceRegistrationMaster() {
	}

	public DeviceRegistrationMaster(final BigDecimal deviceRegistrationId, final String registrationId, final String challenge, final String deviceId, final String applicationId, final String csr, final String certificate) {
		this();
		this.deviceRegistrationId = deviceRegistrationId;
		this.registrationId = registrationId;
		this.challenge = challenge;
		this.deviceId = deviceId;
		this.applicationId = applicationId;
		this.csr = csr;
		this.certificate = certificate;
	}

	public DeviceRegistrationMaster(final String registrationId, final String deviceId, final String applicationId) {
		this(null, registrationId, null, deviceId, applicationId, null, null);
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
	public AuditColumns getAuditColumns() {
		return this.auditColumns;
	}

	@Override
	public void setAuditColumns(final AuditColumns auditColumns) {
		this.auditColumns = auditColumns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.deviceId == null ? 0 : this.deviceId.hashCode());
		result = prime * result + (this.registrationId == null ? 0 : this.registrationId.hashCode());
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
		final DeviceRegistrationMaster other = (DeviceRegistrationMaster) obj;
		if(this.deviceId == null) {
			if(other.deviceId != null) {
				return false;
			}
		} else if(!this.deviceId.equals(other.deviceId)) {
			return false;
		}
		if(this.registrationId == null) {
			if(other.registrationId != null) {
				return false;
			}
		} else if(!this.registrationId.equals(other.registrationId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DeviceRegistrationMaster [deviceRegistrationId=" + this.deviceRegistrationId + ", registrationId=" + this.registrationId + ", deviceId=" + this.deviceId + ", applicationId=" + this.applicationId + "]";
	}

}