FROM eclipse-temurin:21-alpine AS builder

WORKDIR /app
COPY pom.xml .
COPY src src
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

RUN apk add --no-cache bash dos2unix

RUN dos2unix mvnw && chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-alpine

COPY --from=builder /app/target/faksfit-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]