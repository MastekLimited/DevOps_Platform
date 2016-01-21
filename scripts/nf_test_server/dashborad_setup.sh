#!/bin/bash
cd /mnt/gluter/repo
./install.sh /usr/share/grafana
echo "Install finished."

echo "Re-starting grafana server."
service grafana-server start
