#!/bin/bash
rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
cp /mnt/gluster/repo/logstash-forwarder.repo  /etc/yum.repos.d
yum -y install logstash-forwarder
cp /mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs
#OR
#scp root@172.16.72.246:/mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs/
cp /mnt/gluster/repo/logstash-forwarder.conf  /etc
service logstash-forwarder restart
exit