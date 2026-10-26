# Séance 3 – Méthodes, constantes et structure du robot

**Semaine 2 · 1 heure**

## 🎯 Objectifs

- Écrire tes propres **méthodes**
- Comprendre ce qu'est une **constante** et pourquoi on les range dans un fichier à part
- Comprendre comment un programme de robot FRC est organisé (la boucle de 20 ms)
- Créer le fichier **`FlywheelConstants.java`**

---

## Étape 1 – Les méthodes (15 min)

Une **méthode**, c'est une **recette** : on lui donne des ingrédients (les **paramètres**),
elle fait un travail et peut **renvoyer** un résultat.

```java
//     type renvoyé    nom de la méthode     paramètre
static double rpmToRotationsPerSecond(double rpm) {
  return rpm / SECONDS_PER_MINUTE;   // "return" renvoie le résultat
}
```

Dans `JavaPractice.java` :

**1.** Sous `// ===== Séance 3 : une constante =====` (en haut de la classe), ajoute :

```java
  // "static final" = une valeur qui ne change jamais. Nom en MAJUSCULES par convention.
  static final double SECONDS_PER_MINUTE = 60.0;
```

**2.** Sous `// ===== Séance 3 : des méthodes =====` (en bas, **avant** la dernière `}`) :

```java
  /** Convertit des tours par minute en tours par seconde. */
  static double rpmToRotationsPerSecond(double rpm) {
    return rpm / SECONDS_PER_MINUTE;
  }

  /** Vrai si "measured" est à moins de "tolerance" de "target". */
  static boolean isNear(double target, double measured, double tolerance) {
    return Math.abs(target - measured) < tolerance;
  }
```

**3.** Sous `// ===== Séance 3 : utiliser mes méthodes =====` (dans `main`) :

```java
    System.out.println("3000 RPM = " + rpmToRotationsPerSecond(3000.0) + " tours/s");
    System.out.println("3000 RPM proche de 2950 ? " + isNear(3000.0, 2950.0, 100.0));
```

**Run** ! Tu dois voir `50.0 tours/s` et `true`.

🧪 **Défi :** écris une méthode `rotationsPerSecondToRpm(double rps)` qui fait le calcul
inverse, et vérifie que `rotationsPerSecondToRpm(rpmToRotationsPerSecond(1234.0))` redonne
bien `1234.0`.

> 💡 **Pourquoi une constante `SECONDS_PER_MINUTE` plutôt que `60.0` ?** Parce qu'un nom
> explique ce que le nombre **veut dire**. Un nombre seul au milieu du code s'appelle un
> « nombre magique » 🪄, et c'est une mauvaise habitude : personne ne sait d'où il vient !

## Étape 2 – Comment fonctionne un robot FRC (15 min)

Ton animateur va expliquer ce schéma au tableau :

```
                       Main.java  ── démarre ──▶  Robot.java
                                                      │
                                    crée (une fois)   ▼
                                               RobotContainer.java
                                                ├── sous-systèmes (le volant...)
                                                └── boutons ──▶ commandes

  Toutes les 20 ms (50 fois par seconde) :
  ┌──────────────────────────────────────────────────────────────────┐
  │ Robot.robotPeriodic()                                            │
  │   └── CommandScheduler.run()                                     │
  │         ├── lit les boutons de la manette                         │
  │         ├── démarre / exécute / arrête les commandes             │
  │         └── appelle periodic() de chaque sous-système            │
  └──────────────────────────────────────────────────────────────────┘
```

Ouvre `Robot.java` et trouve :

| Méthode | Quand est-elle appelée ? |
|---|---|
| `Robot()` (le constructeur) | **Une fois**, au démarrage |
| `robotPeriodic()` | **Toutes les 20 ms**, peu importe le mode |
| `autonomousInit()` | **Une fois**, au début de la période autonome (15 premières secondes du match) |
| `teleopInit()` | **Une fois**, au début de la période pilotée |
| `teleopPeriodic()` | Toutes les 20 ms pendant la période pilotée |
| `disabledInit()` | Une fois, quand le robot est désactivé |

❓ **Question :** combien de fois `robotPeriodic()` est-elle appelée pendant un match de
2 min 30 s ? (Réponse : 150 s × 50 = **7500 fois** !)

Les deux grandes idées de la programmation FRC « par commandes » :

- Un **sous-système** (*subsystem*) = une **partie physique** du robot. Il sait **COMMENT**
  faire bouger son matériel. Exemple : le volant sait comment faire tourner son moteur.
- Une **commande** (*command*) = une **action**. Elle décide **QUAND** et **QUOI** faire.
  Exemple : « fais tourner le volant à 3000 RPM tant que le bouton A est enfoncé ».

## Étape 3 – Le fichier des constantes du volant (25 min)

Un robot a **beaucoup** de réglages : numéro CAN du moteur, vitesse visée, limites... On les
range **tous au même endroit** pour les retrouver et les changer facilement, sans fouiller
dans le code. Pour notre volant, ce sera `FlywheelConstants.java`.

**1.** Dans l'Explorateur, fais un **clic droit** sur le dossier `subsystems/flywheel` →
**New File...** (« Nouveau fichier »). Nomme-le exactement :

```
FlywheelConstants.java
```

> ⚠️ Le nom du fichier doit être **identique** au nom de la classe, majuscules comprises.

**2.** Tape (ou copie) ce code. **Lis bien chaque commentaire** : chaque constante sera
utilisée dans les prochaines séances.

```java
package frc.robot.subsystems.flywheel;

/**
 * Toutes les constantes du volant d'inertie (flywheel), au même endroit.
 *
 * <p>Pour changer le comportement du volant (vitesse, réglages, numéro CAN...), c'est ICI qu'on
 * modifie les valeurs, jamais directement dans le code du sous-système.
 */
public final class FlywheelConstants {
  // Constructeur privé : on ne crée jamais d'objet FlywheelConstants, on lit seulement ses
  // valeurs, par exemple FlywheelConstants.TARGET_RPM.
  private FlywheelConstants() {}

  // ---------------------------------------------------------------------------------------
  // Matériel
  // ---------------------------------------------------------------------------------------

  // Numéro CAN du contrôleur SPARK MAX (réglé avec l'application REV Hardware Client).
  public static final int MOTOR_CAN_ID = 10;

  // Mettre à true si le volant tourne dans le mauvais sens.
  public static final boolean MOTOR_INVERTED = false;

  // Limite de courant pour protéger le moteur NEO 1.1 (en ampères).
  public static final int CURRENT_LIMIT_AMPS = 40;

  // Rapport de réduction : nombre de tours du moteur pour 1 tour du volant.
  // 1.0 = le volant est fixé directement sur l'arbre du moteur.
  public static final double GEAR_RATIO = 1.0;

  // ---------------------------------------------------------------------------------------
  // Vitesses (en tours par minute du volant, "RPM")
  // ---------------------------------------------------------------------------------------

  // Puissance utilisée pour les premiers essais SANS contrôle de vitesse (0.5 = 50 %).
  public static final double TEST_PERCENT_OUTPUT = 0.5;

  // Vitesse visée par la commande principale.
  public static final double TARGET_RPM = 3000.0;

  // Vitesse maximale permise. Un NEO 1.1 tourne au maximum à environ 5676 RPM sans charge.
  public static final double MAX_RPM = 5000.0;

  // Écart accepté pour dire que le volant est "à la bonne vitesse".
  public static final double RPM_TOLERANCE = 100.0;

  // ---------------------------------------------------------------------------------------
  // Réglages du contrôle de vitesse (boucle fermée dans le SPARK MAX)
  // ---------------------------------------------------------------------------------------

  // kV : tension (volts) envoyée pour chaque RPM demandé. C'est l'"anticipation"
  // (feedforward) : elle fait presque tout le travail.
  // Un NEO tourne à environ 473 RPM par volt, donc kV vaut à peu près 1 / 473 par tour de
  // moteur. On multiplie par GEAR_RATIO parce que la vitesse est mesurée au volant.
  public static final double KV = GEAR_RATIO / 473.0;

  // kS : petite tension (volts) pour vaincre la friction. 0 en simulation.
  public static final double KS = 0.0;

  // kP : correction proportionnelle. Plus l'erreur de vitesse est grande, plus on corrige.
  public static final double KP = 0.0003;

  // kI et kD : on ne s'en sert pas pour un volant simple.
  public static final double KI = 0.0;
  public static final double KD = 0.0;

  // ---------------------------------------------------------------------------------------
  // Simulation seulement
  // ---------------------------------------------------------------------------------------

  // Moment d'inertie du volant (kg·m²) : plus il est grand, plus le volant est "lourd" à
  // faire tourner et plus il met de temps à accélérer.
  public static final double MOMENT_OF_INERTIA_KG_M2 = 0.002;
}
```

**3.** Vérifie qu'il n'y a **rien de souligné en rouge**, puis compile tout le projet. Dans le
terminal :

```bash
./gradlew build
```

Tu dois voir `BUILD SUCCESSFUL` ✅. Sinon, voir le [guide 5](../guides/05-deboguer.md).

> ❓ Pourquoi `KV`, `KP`... ? Ce sont des noms de réglages célèbres en robotique. Pas de
> panique, on les expliquera à la séance 7 !

## Étape 4 – Sauvegarder (5 min)

[Guide 3](../guides/03-sauvegarder.md). Message : `Séance 3 : méthodes et constantes du volant`

## ✅ J'ai réussi si...

- [ ] Mes méthodes `rpmToRotationsPerSecond` et `isNear` fonctionnent
- [ ] Je peux expliquer la différence entre un **sous-système** et une **commande**
- [ ] Je sais que le robot exécute son code **toutes les 20 ms**
- [ ] `FlywheelConstants.java` existe et `./gradlew build` affiche `BUILD SUCCESSFUL`

## 📖 Mots appris

**Méthode** · **paramètre** · `return` · **constante** (`static final`) · **nombre magique**
· **sous-système** · **commande** · **boucle de 20 ms**
