#!/usr/bin/env bash
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


PORT=9000

set -e

function probe_sonar {
    if wget -q -O - http://localhost:${PORT}/ | grep serverStatus | grep UP 2>&1 >/dev/null; then
        echo "1"
    else
        echo "0"
    fi
}

function wait_for_sonar {
    echo -n "waiting for sonarqube ."
    while [[ $(probe_sonar) == 0 ]]; do
        sleep 1
        echo -n "."
    done
    echo " done!"
}

echo "Running tests"
mvn clean test
if [[ $(probe_sonar) == 0 ]]; then
    echo "sonarqube docker does not seem to be up. Trying to restart"
    docker run -d --name sonarqube -p ${PORT}:9000 sonarqube
    wait_for_sonar
fi
echo "Running sonar analysis"
mvn sonar:sonar
echo "Your report is soon ready over here: http://localhost:${PORT}/projects?sort=-analysis_date"
