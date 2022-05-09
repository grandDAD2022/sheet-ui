# syntax=docker/dockerfile:1
# Multistage Docker for `sheet-ui`

## Build stage

FROM maven:3-eclipse-temurin-17-alpine

COPY ./pom.xml ./pom.xml
COPY ./src ./src

ARG BUILD_VARS
RUN mvn dependency:go-offline -B
RUN mvn package $BUILD_VARS

## Execution stage

FROM eclipse-temurin:17-jre-alpine

COPY --from=0 ./target/*.jar sheet-ui.jar

ARG SPRING_ARGS
EXPOSE 8080
ENTRYPOINT java -jar sheet-ui.jar $SPRING_ARGS
