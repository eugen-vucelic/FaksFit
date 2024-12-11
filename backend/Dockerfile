# Build stage
FROM maven:3.8.4-openjdk-17 as builder


WORKDIR /app

COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
COPY mvnw.cmd .


RUN mvn dependency:go-offline


COPY src/ src/


RUN mvn clean package -DskipTests


FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar


EXPOSE 8080

CMD ["java", "-jar", "app.jar"]