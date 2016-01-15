TLS_DIR=/etc/pki/tls
sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
if [ -d "/etc/yum.repos.d" ];
then
    echo .........................yum.repos.d already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo mkdir -p /etc/yum.repos.d
	sudo chmod 777 /etc/yum.repos.d
fi

sudo cp /mnt/gluster/repo/elasticsearch.repo  /etc/yum.repos.d
sudo yum -y install elasticsearch-1.4.4


sudo cp /mnt/gluster/repo/elasticsearch.yml  /etc/elasticsearch

cd /usr/share/elasticsearch/

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

sudo systemctl start elasticsearch

cd ~;

if [ -d "kibana-4.0.1-linux-x64.tar.gz" ];
then
    echo .........................kibana-4.0.1-linux-x64.tar.gz already Exist.................................
else
	echo ...........................Download kibana-4.0.1-linux-x64.tar.gz............................
	sudo wget https://download.elasticsearch.org/kibana/kibana/kibana-4.0.1-linux-x64.tar.gz
	sudo chmod 777 kibana-4.0.1-linux-x64.tar.gz
	sudo tar xvf kibana-*.tar.gz
fi

#sudo cp /mnt/gluster/repo/kibana.yml ~/kibana-4*/config



if [ -d "/opt/kibana" ];
then
    echo .........................Kibana already Exist.................................
else
	echo ...........................Create kibana DIRECTORY............................
	sudo mkdir -p /opt/kibana
	sudo chmod 777 /opt/kibana
	
fi

sudo cp -R ~/kibana-4*/* /opt/kibana/

if [ -d "/etc/systemd" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo mkdir -p /etc/systemd
		
fi

if [ -d "/etc/systemd/system" ];
then
    echo .........................system dir already Exist.................................
else
	echo ...........................Create system dir............................
	sudo mkdir -p /etc/systemd/system
	sudo chmod 777 /etc/systemd/system		
fi

sudo cp /mnt/gluster/repo/kibana4.service /etc/systemd/system

sudo systemctl start kibana4
sudo systemctl enable kibana4

sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch

sudo cp /mnt/gluster/repo/logstash.repo  /etc/yum.repos.d/

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
sudo cp -f /etc/pki/tls/certs/logstash-forwarder.crt /vshare/docker/roles/base/
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

#bin/logstash -e 'input { stdin { } } output { elasticsearch { host => localhost } }'