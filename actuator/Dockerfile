FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

# for faster build we skip test
RUN ./mvnw clean package -DskipTests

EXPOSE 9000

ENTRYPOINT [ "java", "-jar", "target/actuator-0.0.1-SNAPSHOT.jar" ]