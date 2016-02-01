#!/bin/sh
buildAll () {
echo "build base"
./scripts/base/build.sh

echo ">>>>>>>>>>>>>>>>>>>build uuid>>>>>>>>>>>>>>>>>>>"
./scripts/uuid/build.sh

echo ">>>>>>>>>>>>>>>>>>>build employee>>>>>>>>>>>>>>>>>>>"
./scripts/employee/build.sh

echo ">>>>>>>>>>>>>>>>>>>build project>>>>>>>>>>>>>>>>>>>"
./scripts/project/build.sh

echo ">>>>>>>>>>>>>>>>>>>build project assignment>>>>>>>>>>>>>>>>>>>"
./scripts/project-assignment/build.sh

echo ">>>>>>>>>>>>>>>>>>>build device registration>>>>>>>>>>>>>>>>>>>"
./scripts/device-registration/build.sh

echo ">>>>>>>>>>>>>>>>>>>build device authentication>>>>>>>>>>>>>>>>>>>"
./scripts/device-authentication/build.sh

echo ">>>>>>>>>>>>>>>>>>>build organisation web>>>>>>>>>>>>>>>>>>>"
./scripts/organisation-web/build.sh
}

buildAll