#!/bin/bash
SERVICE_NAME=worthytrip-permission-template

cd `dirname $0`
echo "$(date) enter directory: `pwd`"

cd ..
PID=`cat $SERVICE_NAME".pid"`

if [ "$PID" == "" ];then
  echo "$(date) There has no pid file, Server shutdown failed."
  exit 1;
else
  kill -9 "$PID"
  rm -f $SERVICE_NAME".pid"
  echo "$(date) Server has been shutdown!"
  exit 0;
fi
