# ===== Builder stage =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:resolve dependency:resolve-plugins
COPY src ./src
RUN mvn -q -e -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ARG JAR_FILE=target/*.jar
ENV SPRING_PROFILES_ACTIVE=default \
    JAVA_OPTS="" \
    TZ=UTC

# Copy jar from build stage
COPY --from=build /app/${JAR_FILE} app.jar

# Copy data directory (ensure `data` exists at project root)
COPY data ./data

EXPOSE 8080

ENTRYPOINT ["/bin/sh","-c","java $JAVA_OPTS -jar app.jar"]