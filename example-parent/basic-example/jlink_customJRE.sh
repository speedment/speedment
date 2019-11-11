#!/bin/bash
MODULEPATH=$(find ../../ -name "*.jar" | grep -v sources | grep -v javadoc | xargs echo | tr ' ' ':')
echo $MODULEPATH
#$JAVA_HOME/bin/jlink --module-path "$JAVA_HOME\jmods:$MODULEPATH" --add-modules basic.example --output customjre

$JAVA_HOME/bin/jlink --launcher customjrelauncher=basic.example/com.speedment.example.basic_example.Max --module-path "$JAVA_HOME\jmods:$MODULEPATH:~/.m2/repository/mysql/mysql-connector-java/5.1.48/mysql-connector-java-5.1.48.jar" --add-modules basic.example --output customjre
