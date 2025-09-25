# Usa Ubuntu 22.04 como base
FROM ubuntu:22.04

# Evita preguntas interactivas al instalar paquetes
ENV DEBIAN_FRONTEND=noninteractive

# Copia el archivo tar.gz de Java al contenedor
COPY openjdk-17.tar.gz /tmp/

# Instala dependencias necesarias y descomprime el JDK
RUN apt-get update && \
    apt-get install -y tar curl && \
    tar -xzf /tmp/openjdk-17.tar.gz -C /opt/ && \
    rm /tmp/openjdk-17.tar.gz

# Define variables de entorno
ENV JAVA_HOME=/opt/jdk-17
ENV PATH=$JAVA_HOME/bin:$PATH

# Verifica la instalación (opcional)
RUN java -version
