FROM openjdk:17-jdk-slim

VOLUME /tmp

ARG JAR_FILE=target/test_attempet_2-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

COPY src/main/resources/db/changelog /db/changelog

ENTRYPOINT ["java","-jar","/app.jar"]
