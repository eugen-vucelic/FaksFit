FROM openjdk:21-jdk-slim AS builder

COPY ../../.mvn .mvn
COPY ../../mvnw .
COPY ../../pom.xml .
COPY ../../src src
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests


FROM openjdk:21-jdk-slim

COPY --from=builder target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]