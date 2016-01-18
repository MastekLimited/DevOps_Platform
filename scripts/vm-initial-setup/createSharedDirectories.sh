#!/bin/bash
#This script will create a directory in /mnt on the vm

echo "Creating gluster directory"
mkdir /mnt/gluster

echo "Creating repo directory"
mkdir /mnt/gluster/repo

echo "Modifying the folder permissions"
chmod 777 /mnt/gluster
chmod 777 /mnt/gluster/*
