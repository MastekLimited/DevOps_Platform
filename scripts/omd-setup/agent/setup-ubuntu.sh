#!/bin/bash

INSTALL_DIR=/opt/omd

mkdir -p $INSTALL_DIR

cd $INSTALL_DIR

apt-get update

apt-get upgrade

wget http://mathias-kettner.de/download/check-mk-agent_1.2.4p5-2_all.deb

dpkg -i check-mk-agent_1.2.4p5-2_all.deb

chmod 777 /etc/xinetd.d

cp /mnt/gluster/repo/omd-setup/agent/config/check_mk  /etc/xinetd.d/check_mk

service xinetd restart

exit