#!/bin/bash


# Install Grafana
echo ........................... Installing Grafana Package ...........................

sudo yum install -y https://grafanarel.s3.amazonaws.com/builds/grafana-2.5.0-1.x86_64.rpm

echo ........................... Updating Grafana Public Directory ...........................
#sudo rm -rf  /usr/share/grafana/public/
#sudo sudo cp -r /mnt/gluster/repo/public /usr/share/grafana/
sudo cp /mnt/gluster/repo/config.js /usr/share/grafana/public/app/components/config.js
sudo cp /usr/share/grafana/public/dashboards/scripted.js /usr/share/grafana/public/dashboards/getnfdash.js
sleep 10

echo ........................... Starting Grafana Services ...........................
sudo systemctl daemon-reload
sudo systemctl start grafana-server
sudo systemctl status grafana-server

echo ........................... Grafana Installation Complete ...........................