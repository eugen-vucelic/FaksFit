FROM eclipse-temurin:21-alpine AS builder
ENV HOME=/usr/app
WORKDIR $HOME

RUN apk add --no-cache dos2unix

COPY . .

# Convert all files to unix format
RUN find . -type f -name "*.sh" -exec dos2unix {} \;
RUN find . -type f -name "mvnw" -exec dos2unix {} \;


RUN chmod +x ./mvnw

RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

FROM eclipse-temurin:21-alpine
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=builder $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]