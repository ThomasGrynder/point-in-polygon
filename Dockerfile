# Stage 0
FROM maven:3.5.4-jdk-8-alpine AS builder

WORKDIR /usr/src/app

COPY ./pom.xml .
# Download the package and make it cached in docker image
RUN mvn -B -f ./pom.xml verify clean --fail-never

COPY ./ .
# Build the code
RUN mvn -B -f ./pom.xml package


# Stage 1
FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG JAR_FILE=pip-0.0.1-SNAPSHOT.jar

COPY --from=builder /usr/src/app/target/${JAR_FILE} /opt/pip/point-in-polygon.jar  

EXPOSE 8080  

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/pip/point-in-polygon.jar"]