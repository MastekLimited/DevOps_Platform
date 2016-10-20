create table device_registration(
	device_registration_id numeric primary key,
	registration_id text,
	challenge varchar(20),
	device_id text,
	application_id text,
	csr text,
	certificate text,
	created_by_id varchar(12),
	created_by varchar(200),
	created_date TIMESTAMP,
	modified_by_id varchar(12),
	modified_by varchar(200),
	modified_date TIMESTAMP
);

create sequence device_registration_id_sequence;

--drop table device_registration;