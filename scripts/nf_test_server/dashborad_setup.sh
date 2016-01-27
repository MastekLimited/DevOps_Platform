#!/bin/bash
cd /mnt/gluster/repo/
./install.sh /usr/share/grafana
echo "Install finished."

echo "Re-Starting grafana server."
service grafana-server start

echo "Re-Starting collectd service."
systemctl restart collectd.service
