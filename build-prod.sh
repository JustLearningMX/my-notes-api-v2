#!/bin/bash

# ==========================
# 🏗️ Script de construcción de imagen Docker para producción
# ==========================

# 🔧 CONFIGURACIÓN GENERAL
APP_NAME="my-notes-api-v2"
TAG="latest"
IMAGE_NAME="${APP_NAME}:${TAG}"
IMAGE_TAR="${APP_NAME}.tar"
ENV_FILE=".env.prod"

echo "🚀 Iniciando build para '$APP_NAME'..."

# ==========================
# 1️⃣ Cargar variables de entorno
# ==========================
if [ -f "$ENV_FILE" ]; then
  export $(cat "$ENV_FILE" | grep -v '^#' | xargs)
  echo "✅ Variables cargadas desde $ENV_FILE"
  echo "📌 PORT configurado: $PORT"
else
  echo "❌ Error: No se encontró $ENV_FILE"
  exit 1
fi

# ==========================
# 2️⃣ Construir imagen Docker
# ==========================
echo ""
echo "🚀 Construyendo imagen de producción para $APP_NAME..."

docker build --platform linux/arm64 \
  --build-arg APP_PORT="$PORT" \
  -t "$IMAGE_NAME" .

if [ $? -eq 0 ]; then
  echo "✅ Imagen '$IMAGE_NAME' construida exitosamente"
  echo "📦 Puerto configurado: $PORT"
else
  echo "❌ Error en la construcción de la imagen"
  exit 1
fi

# ==========================
# 3️⃣ Empaquetar la imagen en un TAR
# ==========================
echo ""
echo "📦 Empaquetando imagen en archivo TAR..."

docker save "$IMAGE_NAME" -o "$IMAGE_TAR"

if [ $? -eq 0 ]; then
  FILE_SIZE=$(du -h "$IMAGE_TAR" | cut -f1)
  echo "✅ Imagen empaquetada exitosamente"
  echo "📄 Archivo: $IMAGE_TAR"
  echo "💾 Tamaño: $FILE_SIZE"
  echo ""
  echo "🚀 Para cargar en tu VM:"
  echo "   1. Sube el archivo mediante SFTP o SCP:"
  echo "      scp $IMAGE_TAR usuario@tu-vm:/ruta/"
  echo "   2. Carga la imagen:"
  echo "      docker load -i $IMAGE_TAR"
  echo "   3. Levanta la aplicación:"
  echo "      docker compose --env-file .env.prod -f compose.prod.yaml up -d"
  echo "   4. Verifica el estatus:"
  echo "      docker ps"
else
  echo "❌ Error al empaquetar la imagen"
  exit 1
fi

echo ""
echo "🎉 Build completado exitosamente"
echo "🏷️ Imagen: $IMAGE_NAME"
echo "📦 Archivo: $IMAGE_TAR"