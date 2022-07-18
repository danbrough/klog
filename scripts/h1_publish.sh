#!/bin/bash

cd $(dirname $0) && cd .. 

./gradlew publishAllPublicationsToM2Repository
rsync -avHSx ./build/m2/ h1:/srv/https/maven/
