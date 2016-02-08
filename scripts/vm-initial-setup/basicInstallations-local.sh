#!/bin/bash
#This script will install basic software on the vm

echo "Installing Net tools (ifconfig)"
yum install -y /vshare/base-images/misc/net-tools-2.0-0.17.20131004git.el7.x86_64.rpm

echo "Installing telnet"
yum install -y /vshare/base-images/misc/telnet-0.17-59.el7.x86_64.rpm


echo "Installing wget"
yum install -y /vshare/base-images/misc/wget-1.14-10.el7.x86_64.rpm
