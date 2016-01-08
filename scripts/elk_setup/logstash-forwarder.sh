#!/bin/bash



sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch

sudo vi /etc/yum.repos.d

sudo cp /mnt/gluster/repo/logstash-forwarder.repo  /etc/yum.repos.d

sudo yum -y install logstash-forwarder

sudo cp /mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs

sudo cp /mnt/gluster/repo/logstash-forwarder.conf  /etc

sudo service logstash-forwarder restart





#sudo curl -O http://download.elasticsearch.org/logstash-forwarder/packages/logstash-forwarder-0.3.1-1.x86_64.rpm

#sudo rpm -ivh ~/logstash-forwarder-0.3.1-1.x86_64.rpm

#sudo cd /etc/init.d/; sudo curl -o logstash-forwarder http://logstashbook.com/code/4/logstash_forwarder_redhat_init

#sudo chmod +x logstash-forwarder

#sudo curl -o /etc/sysconfig/logstash-forwarder http://logstashbook.com/code/4/logstash_forwarder_redhat_sysconfig

#sudo cp /mnt/gluster/repo/logstash-forwarder /etc/sysconfig 

#sudo cp /mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs
#cd /mnt/gluster/repo
#sudo cp logstash-forwarder-json /etc/logstash-forwarder
#sudo chkconfig --add logstash-forwarder
#sudo service logstash-forwarder start

exit