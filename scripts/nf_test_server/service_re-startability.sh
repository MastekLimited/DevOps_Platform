#!/bin/bash

#sudo service influxdb start
sudo systemctl stop collectd.service
sudo systemctl stop influxdb.service

sleep 10
sudo systemctl start collectd.service
sudo systemctl start influxdb.service


sudo service grafana-server start
sudo /usr/local/bin/collectd-server start


sleep 10
sudo systemctl enable collectd.service
sudo systemctl enable influxdb.service
sudo systemctl enable grafana-server

#sudo service postgresql-9.4 restart

#sleep 10
#sudo java -jar /mnt/gluster/repo/devops/employeeService.jar --spring.config.location=file:/mnt/gluster/repo/devops/services.properties,classpath:/application.properties &
#sleep 10
#sudo java -jar /mnt/gluster/repo/devops/projectService.jar --spring.config.location=file:/mnt/gluster/repo/devops/services.properties,classpath:/application.properties &
#sleep 10
#sudo java -jar /mnt/gluster/repo/devops/organisationWebApp.war --spring.config.location=file:/mnt/gluster/repo/devops/services.properties,classpath:/application.properties &

sudo systemctl stop firewalld
sudo systemctl disable firewalld