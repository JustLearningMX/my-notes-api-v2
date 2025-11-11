# syntax=docker/dockerfile:1

# ===============================
# 🏗️ Etapa 1: Construcción con Maven
# ===============================
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos los archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilamos el JAR (usando mvn, equivalente a gradle bootJar)
RUN mvn clean package -DskipTests --no-transfer-progress

# ===============================
# 🚀 Etapa 2: Imagen ligera de ejecución
# ===============================
FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser
WORKDIR /app

# Copiamos el JAR desde la etapa anterior (Maven lo genera en target/)
COPY --from=build /app/target/*.jar app.jar

# ARG para recibir el puerto en tiempo de build
ARG APP_PORT=8080

# Puerto configurable
ENV PORT=${APP_PORT}
EXPOSE ${PORT}

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]