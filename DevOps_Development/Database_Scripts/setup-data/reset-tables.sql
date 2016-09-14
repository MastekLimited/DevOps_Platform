------------------------------------------------Project Assignment Table------------------------------------------------
delete from project_assignment;
alter sequence project_assignment_id_sequence RESTART 1;
			
------------------------------------------------Project Table------------------------------------------------
delete from project;
alter sequence project_id_sequence RESTART 1;

------------------------------------------------Employee Table------------------------------------------------
delete from employee;
alter sequence employee_id_sequence RESTART 1;

------------------------------------------------Address Table------------------------------------------------
delete from address;
alter sequence address_id_sequence RESTART 1;

------------------------------------------------Device Registration Table------------------------------------------------
delete from device_registration;
alter sequence device_registration_id_sequence RESTART 1;

------------------------------------------------Device Authentication Table------------------------------------------------
delete from device_authentication;
alter sequence device_authentication_id_sequence RESTART 1;