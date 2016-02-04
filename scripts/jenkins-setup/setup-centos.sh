#!/bin/bash


cd /opt;
wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.rpm";
#wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jre-8u45-linux-x64.rpm";

rpm -Uvh jdk-8u45-linux-x64.rpm;
#rpm -Uvh jre-8u45-linux-x64.rpm;

yum --enablerepo=epel -y install sshpass

yum install -y git

wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo
rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
yum -y install jenkins
service jenkins start
sleep 1m
cp /mnt/gluster/repo/jenkins-setup/plugins/*.hpi /var/lib/jenkins/plugins/
chown jenkins:jenkins /var/lib/jenkins/plugins/*
cp /mnt/gluster/repo/jenkins-setup/config/*.xml /var/lib/jenkins/
chown jenkins:jenkins /var/lib/jenkins/*
cp /mnt/gluster/repo/jenkins-setup/pwd /var/lib/jenkins/
service jenkins restart
