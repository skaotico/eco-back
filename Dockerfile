# ===========================================================
# Dockerfile simplificado usando OpenJDK oficial
# ===========================================================

# Usa OpenJDK 17 oficial como base (Ubuntu + JDK incluido)
FROM openjdk:17-jdk

# Evita preguntas interactivas al instalar paquetes
ENV DEBIAN_FRONTEND=noninteractive

# Copia tu aplicación (ajusta según tu proyecto)
COPY . /app
WORKDIR /app

# Opcional: compilar si es necesario (Gradle/Maven)
# RUN ./gradlew build

# Define JAVA_HOME (ya viene configurado, pero por claridad)
ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$JAVA_HOME/bin:$PATH

# Comando por defecto al iniciar el contenedor
CMD ["java", "-jar", "tu-aplicacion.jar"]
