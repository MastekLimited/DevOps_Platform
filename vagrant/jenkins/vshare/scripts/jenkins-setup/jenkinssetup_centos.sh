#!/bin/bash
echo 'jenkins  ALL=NOPASSWD: ALL' >> /etc/sudoers
echo 'vagrant  ALL=NOPASSWD: ALL' >> /etc/sudoers
sed -i '/Defaults \+requiretty/s/^/#/' /etc/sudoers
wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo
rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
yum install -y jenkins
firewall-cmd --zone=public --add-port=8080/tcp --permanent
firewall-cmd --zone=public --add-service=http --permanent
firewall-cmd --reload
firewall-cmd --list-all
service jenkins start

