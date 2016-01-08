#!/bin/bash
echo ">>>>>>>>>>>>>>>> Transfering Artifact >>>>>>>>>>>>>>>>>>>>"
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/employee-service/target/employeeService.jar vagrant@192.168.29.110:/vshare/Build/employee/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/project-service/target/projectService.jar vagrant@192.168.29.110:/vshare/Build/project/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/Microservices/project-assignment-service/target/projectAssignmentService.jar vagrant@192.168.29.110:/vshare/Build/project-assignment-service/
scp  -o StrictHostKeyChecking=no /var/lib/jenkins/jobs/DevOps_Development/workspace/WebApplications/organisation-web-application/target/organisationWebApp.war vagrant@192.168.29.110:/vshare/Build/organisation-web/
echo ">>>>>>>>>>>>>>>> Transfered Artifact >>>>>>>>>>>>>>>>>>>>"
echo ".................. Re run the docker container ........................"
ssh -o StrictHostKeyChecking=no vagrant@192.168.29.110 'cd /vshare/docker/ && ./runAllScripts.sh'
echo ".................. Successfully Executed ........................"
exit
