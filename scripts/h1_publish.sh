#!/bin/bash

cd $(dirname $0) && cd ..

#BUILD_VERSION=$(./gradlew  -q buildVersion)
#BUILD_VERSION_NAME=$(./gradlew  -q buildVersionName)
VERSION_NAME=$(./gradlew -q buildVersionIncrement)
echo publishing $VERSION_NAME
gitsave


ssh -t mac "$( cat << CMD
cd workspace/klog
git pull
./gradlew clean
./gradlew  publishMacosArm64PublicationToM2Repository  publishMacosX64PublicationToM2Repository
CMD
)"

#./gradlew publishAllPublicationsToM2Repository $@
#rsync -avHSx ./build/m2/ h1:/srv/https/maven/
