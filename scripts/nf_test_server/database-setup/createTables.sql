create table address(
	addressid numeric primary key,
	addressline1 varchar(100),
	addressline2 varchar(100),
	postcode varchar(10),
	city varchar(100),
	state varchar(100),
	country varchar(100)
);

create sequence address_id_sequence;

create table employee(
	employeeid numeric primary key,
	employeenumber varchar(12),
	firstname varchar(100),
	lastname varchar(100),
	salary numeric,
	mobilenumber varchar(13),
	emailaddress varchar(100),
	addressid numeric references address(addressid)
);

create sequence employee_id_sequence;


create table project(
	projectid numeric primary key,
	projectcode varchar(100),
	projectname varchar(100),
	projectlocation varchar(100),
	starttime timestamp,
	endtime timestamp,
	projectmanagerid numeric references employee(employeeid)
);

create sequence project_id_sequence;


create table project_assignment(
	projectassignmentid numeric primary key,
	projectid numeric references project(projectid),
	employeeid numeric references employee(employeeid),
	assignmentstarttime TIMESTAMP,
	assignmentendtime TIMESTAMP
);

create sequence project_assignment_id_sequence;
