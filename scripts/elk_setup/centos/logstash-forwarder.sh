#!/bin/bash
rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
cp /mnt/gluster/repo/logstash-forwarder.repo  /etc/yum.repos.d
yum -y install logstash-forwarder
cp /mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs
#OR
#scp root@192.168.51.102:/mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs/
cp /mnt/gluster/repo/logstash-forwarder.conf  /etc
service logstash-forwarder restart
exit