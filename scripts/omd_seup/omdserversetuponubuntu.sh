#!/bin/bash

INSTALL_DIR=/opt/omd

mkdir -p $INSTALL_DIR

cd $INSTALL_DIR

apt-get update 

apt-get upgrade

wget http://files.omdistro.org/releases/debian_ubuntu/omd-1.20.trusty.amd64.deb

dpkg -i omd-1.20.trusty.amd64.deb

apt-get -f install

omd create monitoring

omd start monitoring

wget http://mathias-kettner.de/download/check-mk-agent_1.2.4p5-2_all.deb

dpkg -i check-mk-agent_1.2.4p5-2_all.deb

chmod 777 /etc/xinetd.d

cp /mnt/gluster/repo/check_mk_forserver  /etc/xinetd.d/check_mk
service xinetd restart

exit