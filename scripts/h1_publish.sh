#!/bin/bash

cd "$(dirname "$0")" && cd ..

#BUILD_VERSION=$(./gradlew  -q buildVersion)
#BUILD_VERSION_NAME=$(./gradlew  -q buildVersionName)

function increment_version() {
  VERSION_NAME=$(./gradlew -q buildVersionIncrement)
  echo publishing "$VERSION_NAME"
  gitsave
}


ssh -t mac "$( cat << CMD
cd workspace/klog
git pull
rm -rf build/m2
./gradlew  publishMacosArm64PublicationToM2Repository  publishMacosX64PublicationToM2Repository
CMD
)" || exit 1

rm -rf build/m2
./gradlew publishAllPublicationsToM2Repository || exit 1
rsync -avHSx mac:~/workspace/klog/build/m2/ ./build/m2/ || exit 1
rsync -avHSx ./build/m2/ h1:/srv/https/maven/

#./gradlew publishAllPublicationsToM2Repository $@
#rsync -avHSx ./build/m2/ h1:/srv/https/maven/
