#!/usr/bin/env bash
# Exécuté UNE seule fois, à la création du Codespace, AVANT que VS Code s'ouvre.
# Ainsi, l'extension Java trouve WPILib déjà téléchargé et ne lance pas une 2e compilation
# en même temps que celle-ci.
set -e

chmod +x gradlew

echo "==> Téléchargement de WPILib et REVLib (2 à 5 minutes la première fois)..."
# extractReleaseNative prépare aussi les fichiers du simulateur : la première simulation
# démarre donc tout de suite.
./gradlew build extractReleaseNative -x test --console=plain \
  || echo "La compilation a échoué : c'est normal si le code n'est pas terminé."

echo ""
echo "=========================================="
echo "  Ton Codespace est prêt ! Bonne séance :)"
echo "=========================================="
