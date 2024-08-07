# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/tenant-service.jar /app/tenant-service.jar

# Expose the port the application runs on
EXPOSE 8181

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/tenant-service.jar"]