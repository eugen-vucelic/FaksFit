FROM openjdk:21-jdk-slim AS builder

WORKDIR /app

COPY . .
RUN mvn clean package

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]