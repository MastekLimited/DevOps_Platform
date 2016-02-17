#!/bin/bash
checkMKAgentConfigFilePath=$1

installablesDirectory=$2
systemdRPMFilePath=$installablesDirectory/systemd-sysv-219-19.el7.x86_64.rpm
tcpWrappersLibsRPMFilePath=$installablesDirectory/tcp_wrappers-libs-7.6-77.el7.x86_64.rpm
xinetdRPMFilePath=$installablesDirectory/xinetd-2.3.15-12.el7.x86_64.rpm
checkMKAgentRPMFilePath=$installablesDirectory/check_mk-agent-1.2.4p5-1.noarch.rpm

INSTALLATION_DIRECTORY=/opt/omd
mkdir -p $INSTALLATION_DIRECTORY
cd $INSTALLATION_DIRECTORY

echo '================================================================================'
echo '			Installing check mk agent'
echo '================================================================================'

if [ -f $systemdRPMFilePath ]; then
	yum install -y --skip-broken $systemdRPMFilePath
fi

if [ -f $tcpWrappersLibsRPMFilePath ]; then
	yum install -y --skip-broken $tcpWrappersLibsRPMFilePath
fi

if [ -f $xinetdRPMFilePath ]; then
	yum install -y --skip-broken $xinetdRPMFilePath
fi

if [ -f $checkMKAgentRPMFilePath ]; then
	yum install -y $checkMKAgentRPMFilePath
else
	wget http://mathias-kettner.de/download/check_mk-agent-1.2.4p5-1.noarch.rpm
	yum -y install check_mk-agent-1.2.4p5-1.noarch.rpm
fi

echo '================================================================================'
echo '			Configuring and Restarting check mk agent'
echo '================================================================================'
chmod 777 /etc/xinetd.d
cp $checkMKAgentConfigFilePath  /etc/xinetd.d/check_mk
#service xinetd restart
exit