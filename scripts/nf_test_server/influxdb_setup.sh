#!/bin/bash


# Install influxdb 0.9.5.1-1.x86_64 RPM

echo ...........................Installing influxdb 0.9.5 RPM............................
sudo wget http://influxdb.s3.amazonaws.com/influxdb-0.9.5.1-1.x86_64.rpm
sudo yum install -y influxdb-0.9.5.1-1.x86_64.rpm
sudo sudo cp /mnt/gluster/repo/influxdb.conf /etc/influxdb/
sudo systemctl start influxdb.service

# Install influxdb 0.8.9-1.x86_64 RPM
#echo ...........................Installing influxdb-0.9.5 RPM............................
#sudo wget http://get.influxdb.org.s3.amazonaws.com/influxdb-0.8.9-1.x86_64.rpm
#sudo yum localinstall -y influxdb-0.8.9-1.x86_64.rpm
#sudo stat /usr/lib64/libbz2.so.1
#sudo ln -s /usr/lib64/libbz2.so.1.0.6 /usr/lib64/libbz2.so.1.0
#sudo ldd /usr/bin/influxdb
#sudo /etc/init.d/influxdb start


sleep 10 
sudo curl -G 'http://localhost:8086/query' --data-urlencode "q=CREATE DATABASE jmeter"
sudo curl -G 'http://localhost:8086/query' --data-urlencode "q=CREATE DATABASE collectd"
