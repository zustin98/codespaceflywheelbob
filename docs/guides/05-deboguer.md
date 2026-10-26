# Guide 5 – Déboguer : trouver et corriger les erreurs 🐛

Tous les programmeurs font des erreurs, même les meilleurs ! Un **bug**, c'est une erreur dans
le code. **Déboguer**, c'est la trouver et la corriger. C'est un vrai talent de programmeur.

Il existe deux grandes familles d'erreurs :

| Type | Quand ? | Exemple |
|---|---|---|
| **Erreur de compilation** | Avant même que le programme démarre | Un `;` oublié, un nom mal écrit |
| **Erreur de logique** | Le programme marche, mais ne fait pas ce qu'on veut | Le volant tourne à l'envers, la vitesse est fausse |

---

## Outil 1 : les soulignements rouges et l'onglet PROBLEMS

L'éditeur vérifie ton code **pendant que tu tapes**.

- Un mot **souligné en rouge** 〰️ = une erreur. **Passe la souris dessus** pour lire le message.
- Un mot **souligné en jaune** = un avertissement (pas grave, mais à regarder).
- L'onglet **PROBLEMS** en bas affiche **toutes** les erreurs. Clique sur une erreur pour
  aller directement à la bonne ligne.

> 💡 **L'ampoule magique 💡** : place ton curseur sur un mot souligné et appuie sur
> **Ctrl + .** (point). L'éditeur propose souvent une correction, par exemple ajouter un
> `import` qui manque.

## Outil 2 : lire un message d'erreur de compilation

Quand tu lances `./gradlew build` ou `./gradlew simulateJava`, une erreur ressemble à ça :

```
/workspaces/volant-alex/src/main/java/frc/robot/RobotContainer.java:27: error: ';' expected
    driverController.a().whileTrue(new SetFlywheelSpeed(flywheel, 0.5))
                                                                       ^
```

Comment le lire :
1. **Le fichier** : `RobotContainer.java`
2. **La ligne** : `27`
3. **Le problème** : `';' expected` = il manque un point-virgule
4. **Le ^** montre l'endroit exact.

> 💡 Dans le terminal, **Ctrl + clic** sur le nom du fichier ouvre directement la bonne ligne.

### Les messages les plus fréquents

| Message | Ça veut dire... | Solution |
|---|---|---|
| `';' expected` | Il manque un `;` | Ajoute `;` à la fin de l'instruction |
| `cannot find symbol` | Java ne connaît pas ce nom | Vérifie l'orthographe **et les majuscules** (`flywheel` ≠ `Flywheel`), ou ajoute l'`import` (Ctrl + .) |
| `incompatible types` | Mauvais type de valeur | Ex. mettre `"3000"` (du texte) dans un `double` → écris `3000.0` |
| `missing return statement` | La méthode doit renvoyer une valeur | Ajoute `return ...;` |
| `class X is public, should be declared in a file named X.java` | Le nom du fichier ≠ nom de la classe | Renomme le fichier ou la classe pour qu'ils soient identiques |
| `reached end of file while parsing` | Il manque une accolade `}` | Compte tes `{` et tes `}` |
| `variable x might not have been initialized` | La variable n'a pas de valeur | Donne-lui une valeur au départ |

## Outil 3 : afficher des messages (`System.out.println`)

Le plus simple des outils : faire « parler » ton code.

```java
System.out.println("La commande démarre ! Vitesse visée : " + targetRpm);
```

Le message s'affiche dans le **TERMINAL** quand le code passe par cette ligne. Pratique pour
savoir **si** une partie du code s'exécute, et **avec quelles valeurs**.

> ⚠️ Ne mets pas de `println` dans une méthode `periodic()` : elle s'exécute 50 fois par
> seconde, ton terminal serait inondé ! Utilise plutôt le tableau de bord (outil 4).

## Outil 4 : le tableau de bord (SmartDashboard) et les graphiques

Pour des valeurs qui changent tout le temps (comme une vitesse), publie-les dans le tableau
de bord :

```java
SmartDashboard.putNumber("Volant/Vitesse (RPM)", getVelocityRpm());
```

Puis regarde-les (et trace-les en graphique) dans le simulateur : voir le
[guide 4](04-simulateur.md#voir-les-valeurs-du-volant).

## Outil 5 : le débogueur et les points d'arrêt 🔴

Le **débogueur** permet de **mettre le programme sur pause** à une ligne précise, puis de
regarder la valeur de chaque variable. C'est comme une caméra au ralenti pour ton code.

### Poser un point d'arrêt

Clique **à gauche du numéro de ligne** : une **pastille rouge 🔴** apparaît. C'est un
**point d'arrêt** (*breakpoint*). Clique de nouveau dessus pour l'enlever.

### Lancer le robot en mode débogage

1. **Arrête** la simulation si elle tourne (Ctrl + C dans le terminal).
2. Clique sur l'icône **Run and Debug** ▶️🐞 dans la barre de gauche.
3. En haut, dans la liste déroulante, choisis **Déboguer la simulation**.
4. Clique sur la flèche verte ▶️ (ou appuie sur **F5**).
5. La fenêtre du simulateur s'ouvre dans l'onglet noVNC, comme d'habitude.

### Quand le programme s'arrête sur ton point d'arrêt

La ligne est **surlignée en jaune** : le programme est en pause **juste avant** cette ligne.

- Le panneau **VARIABLES** (à gauche) montre la valeur de chaque variable.
- **Passe la souris** sur une variable dans le code pour voir sa valeur.

Une barre de boutons apparaît en haut :

| Bouton | Touche | Action |
|---|---|---|
| ▶️ Continue | **F5** | Repartir jusqu'au prochain point d'arrêt |
| ⤵️ Step Over | **F10** | Exécuter **une** ligne, puis s'arrêter de nouveau |
| ⬇️ Step Into | **F11** | **Entrer** dans la méthode appelée sur cette ligne |
| ⬆️ Step Out | **Maj + F11** | Finir la méthode actuelle et revenir à celle qui l'a appelée |
| ⏹️ Stop | **Maj + F5** | Arrêter complètement le programme |

> ⚠️ Pendant la pause, **tout le robot simulé est figé** (le volant aussi). C'est normal !
> Sur un vrai robot, on évite les points d'arrêt quand les moteurs tournent.

### Pour du code Java simple (séances 1 à 3)

Dans `JavaPractice.java`, tu peux cliquer sur **Debug** (au-dessus de `main`) au lieu de
**Run** : les points d'arrêt fonctionnent aussi.

## Outil 6 : les tests automatiques

Un **test** est un petit programme qui vérifie que ton code fait ce qu'il doit faire. Lance
tous les tests avec :

```bash
./gradlew test
```

Tu vois `PASSED` (réussi ✅) ou `FAILED` (échoué ❌) pour chaque test. On en parle à la
[séance 8](../seances/seance-8.md).

---

## 🕵️ Méthode du détective

Quand quelque chose ne marche pas :

1. **Lis le message d'erreur** en entier, calmement. Il dit souvent exactement quoi faire.
2. **Regarde la ligne indiquée**, et celle juste **au-dessus** (le `;` oublié est souvent là).
3. **Compare** avec l'exemple de la séance, caractère par caractère : majuscules, `;`, `{ }`,
   `( )`.
4. **Fais parler ton code** (`println`, tableau de bord) ou **pose un point d'arrêt** pour
   vérifier tes hypothèses.
5. **Explique ton code à voix haute** à quelqu'un (ou à un canard en plastique 🦆 !). Souvent,
   on trouve le bug en l'expliquant.
6. Toujours bloqué après 10 minutes ? **Demande de l'aide** : c'est ce que font les vrais
   programmeurs.
