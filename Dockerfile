FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the entire project to the container
COPY . .

# Change working directory to backend to run maven wrapper
WORKDIR /app/backend

# Make mvnw executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "target/resumeanalyzer-0.0.1-SNAPSHOT.jar"]
