#!/bin/bash
# Grafana2 getdash scripted dashboard install script.

#sudo git clone --depth=1 https://github.com/anryko/grafana-influx-dashboard.git
#cd grafana-influx-dashboard
sudo ./install.sh /usr/share/grafana
echo "Install finished."

echo "Re-starting grafana server."
sudo service grafana-server start
