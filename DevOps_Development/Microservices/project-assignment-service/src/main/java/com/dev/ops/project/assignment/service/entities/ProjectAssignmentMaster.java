package com.dev.ops.project.assignment.service.entities;

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
//@Audited
@Table(name = "project_assignment")
//@EntityListeners(AuditColumnsListener.class)
@NamedQueries({@NamedQuery(name = "ProjectAssignmentMaster.fetchAllProjects", query = "SELECT projectAssignmentMaster FROM ProjectAssignmentMaster projectAssignmentMaster"), @NamedQuery(name = "ProjectAssignmentMaster.fetchAllProjectEmployees", query = "SELECT projectAssignmentMaster FROM ProjectAssignmentMaster projectAssignmentMaster where projectId=:projectId"), @NamedQuery(name = "ProjectAssignmentMaster.fetchAllEmployeeProjects", query = "SELECT projectAssignmentMaster FROM ProjectAssignmentMaster projectAssignmentMaster where employeeId=:employeeId")})
public class ProjectAssignmentMaster implements AuditableColumns {

	@Id
	@SequenceGenerator(name = "PROJECT_ASSIGNMENT_ID_SEQUENCE_GENERATOR", sequenceName = "PROJECT_ASSIGNMENT_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_ASSIGNMENT_ID_SEQUENCE_GENERATOR")
	@Column(name = "project_assignment_id")
	private BigDecimal projectAssignmentId;

	@Column(name = "project_id")
	private BigDecimal projectId;

	@Column(name = "employee_id")
	private BigDecimal employeeId;

	@Column(name = "assignment_start_time")
	private Timestamp assignmentStartTime;

	@Column(name = "assignment_end_time")
	private Timestamp assignmentEndTime;

	private AuditColumns auditColumns;

	public ProjectAssignmentMaster() {
		this.auditColumns = new AuditColumns();
	}

	public ProjectAssignmentMaster(final BigDecimal projectAssignmentId, final BigDecimal projectId, final BigDecimal employeeId, final Timestamp assignmentStartTime, final Timestamp assignmentEndTime) {
		this();
		this.projectAssignmentId = projectAssignmentId;
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.assignmentStartTime = assignmentStartTime;
		this.assignmentEndTime = assignmentEndTime;
	}

	public ProjectAssignmentMaster(final BigDecimal projectId, final BigDecimal employeeId, final Timestamp assignmentStartTime, final Timestamp assignmentEndTime) {
		this(null, projectId, employeeId, assignmentStartTime, assignmentEndTime);
	}

	public ProjectAssignmentMaster(final BigDecimal projectId, final BigDecimal employeeId) {
		this(null, projectId, employeeId, null, null);
	}

	public BigDecimal getProjectAssignmentId() {
		return this.projectAssignmentId;
	}

	public void setProjectAssignmentId(final BigDecimal projectAssignmentId) {
		this.projectAssignmentId = projectAssignmentId;
	}

	public BigDecimal getProjectId() {
		return this.projectId;
	}

	public void setProjectId(final BigDecimal projectId) {
		this.projectId = projectId;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(final BigDecimal employeeId) {
		this.employeeId = employeeId;
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
		final ProjectAssignmentMaster other = (ProjectAssignmentMaster) obj;
		if(this.employeeId == null) {
			if(other.employeeId != null) {
				return false;
			}
		} else if(!this.employeeId.equals(other.employeeId)) {
			return false;
		}
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
		return "ProjectAssignment [projectAssignmentId=" + this.projectAssignmentId + ", projectId=" + this.projectId + ", employeeId=" + this.employeeId + ", assignmentStartTime=" + this.assignmentStartTime + ", assignmentEndTime=" + this.assignmentEndTime + "]";
	}
}