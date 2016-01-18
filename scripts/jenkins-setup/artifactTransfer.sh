#!/bin/bash
echo ">>>>>>>>>>>>>>>> Transfering Artifact >>>>>>>>>>>>>>>>>>>>"
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/employee-service/target/employeeService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/employee/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/project-service/target/projectService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/project/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/project-assignment-service/target/projectAssignmentService.jar vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/project-assignment-service/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/WebApplications/organisation-web-application/target/organisationWebApp.war vagrant@&&DOCKER_HOST_IP&&:/vshare/Build/organisation-web/
echo ">>>>>>>>>>>>>>>> Transfered Artifact >>>>>>>>>>>>>>>>>>>>"
echo ".................. Re run the docker container ........................"
ssh -o StrictHostKeyChecking=no vagrant@&&DOCKER_HOST_IP&& 'cd /vshare/docker/ && ./runAllScripts.sh'
echo ".................. Successfully Executed ........................"
exit
