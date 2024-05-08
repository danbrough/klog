#!/bin/bash

cd "$(dirname "$0")" && cd ..

./gradlew publishAllPublicationsToLocal || exit 1


rsync -avHSx ./build/m2/ maven@h1:~/m2/
