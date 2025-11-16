FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Expose Spring Boot port
EXPOSE 8080

# Run with Spring Boot Maven plugin for hot reload
CMD ["mvn", "spring-boot:run"]