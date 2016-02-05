#/bin/bash

printExecutionTime() {
	local executionTime=$1;
	local message=$2
	minutes=`expr $((executionTime/60))`;
	seconds=`expr $((executionTime%60))`;
	echo $message $minutes m $seconds s.
}

jenkins_vm_start_time=`date +%s`
sleep 2
jenkins_vm_end_time=`date +%s`

elk_vm_start_time=`date +%s`
sleep 5
elk_vm_end_time=`date +%s`

postgres_vm_start_time=`date +%s`

postgres_vm_end_time=`date +%s`

omd_vm_start_time=`date +%s`

omd_vm_end_time=`date +%s`

docker_vm_start_time=`date +%s`

docker_vm_end_time=`date +%s`

nf_vm_start_time=`date +%s`

nf_vm_end_time=`date +%s`

echo "----------------------------------------------------------------------------------------------"
echo "-----------------------------DevOps Suite Execution Summary-----------------------------------"
echo "----------------------------------------------------------------------------------------------"
printExecutionTime `expr $jenkins_vm_end_time - $jenkins_vm_start_time` "jenkins setup time"
printExecutionTime `expr $elk_vm_end_time - $elk_vm_start_time` "elk setup time"
printExecutionTime `expr $postgres_vm_end_time - $postgres_vm_start_time` "postgres setup time"
printExecutionTime `expr $omd_vm_end_time - $omd_vm_start_time` "omd setup time"
printExecutionTime `expr $docker_vm_end_time - $docker_vm_start_time` "docker setup time"
printExecutionTime `expr $nf_vm_end_time - $nf_vm_start_time` "nf server setup time"
#printExecutionTime `expr $all_vms_end_time - $all_vms_start_time` "Total execution time"
echo "----------------------------------------------------------------------------------------------"
