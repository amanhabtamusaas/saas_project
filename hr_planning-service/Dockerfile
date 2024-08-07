# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/hr_planning-service.jar /app/hr_planning-service.jar

# Expose the port the application runs on
EXPOSE 8186

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/hr_planning-service.jar"]