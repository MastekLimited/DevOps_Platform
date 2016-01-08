#!/bin/bash

wget -q -O - https://jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
apt-get update
apt-get install jenkins
apt-get update
apt-get install jenkins
service jenkins start
#If your /etc/init.d/jenkins file fails to start jenkins, edit the /etc/default/jenkins to replace the line
#HTTP_PORT=8080  replace port HTTP_PORT=8081
cp /mnt/gluster/repo/*.hpi /var/lib/jenkins/plugins/
cd /var/lib/jenkins/plugins/
chown jenkins:jenkins *
service jenkins restart

