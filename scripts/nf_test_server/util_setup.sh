#!/bin/bash



# Update
echo ...........................Update ............................
sudo yum update -y

## Install Xorg
echo ...........................Installing Xorg ............................
sudo yum install -y openssh-server xorg-x11-xauth xorg-x11-apps

## Install or Update Firefox 30 in Fedora 19/18/17/16 ##
echo ...........................Installing Firefox ............................
sudo yum install -y firefox


## Install Network Manager
echo ...........................Installing Network Manager Util............................
sudo yum install -y NetworkManager-tui
sudo systemctl start NetworkManager.service
sudo systemctl enable NetworkManager.service
sudo systemctl status NetworkManager.service
sudo systemctl stop firewalld


sudo rm collectd-5.4.0.tar.gz
sudo rm influxdb-0.9.5.1-1.x86_64.rpm
sudo rm ZAP_2.4.3_Linux.tar.gz