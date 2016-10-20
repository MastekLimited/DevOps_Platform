TLS_DIR=/etc/pki/tls

sudo wget -O - http://packages.elasticsearch.org/GPG-KEY-elasticsearch | sudo apt-key add -

echo 'deb http://packages.elasticsearch.org/elasticsearch/1.4/debian stable main' | sudo tee /etc/apt/sources.list.d/elasticsearch.list

sudo apt-get update

sudo apt-get -y install elasticsearch=1.4.4

sudo cp /mnt/gluster/repo/elk-setup/server/elasticsearch/config/elasticsearch.yml  /etc/elasticsearch

sudo service elasticsearch restart

sudo update-rc.d elasticsearch defaults 95 10

cd ~;

sudo wget https://download.elasticsearch.org/kibana/kibana/kibana-4.0.1-linux-x64.tar.gz


sudo chmod 777 kibana-4.0.1-linux-x64.tar.gz
sudo tar xvf kibana-*.tar.gz

#sudo cp /mnt/gluster/repo/kibana.yml ~/kibana-4*/config



if [ -d "/opt/kibana" ];
then
    echo .........................Kibana already Exist.................................
else
	echo ...........................Create kibana DIRECTORY............................
	sudo mkdir -p /opt/kibana
	sudo chmod 777 /opt/kibana

fi


cd /etc/init.d

sudo wget https://gist.githubusercontent.com/thisismitch/8b15ac909aed214ad04a/raw/bce61d85643c2dcdfbc2728c55a41dab444dca20/kibana4

sudo chmod +x /etc/init.d/kibana4
sudo update-rc.d kibana4 defaults 96 9
sudo service kibana4 start


echo 'deb http://packages.elasticsearch.org/logstash/1.5/debian stable main' | sudo tee /etc/apt/sources.list.d/logstash.list

sudo apt-get update

sudo apt-get install logstash

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
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/openssl.cnf  /etc/ssl


cd /etc/pki/tls
sudo openssl req -config /etc/ssl/openssl.cnf -x509 -days 3650 -batch -nodes -newkey rsa:2048 -keyout private/logstash-forwarder.key -out certs/logstash-forwarder.crt


if [ -f $TLS_DIR"/certs/logstash-forwarder.crt" ];
then
echo ...........................logstash-forwarder.crt found  now coping file............................
sudo cp /etc/pki/tls/certs/logstash-forwarder.crt /mnt/gluster/repo
else
echo ...........................logstash-forwarder.crt file does not exist............................
fi


echo Lets coping a configuration file called 01-lumberjack-input.conf and set up our lumberjack input the protocol that Logstash Forwarder uses..............
sudo chmod 777 /mnt/gluster/repo/elk-setup/server/logstash/config/01-lumberjack-input.conf
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/01-lumberjack-input.conf /etc/logstash/conf.d

echo Now lets coping a configuration file called 10-syslog.conf where we will add a filter for syslog messages...............................
sudo chmod 777 /mnt/gluster/repo/elk-setup/server/logstash/config/10-syslog.conf
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/10-syslog.conf /etc/logstash/conf.d

echo ..........................we will coping a configuration file called 30-lumberjack-output.conf
sudo chmod 777 /mnt/gluster/repo/elk-setup/server/logstash/config/30-lumberjack-output.conf
sudo cp /mnt/gluster/repo/elk-setup/server/logstash/config/30-lumberjack-output.conf /etc/logstash/conf.d

sudo service logstash restart

#bin/logstash -e 'input { stdin { } } output { elasticsearch { host => localhost } }'