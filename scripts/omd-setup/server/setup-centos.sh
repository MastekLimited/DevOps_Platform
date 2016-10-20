INSTALLATION_DIRECTORY=/opt/omd
mkdir -p $INSTALLATION_DIRECTORY
cd $INSTALLATION_DIRECTORY
chmod 777 *

yum remove MySQL-*

echo '================================================================================'
echo '			Installing mariadb: START'
echo '================================================================================'

if [ -f "/vshare/repo/check_mk/server/mariadb-libs-5.5.44-2.el7.centos.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/mariadb-libs-5.5.44-2.el7.centos.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Data-Dumper-2.145-3.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Data-Dumper-2.145-3.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Compress-Raw-Bzip2-2.061-3.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Compress-Raw-Bzip2-2.061-3.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Compress-Raw-Zlib-2.061-4.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Compress-Raw-Zlib-2.061-4.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-IO-Compress-2.061-2.el7.noarch.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-IO-Compress-2.061-2.el7.noarch.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Net-Daemon-0.48-5.el7.noarch.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Net-Daemon-0.48-5.el7.noarch.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-PlRPC-0.2020-14.el7.noarch.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-PlRPC-0.2020-14.el7.noarch.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-DBI-1.627-4.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-DBI-1.627-4.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-DBD-MySQL-4.023-5.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-DBD-MySQL-4.023-5.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/mariadb-server-5.5.44-2.el7.centos.x86_64.rpm" ] && [ -f "/vshare/repo/check_mk/server/mariadb-5.5.44-2.el7.centos.x86_64.rpm" ]; then
	yum install -y /vshare/repo/check_mk/server/mariadb-server-5.5.44-2.el7.centos.x86_64.rpm /vshare/repo/check_mk/server/mariadb-5.5.44-2.el7.centos.x86_64.rpm
else
	yum -y install mariadb-server mariadb
fi

echo '================================================================================'
echo '			Installing mariadb: END'
echo '================================================================================'

echo '================================================================================'
echo '			Starting mariadb'
echo '================================================================================'
systemctl start mariadb
systemctl enable mariadb
systemctl start mariadb

echo '================================================================================'
echo '			Installing SNMP: START'
echo '================================================================================'

if [ -f "/vshare/repo/check_mk/server/perl-Digest-1.17-245.el7.noarch.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Digest-1.17-245.el7.noarch.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Digest-MD5-2.52-3.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Digest-MD5-2.52-3.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Digest-SHA-5.85-3.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Digest-SHA-5.85-3.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Digest-HMAC-1.03-5.el7.noarch.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Digest-HMAC-1.03-5.el7.noarch.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Crypt-DES-2.05-20.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Crypt-DES-2.05-20.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Digest-SHA1-2.13-9.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Digest-SHA1-2.13-9.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Socket6-0.23-15.el7.x86_64.rpm" ]; then
	yum install -y --skip-broken /vshare/repo/check_mk/server/perl-Socket6-0.23-15.el7.x86_64.rpm
fi

if [ -f "/vshare/repo/check_mk/server/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm" ]; then
	cp /vshare/repo/check_mk/server/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm .
else
	wget  http://pkgs.repoforge.org/perl-Net-SNMP/perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm
fi
yum -y install perl-Net-SNMP-5.2.0-1.2.el6.rf.noarch.rpm

echo '================================================================================'
echo '			Installing SNMP: END'
echo '================================================================================'

echo '================================================================================'
echo '			Installing libmcrypt'
echo '================================================================================'
if [ -f "/vshare/repo/check_mk/server/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm" ]; then
	cp /vshare/repo/check_mk/server/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm .
else
	wget  http://pkgs.repoforge.org/libmcrypt/libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm
fi
yum -y install libmcrypt-2.5.7-1.2.el6.rf.x86_64.rpm

echo '================================================================================'
echo '			Installing fping'
echo '================================================================================'
if [ -f "/vshare/repo/check_mk/server/fping-3.10-1.el6.rf.x86_64.rpm" ]; then
	cp /vshare/repo/check_mk/server/fping-3.10-1.el6.rf.x86_64.rpm .
else
	wget  http://pkgs.repoforge.org/fping/fping-3.10-1.el6.rf.x86_64.rpm
fi
yum -y install fping-3.10-1.el6.rf.x86_64.rpm

echo '================================================================================'
echo '			Installing radiusclient'
echo '================================================================================'
if [ -f "/vshare/repo/check_mk/server/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm" ]; then
	cp /vshare/repo/check_mk/server/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm .
else
	wget  http://pkgs.repoforge.org/radiusclient-ng/radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm
fi
yum -y install radiusclient-ng-0.5.6-5.el6.rf.x86_64.rpm

echo '================================================================================'
echo '			Installing OMD'
echo '================================================================================'
if [ -f "/vshare/repo/check_mk/server/omd-1.20.rhel7.x86_64.rpm" ]; then
	cp /vshare/repo/check_mk/server/omd-1.20.rhel7.x86_64.rpm .
else
	wget http://files.omdistro.org/releases/centos_rhel/omd-1.20.rhel7.x86_64.rpm
fi
yum -y install omd-1.20.rhel7.x86_64.rpm

echo '================================================================================'
echo '			Installing Check MK agent'
echo '================================================================================'
if [ -f "/vshare/repo/check_mk/agent/check_mk-agent-1.2.4p5-1.noarch.rpm" ]; then
	cp /vshare/repo/check_mk/agent/check_mk-agent-1.2.4p5-1.noarch.rpm .
else
	wget http://mathias-kettner.de/download/check_mk-agent-1.2.4p5-1.noarch.rpm
fi
yum -y install check_mk-agent-1.2.4p5-1.noarch.rpm

echo '================================================================================'
echo '			Starting OMD server'
echo '================================================================================'
omd create monitoring
omd start monitoring

echo '================================================================================'
echo '		Allowing httpd processes to make outbound connection'
echo '================================================================================'
#SELinux on RHEL CentOS by default ships so that httpd processes cannot initiate outbound connections
cd /usr/sbin
setsebool -P httpd_can_network_connect 1

echo '================================================================================'
echo '			Configuring check mk agent'
echo '================================================================================'
chmod 777 /etc/xinetd.d
cp /mnt/gluster/repo/omd-setup/server/config/check_mk  /etc/xinetd.d/check_mk
service xinetd restart

echo '================================================================================'
echo '			Change configuration for SNMP'
echo '================================================================================'
chmod 777 /etc/snmp/snmpd.conf
cp -R /mnt/gluster/repo/omd-setup/server/config/snmpd.conf /etc/snmp/snmpd.conf
service snmpd restart

echo '================================================================================'
echo '			Change configuration for OMD server'
echo '================================================================================'
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

echo '================================================================================'
echo '			Restarting OMD server'
echo '================================================================================'
runuser -l monitoring -c 'cmk --reload'
su - monitoring
omd restart
exit
