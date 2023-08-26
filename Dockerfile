# Use the official OpenJDK base image
FROM openjdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/battery-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application runs on
EXPOSE 8080

# Run the JAR file when the container starts
CMD ["java", "-jar", "app.jar"]