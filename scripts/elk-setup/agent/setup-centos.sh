#!/bin/bash
logstashForwarderConfigDirectoryPath=$1

logstashForwarderConfigFilePath=$logstashForwarderConfigDirectoryPath/logstash-forwarder.conf
logstashForwarderCertificateFilePath=$logstashForwarderConfigDirectoryPath/logstash-forwarder.crt
logstashForwarderRepoFilePath=$logstashForwarderConfigDirectoryPath/logstash-forwarder.repo

logstashForwarderInstallablesDirectoryPath=$2

gpgKeyElasticsearchFilePath=$logstashForwarderInstallablesDirectoryPath/GPG-KEY-elasticsearch
logstashForwarderRPMFilePath=$logstashForwarderInstallablesDirectoryPath/check_mk-agent-1.2.4p5-1.noarch.rpm

echo ...........................Installing logstash-forwarder...........................
rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
cp $logstashForwarderRepoFilePath  /etc/yum.repos.d
yum -y install logstash-forwarder

cp $logstashForwarderCertificateFilePath /etc/pki/tls/certs
cp $logstashForwarderConfigFilePath  /etc
service logstash-forwarder restart
exit