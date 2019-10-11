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
