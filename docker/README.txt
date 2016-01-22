Steps to create new docker container.
	1. Make sure your jenkins job is creating required jar/war/required artifact at /var/lib/jenkins/jobs/DevOps_Development/workspace/ location. e.g. /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/device-registration-service/target/deviceRegistrationService.jar

	2. Add a new scp command in to "artifactTransfer.sh" this artifact to docker container on jenkins build finish. Make sure you have service-name directory in ${path-to-platform-source-code}/DevOps_Platform/Build/${service-name}
		command: scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/${path-to-deploying-artifact} vagrant@192.168.29.110:/vshare/Build/${service-name}/
		e.g. scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/device-registration-service/target/deviceRegistrationService.jar vagrant@192.168.29.110:/vshare/Build/device-registration/

	3. Go to ${path-to-platform-source-code}/DevOps_Platform/docker/scripts directory and make a copy of device-authentication directory in the same directory with name ${service-name} e.g. device-registration

	4. Replace 'device-authentication' in build.sh & run.sh (in the above copied directory) with ${service-name} e.g. device-registration.

	5. Make necessary changes in run.sh for docker port forwardings for ${service-mapping-port} and ${check-mk-mapping-port}.
	e.g. docker run -p ${service-mapping-port}:${service-port} -p ${check-mk-mapping-port}:${check-mk-port} --name device-authentication -h device-authentication -v /vshare/docker/config/services:/home/devops/config/services -v /vshare/Build/device-authentication/:/usr/share/ device-authentication

	6. Add execution of above created build.sh in ${path-to-platform-source-code}/DevOps_Platform/docker/scripts/startupScripts.sh by specifying the relative path.
		commands:
			echo ">>>>>>>>>>>>>>>>>>>build ${service-name}>>>>>>>>>>>>>>>>>>>"
			./scripts/${service-name}/build.sh
		e.g.
			echo ">>>>>>>>>>>>>>>>>>>build device registration>>>>>>>>>>>>>>>>>>>"
			./scripts/device-registration/build.sh

	7. Add execution of above created run.sh in ${path-to-platform-source-code}/DevOps_Platform/docker/scripts/runAllScripts.sh by specifying the relative path.
		commands:
			echo "...................run ${service-name}..................."
			./scripts/${service-name}/run.sh
		e.g.
			echo "...................run device registration..................."
			./scripts/device-registration/run.sh

	8. Go to ${path-to-platform-source-code}/DevOps_Platform/docker/roles directory and make a copy of device-authentication directory in the same directory with name ${service-name} e.g. device-registration
	9. Replace 'device-authentication' in supervisord.conf (in the above copied directory) with ${service-name} e.g. device-registration.
	10. Replace 'deviceAuthenticationService.jar' in supervisord.conf (in the above copied directory) with ${service-artifact} e.g. deviceRegistrationService.jar.

The check mk port specified in run.sh should be added to ${path-to-platform-source-code}/DevOps_Platform/scripts/omd_seup/rules.mk
Setting up check mk monitoring.
	I. Modify ${path-to-platform-source-code}/DevOps_Platform/scripts/omd_seup/rules.mk file:
		1. Add agent port entry:
			agent_ports = [
			  ( 10011, ['cmk-agent', ], ['Employee'] ),
			  ( 10021, ['cmk-agent', ], ['Project'] ),
			  ( 10031, ['cmk-agent', ], ['Project-Assignment'] ),
			  ( 10041, ['cmk-agent', ], ['Device-Registration'] ),
			  ( 11991, ['cmk-agent', ], ['Organisation-Web'] ),
			  ( 6556, ['cmk-agent', ], ['OMD_SERVER'] ),
			  ( ${check-mk-mapping-port}, ['cmk-agent', ], ['${service-name}'] ),
			] + agent_ports

			Note: The ${check-mk-mapping-port} specified in the above proceess in run.sh should be replaced here.

			e.g. agent_ports = [
			  ( 10011, ['cmk-agent', ], ['Employee'] ),
			  ( 10021, ['cmk-agent', ], ['Project'] ),
			  ( 10031, ['cmk-agent', ], ['Project-Assignment'] ),
			  ( 10041, ['cmk-agent', ], ['Device-Registration'] ),
			  ( 10051, ['cmk-agent', ], ['Device-Authentication'] ),
			  ( 11991, ['cmk-agent', ], ['Organisation-Web'] ),
			  ( 6556, ['cmk-agent', ], ['OMD_SERVER'] ),
			] + agent_ports

		2. Add an entry to host contactgroups:
			host_contactgroups = [
			  ( 'DevOps', [], ['Employee', 'Project', 'Project-Assignment', 'Device-Authentication', 'Organisation-Web', 'OMD_SERVER', ${service-name}], {'comment': u'Put all hosts into the contact group "all"'} ),
			] + host_contactgroups

			e.g.
			host_contactgroups = [
			  ( 'DevOps', [], ['Employee', 'Project', 'Project-Assignment', 'Device-Registration', 'Device-Authentication', 'Organisation-Web', 'OMD_SERVER'], {'comment': u'Put all hosts into the contact group "all"'} ),
			] + host_contactgroups

		3. Add an entry to active checks:
			active_checks['tcp'] = [
			  ( (10010, {'response_time': (100.0, 200.0)}), [], ['Employee'] ),
			  ( (10020, {'response_time': (100.0, 200.0)}), [], ['Project'] ),
			  ( (10030, {'response_time': (100.0, 200.0)}), [], ['Project-Assignment'] ),
			  ( (10050, {'response_time': (100.0, 200.0)}), [], ['Device-Authentication'] ),
			  ( (11990, {'response_time': (100.0, 200.0)}), [], ['Organisation-Web'] ),
			  ( (${service-mapping-port}, {'response_time': (100.0, 200.0)}), [], ['${service-name}'] ),
			] + active_checks['tcp']

			Note: The ${service-mapping-port} specified in the above proceess in run.sh should be replaced here.

			e.g.
			active_checks['tcp'] = [
			  ( (10010, {'response_time': (100.0, 200.0)}), [], ['Employee'] ),
			  ( (10020, {'response_time': (100.0, 200.0)}), [], ['Project'] ),
			  ( (10030, {'response_time': (100.0, 200.0)}), [], ['Project-Assignment'] ),
			  ( (10050, {'response_time': (100.0, 200.0)}), [], ['Device-Authentication'] ),
			  ( (11990, {'response_time': (100.0, 200.0)}), [], ['Organisation-Web'] ),
			  ( (10040, {'response_time': (100.0, 200.0)}), [], ['Device-Registration'] ),
			] + active_checks['tcp']

		II. Modify ${path-to-platform-source-code}/DevOps_Platform/scripts/omd_seup/hosts.mk file:
			1. Add an entry to all hosts:
			all_hosts += [
			  "Employee|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Project|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Project-Assignment|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Device-Authentication|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Organisation-Web|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "OMD_SERVER|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "${service-name}|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			]

			e.g.
			all_hosts += [
			  "Employee|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Project|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Project-Assignment|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Device-Authentication|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Organisation-Web|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "OMD_SERVER|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			  "Device-Registration|wan|cmk-agent|prod|tcp|wato|/" + FOLDER_PATH + "/",
			]
		2. Add an entry to explicit address:
			ipaddresses.update({'Employee': u'192.168.29.110',
			 'Project': u'192.168.29.110',
			 'Project-Assignment': u'192.168.29.110',
			 'Device-Authentication': u'192.168.29.110',
			 'Organisation-Web': u'192.168.29.110',
			 'OMD_SERVER': u'127.0.0.1',
			 '${service-name}': u'127.0.0.1'})

			 e.g.
			ipaddresses.update({'Employee': u'192.168.29.110',
			 'Project': u'192.168.29.110',
			 'Project-Assignment': u'192.168.29.110',
			 'Device-Authentication': u'192.168.29.110',
			 'Organisation-Web': u'192.168.29.110',
			 'OMD_SERVER': u'127.0.0.1',
			 'Device-Registration': u'192.168.29.110'})
		3. Add an entry to host attributes:
			host_attributes.update(
				{'Employee': {'ipaddress': u'192.168.29.110',
				         'tag_agent': 'cmk-agent',
				         'tag_networking': 'wan'},
				 'Project': {'ipaddress': u'192.168.29.110',
				             'tag_agent': 'cmk-agent',
				             'tag_networking': 'wan'},
				 'Project-Assignment': {'ipaddress': u'192.168.29.110',
				             'tag_agent': 'cmk-agent',
				             'tag_networking': 'wan'},
				 'Device-Authentication': {'ipaddress': u'192.168.29.110',
				             'tag_agent': 'cmk-agent',
				             'tag_networking': 'wan'},
				 'Organisation-Web': {'ipaddress': u'192.168.29.110',
				         'tag_agent': 'cmk-agent',
				         'tag_networking': 'wan'}},
				 'OMD_SERVER': {'ipaddress': u'127.0.0.1',
				                'tag_agent': 'cmk-agent',
				                'tag_networking': 'wan'},
				 '${service-name}': {'ipaddress': u'127.0.0.1',
				                'tag_agent': 'cmk-agent',
				                'tag_networking': 'wan'})

				e.g.
				host_attributes.update(
					{'Employee': {'ipaddress': u'192.168.29.110',
					         'tag_agent': 'cmk-agent',
					         'tag_networking': 'wan'},
					 'Project': {'ipaddress': u'192.168.29.110',
					             'tag_agent': 'cmk-agent',
					             'tag_networking': 'wan'},
					 'Project-Assignment': {'ipaddress': u'192.168.29.110',
					             'tag_agent': 'cmk-agent',
					             'tag_networking': 'wan'},
					 'Device-Authentication': {'ipaddress': u'192.168.29.110',
					             'tag_agent': 'cmk-agent',
					             'tag_networking': 'wan'},
					 'Organisation-Web': {'ipaddress': u'192.168.29.110',
					         'tag_agent': 'cmk-agent',
					         'tag_networking': 'wan'},
					 'OMD_SERVER': {'ipaddress': u'127.0.0.1',
					                'tag_agent': 'cmk-agent',
					                'tag_networking': 'wan'},
					 'Device-Registration': {'ipaddress': u'192.168.29.110',
					             'tag_agent': 'cmk-agent',
					             'tag_networking': 'wan'}})