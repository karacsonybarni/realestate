# Stage 1: Build the application using Gradle
FROM gradle:8.5-jdk17-alpine AS build
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

# Stage 2: Create the final runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/backend/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Render will assign (though Spring Boot uses the command-line arg)
# RENDER INTERNAL NOTE: Render sets the PORT env var automatically.
# EXPOSE $PORT is not strictly needed here as the start command uses the env var.

# Environment variable for Spring Profiles (can be overridden in render.yaml)
ENV SPRING_PROFILES_ACTIVE=prod

# Start command - This will be overridden by startCommand in render.yaml for flexibility
# Using exec form is recommended
ENTRYPOINT ["java", "-jar", "app.jar"]

# --- MCP Server Specific Start (Alternative Entrypoint - See render.yaml) ---
# If you wanted *this* Dockerfile to ONLY run the MCP server, you'd change the ENTRYPOINT:
# ENTRYPOINT ["java", "-cp", "app.jar", "com.realestate.backend.mcp.MCPServerApplication"]