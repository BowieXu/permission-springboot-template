#!/bin/bash
SERVICE_NAME=worthytrip-permission-template
## Adjust server port if necessary
SERVER_PORT=8080
SERVER_URL="http://localhost:"$SERVER_PORT"/health"

## Adjust memory settings if necessary
#export JAVA_OPTS="-Xms6144m -Xmx6144m -Xss256k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=384m -XX:NewSize=4096m -XX:MaxNewSize=4096m -XX:SurvivorRatio=8"

## Only uncomment the following when you are using server jvm
#export JAVA_OPTS="$JAVA_OPTS -server -XX:-ReduceInitialCardMarks"

########### The following is the jvm export ###########
export JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:ParallelGCThreads=4 -XX:MaxTenuringThreshold=9 -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC -XX:+UseCMSInitiatingOccupancyOnly -XX:+ScavengeBeforeFullGC -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=9 -XX:CMSInitiatingOccupancyFraction=60 -XX:+CMSClassUnloadingEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSPermGenSweepingEnabled -XX:CMSInitiatingPermOccupancyFraction=70 -XX:+ExplicitGCInvokesConcurrent -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -Duser.timezone=Asia/Shanghai -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom"
export JAVA_OPTS="$JAVA_OPTS -Dserver.port=$SERVER_PORT"

JAR_NAME=$SERVICE_NAME".jar"

cd `dirname $0`
echo "$(date) enter directory: `pwd`"

cd ..
for i in `ls lib/$SERVICE_NAME-*.jar 2>/dev/null`
do
  if [[ ! $i == *"-sources.jar" ]];then
    JAR_NAME=$i
    break
  fi
done

nohup java $JAVA_OPTS -jar $JAR_NAME >/dev/null 2>&1 &
rc=$?
SPID=$!
echo $SPID > $SERVICE_NAME".pid"
echo "$(date) $SERVICE_NAME\".pid\" has been created."

if [[ $rc != 0 ]];then
  echo "$(date) Failed to start $SERVICE_NAME.jar, return code: $rc"
  exit $rc;
fi

function checkPidAlive {
    for i in `ls -t $SERVICE_NAME*.pid 2>/dev/null`
    do
        read pid < $i

        result=$(ps -p "$pid")
        if [ "$?" -eq 0 ]; then
            return 0
        else
            printf "\n$(date) pid - $pid just quit unexpectedly, please check logs under `pwd`/../logs for more information!"
            exit 1;
        fi
    done

    printf "\n$(date) No pid file found, startup may failed. Please check logs under `pwd`/../logs for more information!"
    exit 1;
}

declare -i counter=0
declare -i max_counter=30 # 30*1=30s
declare -i total_time=0

printf "$(date) Waiting for server startup"
until [[ (( counter -ge max_counter )) || "$(curl -X GET --silent --connect-timeout 1 --max-time 2 $SERVER_URL | grep "\"status\":\"UP\"")" != "" ]];
do
    printf "."
    counter+=1
    sleep 1

    checkPidAlive
done

total_time=counter*1
printf "\n"
if [[ (( counter -ge max_counter )) ]];
then
    echo "$(date) Server failed to start in $total_time seconds!"
    exit 1;
fi

echo "$(date) Server started in $total_time seconds!"

## tail -f ../logs/$SERVICE_NAME-`date "+%Y-%m-%d"`.log