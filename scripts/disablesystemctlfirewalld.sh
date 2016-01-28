#!/bin/sh
echo "Going to do stop systemct firewalld stop"
sudo systemctl stop firewalld
echo "Going to do stop systemct firewalld disable"
sudo systemctl disable firewalld