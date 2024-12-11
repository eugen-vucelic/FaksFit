FROM eclipse-temurin:11-jdk-alpine AS build

COPY ./pom.xml /usr/src/app/
COPY ./src /usr/src/app/src

RUN mvn -f /usr/src/app/pom.xml clean package

FROM eclipse-temurin:11-jdk-alpine

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","usr/app/app.jar"]