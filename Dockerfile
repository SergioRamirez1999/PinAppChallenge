FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y maven && \
    apt-get install openjdk-21-jdk -y && \
    apt-get clean

COPY . /app
WORKDIR /app

RUN mvn clean package

FROM openjdk:21-slim

EXPOSE 8080

COPY --from=build /app/target/eldar-1.0.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]