#!/bin/sh
buildAll () {
echo "buildbase"
./scripts/buildbase.sh
echo "buildemployee"
./scripts/buildemployee.sh
echo "buildproject"
./scripts/buildproject.sh
echo "buildprojectAssignment"
./scripts/buildprojectAssignment.sh
echo "buildDeviceAuthentication"
./scripts/builddeviceAuthentication.sh
echo "buildorganisation"
./scripts/buildorganisation.sh
}

buildAll