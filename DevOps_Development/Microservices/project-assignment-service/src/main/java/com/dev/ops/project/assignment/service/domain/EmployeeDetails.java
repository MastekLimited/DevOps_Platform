package com.dev.ops.project.assignment.service.domain;

import java.util.HashSet;
import java.util.Set;

import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.project.service.domain.Project;

public class EmployeeDetails {
	private Employee employee;
	private final Set<Project> projects;

	public EmployeeDetails() {
		this.projects = new HashSet<Project>();
	}

	public EmployeeDetails(final Employee employee) {
		this();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}

	public Set<Project> getProjects() {
		return this.projects;
	}

	public void addProject(final Project project) {
		this.projects.add(project);
	}

	public void addProjects(final Set<Project> projects) {
		this.projects.addAll(projects);
	}

	public void removeProject(final Project project) {
		this.projects.remove(project);
	}

	public void removeProjects(final Set<Project> projects) {
		this.projects.removeAll(projects);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.employee == null ? 0 : this.employee.hashCode());
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
		final EmployeeDetails other = (EmployeeDetails) obj;
		if(this.employee == null) {
			if(other.employee != null) {
				return false;
			}
		} else if(!this.employee.equals(other.employee)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeDetails [employee=" + this.employee + ", projects=" + this.projects + "]";
	}
}