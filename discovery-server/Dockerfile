# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/discovery-server.jar /app/discovery-server.jar

# Expose the port the application runs on
EXPOSE 8060

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/discovery-server.jar"]