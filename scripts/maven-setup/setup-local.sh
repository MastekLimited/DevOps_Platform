#!/bin/bash
mkdir /opt/maven
chmod 777 /opt/maven
cd /opt/maven

cp /vshare/base-images/jenkins/apache-maven-3.0.5-bin.tar.gz .

chmod 777 apache-maven-3.0.5-bin.tar.gz
tar xzf apache-maven-3.0.5-bin.tar.gz
mv apache-maven-3.0.5 maven
chmod -R 777 maven
cp /mnt/gluster/repo/maven-setup/config/maven.sh /etc/profile.d


