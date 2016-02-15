#!/bin/bash

checkMKAgentConfigFilePath=$1
checkMKAgentRPMFilePath=$2

INSTALLATION_DIRECTORY=/opt/omd
mkdir -p $INSTALLATION_DIRECTORY
cd $INSTALLATION_DIRECTORY

echo ...........................Installing check mk agent...........................
if [ -f $checkMKAgentRPMFilePath ]; then
	yum install -y $checkMKAgentRPMFilePath
else
	wget http://mathias-kettner.de/download/check_mk-agent-1.2.4p5-1.noarch.rpm
	yum -y install check_mk-agent-1.2.4p5-1.noarch.rpm
fi

echo ...........................Configuring check mk agent...........................
chmod 777 /etc/xinetd.d
cp $checkMKAgentConfigFilePath  /etc/xinetd.d/check_mk

echo ...........................Restarting check mk agent...........................
service xinetd restart

exit