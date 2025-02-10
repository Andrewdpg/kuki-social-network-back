# Use the official Gradle image as the base image
FROM gradle:7.6-jdk17 AS build
WORKDIR /app

# Copy the necessary files to the container
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY src /app/src

# Build the project
RUN  gradle build

# Use the official OpenJDK image as the base image for the runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/social-networking-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]