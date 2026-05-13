#!/bin/bash

cd "$(dirname "$0")" && cd ..

#rm -rf ./build/m2 2> /dev/null

#./gradlew publishAllPublicationsToLocal || exit 1
DIR_TO_COPY=/Users/dan/workspace/xtras/xtras/maven/org/danbrough/klog/

[ -d $DIR_TO_COPY ] && rm -rf /Users/dan/workspace/xtras/xtras/maven/org/danbrough/klog/
#rsync -avHSx ./build/m2/ maven@h1:~/m2/
./gradlew publishAllPublicationsToXtras || exit 1


[ -d $DIR_TO_COPY ] && \
	rsync -avHSx /Users/dan/workspace/xtras/xtras/maven/org/danbrough/klog/ maven:~/m2/org/danbrough/klog/
