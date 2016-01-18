sudo yum -y install wget

sudo yum -y install telnet
sudo yum -y update

sudo yum -y install git

sudo mkdir /opt/gitrepo

cd /opt/gitrepo/

sudo git clone http://104.155.74.167:8085/git/DevOps_Platform.git
cd DevOps_Platform/

sudo git checkout develop

sudo git pull
mkdir /mnt/gluster
mkdir /mnt/gluster/repo
chmod 777 /mnt/gluster
chmod 777 /mnt/gluster/*
cd /opt
wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \"http://download.oracle.com/otn-pub/java/jdk/8u40-b25/jre-8u40-linux-x64.tar.gz"
tar xvf jre-8*.tar.gz
chown -R root: jre1.8*
alternatives --install /usr/bin/java java /opt/jre1.8*/bin/java 1
rm /opt/jre-8*.tar.gz


