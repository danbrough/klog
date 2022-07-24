#!/bin/bash


cd $(dirname $0) && cd ..
source scripts/common.sh

if is_mac; then
  ./gradlew -PpublishDocs -PsignPublications publishMacTargetsToOSSRepository
  exit 0
fi


set_gradle_prop build.snapshot true

VERSION_NAME=$(./gradlew -q buildVersionNameNext)

echo Creating release: $VERSION_NAME

while true; do
  read -p "Do you wish to proceed?: " yn
  case $yn in
  [Yy]*) break ;;
  [Nn]*) exit ;;
  *) echo "Please answer yes or no." ;;
  esac
done

git add .
git commit -am "$VERSION_NAME"

./gradlew publishAllPublicationsToOssRepository -PpublishDocs -PsignPublications

