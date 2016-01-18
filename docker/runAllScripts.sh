#!/bin/sh
stopContainerByName() {
echo "Stoping all containers"
docker stop $(docker ps -a -q)
echo "Removing containers"
docker rm $(docker ps -a -q)
#for REMOVEID in $(docker ps -qa  -all)
#do
#	echo "Removing container $REMOVEID"
#	docker rm -f $REMOVEID
#done
}
runAll() {
echo "The current working directory."
pwd
echo "runemployee"
./scripts/runemployee.sh &
echo "runproject"
./scripts/runproject.sh &
echo "runprojectAssignment"
./scripts/runprojectAssignment.sh &
echo "rundeviceAuthentication"
./scripts/rundeviceAuthentication.sh &
echo "runorganisation"
./scripts/runorganisation.sh &
}

stopContainerByName
runAll
