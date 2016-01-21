#!/bin/bash

cd /opt
# Install Grafana
echo ........................... Installing Grafana Package ...........................

yum install -y https://grafanarel.s3.amazonaws.com/builds/grafana-2.5.0-1.x86_64.rpm

echo ........................... Updating Grafana Public Directory ...........................
#sudo rm -rf  /usr/share/grafana/public/
#sudo sudo cp -r /mnt/gluster/repo/public /usr/share/grafana/
cp /mnt/gluster/repo/config.js /usr/share/grafana/public/app/components/config.js
cp /usr/share/grafana/public/dashboards/scripted.js /usr/share/grafana/public/dashboards/getnfdash.js
sleep 10

echo ........................... Starting Grafana Services ...........................
systemctl daemon-reload
systemctl start grafana-server
systemctl status grafana-server

echo ........................... Grafana Installation Complete ...........................