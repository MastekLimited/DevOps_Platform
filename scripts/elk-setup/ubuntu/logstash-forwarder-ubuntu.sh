#!/bin/bash

TLS_DIR=/etc/pki/tls
sudo echo 'deb http://packages.elasticsearch.org/logstashforwarder/debian stable main' | sudo tee /etc/apt/sources.list.d/logstashforwarder.list

sudo wget -O - http://packages.elasticsearch.org/GPG-KEY-elasticsearch | sudo apt-key add -

sudo apt-get update

sudo apt-get install logstash-forwarder

if [ -d $TLS_DIR"/certs" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo mkdir -p $TLS_DIR/certs
	sudo chmod 777 $TLS_DIR/certs
	
fi


sudo cp /mnt/gluster/repo/logstash-forwarder.crt /etc/pki/tls/certs/

sudo cp /mnt/gluster/repo/logstash-forwarder.conf /etc

sudo service logstash-forwarder restart

exit