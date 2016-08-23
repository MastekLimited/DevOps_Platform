create table project(
	project_id numeric primary key,
	project_code varchar(100),
	project_name varchar(100),
	project_location varchar(100),
	start_time TIMESTAMP,
	end_time TIMESTAMP,
	project_manager_id numeric references employee(employee_id),
	created_by_id varchar(12),
	created_by varchar(200),
	created_date TIMESTAMP,
	modified_by_id varchar(12),
	modified_by varchar(200),
	modified_date TIMESTAMP
);

create sequence project_id_sequence;

create sequence project_number_sequence increment 1 minvalue 1 maxvalue 999999999 start 1 cycle;

--drop table project;