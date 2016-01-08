#!/bin/bash

#adduser sonar -p sonar
#su sonar
mkdir /opt/sonar
chmod 777 /opt/sonar
cd /opt/sonar
yum install -y unzip
wget http://dist.sonar.codehaus.org/sonarqube-5.1.zip
chmod 777 sonarqube-5.1.zip
unzip sonarqube-5.1.zip
chmod 777 sonarqube-5.1
cd sonarqube-5.1
cp /mnt/gluster/repo/sonar.properties conf/
chmod -R 777 conf/*
cp /mnt/gluster/repo/sonar-tanaguru-latest.jar extensions/plugins/
chmod -R 777 extensions/plugins/*
cp /mnt/gluster/repo/sonar.sh /etc/init.d/sonar
chkconfig --del sonar
chmod 755 /etc/init.d/sonar
chkconfig --add sonar  
service sonar start
