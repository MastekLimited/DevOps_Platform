#!/bin/bash
# Perform installation as root
# Install prereqs
cd /opt
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

# Set the correct permissions
echo ...........................Set the correct permissions............................
chmod +x /etc/init.d/collectd

# Copying collectd service file and creating soft link 
echo ...........................Copying collectd service file and creating soft link............................
cp /mnt/gluster/repo/collectd.service /usr/lib/systemd/system/
ln -sf /usr/lib/systemd/system/collectd.service /etc/systemd/system/multi-user.target.wants/collectd.service

# Start the deamon
echo ...........................Start the deamon............................
#sudo service collectd start
systemctl start collectd.service
systemctl enable collectd.service

# Install Collectd Web
echo ...........................Install Collectd Web............................
sudo yum install -y git
#sudo yum install -y rrdtool rrdtool-devel rrdtool-perl perl-HTML-Parser perl-JSON
cd /usr/local/
git clone https://github.com/httpdss/collectd-web.git
cd /usr/local/collectd-web
chmod +x cgi-bin/graphdefs.cgi
./runserver.py &
cp /mnt/gluster/repo/collectd.conf /etc/collectd.conf
cp /mnt/gluster/repo/collectd-server /usr/local/bin/collectd-server
chmod +x /usr/local/bin/collectd-server

/usr/local/bin/collectd-server start 

