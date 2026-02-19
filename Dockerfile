# ETAPA 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copiamos el pom y el código fuente
COPY pom.xml .
COPY src ./src
# Ejecutamos el empaquetado (esto crea el target/app.jar dentro de Render)
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
RUN mkdir -p /app/logs
# Copiamos el JAR desde la etapa de construcción anterior
COPY --from=build /app/target/app.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]