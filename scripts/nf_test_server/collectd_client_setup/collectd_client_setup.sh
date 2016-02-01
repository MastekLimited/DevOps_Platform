#!/bin/bash
# Perform installation as root
# Install prereqs

cd /opt
if [ -d "/mnt/gluster" ];
then
    echo ........................... mnt  . gluster directory not present .......................................
else
	mkdir -p /mnt/gluster
	chmod  777 /mnt/gluster
fi

if [ -d "/mnt/gluster/repo" ];
then
   echo ........................... mnt  . gluster repo directory not present .......................................
else
	   echo ........................... creating  mnt  gluster repo directory .......................................
	mkdir -p /mnt/gluster/repo
	chmod  777 /mnt/gluster/repo
fi

if [ -d "/mnt/gluster/repo/collectd_client_setup/" ];
then
   echo ........................... mnt  . gluster repo collectd directory not present .......................................
else
           echo ........................... creatting  mnt  gluster repo collectd directory .......................................
        mkdir -p /mnt/gluster/repo/collectd_client_setup
        chmod  777 /mnt/gluster/repo/collectd_client_setup
fi

#if [ -f "collectd.conf" ];
#then
#echo ...........................collectd.conf found  now coping file............................
cp /mnt/gluster/repo/collectd_client_setup/collectd.conf /etc/collectd.conf
#else
#echo ...........................collectd.conf file does not exist............................
#exit
#fi

#if [ -f "collectd.service" ];
#then
#echo ...........................collectd.service found  now coping file............................
cp /mnt/gluster/repo/collectd_client_setup/collectd.service /usr/lib/systemd/system/
#else
#echo ...........................collectd.service file does not exist............................
#exit
#fi

echo ...........................Install prereqs............................
#sudo yum -y install libcurl libcurl-devel rrdtool rrdtool-devel rrdtool-prel libgcrypt-devel libyajl libyajl-devel gcc make gcc-c++
yum install -y rrdtool rrdtool-devel rrdtool-perl perl-HTML-Parser perl-JSON perl-CPAN perl-devel gcc make gcc-c++

# Enable the EPEL
echo ...........................Install EPEL............................
#sudo yum -y install http://dl.fedoraproject.org/pub/epel/7/x86_64/e/epel-release-7-5.noarch.rpm
yum install -y epel-release

# Get Collectd, untar it, make it and install
echo ...........................Get Collectd TAR file............................
wget http://collectd.org/files/collectd-5.4.0.tar.gz
echo ...........................Untar Collectd TAR file............................
tar zxvf collectd-5.4.0.tar.gz

cd collectd-5.4.0

echo ...........................Executed configure file............................
./configure --prefix=/usr --sysconfdir=/etc --localstatedir=/var --libdir=/usr/lib64 --mandir=/usr/share/man --enable-all-plugins

#echo ...........................Executed MAKE. command...........................
#sudo make

echo ...........................Executed MAKE Install command............................
make all install

# Copy the default init.d script
echo ...........................Copy the default init.d script............................
cp /opt/collectd-5.4.0/contrib/redhat/init.d-collectd /etc/init.d/collectd

chmod 755 /etc/collectd.conf

# Set the correct permissions
echo ...........................Set the correct permissions............................
chmod +x /etc/init.d/collectd

# Copying collectd service file and creating soft link
echo ...........................Copying collectd service file and creating soft link............................

ln -sf /usr/lib/systemd/system/collectd.service /etc/systemd/system/multi-user.target.wants/collectd.service



# Start the deamon
echo ...........................Start the deamon............................
service collectd start
#sudo systemctl start collectd.service
#sudo systemctl enable collectd.service
echo ...........................Done............................

