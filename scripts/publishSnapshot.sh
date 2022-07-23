#!/bin/bash


cd `dirname $0` && cd ..



#arrIN=(${IN//;/ })

function set_gradle_prop(){
  sed -i gradle.properties  -e 's|^'$1'=.*|'$1'='$2'|g'
}

function get_gradle_prop(){
  cat gradle.properties  | sed -n -e '/'$1'=/p' | sed -e 's|'$1'=||g'
}

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

