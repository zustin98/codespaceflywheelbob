# Guide 4 – Utiliser le simulateur de robot

Pas de robot sous la main ? Pas grave ! Le **simulateur** fait croire à ton code qu'il
contrôle un vrai robot. Le fichier `FlywheelSimulation.java` (fourni) calcule comment un vrai
volant accélérerait avec la tension envoyée au moteur.

## Étape 1 : ouvrir le bureau du simulateur (une fois par séance)

La fenêtre du simulateur s'affiche sur un **bureau virtuel**, dans un autre onglet de Chrome.

1. En bas de ton Codespace, clique sur l'onglet **PORTS**.
2. Trouve la ligne **Bureau du simulateur (6080)**.
3. Passe la souris dessus et clique sur l'icône 🌐 **Open in Browser** (« Ouvrir dans le
   navigateur »).
4. Un nouvel onglet s'ouvre avec la page **noVNC**. Clique sur **Connect** (« Connecter »).
5. Tu vois un bureau vide : c'est normal ! Garde cet onglet ouvert.

> 💡 **L'image est trop petite ou trop grande ?** Dans noVNC, ouvre le petit menu à gauche →
> ⚙️ **Settings** → **Scaling Mode** → choisis **Remote Resizing**.

## Étape 2 : lancer la simulation

Retourne dans l'onglet de ton Codespace. Dans le **TERMINAL**, tape :

```bash
./gradlew simulateJava
```

Puis appuie sur **Entrée**. Après quelques secondes, tu vois :

```
********** Robot program startup complete **********
```

Retourne dans l'onglet **noVNC** : la fenêtre **Robot Simulation** est apparue ! 🎉

> 💡 Tu peux aussi lancer la simulation par le menu ☰ → **Terminal** → **Run Task...** →
> **Simuler le robot**.

## Étape 3 : comprendre la fenêtre du simulateur

La fenêtre contient plusieurs petits panneaux :

| Panneau | À quoi ça sert |
|---|---|
| **Robot State** | Choisir le mode du robot : **Disabled** (désactivé), **Autonomous** (autonome), **Teleoperated** (piloté) |
| **System Joysticks** | Les manettes disponibles, dont **Keyboard 0** (ton clavier qui imite une manette) |
| **Joysticks** | Les manettes « branchées » dans la Driver Station simulée |
| **NetworkTables** | Toutes les valeurs que ton robot publie (vitesse, cible...) |
| **Other Devices** | Les appareils simulés, dont le **SPARK MAX** du volant |

### Activer le robot

Dans **Robot State**, clique sur **Teleoperated**. Le robot est maintenant **activé** : les
moteurs ont le droit de tourner.

> ⚠️ En mode **Disabled**, les moteurs ne tournent **jamais**, même si ton code le demande.
> C'est une règle de sécurité des robots FRC.

### Utiliser le clavier comme manette

1. Dans **System Joysticks**, trouve **Keyboard 0**.
2. **Glisse-le** (clic maintenu) jusque dans la case **Joystick[0]** du panneau **Joysticks**.
3. Clique une fois dans la fenêtre du simulateur pour qu'elle reçoive le clavier.

Correspondance des touches pour **Keyboard 0** :

| Touche | Bouton de la manette Xbox |
|---|---|
| **Z** | Bouton 1 = **A** |
| **X** | Bouton 2 = **B** |
| **C** | Bouton 3 = **X** |
| **V** | Bouton 4 = **Y** |

Donc : **maintenir la touche Z = maintenir le bouton A** de la manette.

### Voir les valeurs du volant

Ouvre le menu **NetworkTables** en haut de la fenêtre, puis **SmartDashboard**. Tu y trouves :

- **Volant/Vitesse (RPM)** : la vitesse mesurée par l'encodeur
- **Volant/Cible (RPM)** : la vitesse demandée (à partir de la séance 7)
- **Volant/Sortie moteur** : la puissance envoyée (-1 à 1)
- **Lancer le volant** : un bouton pour lancer la commande sans manette

### Tracer un graphique 📈

1. Menu **Plot** → **New Plot Window**.
2. **Glisse** la valeur **Volant/Vitesse (RPM)** depuis le panneau NetworkTables jusque dans
   le graphique.
3. Fais pareil avec **Volant/Cible (RPM)**.

Tu vois maintenant le volant accélérer en direct !

## Étape 4 : arrêter la simulation

Retourne dans l'onglet de ton Codespace, clique dans le **TERMINAL** et appuie sur
**Ctrl + C**.

> 🔁 **Après chaque modification du code**, il faut arrêter la simulation (Ctrl + C) et la
> relancer (`./gradlew simulateJava`). Astuce : la **flèche du haut ↑** dans le terminal
> remet la dernière commande.

## 🆘 Problèmes fréquents

| Problème | Solution |
|---|---|
| La fenêtre du simulateur n'apparaît pas | Vérifie que l'onglet noVNC est connecté (Étape 1), puis relance la simulation. |
| `BUILD FAILED` dans le terminal | Ton code contient une erreur. Voir le [guide 5 – Déboguer](05-deboguer.md). |
| Le volant ne tourne pas | Le robot est-il en **Teleoperated** ? Keyboard 0 est-il dans **Joystick[0]** ? As-tu cliqué dans la fenêtre avant d'appuyer sur Z ? |
| Le port 6080 n'est pas dans la liste | Dans l'onglet PORTS, clique **Add Port** et tape `6080`. |
| La fenêtre est toute noire / figée | Dans noVNC, déconnecte-toi puis reconnecte-toi. |
