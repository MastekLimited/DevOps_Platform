create table project_assignment(
	project_assignment_id numeric primary key,
	project_id numeric references project(project_id),
	employee_id numeric references employee(employee_id),
	assignment_start_time TIMESTAMP,
	assignment_end_time TIMESTAMP,
	created_by_id varchar(12),
	created_by varchar(200),
	created_date TIMESTAMP,
	modified_by_id varchar(12),
	modified_by varchar(200),
	modified_date TIMESTAMP
);

create sequence project_assignment_id_sequence;

--drop table project_assignment;