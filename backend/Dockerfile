# Build stage
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app

COPY pom.xml .
COPY src src

COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080