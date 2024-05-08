#!/bin/bash

cd "$(dirname "$0")" && cd ..

rm -rf ./build/m2 2> /dev/null

./gradlew publishAllPublicationsToLocal || exit 1


rsync -avHSx ./build/m2/ maven@h1:~/m2/
