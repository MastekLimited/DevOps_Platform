#!/bin/bash
echo '================================================================================'
echo '			Transferring built artifacts'
echo '================================================================================'
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/Microservices/uuid-service/target/uuidService.jar vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/uuid
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/Microservices/employee-service/target/employeeService.jar vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/employee
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/Microservices/project-service/target/projectService.jar vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/project
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/Microservices/project-assignment-service/target/projectAssignmentService.jar vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/project-assignment
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/Microservices/device-registration-service/target/deviceRegistrationService.jar vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/device-registration
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/Microservices/device-authentication-service/target/deviceAuthenticationService.jar vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/device-authentication
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/DevOps_Development/WebApplications/organisation-web-application/target/organisationWebApp.war vagrant@&&DOCKER_HOST_IP&&:/mnt/gluster/repo/Build/organisation-web

echo '================================================================================'
echo '			Restart the docker containers: START'
echo '================================================================================'
ssh -o StrictHostKeyChecking=no vagrant@&&DOCKER_HOST_IP&& 'cd /mnt/gluster/repo/docker/ && ./runAllScripts.sh'
echo '================================================================================'
echo '			Restart the docker containers: END'
echo '================================================================================'
exit
