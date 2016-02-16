#!/bin/bash


cd /opt;
echo ...........................Installing java...........................
if [ -f "/vshare/base-images/jdk/jdk-8u45-linux-x64.rpm" ]; then
	yum install -y /vshare/base-images/jdk/jdk-8u45-linux-x64.rpm
else
	wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.rpm";
	rpm -Uvh jdk-8u45-linux-x64.rpm;
fi

echo ...........................Installing sshpass...........................
if [ -f "/vshare/base-images/misc/sshpass-1.05-9.2.x86_64.rpm" ]; then
	yum --enablerepo=epel -y install /vshare/base-images/misc/sshpass-1.05-9.2.x86_64.rpm
else
	yum --enablerepo=epel -y install sshpass
fi

echo ...........................Installing git...........................

if [ -f "/vshare/base-images/git/perl-Error-0.17020-2.el7.noarch.rpm" ]; then
	yum install -y --skip-broken /vshare/base-images/git/perl-Error-0.17020-2.el7.noarch.rpm
fi

if [ -f "/vshare/base-images/git/libgnome-keyring-3.8.0-3.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/base-images/git/libgnome-keyring-3.8.0-3.el7.x86_64.rpm
fi

if [ -f "/vshare/base-images/git/perl-TermReadKey-2.30-20.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/base-images/git/perl-TermReadKey-2.30-20.el7.x86_64.rpm
fi

if [ -f "/vshare/base-images/git/git-1.8.3.1-6.el7.x86_64.rpm" ] && [ -f "/vshare/base-images/git/perl-Git-1.8.3.1-6.el7.noarch.rpm" ]; then
	yum install -y /vshare/base-images/git/git-1.8.3.1-6.el7.x86_64.rpm /vshare/base-images/git/perl-Git-1.8.3.1-6.el7.noarch.rpm
else
	yum install -y git
fi

echo ...........................Installing jenkins...........................
if [ -f "/vshare/base-images/jenkins/jenkins-ci.org.key" ]; then
	rpm --import /vshare/base-images/jenkins/jenkins-ci.org.key
else
	wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo
	rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
fi

if [ -f "/vshare/base-images/jenkins/jenkins-1.642.1-1.1.noarch.rpm" ]; then
	yum -y install /vshare/base-images/jenkins/jenkins-1.642.1-1.1.noarch.rpm
else
	yum -y install jenkins
fi


echo ...........................Starting Jenkins...........................
service jenkins start
sleep 20

echo ...........................Copying configuration files...........................
cp /mnt/gluster/repo/jenkins-setup/plugins/*.hpi /var/lib/jenkins/plugins/
chown jenkins:jenkins /var/lib/jenkins/plugins/*
cp /mnt/gluster/repo/jenkins-setup/config/*.xml /var/lib/jenkins/
chown jenkins:jenkins /var/lib/jenkins/*
cp /mnt/gluster/repo/jenkins-setup/job-config/pwd /var/lib/jenkins/

echo ...........................Restarting Jenkins...........................
service jenkins restart
