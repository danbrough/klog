#!/bin/bash


cd $(dirname $0) && cd ..
source scripts/common.sh

if is_mac; then
  ./gradlew -PpublishDocs -PsignPublications publishMacTargetsToOSSRepository
  exit 0
fi

set_gradle_prop build.snapshot false


VERSION_NAME=$(./gradlew -q buildVersionNameNext)

echo Creating release: $VERSION_NAME

if ! message_prompt "Creating release $VERSION_NAME. Continue?"; then
  exit 0
fi


git add .
git commit -am "$VERSION_NAME"

git tag "$VERSION_NAME"
git push
git push origin "$VERSION_NAME"



./gradlew -PpublishDocs -PsignPublications publishAllPublicationsToOssRepository

