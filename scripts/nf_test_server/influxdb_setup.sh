#!/bin/bash


# Install influxdb 0.9.5.1-1.x86_64 RPM
cd /opt
echo ...........................Installing influxdb 0.9.5 RPM............................
wget http://influxdb.s3.amazonaws.com/influxdb-0.9.5.1-1.x86_64.rpm
yum install -y influxdb-0.9.5.1-1.x86_64.rpm
cp /mnt/gluster/repo/influxdb.conf /etc/influxdb/
systemctl start influxdb.service
sleep 10 
curl -G 'http://localhost:8086/query' --data-urlencode "q=CREATE DATABASE jmeter"
curl -G 'http://localhost:8086/query' --data-urlencode "q=CREATE DATABASE collectd"
