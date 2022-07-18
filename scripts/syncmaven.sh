#!/bin/bash

cd $(dirname $0) && cd ..


rsync -avHSx build/m2/org/danbrough/ h1:/srv/https/maven/org/danbrough/
