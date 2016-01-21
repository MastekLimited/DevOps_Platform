#!/bin/bash
# Perform installation as root
# Install prereqs

echo ...........................Install prereqs............................
#sudo yum -y install libcurl libcurl-devel rrdtool rrdtool-devel rrdtool-prel libgcrypt-devel libyajl libyajl-devel gcc make gcc-c++
sudo yum install -y rrdtool rrdtool-devel rrdtool-perl perl-HTML-Parser perl-JSON perl-CPAN perl-devel gcc make gcc-c++

# Enable the EPEL
echo ...........................Install EPEL............................
#sudo yum -y install http://dl.fedoraproject.org/pub/epel/7/x86_64/e/epel-release-7-5.noarch.rpm
sudo yum install -y epel-release

# Get Collectd, untar it, make it and install
echo ...........................Get Collectd TAR file............................
sudo wget http://collectd.org/files/collectd-5.4.0.tar.gz
echo ...........................Untar Collectd TAR file............................
sudo tar zxvf collectd-5.4.0.tar.gz

cd collectd-5.4.0

echo ...........................Executed configure file............................
sudo ./configure --prefix=/usr --sysconfdir=/etc --localstatedir=/var --libdir=/usr/lib64 --mandir=/usr/share/man --enable-all-plugins

#echo ...........................Executed MAKE. command...........................
#sudo make

echo ...........................Executed MAKE Install command............................
sudo make all install

# Copy the default init.d script
echo ...........................Copy the default init.d script............................
sudo cp /home/vagrant/collectd-5.4.0/contrib/redhat/init.d-collectd /etc/init.d/collectd

# Set the correct permissions
echo ...........................Set the correct permissions............................
sudo chmod +x /etc/init.d/collectd

# Copying collectd service file and creating soft link 
echo ...........................Copying collectd service file and creating soft link............................
sudo cp /mnt/gluster/repo/collectd.service /usr/lib/systemd/system/
sudo ln -sf /usr/lib/systemd/system/collectd.service /etc/systemd/system/multi-user.target.wants/collectd.service

# Start the deamon
echo ...........................Start the deamon............................
#sudo service collectd start
sudo systemctl start collectd.service
sudo systemctl enable collectd.service

# Install Collectd Web
echo ...........................Install Collectd Web............................
sudo yum install -y git
#sudo yum install -y rrdtool rrdtool-devel rrdtool-perl perl-HTML-Parser perl-JSON
 cd /usr/local/
sudo git clone https://github.com/httpdss/collectd-web.git
 cd /usr/local/collectd-web
sudo chmod +x cgi-bin/graphdefs.cgi
sudo ./runserver.py &
sudo cp /mnt/gluster/repo/collectd.conf /etc/collectd.conf
sudo cp /mnt/gluster/repo/collectd-server /usr/local/bin/collectd-server
sudo chmod +x /usr/local/bin/collectd-server

sudo /usr/local/bin/collectd-server start 

