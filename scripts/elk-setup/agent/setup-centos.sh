#!/bin/bash
logstashForwarderConfigDirectoryPath=$1

logstashForwarderConfigFilePath=$logstashForwarderConfigDirectoryPath/logstash-forwarder.conf
logstashForwarderCertificateFilePath=$logstashForwarderConfigDirectoryPath/logstash-forwarder.crt
logstashForwarderRepoFilePath=$logstashForwarderConfigDirectoryPath/logstash-forwarder.repo

logstashForwarderInstallablesDirectoryPath=$2

gpgKeyElasticsearchFilePath=$logstashForwarderInstallablesDirectoryPath/GPG-KEY-elasticsearch
logstashForwarderRPMFilePath=$logstashForwarderInstallablesDirectoryPath/logstash-forwarder-0.4.0-1.x86_64.rpm

echo '================================================================================'
echo '			Installing logstash-forwarder'
echo '================================================================================'

if [ -f $gpgKeyElasticsearchFilePath ]; then
	rpm --import $gpgKeyElasticsearchFilePath
else
	rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
fi

cp $logstashForwarderRepoFilePath  /etc/yum.repos.d

if [ -f $logstashForwarderRPMFilePath ]; then
	yum install -y $logstashForwarderRPMFilePath
else
	yum -y install logstash-forwarder
fi

cp $logstashForwarderCertificateFilePath /etc/pki/tls/certs
cp $logstashForwarderConfigFilePath  /etc
service logstash-forwarder restart
exit