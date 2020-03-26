#Stage 1
FROM gradle:jdk11 as builder

RUN mkdir /opt/service
RUN mkdir /opt/service/build
RUN mkdir /opt/service/src

WORKDIR /opt/service

COPY --chown=gradle:gradle src /opt/service/src
COPY --chown=gradle:gradle build.gradle /opt/service/build.gradle

RUN gradle build -x test

#Stage 2 Final
FROM openjdk:11-jre-slim

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

RUN mkdir /opt/java-ms

#TODO for compitalions change name of jar
COPY --from=builder /opt/service/build/libs/ms-post* /opt/java-ms/ms-post.jar

#TODO port
EXPOSE 8080

#TODO for compitalions change name of jar
CMD java ${JAVA_OPTS} -server -XX:+UseParallelGC -XX:+UseNUMA -Djava.security.egd=file:/dev/./urandom -jar /opt/java-ms/ms-post.jar
