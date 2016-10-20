TLS_DIR=/etc/pki/tls

echo '================================================================================'
echo '			Installing java'
echo '================================================================================'
if [ -f "/vshare/repo/java/jdk-8u45-linux-x64.rpm" ]; then
	sudo yum install -y /vshare/repo/java/jdk-8u45-linux-x64.rpm
else
	sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.rpm";
	sudo rpm -Uvh jdk-8u45-linux-x64.rpm;
fi

echo ...........................Checking and creating certs directory...........................
if [ -d "/etc/yum.repos.d" ]; then
    echo ...........................yum.repos.d already exists...........................
else
	sudo mkdir -p /etc/yum.repos.d
	sudo chmod 777 /etc/yum.repos.d
fi

echo '================================================================================'
echo '			Installing Elasticsearch'
echo '================================================================================'
sudo cp /mnt/gluster/repo/elk-setup/server/elasticsearch/config/elasticsearch.repo /etc/yum.repos.d

if [ -f "/vshare/repo/elk/elasticsearch-1.4.4.noarch.rpm" ]; then
	sudo yum install --skip-broken -y /vshare/repo/elk/elasticsearch-1.4.4.noarch.rpm
else
	sudo yum -y install elasticsearch-1.4.4
fi

sudo cp /mnt/gluster/repo/elk-setup/server/elasticsearch/config/elasticsearch.yml /etc/elasticsearch

cd /usr/share/elasticsearch/

echo '================================================================================'
echo '			Checking and Installing bigdesk'
echo '================================================================================'
if [ -d "plugins/bigdesk" ]; then
    echo ...........................bigdesk already installed...........................
else
	sudo bin/plugin -install lukas-vlcek/bigdesk/2.4.0
fi

echo '================================================================================'
echo '			Checking and Installing elasticsearch-head'
echo '================================================================================'
if [ -d "plugins/elasticsearch-head" ]; then
    echo ...........................elasticsearch-head already installed...........................
else
	sudo bin/plugin -install mobz/elasticsearch-head
fi

echo ...........................Starting Elasticsearch...........................
sudo systemctl enable elasticsearch
sudo systemctl start elasticsearch

echo '================================================================================'
echo '			Installing Kibana'
echo '================================================================================'
cd ~;

if [ -d "kibana-4.0.1-linux-x64.tar.gz" ]; then
    echo ...........................kibana-4.0.1-linux-x64.tar.gz already installed...........................
else
	if [ -f "/vshare/repo/elk/kibana-4.0.1-linux-x64.tar.gz" ]; then
		sudo cp /vshare/repo/elk/kibana-4.0.1-linux-x64.tar.gz .
	else
		sudo wget https://download.elasticsearch.org/kibana/kibana/kibana-4.0.1-linux-x64.tar.gz
	fi
	sudo chmod 777 kibana-4.0.1-linux-x64.tar.gz
	sudo tar xf kibana-*.tar.gz
fi

echo ...........................Copying kibana installation directory in opt...........................
if [ -d "/opt/kibana" ]; then
    echo .........................Kibana already exists.................................
else
	echo ...........................Create kibana directory............................
	sudo mkdir -p /opt/kibana
	sudo chmod 777 /opt/kibana
fi

sudo cp -R ~/kibana-4*/* /opt/kibana/

echo ...........................Checking and creating certs directory systemd...........................
if [ -d "/etc/systemd" ]; then
    echo .........................certs directory already exists.................................
else
	sudo mkdir -p /etc/systemd
fi

echo ...........................Checking and creating system directory...........................
if [ -d "/etc/systemd/system" ]; then
    echo ...........................system directory already exists...........................
else
	sudo mkdir -p /etc/systemd/system
	sudo chmod 777 /etc/systemd/system
fi

echo ...........................Starting Kibana...........................
sudo cp /mnt/gluster/repo/elk-setup/server/kibana/config/kibana4.service /etc/systemd/system
sudo systemctl start kibana4
sudo systemctl enable kibana4

echo '================================================================================'
echo '			Installing Logstash'
echo '================================================================================'
echo ...........................Installing Logstash...........................

if [ -f "/vshare/repo/elk/GPG-KEY-elasticsearch" ]; then
	sudo cp /vshare/repo/elk/GPG-KEY-elasticsearch .
else
	sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch
fi

sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/logstash.repo  /etc/yum.repos.d/

if [ -f "/vshare/repo/elk/logstash-1.5.6-1.noarch.rpm" ]; then
	sudo yum -y install /vshare/repo/elk/logstash-1.5.6-1.noarch.rpm
else
	sudo yum -y install logstash
fi

echo ..........................Replace openssl.cnf file into etc.ssl...........................
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/openssl.cnf  /etc/ssl

echo ...........................Generate SSL certificate and private key...........................
cd /etc/pki/tls
sudo openssl req -config /etc/ssl/openssl.cnf -x509 -days 3650 -batch -nodes -newkey rsa:2048 -keyout private/logstash-forwarder.key -out certs/logstash-forwarder.crt

echo ...........................Copy newly generated logstash-forwarder.crt into shared folder for logstash forwarder instances running on any VM/docker containers...........................

if [ -f $TLS_DIR"/certs/logstash-forwarder.crt" ]; then
	sudo cp -f $TLS_DIR"/certs/logstash-forwarder.crt" /vshare/certificates/generated/
else
	echo ...........................logstash-forwarder.crt file does not exist...........................
fi

echo ...........................Checking and creating logstash directory...........................
if [ -d /etc/logstash ]; then
    echo ...........................logstash directory already Exist in etc...........................
else
	sudo mkdir /etc/logstash
	sudo chmod 777 /etc/logstash
fi

echo ...........................Checking and creating conf.d directory...........................
if [ -d /etc/logstash/conf.d ]; then
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

#bin/logstash -e 'input { stdin { } } output { elasticsearch { host => localhost } }'