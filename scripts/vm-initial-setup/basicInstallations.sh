#!/bin/bash
#This script will install basic software on the vm

#echo "Installing APT"
#wget http://pkgs.repoforge.org/rpmforge-release/rpmforge-release-0.5.3-1.el6.rf.x86_64.rpm
#rpm -i rpmforge-release-0.5.*.rpm
#yum install apt
#sudo apt-get update

echo ...........................Installing telnet...........................
if [ -f "/vshare/base-images/misc/telnet-0.17-59.el7.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/misc/telnet-0.17-59.el7.x86_64.rpm
else
	yum install -y telnet
fi

echo ...........................Installing wget...........................
if [ -f "/vshare/base-images/misc/wget-1.14-10.el7.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/misc/wget-1.14-10.el7.x86_64.rpm
else
	yum install -y wget
fi
