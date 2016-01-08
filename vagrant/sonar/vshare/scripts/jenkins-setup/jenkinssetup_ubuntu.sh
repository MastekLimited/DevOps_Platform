#!/bin/bash

wget -q -O - https://jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt-get update
sudo apt-get install jenkins
sudo apt-get update
sudo apt-get install jenkins
#If your /etc/init.d/jenkins file fails to start jenkins, edit the /etc/default/jenkins to replace the line
#HTTP_PORT=8080  replace port HTTP_PORT=8081
sudo service jenkins start

