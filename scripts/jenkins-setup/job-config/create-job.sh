#!/bin/bash
jobname=$1
cd /var/lib/jenkins/jobs/
echo .............creating job............
mkdir $jobname
cd $jobname
cp /mnt/gluster/repo/jenkins-setup/job-config/config.xml .
echo .............job is created ............
cd /var/lib/jenkins/jobs/
chown jenkins:jenkins $jobname
echo .............Restarting jenkins service ............
service jenkins restart
echo .............Your job has been configured please check via browser ............
exit


