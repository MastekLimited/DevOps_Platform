INSTALLATION_DIRECTORY=/opt/omd
mkdir -p $INSTALLATION_DIRECTORY
cd $INSTALLATION_DIRECTORY
chmod 777 *

yum remove MySQL-*

echo ...........................Installing mariadb...........................
if [ -f "/vshare/base-images/check_mk/server/mariadb-server-5.5.44-2.el7.centos.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/check_mk/server/mariadb-server-5.5.44-2.el7.centos.x86_64.rpm mariadb
else
	yum -y install mariadb-server mariadb
fi

echo ...........................Starting mariadb...........................
systemctl start mariadb
systemctl enable mariadb
systemctl start mariadb

echo ...........................Installing SNMP...........................
if [ -f "/vshare/base-images/check_mk/server/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm" ]; then
	echo "perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch found locally"
	cp /vshare/base-images/check_mk/server/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm .
else
	wget  http://pkgs.repoforge.org/perl-Net-SNMP/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm
fi
yum -y install perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm

echo ...........................Installing libmcrypt...........................
if [ -f "/vshare/base-images/check_mk/server/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm" ]; then
	echo "libmcrypt-2.5.7-1.2.el6.rf.x86_64 found locally"
	cp /vshare/base-images/check_mk/server/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm .
else
	wget  http://pkgs.repoforge.org/libmcrypt/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm
fi
yum -y install libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm

echo ...........................Installing fping...........................
if [ -f "/vshare/base-images/check_mk/server/fping-3.10-1.el6.rf.x86_64.rpm" ]; then
	echo "fping-3.10-1.el6.rf.x86_64 found locally"
	cp /vshare/base-images/check_mk/server/fping-3.10-1.el6.rf.x86_64.rpm .
else
	wget  http://pkgs.repoforge.org/fping/fping-3.10-1.el6.rf.x86_64.rpm
fi
yum -y install fping-3.10-1.el6.rf.x86_64.rpm

echo ...........................Installing radiusclient...........................
if [ -f "/vshare/base-images/check_mk/server/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm" ]; then
	echo "radiusclient-ng-0.5.6-5.el6.rf.x86_64 found locally"
	cp /vshare/base-images/check_mk/server/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm .
else
	wget  http://pkgs.repoforge.org/radiusclient-ng/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm
fi
yum -y install radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm

echo ...........................Installing OMD...........................
if [ -f "/vshare/base-images/check_mk/server/omd-1.20.rhel7.x86_64.rpm" ]; then
	echo "omd-1.20.rhel7.x86_64 found locally"
	cp /vshare/base-images/check_mk/server/omd-1.20.rhel7.x86_64.rpm .
else
	wget http://files.omdistro.org/releases/centos_rhel/omd-1.20.rhel7.x86_64.rpm
fi
yum -y install omd-1.20.rhel7.x86_64.rpm

echo ...........................Installing Check MK agent...........................
if [ -f "/vshare/base-images/check_mk/agent/check_mk-agent-1.2.4p5-1.noarch.rpm" ]; then
	echo "perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch found locally"
	cp /vshare/base-images/check_mk/agent/check_mk-agent-1.2.4p5-1.noarch.rpm .
else
	wget http://mathias-kettner.de/download/check_mk-agent-1.2.4p5-1.noarch.rpm
fi
yum -y install check_mk-agent-1.2.4p5-1.noarch.rpm

echo ...........................Starting OMD server ..........................
omd create monitoring
omd start monitoring

echo ...........................Allowing httpd processes to make outbound connections ..........................
#SELinux on RHEL CentOS by default ships so that httpd processes cannot initiate outbound connections
cd /usr/sbin
setsebool -P httpd_can_network_connect 1

echo ...........................Configuring check mk agent ..........................
chmod 777 /etc/xinetd.d
cp /mnt/gluster/repo/omd-setup/server/config/check_mk  /etc/xinetd.d/check_mk
service xinetd restart

echo ...........................Change configuration for SNMP..........................
chmod 777 /etc/snmp/snmpd.conf
cp -R /mnt/gluster/repo/omd-setup/server/config/snmpd.conf /etc/snmp/snmpd.conf
service snmpd restart

echo ...........................Change configuration for OMD server ..........................
cp /mnt/gluster/repo/omd-setup/server/config/contacts.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/omd-setup/server/config/global.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/omd-setup/server/config/groups.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/omd-setup/server/config/rules.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/omd-setup/server/config/hosts.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
chown monitoring:monitoring /omd/sites/monitoring/etc/check_mk/conf.d/wato/*

cp /mnt/gluster/repo/omd-setup/server/config/users.mk /omd/sites/monitoring/etc/check_mk/multisite.d/wato/
chown monitoring:monitoring /omd/sites/monitoring/etc/check_mk/multisite.d/wato/*

# below code create hosts that are being monitored
#cd /mnt/gluster/repo
#awk '$1=$1' OFS=, hosts.txt > hosts.csv
#chmod 777 hosts.csv
#chmod 777 add_hosts.py
#cp /mnt/gluster/repo/hosts.csv  /omd/versions/1.20/share/doc/check_mk/treasures
#cp /mnt/gluster/repo/add_hosts.py  /omd/versions/1.20/share/doc/check_mk/treasures
#yum -y install dos2unix
#cd /omd/versions/1.20/share/doc/check_mk/treasures
#dos2unix add_hosts.py
#./add_hosts.py hosts.csv

echo ...........................Restarting OMD server ..........................
su - monitoring
omd restart
exit
