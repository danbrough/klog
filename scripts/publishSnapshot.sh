#!/bin/bash


cd `dirname $0` && cd ..
source scripts/common.sh

#arrIN=(${IN//;/ })


set_gradle_prop build.snapshot true

VERSION_NAME=$(./gradlew -q buildVersionNameNext)

echo Creating release: $VERSION_NAME

while true; do
    read -p "Do you wish to proceed?: " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done

git add .
git commit -am "$VERSION_NAME"

if is_mac; then
  ./gradlew   -PpublishDocs -PsignPublications publishMacTargetsToOSSRepository
else
  ./gradlew publishAllPublicationsToOssRepository  -PpublishDocs -PsignPublications
fi
