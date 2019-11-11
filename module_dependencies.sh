 #!/bin/bash
if [ "$1" != "" ]; then
    MODULE=$1
    shift
else
    echo "Usage: $0 module jdep-parameters"
    echo "example: $0 com.speedment.runtime.application  -summary -recursive"
    echo "module examples: com.speedment.runtime.application, com.speedment.runtime.field"
    exit
fi

MODULEPATH=$(find . -name "*.jar" | grep -v sources | grep -v javadoc | xargs echo | tr ' ' ':')
#jdeps --multi-release 9 --module-path $MODULEPATH runtime-parent/runtime-application/target/runtime-application-$VERSION.jar 
jdeps --multi-release 9 --module-path $MODULEPATH -m $MODULE $@ 
