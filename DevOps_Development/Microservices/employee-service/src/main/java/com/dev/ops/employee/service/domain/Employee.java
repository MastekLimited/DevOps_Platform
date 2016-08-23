package com.dev.ops.employee.service.domain;

import java.math.BigDecimal;

public class Employee {

	private BigDecimal employeeId;
	private String employeeNumber;
	private String firstName;
	private String lastName;
	private BigDecimal salary;
	private String mobileNumber;
	private String emailAddress;
	private String googleAuthenticatorKey;
	private Address address;

	public Employee() {
		this.address = new Address();
	}

	public Employee(final BigDecimal employeeId, final String employeeNumber, final String firstName, final String lastName, final BigDecimal salary, final String mobileNumber, final String emailAddress, final String googleAuthenticatorKey, final Address address) {
		this();
		this.employeeId = employeeId;
		this.employeeNumber = employeeNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
		this.googleAuthenticatorKey = googleAuthenticatorKey;
		this.address = address;
	}

	public Employee(final String employeeNumber, final String firstName, final String lastName, final BigDecimal salary, final String mobileNumber, final String emailAddress, final Address address) {
		this(null, employeeNumber, firstName, lastName, salary, mobileNumber, emailAddress, null, address);
	}

	public Employee(final BigDecimal employeeId, final String employeeNumber, final String firstName, final String lastName, final String mobileNumber, final String emailAddress) {
		this(employeeId, employeeNumber, firstName, lastName, null, mobileNumber, emailAddress, null, null);
	}

	public Employee(final BigDecimal employeeId, final String firstName, final String lastName, final BigDecimal salary, final String mobileNumber, final String emailAddress, final Address address) {
		this(employeeId, null, firstName, lastName, salary, mobileNumber, emailAddress, null, address);
	}

	public Employee(final BigDecimal employeeId, final String firstName, final String lastName, final String mobileNumber, final String emailAddress, final Address address) {
		this(employeeId, firstName, lastName, null, mobileNumber, emailAddress, address);
	}

	public Employee(final BigDecimal employeeId) {
		//this(employeeId, null, null, null, null, null, null, null);
		this.employeeId = employeeId;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(final BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	public void setEmployeeNumber(final String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getSalary() {
		return this.salary;
	}

	public void setSalary(final BigDecimal salary) {
		this.salary = salary;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(final String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getGoogleAuthenticatorKey() {
		return this.googleAuthenticatorKey;
	}

	public void setGoogleAuthenticatorKey(final String googleAuthenticatorKey) {
		this.googleAuthenticatorKey = googleAuthenticatorKey;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.employeeId == null ? 0 : this.employeeId.hashCode());
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
		final Employee other = (Employee) obj;
		if(this.employeeId == null) {
			if(other.employeeId != null) {
				return false;
			}
		} else if(!this.employeeId.equals(other.employeeId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + this.employeeId + ", employeeNumber=" + this.employeeNumber + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", salary=" + this.salary + ", mobileNumber=" + this.mobileNumber + ", emailAddress=" + this.emailAddress + ", address=" + this.address + "]";
	}
}