#!/bin/bash

TLS_DIR=/etc/pki/tls
echo 'deb http://packages.elasticsearch.org/logstashforwarder/debian stable main' | tee /etc/apt/sources.list.d/logstashforwarder.list

wget -O - http://packages.elasticsearch.org/GPG-KEY-elasticsearch | sudo apt-key add -

apt-get update

apt-get install logstash-forwarder

if [ -d $TLS_DIR"/certs" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	mkdir -p $TLS_DIR/certs
	chmod 777 $TLS_DIR/certs
	
fi


cp /mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs/
#OR
#scp root@&&ELK_HOST_IP&&:/mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs/

cp /mnt/gluster/repo/logstash-forwarder.conf /etc

service logstash-forwarder restart

exit