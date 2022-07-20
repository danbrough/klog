#!/bin/bash

cd "$(dirname "$0")" && cd ..

function increment_version() {
  VERSION_NAME=$(./gradlew -q buildVersionIncrement)
  echo publishing "$VERSION_NAME"
  gitsave
}

function publish_mac(){
  git pull
  rm -rf build/m2
  ./gradlew  publishMacosArm64PublicationToM2Repository  publishMacosX64PublicationToM2Repository || exit 1
  rsync -avHSx ./build/m2/ h1:/srv/https/maven/
}

function publish_linux() {
  gitsave
  rm -rf build/m2
  ./gradlew publishAllPublicationsToM2Repository || exit 1
  rsync -avHSx mac:~/workspace/klog/build/m2/ ./build/m2/ || exit 1
}

if [ "$MACHTYPE" = "x86_64-apple-darwin21" ]; then
  publish_mac
else
  publish_linux
fi
