#!/bin/bash
#This script will install basic software on the vm
echo 'jenkins  ALL=NOPASSWD: ALL' >> /etc/sudoers
echo 'vagrant  ALL=NOPASSWD: ALL' >> /etc/sudoers
sed -i '/Defaults \+requiretty/s/^/#/' /etc/sudoers
echo "Installing Java"
#yum install -y java
yum install -y java-1.7.0-openjdk
#echo "Installing APT"
#wget http://pkgs.repoforge.org/rpmforge-release/rpmforge-release-0.5.3-1.el6.rf.x86_64.rpm
	
#rpm -i rpmforge-release-0.5.*.rpm

#yum install apt

#sudo apt-get update

echo "Installing Net tools (ifconfig)"
yum install -y net-tools

echo "Installing telnet"
yum install -y telnet


echo "Installing wget"
yum install -y wget
