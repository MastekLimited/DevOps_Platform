

sudo rpm --import http://packages.elasticsearch.org/GPG-KEY-elasticsearch





if [ -d "/etc/yum.repos.d" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo mkdir -p /etc/yum.repos.d
	sudo chmod 777 /etc/yum.repos.d
	
fi

sudo cp /mnt/gluster/repo/elasticsearch.repo  /etc/yum.repos.d

sudo yum -y install elasticsearch-1.4.4


sudo cp /mnt/gluster/repo/elasticsearch.yml  /etc/elasticsearch

sudo systemctl start elasticsearch

cd ~; 
if [ -d "kibana-4.0.1-linux-x64.tar.gz" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo wget https://download.elasticsearch.org/kibana/kibana/kibana-4.0.1-linux-x64.tar.gz
	sudo chmod 777 kibana-4.0.1-linux-x64.tar.gz
	sudo tar xvf kibana-*.tar.gz
fi

sudo cp /mnt/gluster/repo/kibana.yml ~/kibana-4*/config



if [ -d "/opt/kibana" ];
then
    echo .........................CERTS_DIR already Exist.................................
else
	echo ...........................Create CERTS_DIRECTORY............................
	sudo mkdir -p /opt/kibana
	sudo chmod 777 /opt/kibana
	
fi

sudo cp -R ~/kibana-4*/* /opt/kibana/

sudo vi /etc/systemd/system/kibana4.service















