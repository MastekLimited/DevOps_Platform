#!/bin/bash
sudo yum install git
sudo mkdir /usr/local/git
cd /usr/local/git
sudo git clone http://23.251.129.107:8085/git/DevOps_Platform.git
git checkout develop
