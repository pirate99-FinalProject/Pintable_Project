#!/bin/bash

REPOSITORY=/home/ubuntu/git-action-test
cd $REPOSITORY

APP_NAME=demo
JAR_NAME=$(ls $REPOSITORY/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME


# =====================================
# 현재 구동 중인 application pid 확인
# =====================================
CURRENT_PID=$( ps -ef | grep "$JAR_NAME" | grep -v 'grep' |  awk '{print $2}')

if [ -z "$CURRENT_PID" ]; then
   echo "NOT RUNNING"
else
   echo "> kill -9 $CURRENT_PID"
   kill -15 $CURRENT_PID
   sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH --logging.file.path=$REPOSITORY/log --logging.level.org.hibernate.SQL=DEBUG >> $REPOSITORY/log/deploy.log 2>/$REPOSITORY/log/deploy_err.log &
