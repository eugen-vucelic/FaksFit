FROM eclipse-temurin:21-alpine AS builder
ENV HOME=/usr/app
WORKDIR $HOME

RUN apk add --no-cache dos2unix

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN dos2unix ./mvnw
RUN chmod +x ./mvnw

RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package

FROM eclipse-temurin:21-alpine
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=builder $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]