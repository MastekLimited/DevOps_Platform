#!/bin/bash


cd /opt;

echo "Installing Java"
yum install -y /vshare/base-images/jdk/jdk-8u45-linux-x64.rpm

yum --enablerepo=epel -y install /vshare/base-images/misc/sshpass-1.05-9.2.x86_64.rpm

#TODO: Need to find out git package with all the dependencies
yum install -y git

#yum install -y /vshare/base-images/jenkins/perl-Error-0.17020-2.el7.noarch.rpm
#yum install -y /vshare/base-images/jenkins/perl-TermReadKey-2.30-20.el7.x86_64.rpm
#yum install -y /vshare/base-images/jenkins/libgnome-keyring-3.8.0-3.el7.x86_64.rpm
#yum install -y /vshare/base-images/jenkins/perl-Git-1.8.3.1-5.el7.noarch.rpm
#yum install -y /vshare/base-images/jenkins/git-1.8.3.1-5.el7.x86_64.rpm

#wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo
rpm --import /vshare/base-images/jenkins/jenkins-ci.org.key
yum -y install /vshare/base-images/jenkins/jenkins-1.642.1-1.1.noarch.rpm
service jenkins start
sleep 20
cp /mnt/gluster/repo/jenkins-setup/plugins/*.hpi /var/lib/jenkins/plugins/
chown jenkins:jenkins /var/lib/jenkins/plugins/*
cp /mnt/gluster/repo/jenkins-setup/config/*.xml /var/lib/jenkins/
chown jenkins:jenkins /var/lib/jenkins/*
cp /mnt/gluster/repo/jenkins-setup/job-config/pwd /var/lib/jenkins/
service jenkins restart
