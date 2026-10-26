# Image de départ : Java 17 (la version utilisée par WPILib 2026).
FROM mcr.microsoft.com/devcontainers/java:1-17-bookworm

# L'image contient une source de paquets Yarn dont la clé de signature n'est plus valide :
# "apt-get update" échoue et le bureau virtuel ne peut pas s'installer. On n'a pas besoin de
# Yarn, donc on retire cette source.
RUN rm -f /etc/apt/sources.list.d/yarn.list

# Bibliothèques graphiques pour la fenêtre du simulateur (rendu OpenGL logiciel).
RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
       libgl1 libglx-mesa0 libgl1-mesa-dri \
       libx11-6 libxext6 libxrandr2 libxinerama1 libxcursor1 libxi6 \
    && rm -rf /var/lib/apt/lists/*
