# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/employee-service.jar /app/employee-service.jar

# Expose the port the application runs on
EXPOSE 8180

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/employee-service.jar"]