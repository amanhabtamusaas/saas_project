# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/app.jar app.jar

# Expose the port the application runs on
EXPOSE 8000

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]