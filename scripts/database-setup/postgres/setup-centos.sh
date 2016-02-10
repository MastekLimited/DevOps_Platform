#!/bin/bash
#This script will install database on the vm

echo ...........................Installing Postgres SQL:START...........................

echo "...........................Local install postgres RPM..........................."

if [ -f "/vshare/base-images/database/postgres/pgdg-centos94-9.4-1.noarch.rpm" ]; then
	yum install -y /vshare/base-images/database/postgres/pgdg-centos94-9.4-1.noarch.rpm
else
	yum install -y http://yum.postgresql.org/9.4/redhat/rhel-6-x86_64/pgdg-centos94-9.4-1.noarch.rpm
fi

echo "...........................Local install postgres..........................."

if [ -f "/vshare/base-images/database/postgres/postgresql94-server-9.4.5-1PGDG.rhel5.x86_64.rpm" ]; then
	yum install -y /vshare/base-images/database/postgres/postgresql94-server-9.4.5-1PGDG.rhel5.x86_64.rpm
else
	yum install -y postgresql94-server
fi

echo "...........................Initializing postgres database..........................."
/usr/pgsql-9.4/bin/postgresql94-setup initdb

echo "...........................Changing connectivity conifiguration..........................."
mkdir /var/lib/pgsql/9.4/data
cp /mnt/gluster/repo/database-setup/postgres/config/pg_hba.conf /var/lib/pgsql/9.4/data
cp /mnt/gluster/repo/database-setup/postgres/config/postgresql.conf /var/lib/pgsql/9.4/data

service postgresql-9.4 start
sleep 10

echo -e "Password1\nPassword1" | (passwd --stdin postgres)
echo "postgres ALL=(ALL)	ALL" >> /etc/sudoers

