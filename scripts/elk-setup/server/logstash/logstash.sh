#!/bin/bash


TLS_DIR=/etc/pki/tls

sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch


sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/logstash.repo  /etc/yum.repos.d

sudo yum -y install logstash

echo ..........................Replace openssl.cnf file into etc.ssl...........................
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/openssl.cnf  /etc/ssl

echo ...........................Generate SSL certificate and private key...........................
cd /etc/pki/tls
sudo openssl req -config /etc/ssl/openssl.cnf -x509 -days 3650 -batch -nodes -newkey rsa:2048 -keyout private/logstash-forwarder.key -out certs/logstash-forwarder.crt

echo ...........................Copy newly generated logstash-forwarder.crt into shared folder for logstash forwarder instances running on any VM/docker containers...........................

if [ -f $TLS_DIR"/certs/logstash-forwarder.crt" ]; then
	sudo cp /etc/pki/tls/certs/logstash-forwarder.crt /vshare/certificates/generated/
else
	echo ...........................logstash-forwarder.crt file does not exist............................
fi

echo ...........................Checking and creating logstash directory...........................
if [ -d /etc/logstash ]; then
    echo ...........................logstash directory already Exist in etc..........................
else
	sudo mkdir /etc/logstash
	sudo chmod 777 /etc/logstash
fi

echo ...........................Checking and creating conf.d directory...........................
if [ -d /etc/logstash/conf.d ];
then
    echo ...........................conf.d directory already exists inside etc.logstash...........................
else
	sudo mkdir /etc/logstash/conf.d
	sudo chmod 777 /etc/logstash/conf.d

fi

echo ...........................Set up lumberjack input the protocol for logstash forwarder...........................
sudo chmod 777 /mnt/gluster/repo/elk-setup/server/logstash/config/01-lumberjack-input.conf
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/01-lumberjack-input.conf /etc/logstash/conf.d

echo ...........................Add a filter for syslog messages...........................
sudo chmod 777 /mnt/gluster/repo/elk-setup/server/logstash/config/10-syslog.conf
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/10-syslog.conf /etc/logstash/conf.d

echo ...........................Set up lumberjack output the protocol for logstash forwarder...........................
sudo chmod 777 /mnt/gluster/repo/elk-setup/server/logstash/config/30-lumberjack-output.conf
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/30-lumberjack-output.conf /etc/logstash/conf.d

echo ...........................Restarting logstash...........................
sudo service logstash restart






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