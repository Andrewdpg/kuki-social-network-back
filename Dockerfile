# Use the official Gradle image as the base image
FROM gradle:7.6-jdk17 AS build
WORKDIR /app

# Copy the necessary files to the container
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY src /app/src

# Build the project
RUN gradle build

# Use the official OpenJDK image as the base image for the runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install PostgreSQL
RUN apt-get update && apt-get install -y postgresql

# Copy the init-db.sh script
COPY init_db.sh /app/init_db.sh

# Make the script executable
RUN chmod +x /app/init_db.sh

# Copy the built application
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Set the entry point to initialize the database and start the application
USER postgres
ENTRYPOINT ["/bin/bash", "-c", "/app/init_db.sh && java -jar /app/app.jar"]