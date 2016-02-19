#/bin/bash
networkScriptFileName="eth1"
if [ -z "$1" ]
  then
	echo "No argument supplied"
    echo "Please provide the environment type i.e. local,dev,test etc."
	exit
fi

if [ -z "$2" ]
  then
    echo "Please specify which centos image to be used to setup the VMs i.e. 'use-local-image' or 'use-cloud-image'"
    echo "If you pass this parameter as 'use-image-from-local-machine' then please make sure you have {path-to-platform-code}/repo/centos-7.x-64bit-puppet.3.x-vbox.5.0.0.1.box file on your local machine."
	exit
fi

if [ "$1" != "local" ]
  then
	if [ -z "$3" ]
	then
	echo "Please provide one more argument as ethernet file name as  i.e. eth1 OR enp3s0 OR enp4s0 etc."
	exit
	else
	networkScriptFileName=$3
	fi
fi
envfilename=env_inventory/$1/host_address.txt
envname=$1
imagefile=$2
echo "Start building  environment for .....$envname"
echo "ethernet file name .....$networkScriptFileName"

startCleanVirtualBox() {
	local VAGARANTFILE_PATH=$1
	local serverip=$2
	cd $VAGARANTFILE_PATH;
	echo "Destroying the VM if already exists from location - "$VAGARANTFILE_PATH
	vagrant destroy -f;
	echo "Starting the VM at this location - "$VAGARANTFILE_PATH
	echo "Using the centos image configuration: "$imagefile
	SYS_IP=$serverip  ENV_TYPE=$envname IMAGE_FILE=$imagefile NET_CONFIG_FILE=$networkScriptFileName  vagrant up
	exitFromVagrantExecutedDirectory $VAGARANTFILE_PATH
}

exitFromVagrantExecutedDirectory() {
	local path=$1
	count=$(echo $path | grep -ao '/' | wc -l)
	EXIT_STRING="cd ";
	for (( c=1; c <= $count; c++ ))
	do
	   EXIT_STRING=$EXIT_STRING"../"
	done
	$EXIT_STRING
}

printExecutionTime() {
	local executionTime=$1;
	local message=$2
	minutes=`expr $((executionTime/60))`;
	seconds=`expr $((executionTime%60))`;
	echo $message $minutes m $seconds s.
}

all_vms_start_time=`date +%s`

declare -A IPMAP
while read -r line
do
    name=$line
	var=$(echo $name | awk -F"=" '{print $1,$2}')
	set -- $var
	IPMAP[$1]=$2
done < $envfilename

echo "Reset platform scripts before executing the build..."
git reset --hard

echo "Convert all the script files so that they can be executed through unix system..."
find . -name \*.sh -exec dos2unix {} \;

echo "JENKINS SERVER :-  ${IPMAP["JENKINS_HOST_IP"]}"
echo "ELK SERVER :- ${IPMAP["ELK_HOST_IP"]}"
echo "POSTGRES SERVER :- ${IPMAP["POSTGRES_HOST_IP"]}"
echo "OMD SERVER :- ${IPMAP["OMD_HOST_IP"]}"
echo "DOCKER SERVER :- ${IPMAP["DOCKER_HOST_IP"]}"
echo "NF SERVER :- ${IPMAP["NF_HOST_IP"]}"

grep -r '&&JENKINS_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&JENKINS_HOST_IP&&#'${IPMAP["JENKINS_HOST_IP"]}'#g'
grep -r '&&ELK_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&ELK_HOST_IP&&#'${IPMAP["ELK_HOST_IP"]}'#g'
grep -r '&&POSTGRES_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&POSTGRES_HOST_IP&&#'${IPMAP["POSTGRES_HOST_IP"]}'#g'
grep -r '&&OMD_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&OMD_HOST_IP&&#'${IPMAP["OMD_HOST_IP"]}'#g'
grep -r '&&DOCKER_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&DOCKER_HOST_IP&&#'${IPMAP["DOCKER_HOST_IP"]}'#g'
grep -r '&&NF_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&NF_HOST_IP&&#'${IPMAP["NF_HOST_IP"]}'#g'

find . -name "*.sh" -exec chmod +x {} \;

postgres_vm_start_time=`date +%s`
startCleanVirtualBox vagrant/postgres/ ${IPMAP["POSTGRES_HOST_IP"]}
postgres_vm_end_time=`date +%s`

elk_vm_start_time=`date +%s`
startCleanVirtualBox vagrant/elk/  ${IPMAP["ELK_HOST_IP"]}
elk_vm_end_time=`date +%s`

omd_vm_start_time=`date +%s`
startCleanVirtualBox vagrant/omd/  ${IPMAP["OMD_HOST_IP"]}
omd_vm_end_time=`date +%s`

jenkins_vm_start_time=`date +%s`
startCleanVirtualBox vagrant/jenkins_sonar/ ${IPMAP["JENKINS_HOST_IP"]}
jenkins_vm_end_time=`date +%s`

docker_vm_start_time=`date +%s`
startCleanVirtualBox vagrant/docker/ ${IPMAP["DOCKER_HOST_IP"]}
docker_vm_end_time=`date +%s`

#nf_vm_start_time=`date +%s`
#startCleanVirtualBox vagrant/nf_test_server/ ${IPMAP["NF_HOST_IP"]}
#nf_vm_end_time=`date +%s`

#git reset --hard
#git clean -f

all_vms_end_time=`date +%s`

echo "----------------------------------------------------------------------------------------------"
echo "-----------------------------DevOps Suite Execution Summary-----------------------------------"
echo "----------------------------------------------------------------------------------------------"
printExecutionTime `expr $postgres_vm_end_time - $postgres_vm_start_time` "postgres setup time"
printExecutionTime `expr $elk_vm_end_time - $elk_vm_start_time` "elk setup time"
printExecutionTime `expr $omd_vm_end_time - $omd_vm_start_time` "omd setup time"
printExecutionTime `expr $jenkins_vm_end_time - $jenkins_vm_start_time` "jenkins setup time"
printExecutionTime `expr $docker_vm_end_time - $docker_vm_start_time` "docker setup time"
#printExecutionTime `expr $nf_vm_end_time - $nf_vm_start_time` "nf server setup time"
printExecutionTime `expr $all_vms_end_time - $all_vms_start_time` "Total execution time"
echo "----------------------------------------------------------------------------------------------"
