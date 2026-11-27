
#!/bin/bash

# Arrêter le script en cas d'erreur
set -e

# Chemins
TOMCAT="$HOME/L3/logi/apache-tomcat-11.0.29"
WAR="target/Projet.war"

echo "=== Construction du projet ==="
mvn clean package

echo "=== Arrêt de Tomcat ==="
"$TOMCAT/bin/shutdown.sh" || true

sleep 3

echo "=== Déploiement du WAR ==="
rm -rf "$TOMCAT/webapps/nouveau-projet"
rm -f "$TOMCAT/webapps/nouveau-projet.war"

cp "$WAR" "$TOMCAT/webapps/"

echo "=== Redémarrage de Tomcat ==="
"$TOMCAT/bin/startup.sh"

echo "=== Déploiement terminé ! ==="
