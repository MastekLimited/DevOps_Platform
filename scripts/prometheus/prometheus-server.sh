#!/bin/sh
INSTALL_DIR=/opt/prometheus
DOWNLOAD_DIR=/opt/prometheus/Download
NODE_EXPORTER_DIR=/opt/prometheus/node_exporter
SHARE_DIR=/mnt/gluster/repo
mkdir -p $INSTALL_DIR
mkdir -p $DOWNLOAD_DIR
cd $INSTALL_DIR
mkdir -p $NODE_EXPORTER_DIR
cd $DOWNLOAD_DIR
echo "............... Downloading packages............"
curl -LO "https://github.com/prometheus/prometheus/releases/download/0.16.0/prometheus-0.16.0.linux-amd64.tar.gz"
curl -LO "https://github.com/prometheus/node_exporter/releases/download/0.11.0/node_exporter-0.11.0.linux-amd64.tar.gz"
cd $INSTALL_DIR
tar -xvzf $DOWNLOAD_DIR/prometheus-0.16.0.linux-amd64.tar.gz
cd $NODE_EXPORTER_DIR
pwd
tar -xvzf $DOWNLOAD_DIR/node_exporter-0.11.0.linux-amd64.tar.gz
cp -R $SHARE_DIR/node_exporter.service /etc/systemd/system/node_exporter.service
cp -R $SHARE_DIR/prometheus.yml $INSTALL_DIR/prometheus-0.16.0.linux-amd64/
echo "............... reloading daemon............"
systemctl daemon-reload
echo "............... enabling node_exporter ............"
systemctl enable node_exporter.service
echo "............... Start node_exporter ............"
systemctl start node_exporter.service
cd $INSTALL_DIR/prometheus-0.16.0.linux-amd64/
pwd
echo "...................Start the Prometheus server as a background process.........................."
nohup ./prometheus > prometheus.log 2>&1 &

