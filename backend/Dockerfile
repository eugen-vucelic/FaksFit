FROM eclipse-temurin:21-alpine AS builder
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME


RUN apk add --no-cache dos2unix

ADD . $HOME
# fix fine endings
RUN dos2unix mvnw
RUN chmod +x ./mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

FROM eclipse-temurin:21-alpine
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=builder $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]