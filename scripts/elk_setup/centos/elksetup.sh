#!/bin/bash

TLS_DIR=/etc/pki/tls

sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch

sudo cp /mnt/gluster/repo/elasticsearch.repo /etc/yum.repos.d

sudo yum -y install elasticsearch-1.4.4

sudo cp /mnt/gluster/repo/elasticsearch.yml /etc/elasticsearch

sudo systemctl start elasticsearch.service

sudo systemctl enable elasticsearch.service

cd ~; 

sudo wget https://download.elasticsearch.org/kibana/kibana/kibana-4.0.1-linux-x64.tar.gz

sudo tar xvf kibana-*.tar.gz

sudo mkdir -p /opt/kibana

sudo cp -R ~/kibana-4*/* /opt/kibana/

sudo cp /mnt/gluster/repo/kibana4.service /etc/systemd/system

sudo systemctl start kibana4

sudo systemctl enable kibana4

sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch

sudo cp /mnt/gluster/repo/logstash.repo  /etc/yum.repos.d

sudo yum -y install logstash

echo ................................Now replacing openssl.cnf file into etc.ssl ...........................................................
sudo cp /mnt/gluster/repo/openssl.cnf  /etc/ssl

echo ................................Now generate the SSL certificate and private key...........................................................
cd /etc/pki/tls
sudo openssl req -config /etc/ssl/openssl.cnf -x509 -days 3650 -batch -nodes -newkey rsa:2048 -keyout private/logstash-forwarder.key -out certs/logstash-forwarder.crt
echo ................................Copy logstash-forwarder.crt into shared folder mnt.gluster.repo ...........................................................

if [ -f $TLS_DIR"/certs/logstash-forwarder.crt" ];
then
echo ...........................logstash-forwarder.crt found  now coping file............................
sudo cp /etc/pki/tls/certs/logstash-forwarder.crt /mnt/gluster/repo
else
echo ...........................logstash-forwarder.crt file does not exist............................
fi

if [ -d /etc/logstash ];
then
    echo .........................logstash directory already Exist inside etc .................................
else
	echo ...........................Create logstash directory. inside etc............................
	sudo mkdir /etc/logstash
	sudo chmod 777 /etc/logstash
	
fi

if [ -d /etc/logstash/conf.d ];
then
    echo .........................conf.d directory already Exist inside etc.logstash.................................
else
	echo ...........................Create conf.d directory. inside etc.logstash............................
	sudo mkdir /etc/logstash/conf.d
	sudo chmod 777 /etc/logstash/conf.d
	
fi

echo Lets coping a configuration file called 01-lumberjack-input.conf and set up our lumberjack input the protocol that Logstash Forwarder uses..............
sudo chmod 777 /mnt/gluster/repo/01-lumberjack-input.conf
sudo cp /mnt/gluster/repo/01-lumberjack-input.conf /etc/logstash/conf.d

echo Now lets coping a configuration file called 10-syslog.conf where we will add a filter for syslog messages...............................
sudo chmod 777 /mnt/gluster/repo/10-syslog.conf
sudo cp /mnt/gluster/repo/10-syslog.conf /etc/logstash/conf.d

echo ..........................we will coping a configuration file called 30-lumberjack-output.conf
sudo chmod 777 /mnt/gluster/repo/30-lumberjack-output.conf
sudo cp /mnt/gluster/repo/30-lumberjack-output.conf /etc/logstash/conf.d

sudo service logstash restart




exit