------------------------------------------------Address Table------------------------------------------------
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), 'Aspect 14', 'Wellington Street', 'LS2 8WG', 'Leeds', 'West Yorkshire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '274 westpoint', 'Wellington Street', 'LS1 4JT', 'Leeds', 'West Yorkshire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '24 Velocity East', '4 City Walk', 'LS11 9BF', 'Leeds', 'West Yorkshire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '603 Gateway West', '', 'LS9 8DR', 'Leeds', 'West Yorkshire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '148 Old St', '', 'EC1V 9HQ', 'London', 'London', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), 'Mount Pleasant Mail Centre', 'Farringdon Road', 'EC1A 1AZ', 'London', 'Greater London', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '372', 'Cable Street', 'E1 0AF', 'London', 'Greater London', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '46', 'St. James Street', 'L1 0AB', 'Liverpool', 'Lancashire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '33', 'Manor Farm Road', 'HA0 1AB', 'Wembley', 'Greater London', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '102 A', 'John Finnie Street', 'KA1 1BB', 'Kilmarnock', 'Ayrshire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '55', 'Norfolk Street', 'KA1 1BB', 'Sheffield', 'Derbyshire', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), '18', 'Greaves Street', 'OL1 1AD', 'Oldham', 'Greater Manchester', 'United Kingdom');
INSERT INTO address(address_id, address_line_1, address_line_2, postcode, city, state, country)
			VALUES (nextval('address_id_sequence'), 'Temple Buildings', 'Temple Road', 'LE5 4JG', 'Leicester', 'Leicestershire', 'United Kingdom');

------------------------------------------------Employee Table------------------------------------------------
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '540105427566', 'Jonathan', 'Morgan', 76000, '+447654987632', 'Jonathan.Morgan@devops.com', 'SU2XHGWTURMGNKM3', 4);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '009229421046', 'Adolf', 'Monahan', 40000, '+447658998953', 'Adolf.Monahan@devops.com', 'P3XCTCQWBCKNBMZF', 2);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '149255919032', 'Caitlyn', 'Murray', 45700, '+447645734692', 'Caitlyn.Murray@devops.com', 'SAJXYUYIIEP25SDS', 5);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '858479837040', 'Buster', 'Anderson', 33000, '+447634454668', 'Buster.Anderson@devops.com', 'TUBDPC3O67QFJD2J', 10);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '842483031048', 'Benjamin', 'Cummerata', 54000, '+447614377854', 'Benjamin.Cummerata@devops.com', 'TAWUNRS2QG6YIPPC', 6);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '687150388043', 'Juvenal', 'Barton', 23700, '+447675634632', 'Juvenal.Barton@devops.com', 'K6SOVCHCOQGHOG3F', 11);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '156244422044', 'Maya', 'Bayer', 65400, '+447654954634', 'Maya.Bayer@devops.com', 'P7XP5WXDYH2UEDMZ', 3);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '612782462040', 'Bartholome', 'Dare', 32000, '+447067445425', 'Bartholome.Dare@devops.com', '7H44VVJJYGSQKG2M', 8);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '555650426105', 'Pamela', 'Hansen', 87000, '+447678756435', 'Pamela.Hansen@devops.com', '2XWHFBXI25H2LAR4', 1);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '765945341046', 'Monty', 'Pagac', 32000, '+447666345632', 'Monty.Pagac@devops.com', '7PH4S4JHG67WJYGB', 7);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '343441666561', 'Fiona', 'Langworth', 39000, '+447656335322', 'Fiona.Langworth@devops.com', '6IKJOBZ5UYV7I6OK', 13);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '343441666561', 'Floy', 'Yost', 39000, '+447656335322', 'Floy.Yost@devops.com', 'E5WLLIGWKMMJQK4G', 9);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '343441666561', 'Thea', 'Oberbrunner', 39000, '+447656335322', 'Thea.Oberbrunner@devops.com', 'VEVUNHEACWYDTAAS', 4);
INSERT INTO employee(employee_id, employee_number, first_name, last_name, salary, mobile_number, email_address, google_authenticator_key, address_id)
			VALUES (nextval('employee_id_sequence'), '343441666561', 'Rosina', 'O''Connell', 39000, '+447656335322', 'Rosina.Oconnell@devops.com', 'XU4E6A5YNMCBR4JN', 12);
			
------------------------------------------------Project Table------------------------------------------------
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-827564271563', 'HSCIC - CIS', 'Leeds, United Kingdom', '2013-01-15 00:00:00.000', '2017-01-14 00:00:00.000', 4);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-799185913016', 'Ingram Micro', 'Florida, United States', '2016-01-24 00:00:00.000', '2022-01-23 00:00:00.000', 4);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-309985331045', 'Foresters', 'California, United States', '2008-10-11 00:00:00.000', '2022-10-10 00:00:00.000', 3);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-465348966040', 'Frescale Semiconductors', 'Los Angeles, United States', '2002-01-15 00:00:00.000', '2002-01-25 00:00:00.000', 10);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-422633380049', 'Airtel Mobile', 'Mumbai, India', '2013-04-15 00:00:00.000', '2015-12-31 00:00:00.000', 4);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-288786781048', 'Motorola', 'Singapore', '2012-03-03 00:00:00.000', '2017-01-02 00:00:00.000', 10);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-325426243045', 'Nautilus Insurance', 'Mumbai, Mahape, India', '2018-02-28 00:00:00.000', '2025-02-27 00:00:00.000', 3);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-536316592040', 'BT Service', 'Sheffield, United Kingdom', '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000', 3);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-900333394048', 'Nokia', 'Tokiyo, Japan', '2016-01-15 00:00:00.000', '2017-07-23 00:00:00.000', 10);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-516691050046', 'Intel', 'New York, United States', '2014-06-19 00:00:00.000', '2017-08-19 00:00:00.000', 4);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-961001106049', 'HSCIC - SUS', 'Leeds, United Kingdom', '2012-11-01 00:00:00.000', '2017-12-08 00:00:00.000', 10);
INSERT INTO project(project_id, project_code, project_name, project_location, start_time, end_time, project_manager_id)
			VALUES (nextval('project_id_sequence'), 'PR-441321838563', 'Home Office', 'London, United Kingdom', '2009-05-24 00:00:00.000', '2017-05-18 00:00:00.000', 10);

------------------------------------------------Project Assignment Table------------------------------------------------
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 5, 3, '2013-04-15 00:00:00.000', '2015-10-12 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 5, 1, '2013-04-15 00:00:00.000', '2014-12-31 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 5, 5, '2013-04-15 00:00:00.000', '2015-12-31 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 5, 2, '2013-06-19 00:00:00.000', '2015-01-31 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 5, 10, '2013-04-15 00:00:00.000', '2013-01-15 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 3, '2008-10-11 00:00:00.000', '2022-10-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 5, '2009-11-11 00:00:00.000', '2013-01-15 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 2, '2010-01-01 00:00:00.000', '2022-10-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 13, '2008-10-11 00:00:00.000', '2022-10-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 11, '2008-10-11 00:00:00.000', '2022-10-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 6, '2008-12-23 00:00:00.000', '2022-10-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 8, '2008-10-11 00:00:00.000', '2016-07-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 4, '2009-09-11 00:00:00.000', '2021-10-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 12, '2010-10-11 00:00:00.000', '2018-04-16 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 7, '2008-10-11 00:00:00.000', '2020-12-10 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 3, 10, '2008-10-11 00:00:00.000', '2013-01-15 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 1, 12, '2013-04-15 00:00:00.000', '2017-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 1, 1, '2013-04-15 00:00:00.000', '2017-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 10, 2, '2015-06-11 00:00:00.000', '2015-08-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 10, 9, '2014-06-19 00:00:00.000', '2017-04-29 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 10, 5, '2014-06-19 00:00:00.000', '2017-08-19 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 12, 2, '2012-06-24 00:00:00.000', '2017-05-18 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 12, 11, '2009-05-24 00:00:00.000', '2015-03-11 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 2, 2, '2016-01-24 00:00:00.000', '2022-01-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 2, 1, '2016-04-21 00:00:00.000', '2022-01-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 2, 5, '2016-01-24 00:00:00.000', '2021-06-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 2, 7, '2016-01-24 00:00:00.000', '2022-01-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 2, 10, '2016-12-31 00:00:00.000', '2021-01-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 2, 8, '2016-01-24 00:00:00.000', '2022-01-23 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 4, 9, '2002-01-15 00:00:00.000', '2002-01-25 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 4, 13, '2002-01-15 00:00:00.000', '2002-01-25 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 4, 6, '2002-01-15 00:00:00.000', '2002-01-25 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 6, 1, '2012-03-03 00:00:00.000', '2017-01-02 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 2, '2018-02-28 00:00:00.000', '2025-02-27 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 10, '2018-02-28 00:00:00.000', '2024-03-21 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 2, '2018-05-21 00:00:00.000', '2025-02-27 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 11, '2018-02-22 00:00:00.000', '2022-02-27 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 9, '2018-02-28 00:00:00.000', '2025-02-27 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 8, '2018-02-28 00:00:00.000', '2025-02-27 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 7, 1, '2018-02-28 00:00:00.000', '2025-02-27 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 8, 13, '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 8, 12, '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 8, 6, '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 8, 4, '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 8, 3, '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 8, 1, '2005-01-15 00:00:00.000', '2015-01-14 00:00:00.000');
INSERT INTO project_assignment(project_assignment_id, project_id, employee_id, assignment_start_time, assignment_end_time)
					VALUES (nextval('project_assignment_id_sequence'), 9, 5, '2016-01-15 00:00:00.000', '2017-07-23 00:00:00.000');

SELECT table_schema,table_name FROM information_schema.tables where table_schema='public';