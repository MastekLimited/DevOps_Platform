package com.dev.ops.project.service.entities;

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
@Table(name = "project")
//@EntityListeners(AuditColumnsListener.class)
@NamedQueries({@NamedQuery(name = "ProjectMaster.fetchAllProjects", query = "SELECT projectMaster FROM ProjectMaster projectMaster")})
public class ProjectMaster implements AuditableColumns {

	@Id
	@SequenceGenerator(name = "PROJECT_ID_SEQUENCE_GENERATOR", sequenceName = "PROJECT_ID_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_ID_SEQUENCE_GENERATOR")
	@Column(name = "project_id")
	private BigDecimal projectId;

	@Column(name = "project_code")
	private String projectCode;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "project_location")
	private String projectLocation;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "end_time")
	private Timestamp endTime;

	@Column(name = "project_manager_id")
	private BigDecimal projectManagerId;

	private AuditColumns auditColumns;

	public ProjectMaster() {
		this.auditColumns = new AuditColumns();
	}

	public ProjectMaster(final BigDecimal projectId, final String projectCode, final String projectName, final String projectLocation, final Timestamp startTime, final Timestamp endTime, final BigDecimal projectManagerId) {
		this.projectId = projectId;
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.projectLocation = projectLocation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.projectManagerId = projectManagerId;
	}

	public ProjectMaster(final String projectCode, final String projectName, final String projectLocation, final Timestamp startTime, final Timestamp endTime, final BigDecimal projectManagerId) {
		this(null, projectCode, projectName, projectLocation, startTime, endTime, projectManagerId);
	}

	public ProjectMaster(final BigDecimal projectId, final String projectCode, final String projectName, final String projectLocation, final BigDecimal projectManagerId) {
		this(projectId, projectCode, projectName, projectLocation, null, null, projectManagerId);
	}

	public ProjectMaster(final String projectCode, final String projectName, final String projectLocation, final BigDecimal projectManagerId) {
		this(null, projectCode, projectName, projectLocation, null, null, projectManagerId);
	}

	public ProjectMaster(final String projectCode, final String projectName, final String projectLocation) {
		this(null, projectCode, projectName, projectLocation, null, null, null);
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
		final ProjectMaster other = (ProjectMaster) obj;
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
		return "ProjectMaster [projectId=" + this.projectId + ", projectCode=" + this.projectCode + ", projectName=" + this.projectName + ", projectLocation=" + this.projectLocation + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", projectManagerId=" + this.projectManagerId + "]";
	}
}