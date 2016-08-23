package com.dev.ops.employee.service.entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.dev.ops.common.audit.AuditColumns;
import com.dev.ops.common.audit.AuditableColumns;

@Entity
//@Audited
@Table(name = "employee")
//@EntityListeners(AuditColumnsListener.class)
@NamedQueries({@NamedQuery(name = "EmployeeMaster.fetchAllEmployees", query = "SELECT employeeMaster FROM EmployeeMaster employeeMaster")})
public class EmployeeMaster implements AuditableColumns {

	@Id
	@SequenceGenerator(name = "EMPLOYEE_ID_SEQUENCE_GENERATOR", sequenceName = "EMPLOYEE_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_ID_SEQUENCE_GENERATOR")
	@Column(name = "employee_id")
	private BigDecimal employeeId;

	@Column(name = "employee_number")
	private String employeeNumber;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "salary")
	private BigDecimal salary;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "google_authenticator_key")
	private String googleAuthenticatorKey;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private AddressMaster address;

	private AuditColumns auditColumns;

	public EmployeeMaster() {
		this.auditColumns = new AuditColumns();
	}

	public EmployeeMaster(final BigDecimal employeeId, final String employeeNumber, final String firstName, final String lastName, final BigDecimal salary, final String mobileNumber, final String emailAddress, final String googleAuthenticatorKey, final AddressMaster address) {
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

	public EmployeeMaster(final BigDecimal employeeId, final String employeeNumber, final String firstName, final String lastName, final String mobileNumber, final String emailAddress) {
		this(employeeId, employeeNumber, firstName, lastName, null, mobileNumber, emailAddress, null, null);
	}

	public EmployeeMaster(final BigDecimal employeeId, final String firstName, final String lastName, final BigDecimal salary, final String mobileNumber, final String emailAddress, final AddressMaster address) {
		this(employeeId, null, firstName, lastName, salary, mobileNumber, emailAddress, null, address);
	}

	public EmployeeMaster(final BigDecimal employeeId, final String firstName, final String lastName, final String mobileNumber, final String emailAddress, final AddressMaster address) {
		this(employeeId, firstName, lastName, null, mobileNumber, emailAddress, address);
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

	public AddressMaster getAddress() {
		return this.address;
	}

	public void setAddress(final AddressMaster address) {
		this.address = address;
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
		final EmployeeMaster other = (EmployeeMaster) obj;
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
		return "EmployeeMaster [employeeId=" + this.employeeId + ", employeeNumber=" + this.employeeNumber + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", salary=" + this.salary + ", mobileNumber=" + this.mobileNumber + ", emailAddress=" + this.emailAddress + ", address=" + this.address + "]";
	}
}