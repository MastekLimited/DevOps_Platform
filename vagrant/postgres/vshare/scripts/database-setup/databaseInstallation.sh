#!/bin/bash
#This script will install database on the vm

echo "-------------Installing Postgres SQL:START-------------"

echo "*************Local install postgres RPM*************"
yum localinstall -y http://yum.postgresql.org/9.4/redhat/rhel-6-x86_64/pgdg-centos94-9.4-1.noarch.rpm

echo "*************Local install postgres*************"
yum install -y postgresql94-server

echo "*************Initializing postgres database*************"
/usr/pgsql-9.4/bin/postgresql94-setup initdb

echo "*************Changing connectivity conifiguration*************"
cp /mnt/gluster/repo/pg_hba.conf /var/lib/pgsql/9.4/data
cp /mnt/gluster/repo/postgresql.conf /var/lib/pgsql/9.4/data

service postgresql-9.4 start
sleep 10

echo -e "Password1\nPassword1" | (sudo passwd --stdin postgres)
echo "postgres ALL=(ALL)	ALL" >> /etc/sudoers

