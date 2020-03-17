#!/bin/bash
#
#
# Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License"); You may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.
#

#Fail on any error
set -e

if [ $# -eq 0 ]
  then
    echo "Usage $0: version [skip]"
    exit 1
fi
VERSION=$1
echo "** Checking tool versions"
mvn --version

echo "** Bumping to version $VERSION"

echo "** Checking if we are on the 'develop' branch"
DEVELOP_BRANCH=`git branch | grep "* develop" | wc -l`
if [ "$DEVELOP_BRANCH" -ne 1 ]
  then
    echo "Not on the 'develop' branch"
    exit 1
fi

read -p "Press enter to continue"

echo "** Pulling updates from GitHub"
git pull
echo "** Checking license headers"
mvn -Prelease com.mycila:license-maven-plugin:3.0:format -Plicense-check
if [ "$2" == 'skip' ]
 then
  echo "** Skipping build of the enterprise project"
 else
  echo "** Building the enterprise project"
  cd ../speedment-enterprise
  mvn clean install -Prelease
  cd ../speedment
fi

UPDATED_FILES=`git status | grep -e 'new file:' -e 'modified:' | wc -l`
echo "** There are $UPDATED_FILES files to commit"
if [ "$UPDATED_FILES" -gt 0 ]
 then
   echo "** Push changes to GitHub"
   git add --all
   git commit -m "Prepare for version bump to $VERSION"
   git push
fi

echo "** Performing checkout master, pull and merge with develop"
git checkout master
git pull
git merge develop

echo "** Setting versions to $VERSION"
./set_version.sh "$VERSION"
mvn speedmentversion:check

echo "** Building version $VERSION"
mvn clean install -Prelease

echo "** Push changes to GitHub"
git add --all
git commit -m "Bump version to $VERSION"
git push

echo "Completed!"
echo "Ready for mvn -Prelease clean deploy"

