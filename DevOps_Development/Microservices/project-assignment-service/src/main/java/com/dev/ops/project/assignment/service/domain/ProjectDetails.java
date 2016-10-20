package com.dev.ops.project.assignment.service.domain;

import java.util.HashSet;
import java.util.Set;

import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.project.service.domain.Project;

public class ProjectDetails {

	private Project project;
	private final Set<Employee> employees;

	public ProjectDetails() {
		this.employees = new HashSet<Employee>();
	}

	public ProjectDetails(final Project project) {
		this();
		this.project = project;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

	public Set<Employee> getEmployees() {
		return this.employees;
	}

	public void addEmployee(final Employee employee) {
		this.employees.add(employee);
	}

	public void addEmployees(final Set<Employee> employees) {
		this.employees.addAll(employees);
	}

	public void removeEmployee(final Employee employee) {
		this.employees.remove(employee);
	}

	public void removeEmployees(final Set<Employee> employees) {
		this.employees.removeAll(employees);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.project == null ? 0 : this.project.hashCode());
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
		final ProjectDetails other = (ProjectDetails) obj;
		if(this.project == null) {
			if(other.project != null) {
				return false;
			}
		} else if(!this.project.equals(other.project)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProjectDetails [project=" + this.project + ", employees=" + this.employees + "]";
	}
}