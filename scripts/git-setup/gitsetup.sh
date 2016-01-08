#!/bin/bash


sudo adduser gitblit -p gitblit

sudo mkdir /opt/gitblit
sudo chown gitblit:gitblit /opt/gitblit
cd /opt/gitblit
sudo wget http://gitblit.googlecode.com/files/gitblit-1.3.2.zip
sudo chmod 777 gitblit-1.3.2.zip
sudo unzip gitblit-1.3.2.zip
sudo chmod 777 gitblit.jar
sudo cp /mnt/gluster/repo/gitblit.properties /opt/gitblit/data/
sudo chmod 777 gitblit.jar
sudo java -jar gitblit.jar &
