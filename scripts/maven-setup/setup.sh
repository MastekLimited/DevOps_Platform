#!/bin/bash
mkdir /opt/maven
chmod 777 /opt/maven
cd /opt/maven

echo '================================================================================'
echo '			Installing Maven'
echo '================================================================================'
if [ -f "/vshare/repo/jenkins/apache-maven-3.0.5-bin.tar.gz" ]; then
	cp /vshare/repo/jenkins/apache-maven-3.0.5-bin.tar.gz .
else
	wget http://mirror.cc.columbia.edu/pub/software/apache/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
fi

chmod 777 apache-maven-3.0.5-bin.tar.gz
tar xzf apache-maven-3.0.5-bin.tar.gz
mv apache-maven-3.0.5 maven
chmod -R 777 maven
cp /mnt/gluster/repo/maven-setup/config/maven.sh /etc/profile.d
