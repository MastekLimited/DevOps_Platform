#!/bin/bash
yum install -y httpd
service httpd restart
chkconfig httpd on
yum install -y subversion mod_dav_svn
touch /etc/httpd/conf.d/subversion.conf
chmod 777 /etc/httpd/conf.d/subversion.conf
cp /mnt/gluster/repo/subversion.conf /etc/httpd/conf.d/subversion.conf
mkdir -p /opt/svn
cd /opt/svn
svnadmin create testrepo1
svnadmin create testrepo2
chown -R apache.apache /opt/svn/*
touch /etc/svn-users
htpasswd -b /etc/svn-users user1 Password1
service httpd restart









