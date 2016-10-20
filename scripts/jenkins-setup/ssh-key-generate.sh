#!/bin/bash
cd /home/vagrant/.ssh/
pwd
ls -lrt
echo -e  'y\n'|ssh-keygen -q -t rsa -N '' -f /home/vagrant/.ssh/id_rsa;
pwd
sleep 5
if [ -f "/home/vagrant/.ssh/id_rsa.pub" ]; then
	echo ...........................Key Found............................
	chown vagrant:vagrant *
	cp id_rsa.pub /vshare/certificates/generated/jenkins/
else
	echo ...........................Key file does not exist............................
fi
exit