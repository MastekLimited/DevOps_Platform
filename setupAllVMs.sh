#/bin/bash
startCleanVirtualBox() {	
local VAGARANTFILE_PATH=$1
cd $VAGARANTFILE_PATH;
echo "destroying the VM if already exists from location - "$VAGARANTFILE_PATH
vagrant destroy -f;
echo "Starting the VM at this location - "$VAGARANTFILE_PATH
vagrant up;
exitingFromThePassedFolder $VAGARANTFILE_PATH 
}

exitingFromThePassedFolder()
{
local path=$1
count=$(echo $path | grep -ao '/' | wc -l)
EXIT_STRING="cd ";
for (( c=1; c <= $count; c++ ))
do
   EXIT_STRING=$EXIT_STRING"../"
done
$EXIT_STRING
}

startCleanVirtualBox vagrant/jenkins_sonar/ #192.168.51.104
startCleanVirtualBox vagrant/elk/ #192.168.51.102
startCleanVirtualBox vagrant/postgres/ #192.168.51.106
startCleanVirtualBox vagrant/omd/  #192.168.51.105
startCleanVirtualBox vagrant/docker/ #192.168.29.110
