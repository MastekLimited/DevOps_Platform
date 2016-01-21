#!/bin/bash

#sudo service influxdb start
systemctl stop collectd.service
systemctl stop influxdb.service

sleep 10
systemctl restart collectd.service
systemctl restart influxdb.service


service grafana-server start
/usr/local/bin/collectd-server start


sleep 10
systemctl enable collectd.service
systemctl enable influxdb.service
systemctl enable grafana-server

#sudo service postgresql-9.4 restart

#sleep 10
#sudo java -jar /mnt/gluster/repo/devops/employeeService.jar --spring.config.location=file:/mnt/gluster/repo/devops/services.properties,classpath:/application.properties &
#sleep 10
#sudo java -jar /mnt/gluster/repo/devops/projectService.jar --spring.config.location=file:/mnt/gluster/repo/devops/services.properties,classpath:/application.properties &
#sleep 10
#sudo java -jar /mnt/gluster/repo/devops/organisationWebApp.war --spring.config.location=file:/mnt/gluster/repo/devops/services.properties,classpath:/application.properties &
