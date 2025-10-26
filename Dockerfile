# Etapa 1: compilar con Gradle
FROM gradle:8.5-jdk17 AS builder
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean build -x test

# Etapa 2: solo runtime con OpenJDK
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
