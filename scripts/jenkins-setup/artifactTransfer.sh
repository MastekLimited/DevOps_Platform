#!/bin/bash
echo ">>>>>>>>>>>>>>>> Transferring Artifacts >>>>>>>>>>>>>>>>>>>>"
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/uuid-service/target/uuidService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/uuid/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/employee-service/target/employeeService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/employee/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/project-service/target/projectService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/project/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/project-assignment-service/target/projectAssignmentService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/project-assignment/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/device-registration-service/target/deviceRegistrationService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/device-registration/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/device-authentication-service/target/deviceAuthenticationService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/device-authentication/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/WebApplications/organisation-web-application/target/organisationWebApp.war vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/organisation-web/
echo ">>>>>>>>>>>>>>>> Transferred Artifacts >>>>>>>>>>>>>>>>>>>>"
echo ".................. Restart the docker containers:START ........................"
ssh -o StrictHostKeyChecking=no vagrant@&&DOCKER_HOST_IP&& 'cd /vshare/docker/ && ./runAllScripts.sh'
echo ".................. Restart the docker containers:END ........................"
exit
