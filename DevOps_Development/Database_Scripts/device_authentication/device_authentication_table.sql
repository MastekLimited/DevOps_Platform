create table device_authentication(
	device_authentication_id numeric primary key,
	employee_id numeric,
	registration_id text,
	session_id text,
	expiry_timestamp TIMESTAMP,
	created_by_id varchar(12),
	created_by varchar(200),
	created_date TIMESTAMP,
	modified_by_id varchar(12),
	modified_by varchar(200),
	modified_date TIMESTAMP
);

create sequence device_authentication_id_sequence;

create index index_session_id_device_authentication on device_authentication (session_id);

--drop table device_authentication;