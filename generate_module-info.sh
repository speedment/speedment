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

 #!/bin/bash
if [ "$1" != "" ]; then
    JAR=$1
    shift
else
    echo "Usage: $0 jar-file jdep-parameters"
    exit
fi

MODULEPATH=$(find . -name "*.jar" | grep -v sources | grep -v javadoc | xargs echo | tr ' ' ':')
jdeps --module-path $MODULEPATH --multi-release 9 --generate-module-info . $JAR $@ 
