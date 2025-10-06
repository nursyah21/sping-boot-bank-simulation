FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

# to prevent error when build we skip test, because test need database to active
RUN ./mvnw clean package -DskipTests

EXPOSE 8000

ENTRYPOINT [ "java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod" ]