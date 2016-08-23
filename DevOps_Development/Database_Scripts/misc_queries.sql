select * from employee;

select * from address;

select * from project;

select * from project_assignment;

delete from project;

delete from employee;

delete from address;

delete from project_assignment;

alter sequence address_id_sequence RESTART  1;

alter sequence employee_id_sequence RESTART  1;

alter sequence project_id_sequence RESTART  1;

alter sequence project_assignment_id_sequence RESTART  1;