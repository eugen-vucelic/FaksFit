# Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim AS build


# Add a volume pointing to /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar
ADD secured.properties secured.properties
# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]