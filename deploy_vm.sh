#!/bin/bash

# ==========================
# 🚀 Script de despliegue en VM (con limpieza y variables)
# ==========================

# 🔧 CONFIGURACIÓN GENERAL
APP_NAME="my-notes-api-v2"
TAG="latest"
IMAGE_TAR="${APP_NAME}.tar"
IMAGE_NAME="${APP_NAME}:${TAG}"
COMPOSE_FILE="compose-prod.yaml"
ENV_FILE=".env.prod"

echo "🚀 Iniciando despliegue de '$APP_NAME' en la VM..."

# ==========================
# 1️⃣ Verificaciones iniciales
# ==========================
if [ ! -f "$IMAGE_TAR" ]; then
  echo "❌ Error: No se encontró el archivo $IMAGE_TAR"
  exit 1
fi

if [ ! -f "$ENV_FILE" ]; then
  echo "❌ Error: No se encontró el archivo $ENV_FILE"
  exit 1
fi

if [ ! -f "$COMPOSE_FILE" ]; then
  echo "❌ Error: No se encontró el archivo $COMPOSE_FILE"
  exit 1
fi

# ==========================
# 2️⃣ Detener contenedores previos
# ==========================
echo ""
echo "🛑 Deteniendo contenedores previos (si existen)..."
docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" down

# ==========================
# 3️⃣ Eliminar imagen previa si existe
# ==========================
if docker images | grep -q "$APP_NAME"; then
  echo ""
  echo "🧹 Eliminando imagen anterior de $APP_NAME..."
  docker rmi -f "$IMAGE_NAME"
else
  echo ""
  echo "ℹ️ No se encontró una imagen previa llamada $IMAGE_NAME"
fi

# ==========================
# 4️⃣ Cargar la nueva imagen
# ==========================
echo ""
echo "📦 Cargando nueva imagen desde $IMAGE_TAR..."
docker load -i "$IMAGE_TAR"

if [ $? -eq 0 ]; then
  echo "✅ Imagen cargada exitosamente"
else
  echo "❌ Error al cargar la imagen"
  exit 1
fi

# ==========================
# 5️⃣ Mostrar imágenes disponibles
# ==========================
echo ""
echo "🔍 Verificando imágenes cargadas..."
docker images | grep "$APP_NAME"

# ==========================
# 6️⃣ Levantar la aplicación
# ==========================
echo ""
echo "🚀 Levantando la nueva versión..."
docker compose --env-file "$ENV_FILE" -f "$COMPOSE_FILE" up -d

if [ $? -eq 0 ]; then
  echo "✅ Aplicación levantada correctamente"
else
  echo "❌ Error al levantar la aplicación"
  exit 1
fi

# ==========================
# 7️⃣ Mostrar estado final
# ==========================
echo ""
echo "🔍 Contenedores en ejecución:"
docker ps | grep "$APP_NAME"

echo ""
echo "🎉 Despliegue completado con éxito"
echo "🏷️ Imagen: $IMAGE_NAME"
echo "📄 Compose: $COMPOSE_FILE"