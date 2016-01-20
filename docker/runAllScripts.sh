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
echo "run employee"
./scripts/employee/run.sh &

echo "run project"
./scripts/project/run.sh &

echo "run project assignment"
./scripts/project-assignment/run.sh &

echo "run device authentication"
./scripts/device-authentication/run.sh &

echo "run device registration"
./scripts/device-registration/run.sh &

echo "run organisation web"
./scripts/organisation-web/run.sh &
}

stopContainerByName
runAll
