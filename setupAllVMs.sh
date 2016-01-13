#/bin/bash
#TO DO 
#received input env. name
#get file ips from env file
startCleanVirtualBox() {	
local VAGARANTFILE_PATH=$1
cd $VAGARANTFILE_PATH;
echo "destroying the VM if already exists from location - "$VAGARANTFILE_PATH
vagrant destroy -f;
echo "Starting the VM at this location - "$VAGARANTFILE_PATH
vagrant up;
#JENKINS_IP=$1 vagrant up
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

# Call startCleanVirtualBox vagrant/jenkins_sonar/ $JENKINS_IP

#And use ENV['MY_VAR'] in recipe.
#Add below line into vagrant file
 #config.vm.network  "public_network", bridge: 'eth1', ip: ENV['JENKINS_IP'] 
