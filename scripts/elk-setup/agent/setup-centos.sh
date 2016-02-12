#!/bin/bash
logstashForwarderConfigFilePath=$1
logstashForwarderCertificateFilePath=$2
logstashForwarderRepoFilePath=$3

rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
cp $logstashForwarderRepoFilePath  /etc/yum.repos.d
yum -y install logstash-forwarder
cp $logstashForwarderCertificateFilePath /etc/pki/tls/certs
#OR
#scp root@&&ELK_HOST_IP&&:/mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs/
cp $logstashForwarderConfigFilePath  /etc
service logstash-forwarder restart
exit