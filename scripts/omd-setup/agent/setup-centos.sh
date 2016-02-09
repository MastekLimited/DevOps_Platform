#!/bin/bash

INSTALLATION_DIRECTORY=/opt/omd
mkdir -p $INSTALLATION_DIRECTORY
cd $INSTALLATION_DIRECTORY

echo ...........................Installing check mk agent...........................
if [ -f "/vshare/base-images/check_mk/agent/check_mk-agent-1.2.4p5-1.noarch.rpm" ]; then
	yum install -y /vshare/base-images/check_mk/agent/check_mk-agent-1.2.4p5-1.noarch.rpm
else
	wget http://mathias-kettner.de/download/check_mk-agent-1.2.4p5-1.noarch.rpm
	yum -y install check_mk-agent-1.2.4p5-1.noarch.rpm
fi

echo ...........................Configuring check mk agent...........................
chmod 777 /etc/xinetd.d
cp /mnt/gluster/repo/omd-setup/agent/config/check_mk  /etc/xinetd.d/check_mk

echo ...........................Restarting check mk agent...........................
service xinetd restart

exit