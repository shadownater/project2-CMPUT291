#!/bin/bash

### compile ###
CLASSPATH=$CLASSPATH:.:/usr/share/java/db.jar:.
export CLASSPATH
LD_LIBRARY_PATH=/oracle/lib
export LD_LIBRARY_PATH

javac Main.java

### run prog ###
input=""
[ $# -ge 1 -a -f "$1" ] && input="$1" || input="-"

java Main
