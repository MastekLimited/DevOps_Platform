#!/bin/bash

#sudo adduser sonar -p sonar
#su sonar
sudo yum install -y unzip
sudo mkdir /opt/sonar
sudo chmod 777 /opt/sonar
cd /opt/sonar
sudo wget http://dist.sonar.codehaus.org/sonarqube-5.1.zip
sudo chmod 777 sonarqube-5.1.zip
sudo unzip sonarqube-5.1.zip
sudo chmod 777 sonarqube-5.1
cd sonarqube-5.1
sudo cp /mnt/gluster/repo/sonar.properties conf/
sudo cp /mnt/gluster/repo/sonar.sh /etc/init.d/sonar
sudo chkconfig --del  sonar
sudo chmod 755 /etc/init.d/sonar
sudo chkconfig --add sonar
sudo service sonar start
