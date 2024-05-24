#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=sample-springboot-webservice

echo "> 빌드 파일을 복사합니다."

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 실행 중인 앱의 PID를 확인합니다."

CURRENT_PID=$(ps -ef | grep sample-springboot-webservice-1.0.2-SNAPSHOT.jar | grep -v grep | awk '{print $2}')

echo "  - PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 앱이 실행 중이지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep  5
fi

echo "> 새 앱을 배포합니다."

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행 권한을 추가합니다."

chmod +x $JAR_NAME

echo "> $JAR_NAME 을 실행합니다."

nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &