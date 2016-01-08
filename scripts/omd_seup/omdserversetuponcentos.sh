INSTALL_DIR=/opt/omd
mkdir -p $INSTALL_DIR
cd $INSTALL_DIR

yum remove MySQL-*
yum -y install mariadb-server mariadb
systemctl start mariadb
systemctl enable mariadb
systemctl start mariadb

wget  http://pkgs.repoforge.org/perl-Net-SNMP/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm
wget  http://pkgs.repoforge.org/libmcrypt/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm
wget  http://pkgs.repoforge.org/fping/fping-3.10-1.el6.rf.x86_64.rpm
wget  http://pkgs.repoforge.org/radiusclient-ng/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm
wget http://files.omdistro.org/releases/centos_rhel/omd-1.20.rhel7.x86_64.rpm
wget http://mathias-kettner.de/download/check_mk-agent-1.2.4p5-1.noarch.rpm
chmod 777 *
yum -y install perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm
yum -y install libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm
yum -y install fping-3.10-1.el6.rf.x86_64.rpm
yum -y install radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm
yum -y install omd-1.20.rhel7.x86_64.rpm
yum -y install check_mk-agent-1.2.4p5-1.noarch.rpm
omd create monitoring
omd start monitoring
#SELinux on RHEL CentOS by default ships so that httpd processes cannot initiate outbound connections
cd /usr/sbin
setsebool -P httpd_can_network_connect 1

chmod 777 /etc/xinetd.d
cp /mnt/gluster/repo/check_mk_forserver  /etc/xinetd.d/check_mk
service xinetd restart
service xinetd restart
chmod 777 /etc/snmp/snmpd.conf
cp -R /mnt/gluster/repo/snmpd.conf /etc/snmp/snmpd.conf
service snmpd restart

cp /mnt/gluster/repo/contacts.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/global.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/groups.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/rules.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
cp /mnt/gluster/repo/hosts.mk /omd/sites/monitoring/etc/check_mk/conf.d/wato/
chown monitoring:monitoring /omd/sites/monitoring/etc/check_mk/conf.d/wato/*
cp /mnt/gluster/repo/users.mk /omd/sites/monitoring/etc/check_mk/multisite.d/wato/
chown monitoring:monitoring /omd/sites/monitoring/etc/check_mk/multisite.d/wato/*

# below code create hosts that are being monitored
#cd /mnt/gluster/repo
#awk '$1=$1' OFS=, hosts.txt > hosts.csv
#chmod 777 hosts.csv
#chmod 777 add_hosts.py
#cp /mnt/gluster/repo/hosts.csv  /omd/versions/1.20/share/doc/check_mk/treasures
#cp /mnt/gluster/repo/add_hosts.py  /omd/versions/1.20/share/doc/check_mk/treasures
#yum -y install dos2unix
su - monitoring
#cd /omd/versions/1.20/share/doc/check_mk/treasures
#dos2unix add_hosts.py
#./add_hosts.py hosts.csv
omd restart
exit
