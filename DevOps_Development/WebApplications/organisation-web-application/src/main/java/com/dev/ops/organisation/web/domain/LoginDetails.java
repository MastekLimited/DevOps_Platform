package com.dev.ops.organisation.web.domain;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginDetails {

	@NotNull(message = "The user UUID should not be null")
	@NotEmpty(message = "The user UUID should not be empty")
	@Size(min = 12, max = 12, message = "The user UUID should be exactly 12 characters long")
	private String userUUID;

	@NotNull(message = "The user logged in time should not be null")
	private Timestamp loggedInTime;

	public LoginDetails() {
	}

	public LoginDetails(final String userUUID, final Timestamp loggedInTime) {
		this();
		this.userUUID = userUUID;
		this.loggedInTime = loggedInTime;
	}

	public LoginDetails(final String userUUID) {
		this(userUUID, null);
	}

	public String getUserUUID() {
		return this.userUUID;
	}

	public void setUserUUID(final String userUUID) {
		this.userUUID = userUUID;
	}

	public Timestamp getLoggedInTime() {
		return this.loggedInTime;
	}

	public void setLoggedInTime(final Timestamp loggedInTime) {
		this.loggedInTime = loggedInTime;
	}

	@Override
	public String toString() {
		return "LoginDetails [userUUID=" + this.userUUID + ", loggedInTime=" + this.loggedInTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.userUUID == null ? 0 : this.userUUID.hashCode());
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
		final LoginDetails other = (LoginDetails) obj;
		if(this.userUUID == null) {
			if(other.userUUID != null) {
				return false;
			}
		} else if(!this.userUUID.equals(other.userUUID)) {
			return false;
		}
		return true;
	}
}