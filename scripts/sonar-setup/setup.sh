#!/bin/bash

#adduser sonar -p sonar
#su sonar
mkdir /opt/sonar
chmod 777 /opt/sonar
cd /opt/sonar

echo '================================================================================'
echo '			Installing Unzip'
echo '================================================================================'
if [ -f "/vshare/repo/misc/unzip-6.0-13.el7.x86_64.rpm" ]; then
	yum install -y /vshare/repo/misc/unzip-6.0-13.el7.x86_64.rpm
else
	yum install -y unzip
fi

echo '================================================================================'
echo '			Installing Sonarqube: START'
echo '================================================================================'
if [ -f "/vshare/repo/sonarqube/sonarqube-5.3.zip" ]; then
	cp /vshare/repo/sonarqube/sonarqube-5.3.zip .
else
	wget http://dist.sonar.codehaus.org/sonarqube-5.3.zip
fi

echo '================================================================================'
echo '			Installing Sonarqube: END'
echo '================================================================================'

chmod 777 sonarqube-5.3.zip
unzip sonarqube-5.3.zip
chmod 777 sonarqube-5.3
cd sonarqube-5.3

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
