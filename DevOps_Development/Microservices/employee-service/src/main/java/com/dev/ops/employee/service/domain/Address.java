package com.dev.ops.employee.service.domain;

import java.math.BigDecimal;

public class Address {
	private BigDecimal addressId;
	private String addressLine1;
	private String addressLine2;
	private String postCode;
	private String city;
	private String state;
	private String country;

	public Address() {
	}

	public Address(final BigDecimal addressId, final String addressLine1, final String addressLine2, final String postCode, final String city, final String state, final String country) {
		this();
		this.addressId = addressId;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.postCode = postCode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public Address(final BigDecimal addressId, final String addressLine, final String postCode, final String city, final String state, final String country) {
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
	public String toString() {
		return "Address [addressLine1=" + this.addressLine1 + ", addressLine2=" + this.addressLine2 + ", postCode=" + this.postCode + ", city=" + this.city + ", state=" + this.state + ", country=" + this.country + "]";
	}
}