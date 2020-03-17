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
    echo "Usage $0: version"
    exit 1
fi
VERSION=$1

mvn versions:set -DnewVersion="$VERSION"
RETURN_BODY="return \"$VERSION\";"
sed -i tmp "s/getImplementationVersion.*\}/getImplementationVersion() \{ $RETURN_BODY \}/g" runtime-parent/runtime-core/src/main/java/com/speedment/runtime/core/internal/component/InfoComponentImpl.java
rm runtime-parent/runtime-core/src/main/java/com/speedment/runtime/core/internal/component/InfoComponentImpl.javatmp
