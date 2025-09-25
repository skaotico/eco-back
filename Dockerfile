# ===========================================================
# Dockerfile para aplicación Java (OpenJDK 17)
# ===========================================================

# Usa la imagen oficial de OpenJDK 17
FROM openjdk:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia solo el JAR compilado al contenedor
# Ajusta la ruta según tu proyecto (Gradle: build/libs/*.jar, Maven: target/*.jar)
COPY build/libs/mi-aplicacion.jar /app/mi-aplicacion.jar

# Define el comando por defecto al iniciar el contenedor
CMD ["java", "-jar", "mi-aplicacion.jar"]
