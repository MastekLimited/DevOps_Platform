#!/bin/bash
host=$1
buildNumber=$2

cd /mnt/gluster/repo/
echo ''
echo '================================================================================'
echo '			Loading the raspberry pi setup scripts'
echo '================================================================================'

if [ -d "/mnt/gluster/repo/MediPi" ]; then
	cd /mnt/gluster/repo/MediPi
	git reset --hard
	git pull
else
	git clone http://jenkins:jenkins@104.155.90.134:8085/git/MediPi.git
	cd /mnt/gluster/repo/MediPi
	git fetch -a
	git checkout KK-MediPi-remove-python
fi

sudo chmod -R 777 /mnt/gluster/repo/MediPi

echo '================================================================================'
echo '		Copying the installables from the mount directory if exists'
echo '================================================================================'
if [ ! -f "/mnt/gluster/repo/MediPi/raspberry-pi-setup/installables/openjfx-8u60-sdk-overlay-linux-armv6hf.zip" ]; then
	cp /mnt/gluster/repo/installables/openjfx-8u60-sdk-overlay-linux-armv6hf.zip /mnt/gluster/repo/MediPi/raspberry-pi-setup/installables/
fi

if [ ! -f "/mnt/gluster/repo/MediPi/raspberry-pi-setup/installables/librxtx-java_2.2pre2-13_armhf.deb" ]; then
	cp /mnt/gluster/repo/installables/librxtx-java_2.2pre2-13_armhf.deb /mnt/gluster/repo/MediPi/raspberry-pi-setup/installables/
fi

#find . -name \*.sh -exec dos2unix {} \;

echo '================================================================================'
echo '		Removing the known hosts list for the existing IPs'
echo '================================================================================'
sudo sed -i '/^'$host'/d' /home/vagrant/.ssh/known_hosts

echo '================================================================================'
echo '	Removing and Reinstalling the MediPi execution environment on Raspberry Pi'
echo '================================================================================'
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "rm -rf /home/pi/MediPi"
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "mkdir /home/pi/MediPi"
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "chmod -R 777 /home/pi/MediPi"

echo '================================================================================'
echo '			Copying the required setup to Raspberry Pi'
echo '================================================================================'
sshpass -p "raspberry" scp -r /mnt/gluster/repo/MediPi/raspberry-pi-setup pi@$host:/home/pi/MediPi/
sshpass -p "raspberry" scp -r /var/lib/jenkins/jobs/MediPi/workspace/config pi@$host:/home/pi/MediPi/
sshpass -p "raspberry" scp /var/lib/jenkins/jobs/MediPi/workspace/MediPi/target/MediPi.jar pi@$host:/home/pi/MediPi/

echo '================================================================================'
echo '			Changing the file structure for the execution'
echo '================================================================================'
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "mv /home/pi/MediPi/raspberry-pi-setup/README.txt /home/pi/MediPi/"
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "mv /home/pi/MediPi/raspberry-pi-setup/run-medi-pi.sh /home/pi/MediPi/"
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "chmod -R 777 /home/pi/MediPi"

echo '================================================================================'
echo '			Running the raspberry pi setup script'
echo '================================================================================'
/mnt/gluster/repo/jenkins-setup/medi-pi-deployment-job-config/scp-command-execution.sh $host "sudo /home/pi/MediPi/raspberry-pi-setup/setup.sh"