package com.dev.ops.project.service.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Project {

	private BigDecimal projectId;
	private String projectCode;
	private String projectName;
	private String projectLocation;
	private Timestamp startTime;
	private Timestamp endTime;
	private BigDecimal projectManagerId;

	public Project() {
	}

	public Project(final BigDecimal projectId, final String projectCode, final String projectName, final String projectLocation, final Timestamp startTime, final Timestamp endTime, final BigDecimal projectManagerId) {
		this();
		this.projectId = projectId;
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.projectLocation = projectLocation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.projectManagerId = projectManagerId;
	}

	public Project(final String projectCode, final String projectName, final String projectLocation, final Timestamp startTime, final Timestamp endTime, final BigDecimal projectManagerId) {
		this(null, projectCode, projectName, projectLocation, startTime, endTime, projectManagerId);
	}

	public Project(final BigDecimal projectId, final String projectCode, final String projectName, final String projectLocation, final BigDecimal projectManagerId) {
		this(projectId, projectCode, projectName, projectLocation, null, null, projectManagerId);
	}

	public Project(final String projectCode, final String projectName, final String projectLocation, final BigDecimal projectManagerId) {
		this(null, projectCode, projectName, projectLocation, null, null, projectManagerId);
	}

	public Project(final String projectCode, final String projectName, final String projectLocation) {
		this(null, projectCode, projectName, projectLocation, null, null, null);
	}

	public Project(final BigDecimal projectId) {
		this(projectId, null, null, null, null, null, null);
	}

	public BigDecimal getProjectId() {
		return this.projectId;
	}

	public void setProjectId(final BigDecimal projectId) {
		this.projectId = projectId;
	}

	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(final String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public String getProjectLocation() {
		return this.projectLocation;
	}

	public void setProjectLocation(final String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final Timestamp endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getProjectManagerId() {
		return this.projectManagerId;
	}

	public void setProjectManagerId(final BigDecimal projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.projectId == null ? 0 : this.projectId.hashCode());
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
		final Project other = (Project) obj;
		if(this.projectId == null) {
			if(other.projectId != null) {
				return false;
			}
		} else if(!this.projectId.equals(other.projectId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + this.projectId + ", projectCode=" + this.projectCode + ", projectName=" + this.projectName + ", projectLocation=" + this.projectLocation + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", projectManagerId=" + this.projectManagerId + "]";
	}
}