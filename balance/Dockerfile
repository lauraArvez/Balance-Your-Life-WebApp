# Usa una imagen oficial de Maven para compilar
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usa una imagen base de Java para ejecutar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Puerto en el que Spring Boot expone el servicio (asegúrate de que coincida con tu app)
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
