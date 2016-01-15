#!/bin/bash 
#This a test scripts to accept the input from the user

echo "Please enter the user name:"
read username

echo "Please enter the UID for the user:"
read uid

echo "Please enter the GID for the user:"
read gid

echo "Creating an user with user name: $username, UID: $uid, GID:$gid"

sudo useradd -ou $uid -g $gid $username

echo "Created the user: $userame"

echo "Setting the password"

sudo passwd $username
