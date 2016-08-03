#!/bin/sh

#
# Startup script for XACML Server
#


#
# Installation Directory
#
dir=`dirname $0`
if [ "$dir" != "." ]
then
  INST=`dirname $dir`
else
  pwd | grep -e 'bin$' > /dev/null
  if [ $? = 0 ]
  then
    INST=".."
  else
    INST=`dirname $dir`
  fi
fi

INST=${INST:-.}

cd $INST

#
# Read basic settings
#
. conf/startup.properties

#
# check whether the server might be already running
#
if [ -e $PID ] 
 then 
  if [ -d /proc/$(cat $PID) ]
   then
     echo "A B2ACCESS's XACML 3.0 instance may be already running with process id "$(cat $PID)
     echo "If this is not the case, delete the file $INST/$PID and re-run this script"
     exit 1
   fi
fi

#
# setup classpath
#
JARS=${LIB}/*.jar
CP=
for JAR in $JARS ; do 
    CP=$CP:$JAR
done

PARAM=$*
if [ "$PARAM" = "" ]
then
  PARAM=${CONF}/xacmlServer.config
fi

#
# go
#

CLASSPATH=$CP; export CLASSPATH

nohup $JAVA ${MEM} ${OPTS} ${DEFS} eu.eudat.b2access.authz.server.XACMLServer ${PARAM} XACMLSERVER > ${STARTLOG} 2>&1  & echo $! > ${PID}

echo "B2ACCESS XACML 3.0 Server starting"