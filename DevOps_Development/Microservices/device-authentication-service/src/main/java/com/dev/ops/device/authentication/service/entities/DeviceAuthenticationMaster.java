package com.dev.ops.device.authentication.service.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
@Table(name = "device_authentication")
//@EntityListeners(AuditColumnsListener.class)
@NamedQueries({@NamedQuery(name = "DeviceAuthenticationMaster.fetchAllDeviceAuthentications", query = "SELECT deviceAuthenticationMaster FROM DeviceAuthenticationMaster deviceAuthenticationMaster"), @NamedQuery(name = "DeviceAuthenticationMaster.findBySessionId", query = "SELECT deviceAuthenticationMaster FROM DeviceAuthenticationMaster deviceAuthenticationMaster where deviceAuthenticationMaster.sessionId=:sessionId")})
public class DeviceAuthenticationMaster implements AuditableColumns {

	@Id
	@SequenceGenerator(name = "DEVICE_AUTHENTICATION_ID_SEQUENCE_GENERATOR", sequenceName = "DEVICE_AUTHENTICATION_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVICE_AUTHENTICATION_ID_SEQUENCE_GENERATOR")
	@Column(name = "device_authentication_id")
	private BigDecimal deviceAuthenticationId;

	@Column(name = "employee_id")
	private BigDecimal employeeId;

	@Column(name = "registration_id")
	private String registrationId;

	@Column(name = "session_id")
	private String sessionId;

	@Column(name = "expiry_timestamp")
	private Timestamp expiryTimestamp;

	private AuditColumns auditColumns;

	public DeviceAuthenticationMaster() {
		this.auditColumns = new AuditColumns();
	}

	public DeviceAuthenticationMaster(final BigDecimal deviceAuthenticationId, final BigDecimal employeeId, final String registrationId, final String sessionId, final Timestamp expiryTimestamp) {
		this();
		this.deviceAuthenticationId = deviceAuthenticationId;
		this.employeeId = employeeId;
		this.registrationId = registrationId;
		this.sessionId = sessionId;
		this.expiryTimestamp = expiryTimestamp;
	}

	public DeviceAuthenticationMaster(final BigDecimal employeeId, final String deviceRegistrationId, final String sessionId) {
		this(null, employeeId, deviceRegistrationId, sessionId, null);
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
		final DeviceAuthenticationMaster other = (DeviceAuthenticationMaster) obj;
		if(this.deviceAuthenticationId == null) {
			if(other.deviceAuthenticationId != null) {
				return false;
			}
		} else if(!this.deviceAuthenticationId.equals(other.deviceAuthenticationId)) {
			return false;
		}
		return true;
	}

}