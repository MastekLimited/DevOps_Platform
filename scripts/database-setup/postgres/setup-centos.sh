#!/bin/bash
#This script will install database on the vm

echo '================================================================================'
echo '			Installing pgdg-centos'
echo '================================================================================'

if [ -f "/vshare/repo/database/postgres/pgdg-centos94-9.4-1.noarch.rpm" ]; then
	yum install -y /vshare/repo/database/postgres/pgdg-centos94-9.4-1.noarch.rpm
else
	yum install -y http://yum.postgresql.org/9.4/redhat/rhel-6-x86_64/pgdg-centos94-9.4-1.noarch.rpm
fi

echo '================================================================================'
echo '			Installing postgres'
echo '================================================================================'

if [ -f "/vshare/repo/database/postgres/postgresql94-libs-9.4.5-2PGDG.rhel7.x86_64.rpm" ] && [ -f "/vshare/repo/database/postgres/postgresql94-9.4.5-2PGDG.rhel7.x86_64.rpm" ] && [ -f "/vshare/repo/database/postgres/postgresql94-server-9.4.5-2PGDG.rhel7.x86_64.rpm" ]; then
	yum install -y /vshare/repo/database/postgres/postgresql94-libs-9.4.5-2PGDG.rhel7.x86_64.rpm
	yum install -y /vshare/repo/database/postgres/postgresql94-9.4.5-2PGDG.rhel7.x86_64.rpm
	yum install -y /vshare/repo/database/postgres/postgresql94-server-9.4.5-2PGDG.rhel7.x86_64.rpm
else
	yum install -y postgresql94-server
fi

echo '...........................Initializing postgres database...........................'
/usr/pgsql-9.4/bin/postgresql94-setup initdb

echo '...........................Changing connectivity conifiguration...........................'
mkdir /var/lib/pgsql/9.4/data
cp /mnt/gluster/repo/database-setup/postgres/config/pg_hba.conf /var/lib/pgsql/9.4/data
cp /mnt/gluster/repo/database-setup/postgres/config/postgresql.conf /var/lib/pgsql/9.4/data

systemctl enable postgresql-9.4.service
systemctl start postgresql-9.4.service

sleep 10

echo -e "Password1\nPassword1" | (passwd --stdin postgres)
echo "postgres ALL=(ALL)	ALL" >> /etc/sudoers

