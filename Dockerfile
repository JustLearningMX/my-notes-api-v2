# Usamos una imagen base de OpenJDK 17
# syntax=docker/dockerfile:1

# ===============================
# 🏗️ Etapa 1: Construcción con Gradle
# ===============================
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# Copiamos los archivos del proyecto
COPY . .

# Compilamos el JAR (usando gradle global, no ./gradlew)
RUN gradle clean bootJar --no-daemon

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

# Copiamos el JAR desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# ARG para recibir el puerto en tiempo de build
ARG APP_PORT=8080

# Puerto configurable
ENV PORT=${APP_PORT}
EXPOSE ${PORT}

#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]