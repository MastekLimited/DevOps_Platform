#!/bin/sh
echo "Stopping systemctl firewalld"
sudo systemctl stop firewalld

echo "Disabling systemctl firewalld"
sudo systemctl disable firewalld