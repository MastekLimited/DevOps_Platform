package com.dev.ops.project.assignment.service.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.project.service.domain.Project;

public class ProjectAssignment {

	private BigDecimal projectAssignmentId;
	private Project project;
	private Employee employee;
	private Timestamp assignmentStartTime;
	private Timestamp assignmentEndTime;

	public ProjectAssignment() {
	}

	public ProjectAssignment(final BigDecimal projectAssignmentId, final Project project, final Employee employee, final Timestamp assignmentStartTime, final Timestamp assignmentEndTime) {
		this();
		this.projectAssignmentId = projectAssignmentId;
		this.project = project;
		this.employee = employee;
		this.assignmentStartTime = assignmentStartTime;
		this.assignmentEndTime = assignmentEndTime;
	}

	public ProjectAssignment(final Project project, final Employee employee, final Timestamp assignmentStartTime, final Timestamp assignmentEndTime) {
		this(null, project, employee, assignmentStartTime, assignmentEndTime);
	}

	public ProjectAssignment(final Project project, final Employee employee) {
		this(null, project, employee, null, null);
	}

	public BigDecimal getProjectAssignmentId() {
		return this.projectAssignmentId;
	}

	public void setProjectAssignmentId(final BigDecimal projectAssignmentId) {
		this.projectAssignmentId = projectAssignmentId;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}

	public Timestamp getAssignmentStartTime() {
		return this.assignmentStartTime;
	}

	public void setAssignmentStartTime(final Timestamp assignmentStartTime) {
		this.assignmentStartTime = assignmentStartTime;
	}

	public Timestamp getAssignmentEndTime() {
		return this.assignmentEndTime;
	}

	public void setAssignmentEndTime(final Timestamp assignmentEndTime) {
		this.assignmentEndTime = assignmentEndTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.projectAssignmentId == null ? 0 : this.projectAssignmentId.hashCode());
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
		final ProjectAssignment other = (ProjectAssignment) obj;
		if(this.projectAssignmentId == null) {
			if(other.projectAssignmentId != null) {
				return false;
			}
		} else if(!this.projectAssignmentId.equals(other.projectAssignmentId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProjectAssignment [projectAssignmentId=" + this.projectAssignmentId + ", project=" + this.project + ", employee=" + this.employee + ", assignmentStartTime=" + this.assignmentStartTime + ", assignmentEndTime=" + this.assignmentEndTime + "]";
	}
}