create table address(
	address_id numeric primary key,
	address_line_1 varchar(100),
	address_line_2 varchar(100),
	postcode varchar(10),
	city varchar(100),
	state varchar(100),
	country varchar(100),
	created_by_id varchar(12),
	created_by varchar(200),
	created_date TIMESTAMP,
	modified_by_id varchar(12),
	modified_by varchar(200),
	modified_date TIMESTAMP
);

create sequence address_id_sequence;

create table employee(
	employee_id numeric primary key,
	employee_number varchar(20),
	first_name varchar(100),
	last_name varchar(100),
	salary numeric,
	mobile_number varchar(13),
	email_address varchar(100),
	google_authenticator_key varchar(100),
	address_id numeric references address(address_id),
	created_by_id varchar(12),
	created_by varchar(200),
	created_date TIMESTAMP,
	modified_by_id varchar(12),
	modified_by varchar(200),
	modified_date TIMESTAMP
);

create sequence employee_id_sequence;

create sequence employee_number_sequence increment 1 minvalue 1 maxvalue 999999999 start 1 cycle;

--drop table employee;