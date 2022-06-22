#!/bin/bash

cd $(dirname $0) && cd ..


./gradlew -Pbuild.snapshot=true publishAllPublicationsToMavenRepository || exit 1

rsync -avHSx build/maven/org/danbrough/ h1:/srv/https/maven/org/danbrough/
