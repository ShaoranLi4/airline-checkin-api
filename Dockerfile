# Usamos la imagen oficial de OpenJDK
FROM openjdk:17-jdk-slim

# Definimos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el jar de tu proyecto al contenedor
COPY target/airline-checkin-api-1.0.0.jar app.jar

# Puerto que expondrá el contenedor
EXPOSE 8080

# Comando para ejecutar tu API
ENTRYPOINT ["java", "-jar", "app.jar"]
