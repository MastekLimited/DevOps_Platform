#!/bin/bash
sudo su - jenkins
echo 'jenkins' > /var/lib/jenkins/pwd
chmod 600 pwd
exit
