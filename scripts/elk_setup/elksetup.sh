#!/bin/bash

INSTALL_DIR=/opt/elk
LOGSTASH_PATH=logstash-1.4.2
LOGSTASH_BINARY=$LOGSTASH_PATH.tar.gz
ES_PATH=elasticsearch-1.4.4
ES_BINARY=$ES_PATH.tar.gz
KIBANA_VERSION=kibana-4.0.1
KIBANA_OS=linux-x64
KIBANA_BINARY=$KIBANA_VERSION-$KIBANA_OS
TLS_DIR=/etc/pki/tls
echo Installing ELK stack into $INSTALL_DIR
sudo mkdir -p $INSTALL_DIR
cd $INSTALL_DIR

if test -s $LOGSTASH_BINARY
then
    echo Logstash already Downloaded
else
	echo ...................................Downloading logstash 1.4.2..................................................
 	sudo curl -O https://download.elasticsearch.org/logstash/logstash/$LOGSTASH_BINARY
fi

if [ -d $LOGSTASH_BINARY ];
then
    echo Logstash already installed
else
	echo ...................................Installing Logstash latest.................................................
	sudo tar zxvf $LOGSTASH_BINARY
fi

if test -s $ES_BINARY
then
    echo Elasticsearch already Downloaded
else
	echo Downloading $ES_PATH
	sudo curl -O https://download.elasticsearch.org/elasticsearch/elasticsearch/$ES_BINARY
fi

if [ -d $ES_BINARY ];
then
    echo ............................Elasticsearch already installed....................................................
else
	echo .............................Installing Elasticsearch latest...................................................	
	sudo tar zxvf $ES_BINARY
fi

if test -s $KIBANA_BINARY
then
    echo ............................Kibana already Downloaded........................................................
else
	echo ............................Downloading Kibana Latest.......................................................
	sudo curl -O https://download.elasticsearch.org/kibana/kibana/$KIBANA_BINARY.tar.gz
fi

if [ -d $KIBANA_BINARY ];
then
    echo ..........................Kibana already installed..........................................................
else
	echo ..........................Installing Kibana latest...........................................................	
	sudo tar zxvf $KIBANA_BINARY.tar.gz
fi

pwd 
cd $ES_PATH
if [ -d "plugins/marvel" ];
then
    echo ............................Marvel already installed........................................................
else
	echo ...........................Installing Marvel latest.........................................................
	sudo bin/plugin -i elasticsearch/marvel/latest
fi

if [ -d "plugins/bigdesk" ];
then
    echo ...........................bigdesk already installed.......................................
else
	echo ...........................Installing bigdesk latest.......................................
	sudo bin/plugin -install lukas-vlcek/bigdesk/2.4.0
fi

if [ -d "plugins/elasticsearch-head" ];
then
    echo .........................elasticsearch-head already installed.................................
else
	echo ...........................Installing elasticsearch-head latest............................
	sudo bin/plugin -install mobz/elasticsearch-head
	
fi

echo .......................................Starting Elasticsearch to run in the background. ............................
sudo bin/elasticsearch -d --cluster.name=es_demo --number_of_shards=1 --number_of_replicas=0 --network.host=0.0.0.0

cd /opt/elk

echo .......................................Starting Kibana to run in the background. ............................
sudo ./$KIBANA_BINARY/bin/kibana &


echo .......................................Logstash is installed but it is not configured yet.. ............................
echo .......................................Starting Logtash configuration ...............................................

if [ -d $TLS_DIR"/certs" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo mkdir -p $TLS_DIR/certs
	sudo chmod 777 $TLS_DIR/certs
	
fi

if [ -d $TLS_DIR"/private" ];
then
    echo .........................PRIVATE_DIR already Exist inside TLS DIRECTORY.................................
else
	echo ...........................Create PRIVATE_DIRECTORY. inside TLS DIRECTORY............................
	sudo mkdir $TLS_DIR/private
	sudo chmod 777 $TLS_DIR/private
	
fi

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

#bin/logstash -e 'input { stdin { } } output { elasticsearch { protocol => "http" } }' 

exit