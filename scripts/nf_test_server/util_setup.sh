#!/bin/bash



# Update
echo ...........................Update ............................
yum update -y

## Install Xorg
echo ...........................Installing Xorg ............................
yum install -y openssh-server xorg-x11-xauth xorg-x11-apps

## Install or Update Firefox 30 in Fedora 19/18/17/16 ##
echo ...........................Installing Firefox ............................
yum install -y firefox


## Install Network Manager
echo ...........................Installing Network Manager Util............................
yum install -y NetworkManager-tui
systemctl start NetworkManager.service
systemctl enable NetworkManager.service
systemctl status NetworkManager.service
systemctl stop firewalld
