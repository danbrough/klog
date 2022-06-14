#!/bin/bash


cd `dirname $0` && cd ..

#arrIN=(${IN//;/ })

VERSION_NAME=$(./gradlew -q nextBuildVersionName)

echo Creating release: $VERSION_NAME

while true; do
    read -p "Do you wish to proceed?: " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done

if git tag | grep "$VERSION_NAME" > /dev/null; then
  while true; do
    read -p "Existing Tag $VERSION_NAME found. Shall I delete it?: " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
  done
  echo removing existing tag "$VERSION_NAME"
  git tag -d "$VERSION_NAME"
  git push origin --delete "$VERSION_NAME"
fi





#sed -i README.md -e 's/## Latest version:.*/## Latest version: '$VERSION_NAME'/g'

#./gradlew dokkaGfmMultiModule || exit 1

./gradlew -q buildVersionIncrement
git add .
git commit -am "$VERSION_NAME"
git tag "$VERSION_NAME" && git push && git push origin "$VERSION_NAME"

#./gradlew publishToMavenLocal || exit 1
./gradlew publishAllPublicationsToMavenRepository
exit 0

#find ~/.m2/repository/com/github/danbrough/androidutils/ -type f -name 'maven-metadata-local.xml' | \
#while read n; do
#  mv "${n}"   "$(echo $n | sed  -e 's:-local::g')"
#done
#
#rsync -avHSx /home/dan/.m2/repository/com/github/danbrough/ h1:/srv/https/maven/com/github/danbrough/


sleep 1
#wget "https://jitpack.io/com/github/danbrough/klog/${VERSION_NAME}/util-${VERSION_NAME}.jar" -O /tmp/rubbish.jar &
sleep 1
BUILD_URL="https://jitpack.io/com/github/danbrough/klog/${VERSION_NAME}/build.log"

sleep 1
echo loading $BUILD_URL
curl "$BUILD_URL" && exit 0




