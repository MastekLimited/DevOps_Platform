#/bin/bash
networkScriptFileName="eth1"
if [ -z "$1" ]
  then
	echo "No argument supplied"
    echo "Please provide the environment type i.e. local and dev"
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
	printf "\nPlease provide Ethernet File name as  i.e. eth1 OR enp3s0 OR enp4s0 etc.\n\n"
	exit
	else
	networkScriptFileName=$3
	fi
fi

if [ -z $4 ]
	then 
	printf "\nPlease specify type of VMs set to be created <DEV | OPS | DEVOPS>\n\n"
	exit
else
	dops=$4
fi

#Loop for checking if user wants to implement Digility Recommded CI template or not.
if [ -z "$5" ]
    then
    read -r -p "==> Do you want to setup CI server with Digility CI template (yes/no) : " ANSWER
    case $ANSWER in 
    	[yY] | [Yy][Ee][Ss] )
	  	    printf "\nAs per selected option following command will be executed : \n "
	  	    printf "\t bash setupAllVMs.sh <local|dev|test> <use-local-image|use-cloud-image> <ethernet file name> <DEV|OPS|DEVOPS> Digility_template\n\n"
	  	    printf "Starting DEV VMs setup with Digility CI template\n\n"
	  	    template="Digility_template"
	  	    ;;

  	    [nN] | [nN][oO] )
			printf "\nStarting DEV VMs setup with default CI template\n\n"
			;;
		"")
			printf "\nPlease enter yes/y or no/n \n\n"
			exit
	esac
fi

config_file=./parameters.txt
envfilename=env_inventory/$1/host_address.txt
envname=$1
imagefile=$2
printf "\nStart building  environment for ..... $envname\n"
printf "\nEthernet file name ..... $networkScriptFileName\n\n"


declare -A IPMAP
while read -r line
do
    name=$line
	var=$(echo $name | awk -F"=" '{print $1,$2}')
	set -- $var
	IPMAP[$1]=$2
done < $envfilename

declare -A PARAM
while read -r line
do
    name=$line
	var=$(echo $name | awk -F"=" '{print $1,$2}')
	set -- $var
	PARAM[$1]=$2
done < $config_file

# echo "Reset platform scripts before executing the build..."
# git reset --hard   

echo "Convert all the script files so that they can be executed through unix system..."
find . -name \*.sh -exec dos2unix {} \;

echo "JENKINS SERVER :-  ${IPMAP["JENKINS_HOST_IP"]}"
echo "POSTGRES SERVER :- ${IPMAP["POSTGRES_HOST_IP"]}"
echo "ELK SERVER :- ${IPMAP["ELK_HOST_IP"]}"
echo "OMD SERVER :- ${IPMAP["OMD_HOST_IP"]}"
echo "DOCKER SERVER :- ${IPMAP["DOCKER_HOST_IP"]}"

printf "Job Name : ${PARAM["JOBNAME"]} \nRepository : ${PARAM["REPOLINK"]} \nMail IDs : ${PARAM["MAIL-IDS"]}\n"

grep -r '&&JENKINS_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&JENKINS_HOST_IP&&#'${IPMAP["JENKINS_HOST_IP"]}'#g'
grep -r '&&ELK_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&ELK_HOST_IP&&#'${IPMAP["ELK_HOST_IP"]}'#g'
grep -r '&&OMD_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&OMD_HOST_IP&&#'${IPMAP["OMD_HOST_IP"]}'#g'
grep -r '&&POSTGRES_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&POSTGRES_HOST_IP&&#'${IPMAP["POSTGRES_HOST_IP"]}'#g'
grep -r '&&DOCKER_HOST_IP&&' -l --null $PWD/*/ | xargs -0 sed -i 's#&&DOCKER_HOST_IP&&#'${IPMAP["DOCKER_HOST_IP"]}'#g'

#Replacing place holders with custom project related values in XML files.
grep -r 'REPOLINK' -l --null $PWD/scripts/*jenkins* | xargs -0 sed -i 's#REPOLINK#'${PARAM["REPOLINK"]}'#g'
grep -r 'MAIL-IDS' -l --null $PWD/scripts/*jenkins* | xargs -0 sed -i 's#MAIL-IDS#'${PARAM["MAIL-IDS"]}'#g'
grep -r 'localhost' -l --null $PWD/scripts/*jenkins*/* | xargs -0 sed -i 's#localhost#'${IPMAP["JENKINS_HOST_IP"]}'#g'

startCleanVirtualBox() {
	local VAGARANTFILE_PATH=$1
	local serverip=$2
	cd $VAGARANTFILE_PATH;
	echo "Destroying the VM if already exists from location - "$VAGARANTFILE_PATH
	vagrant destroy -f;
	echo "Starting the VM at this location - "$VAGARANTFILE_PATH
	echo "Using the centos image configuration: "$imagefile
	if [[ -n $template ]]; then
		SYS_IP=$serverip  ENV_TYPE=$envname IMAGE_FILE=$imagefile NET_CONFIG_FILE=$networkScriptFileName JOB_NAME=${PARAM["JOBNAME"]} Digility_TEMPLATE=$template vagrant up
	else
		SYS_IP=$serverip  ENV_TYPE=$envname IMAGE_FILE=$imagefile NET_CONFIG_FILE=$networkScriptFileName JOB_NAME=${PARAM["JOBNAME"]} vagrant up
	fi
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


find . -name "*.sh" -exec chmod +x {} \;

if [ "$dops" == "DEV" ]; then 
    printf "\n\n******************************************"
    printf "\nStarting to setup DEV Part of DevOps Suite\n"
    printf "******************************************\n"
    printf "\n\n==========================================="
    printf "\nStarting to create Jenkins-Soanr VM.\n"
    printf "===========================================\n\n"
    jenkins_vm_start_time=`date +%s`
    startCleanVirtualBox vagrant/jenkins_sonar/ ${IPMAP["JENKINS_HOST_IP"]}
	jenkins_vm_end_time=`date +%s`

	all_vms_end_time=`date +%s`
	printf "\n\n----------------------------------------------------------------------------------------------"
	printf "\n-----------------------------DevOps Suite Execution Summary-----------------------------------"
	printf "\n----------------------------------------------------------------------------------------------\n"
	printExecutionTime `expr $jenkins_vm_end_time - $jenkins_vm_start_time` "==> Jenkins setup time :: "
	printExecutionTime `expr $all_vms_end_time - $all_vms_start_time` "==> Total execution time <== :: "
	printf "\n----------------------------------------------------------------------------------------------\n"


elif [ "$dops" == "OPS" ]; then
	printf "\n\n****************************************************"
    printf "\nStarting the OPS Part setup from using DevOps Suite\n"
    printf "********************************************************\n"

    printf "\n\n==========================================="
    printf "\nStarting to create ELK VM.\n"
    printf "===========================================\n\n"
	elk_vm_start_time=`date +%s`
	startCleanVirtualBox vagrant/elk/  ${IPMAP["ELK_HOST_IP"]}
	elk_vm_end_time=`date +%s`

    printf "\n\n==========================================="
    printf "\nStarting to create OMD VM.\n"
    printf "===========================================\n\n"
    omd_vm_start_time=`date +%s`
	startCleanVirtualBox vagrant/omd/  ${IPMAP["OMD_HOST_IP"]}
	omd_vm_end_time=`date +%s`
 
	all_vms_end_time=`date +%s`

	printf "\n\n----------------------------------------------------------------------------------------------"
	printf "\n-----------------------------DevOps Suite Execution Summary-----------------------------------"
	printf "\n----------------------------------------------------------------------------------------------\n"

	printExecutionTime `expr $elk_vm_end_time - $elk_vm_start_time` "==> ELK setup time"
	printExecutionTime `expr $omd_vm_end_time - $omd_vm_start_time` "==> OMD setup time"
	printExecutionTime `expr $all_vms_end_time - $all_vms_start_time` "==> Total execution time <== :: "
	printf "\n----------------------------------------------------------------------------------------------\n"

elif [ "$dops" == "DEVOPS" ]; then
	printf "\n\n******************************************"
    printf "\nStarting to setup entire DevOps Suite\n"
    printf "******************************************\n"
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

	all_vms_end_time=`date +%s`

	printf "\n\n----------------------------------------------------------------------------------------------"
	printf "\n-----------------------------DevOps Suite Execution Summary-----------------------------------"
	printf "\n----------------------------------------------------------------------------------------------\n"
	printExecutionTime `expr $postgres_vm_end_time - $postgres_vm_start_time` "==> Postgres setup time"
	printExecutionTime `expr $elk_vm_end_time - $elk_vm_start_time` "==> ELK setup time"
	printExecutionTime `expr $omd_vm_end_time - $omd_vm_start_time` "==> OMD setup time"
	printExecutionTime `expr $jenkins_vm_end_time - $jenkins_vm_start_time` "==> Jenkins setup time"
	printExecutionTime `expr $docker_vm_end_time - $docker_vm_start_time` "==> Docker setup time"
	printExecutionTime `expr $all_vms_end_time - $all_vms_start_time` "==> Total execution time  :: "
	printf "\n----------------------------------------------------------------------------------------------\n"
fi

#Replacing  project custom values with place holders in XML files.
grep -r "${PARAM["REPOLINK"]}" -l --null $PWD/scripts/*jenkins* | xargs -0 sed -i 's#'${PARAM["REPOLINK"]}'#REPOLINK#g'
grep -r "${PARAM["MAIL-IDS"]}"  -l --null $PWD/scripts/*jenkins* | xargs -0 sed -i 's#'${PARAM["MAIL-IDS"]}'#MAIL-IDS##g'
grep -r "${IPMAP["JENKINS_HOST_IP"]}" -l --null $PWD/scripts/*jenkins*/* | xargs -0 sed -i 's#'${IPMAP["JENKINS_HOST_IP"]}'#localhost#g'