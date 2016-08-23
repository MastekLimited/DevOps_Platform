package com.dev.ops.employee.service.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.dev.ops.common.audit.AuditColumns;
import com.dev.ops.common.audit.AuditableColumns;

@Entity
//@Audited
@Table(name = "address")
//@EntityListeners(AuditColumnsListener.class)
public class AddressMaster implements AuditableColumns {

	@Id
	@SequenceGenerator(name = "ADDRESS_ID_SEQUENCE_GENERATOR", sequenceName = "ADDRESS_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_ID_SEQUENCE_GENERATOR")
	@Column(name = "address_id")
	private BigDecimal addressId;

	@Column(name = "address_line_1")
	private String addressLine1;

	@Column(name = "address_line_2")
	private String addressLine2;

	@Column(name = "postcode")
	private String postCode;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	private AuditColumns auditColumns;

	public AddressMaster() {
		this.auditColumns = new AuditColumns();
	}

	public AddressMaster(final BigDecimal addressId, final String addressLine1, final String addressLine2, final String postCode, final String city, final String state, final String country) {
		this.addressId = addressId;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.postCode = postCode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public AddressMaster(final BigDecimal addressId, final String addressLine, final String postCode, final String city, final String state, final String country) {
		this(addressId, addressLine, null, postCode, city, state, country);
	}

	public BigDecimal getAddressId() {
		return this.addressId;
	}

	public void setAddressId(final BigDecimal addressId) {
		this.addressId = addressId;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(final String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(final String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(final String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
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
		result = prime * result + (this.addressId == null ? 0 : this.addressId.hashCode());
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
		final AddressMaster other = (AddressMaster) obj;
		if(this.addressId == null) {
			if(other.addressId != null) {
				return false;
			}
		} else if(!this.addressId.equals(other.addressId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AddressMaster [addressId=" + this.addressId + ", addressLine1=" + this.addressLine1 + ", addressLine2=" + this.addressLine2 + ", postCode=" + this.postCode + ", city=" + this.city + ", state=" + this.state + ", country=" + this.country + "]";
	}
}