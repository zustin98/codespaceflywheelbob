# Séance 6 – Déboguer comme un pro 🕵️

**Semaine 3 · 1 heure**

## 🎯 Objectifs

- Utiliser le **débogueur** : points d'arrêt, pas à pas, valeurs des variables
- Faire « parler » ton code avec `println`
- Trouver et corriger des bugs de **compilation** et de **logique**

📖 Garde le [guide 5 – Déboguer](../guides/05-deboguer.md) ouvert dans un onglet.

---

## Étape 1 – Mon premier point d'arrêt (15 min)

**1.** Ouvre `commands/SetFlywheelSpeed.java`.

**2.** Clique à gauche du numéro de la ligne `flywheel.setPercentOutput(percent);` (dans
`initialize()`) : une **pastille rouge 🔴** apparaît.

**3.** Pose un deuxième point d'arrêt sur la ligne `flywheel.stop();` (dans `end()`).

**4.** Si la simulation tourne, arrête-la (**Ctrl + C** dans le terminal).

**5.** Icône **Run and Debug** ▶️🐞 à gauche → choisis **Déboguer la simulation** → **F5**.

**6.** Dans l'onglet noVNC : **Teleoperated**, Keyboard 0 → Joystick[0], puis **appuie sur Z**.

**7.** 💥 Le programme s'arrête ! Retourne dans l'onglet du Codespace : la ligne est
surlignée en **jaune**.

**8.** Regarde le panneau **VARIABLES** à gauche :
   - Ouvre **this** : quelle est la valeur de `percent` ? ______
   - Passe la souris sur `percent` dans le code : même valeur ?

**9.** Appuie sur **F11** (*Step Into*) : tu **entres** dans la méthode `setPercentOutput`
du sous-système ! Appuie sur **F10** pour avancer ligne par ligne, puis **F5** pour
continuer.

**10.** Relâche Z dans le simulateur. Le programme s'arrête dans `end()`.
   - ❓ Quelle est la valeur de `interrupted` ? ______ Pourquoi ?

   > 💡 Réponse : `true`, parce que la commande ne s'est pas terminée toute seule
   > (`isFinished()` renvoie toujours `false`) : c'est le relâchement du bouton qui l'a
   > **interrompue**.

**11.** **Maj + F5** pour arrêter le débogage. Enlève les pastilles rouges (clique dessus).

### Bonus : le point d'arrêt conditionnel

Pose un point d'arrêt dans `periodic()` de `FlywheelSubsystem.java`, sur la ligne
`SmartDashboard.putNumber("Volant/Vitesse (RPM)", ...)`. Problème : il s'arrête toutes les
20 ms ! 😵

Fais un **clic droit** sur la pastille → **Edit Breakpoint...** → **Expression** → tape :

```java
getVelocityRpm() > 2000
```

Maintenant, le programme s'arrête **seulement** quand le volant dépasse 2000 RPM. Pratique !

## Étape 2 – Faire parler le code (5 min)

Dans `initialize()` de `SetFlywheelSpeed`, ajoute :

```java
    System.out.println("Volant : démarrage à " + (percent * 100) + " %");
```

Et dans `end()` :

```java
    System.out.println("Volant : arrêt (interrompu = " + interrupted + ")");
```

Lance la simulation normalement (`./gradlew simulateJava`), appuie et relâche Z plusieurs
fois. Regarde le terminal. 📢

## Étape 3 – La chasse aux bugs 🐛 (30 min)

### Partie A : les bugs de compilation

Chaque bout de code ci-dessous a **une** erreur. Trouve-la **sans l'ordinateur**, puis vérifie
en la copiant temporairement dans ton code.

```java
// Bug 1
double targetRpm = 3000.0
```

```java
// Bug 2
double percent = "0.5";
```

```java
// Bug 3
private final flywheelSubsystem flywheel = new FlywheelSubsystem();
```

```java
// Bug 4
public double getVelocityRpm() {
  encoder.getVelocity();
}
```

```java
// Bug 5
if (speed > 3000.0) {
  System.out.println("Trop vite !");
```

### Partie B : les bugs de logique (le code compile, mais...)

Pour chacun, **prédis** ce que fera le volant, puis **essaie dans le simulateur**. Remets le
bon code après chaque essai !

| # | Modification | Ta prédiction | Ce qui se passe vraiment |
|---|---|---|---|
| 6 | Dans `end()`, efface `flywheel.stop();` | | |
| 7 | Dans `isFinished()`, remplace `false` par `true` | | |
| 8 | Dans `RobotContainer`, remplace `whileTrue` par `onTrue` | | |
| 9 | Dans `FlywheelConstants`, mets `MOTOR_INVERTED = true` | | |
| 10 | Dans `FlywheelConstants`, mets `GEAR_RATIO = 2.0` | | |

> 💡 Pour le bug 7, un point d'arrêt dans `end()` aide beaucoup !

### Partie C : le jeu du saboteur 😈 (en équipe de deux)

1. Ton ou ta partenaire regarde ailleurs.
2. Tu ajoutes **un seul** bug dans **ton** code (compilation **ou** logique).
3. Ton ou ta partenaire a 5 minutes pour le trouver avec les outils du
   [guide 5](../guides/05-deboguer.md).
4. On inverse les rôles !

> ⚠️ **Avant de sauvegarder, vérifie que tous les bugs sont corrigés !** Utilise l'onglet
> **Source Control** 🌿 : clique sur un fichier pour voir exactement ce qui a changé.

## Étape 4 – Sauvegarder (5 min)

1. Vérifie que la simulation marche comme à la fin de la séance 5.
2. [Guide 3](../guides/03-sauvegarder.md). Message : `Séance 6 : débogage`

## ✅ J'ai réussi si...

- [ ] Je sais poser un point d'arrêt et lancer **Déboguer la simulation**
- [ ] Je connais **F5**, **F10** et **F11**
- [ ] J'ai trouvé les 5 bugs de compilation et expliqué les 5 bugs de logique
- [ ] Mon code fonctionne de nouveau et il est sauvegardé

## 📖 Mots appris

**Point d'arrêt** (*breakpoint*) · **débogueur** · **pas à pas** (*step*) · **bug de
compilation** · **bug de logique**

<details>
<summary>🔑 Réponses (pour vérifier APRÈS avoir cherché !)</summary>

1. Il manque le `;` à la fin.
2. `"0.5"` est du texte (`String`), pas un nombre. Il faut `0.5`.
3. `flywheelSubsystem` doit commencer par une majuscule : `FlywheelSubsystem`.
4. Il manque `return` : `return encoder.getVelocity();`
5. Il manque l'accolade fermante `}`.
6. Le volant ne s'arrête jamais quand on relâche Z (il continue sur sa lancée).
7. La commande se termine immédiatement : le volant donne juste un tout petit coup.
8. Le volant démarre au premier appui et ne s'arrête **plus jamais** (la commande ne finit
   jamais et n'est plus interrompue au relâchement).
9. La vitesse est **négative** : le volant tourne à l'envers.
10. La vitesse affichée est **divisée par 2** (et la simulation est plus lente) : le volant
    tourne 2 fois moins vite que le moteur.

</details>
