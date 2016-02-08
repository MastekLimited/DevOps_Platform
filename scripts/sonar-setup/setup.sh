#!/bin/bash

#adduser sonar -p sonar
#su sonar
mkdir /opt/sonar
chmod 777 /opt/sonar
cd /opt/sonar

echo ...........................Installing Unzip...........................
if [ -f "/vshare/base-images/jenkins/apache-maven-3.0.5-bin.tar.gz" ]; then
	yum install -y /vshare/base-images/misc/unzip-6.0-13.el7.x86_64.rpm
else
	yum install -y unzip
fi

echo ...........................Installing Sonarqube...........................
if [ -f "/vshare/base-images/sonarqube/sonarqube-5.1.zip" ]; then
	cp /vshare/base-images/sonarqube/sonarqube-5.1.zip .
else
	wget http://dist.sonar.codehaus.org/sonarqube-5.1.zip
fi

chmod 777 sonarqube-5.1.zip
unzip sonarqube-5.1.zip
chmod 777 sonarqube-5.1
cd sonarqube-5.1

echo ...........................Changing Sonarqube configuration...........................
cp /mnt/gluster/repo/sonar-setup/config/sonar.properties conf/
chmod -R 777 conf/*
cp /mnt/gluster/repo/sonar-setup/plugins/sonar-tanaguru-latest.jar extensions/plugins/
chmod -R 777 extensions/plugins/*
cp /mnt/gluster/repo/sonar-setup/config/sonar.sh /etc/init.d/sonar
chkconfig --del sonar
chmod 755 /etc/init.d/sonar
chkconfig --add sonar

echo ...........................Starting Sonarqube...........................
service sonar start
