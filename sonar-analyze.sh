#!/usr/bin/env bash

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
