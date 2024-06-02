#!/bin/bash

# Comprueba si Docker ya está instalado
if ! command -v docker &> /dev/null
then
    echo "Docker no está instalado. Instalando Docker..."
    # Actualiza la lista de paquetes existentes
    sudo apt-get update

    # Instala algunos paquetes de requisitos previos que permiten a apt usar paquetes a través de HTTPS
    sudo apt-get install \
        apt-transport-https \
        ca-certificates \
        curl \
        gnupg \
        lsb-release

    # Agrega la clave GPG oficial de Docker
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

    # Configura el repositorio estable
    echo \
      "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
      $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

    # Actualiza la lista de paquetes e instala Docker Engine
    sudo apt-get update
    sudo apt-get install docker-ce docker-ce-cli containerd.io

    echo "Docker ha sido instalado correctamente."
else
    echo "Docker ya está instalado."
fi

# Navega al directorio donde se encuentra el archivo compose.yaml
cd ~/RutaClara

# Ejecuta el archivo compose.yaml
docker-compose up -d