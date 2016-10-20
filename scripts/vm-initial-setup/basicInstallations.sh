#!/bin/bash
#This script will install basic software on the vm

#echo '================================================================================'
#echo '			Installing APT'
#echo '================================================================================'
#wget http://pkgs.repoforge.org/rpmforge-release/rpmforge-release-0.5.3-1.el6.rf.x86_64.rpm
#rpm -i rpmforge-release-0.5.*.rpm
#yum install apt
#sudo apt-get update

echo '================================================================================'
echo '			Installing telnet'
echo '================================================================================'
if [ -f "/vshare/repo/misc/telnet-0.17-59.el7.x86_64.rpm" ]; then
	yum install -y /vshare/repo/misc/telnet-0.17-59.el7.x86_64.rpm
else
	yum install -y telnet
fi

echo '================================================================================'
echo '			Installing wget'
echo '================================================================================'
if [ -f "/vshare/repo/misc/wget-1.14-10.el7.x86_64.rpm" ]; then
	yum install -y /vshare/repo/misc/wget-1.14-10.el7.x86_64.rpm
else
	yum install -y wget
fi
