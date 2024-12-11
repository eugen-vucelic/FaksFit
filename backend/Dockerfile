FROM eclipse-temurin:21-jdk-alpine AS builder
RUN apk add --no-cache dos2unix

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN find . -type f -exec dos2unix {} \;
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
