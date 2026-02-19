# Paso 1: Usar una imagen de Java 21 ligera (Alpine)
FROM eclipse-temurin:21-jdk-alpine

# Paso 2: Crear el directorio donde vivirán los logs en el contenedor
RUN mkdir -p /app/logs

# Paso 3: Establecer el directorio de trabajo
WORKDIR /app

# Paso 4: Copiar el archivo JAR (asegúrate de que el nombre coincida tras el build)
COPY target/*.jar app.jar

# Paso 5: Exponer el puerto que configuraste (8081)
EXPOSE 8081

# Paso 6: Comando de arranque inyectando el perfil de producción
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]