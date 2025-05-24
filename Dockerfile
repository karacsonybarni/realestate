# Stage 1: Build the application using Gradle with JDK 23
# Use an official Gradle image that includes JDK 23
FROM gradle:jdk23-alpine AS build
WORKDIR /app
# Copy only necessary files first to leverage Docker cache
COPY backend/build.gradle backend/settings.gradle backend/gradlew ./backend/
COPY backend/gradle ./backend/gradle
# Copy the rest of the backend source code
COPY backend/src ./backend/src
# Grant execution permission to gradlew and run the build
RUN chmod +x ./backend/gradlew && \
    cd backend && \
    ./gradlew build -x test --no-daemon

# Stage 2: Create the final runtime image using JRE 23
# Use an official Eclipse Temurin image with JRE 23
FROM eclipse-temurin:23-jre-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/backend/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Environment variable for Spring Profiles (can be overridden in render.yaml)
ENV SPRING_PROFILES_ACTIVE=prod

# Start command - Default for the main backend service
ENTRYPOINT ["java", "-jar", "app.jar"]

# --- MCP Server Specific Start ---
# The MCP service will override this ENTRYPOINT using dockerCommand in render.yaml
# ENTRYPOINT ["java", "-cp", "app.jar", "com.realestate.backend.mcp.MCPServerApplication"]