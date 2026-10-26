# Séance 7 – Contrôler la vitesse 🎯

**Semaine 4 · 1 heure**

## 🎯 Objectifs

- Comprendre la différence entre **boucle ouverte** et **boucle fermée**
- Comprendre l'**anticipation** (kV) et la **correction proportionnelle** (kP)
- Modifier le volant pour qu'il garde **exactement 3000 RPM**
- Régler kV et kP dans le simulateur

---

## Étape 1 – Boucle ouverte ou boucle fermée ? (15 min)

Jusqu'ici, on disait au moteur : « tourne à 50 % ». C'est une **boucle ouverte** : on donne
un ordre, **sans vérifier** le résultat. C'est comme lancer une balle **les yeux fermés**.

Problème : la vitesse obtenue dépend de la batterie, de la friction, des ballons qui
touchent la roue...

Avec une **boucle fermée**, on **mesure** la vitesse (grâce à l'encodeur !) et on
**corrige** sans arrêt :

```
          vitesse voulue        erreur          puissance
 3000 RPM ───────▶ ( − ) ─────────────▶ [contrôleur] ───────▶ moteur ──▶ 🛞
                     ▲                                                   │
                     │           vitesse mesurée (encodeur)              │
                     └───────────────────────────────────────────────────┘
```

> 🚗 **Comme le régulateur de vitesse d'une voiture.** Tu choisis 100 km/h. Dans une montée,
> la voiture ralentit → le régulateur **mesure** l'écart et **appuie plus** sur l'accélérateur.
> Dans une descente, il appuie moins.

Notre contrôleur combine **deux** idées :

### 1. L'anticipation (kV, *feedforward*) : « je sais à peu près combien il faut »

Un NEO tourne à environ **473 RPM par volt**. Pour 3000 RPM, il faut donc environ
3000 ÷ 473 ≈ **6,3 volts**. On l'envoie **tout de suite**, avant même de mesurer :

> tension d'anticipation = **kV** × vitesse voulue = (1 / 473) × 3000 ≈ 6,3 V

### 2. La correction (kP, *proportionnelle*) : « je corrige ce qui manque »

Si le volant est trop lent (erreur positive), on ajoute un peu de puissance ; s'il est trop
rapide, on en enlève. **Plus l'erreur est grande, plus on corrige** :

> correction = **kP** × erreur

C'est le « P » du célèbre contrôleur **PID**. (Les « I » et « D » servent dans d'autres
situations ; pour un volant, kV + kP suffisent.)

> ⚡ Le meilleur : ce calcul se fait **dans le SPARK MAX lui-même, 1000 fois par seconde**.
> Le roboRIO lui dit juste la vitesse voulue, une seule fois !

## Étape 2 – Modifier le sous-système (20 min)

Ouvre `subsystems/flywheel/FlywheelSubsystem.java`.

**1.** Ajoute ces deux `import` avec les autres, en haut du fichier :

```java
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
```

**2.** Sous la ligne de l'encodeur (`private final RelativeEncoder encoder = ...`), ajoute
deux champs :

```java
  // Le contrôleur de vitesse qui tourne À L'INTÉRIEUR du SPARK MAX (1000 fois par seconde !).
  private final SparkClosedLoopController speedController = motor.getClosedLoopController();

  // La vitesse que l'on veut atteindre, en RPM. 0 = arrêté.
  private double targetRpm = 0.0;
```

**3.** Dans le constructeur, **juste avant** la ligne `motor.configure(...)`, ajoute les
réglages de la boucle fermée :

```java
    // Réglages du contrôle de vitesse : correction (PID) + anticipation (feedforward).
    config.closedLoop.pid(FlywheelConstants.KP, FlywheelConstants.KI, FlywheelConstants.KD);
    config.closedLoop.feedForward.kS(FlywheelConstants.KS).kV(FlywheelConstants.KV);
```

**4.** Ajoute ces trois nouvelles méthodes, par exemple après `setPercentOutput` :

```java
  /**
   * Demande au SPARK MAX de garder le volant à une vitesse précise.
   *
   * @param rpm vitesse voulue, en tours par minute du volant
   */
  public void setTargetRpm(double rpm) {
    // Sécurité : on ne dépasse jamais la vitesse maximale.
    targetRpm = MathUtil.clamp(rpm, -FlywheelConstants.MAX_RPM, FlywheelConstants.MAX_RPM);
    speedController.setSetpoint(targetRpm, SparkBase.ControlType.kVelocity);
  }

  /** Vitesse visée, en RPM. */
  public double getTargetRpm() {
    return targetRpm;
  }

  /** Vrai si le volant tourne et qu'il est proche de la vitesse visée. */
  public boolean isAtTargetSpeed() {
    return targetRpm != 0.0
        && Math.abs(targetRpm - getVelocityRpm()) < FlywheelConstants.RPM_TOLERANCE;
  }
```

> 🔁 Tu reconnais `isAtTargetSpeed()` ? C'est presque la méthode `isNear()` de la séance 3 !

**5.** Dans `setPercentOutput(...)` **et** dans `stop()`, ajoute au début :

```java
    targetRpm = 0.0;
```

(Quand on ne contrôle plus la vitesse, il n'y a plus de vitesse visée.)

**6.** Dans `periodic()`, ajoute deux lignes pour voir la cible dans le tableau de bord :

```java
    SmartDashboard.putNumber("Volant/Cible (RPM)", targetRpm);
    SmartDashboard.putBoolean("Volant/A la vitesse", isAtTargetSpeed());
```

## Étape 3 – Modifier la commande (5 min)

Dans `commands/SetFlywheelSpeed.java` :

1. Renomme le champ `percent` en `targetRpm` : clic droit sur `percent` → **Rename Symbol**
   (ou **F2**) → `targetRpm`. Tous les endroits sont renommés d'un coup ! ✨
2. Dans `initialize()`, remplace `flywheel.setPercentOutput(targetRpm);` par :

   ```java
    // Le SPARK MAX garde la vitesse tout seul : on n'a besoin de le dire qu'une fois.
    flywheel.setTargetRpm(targetRpm);
   ```

3. Mets à jour le commentaire `@param` : `la vitesse voulue, en RPM`.

Dans `RobotContainer.java`, remplace les **deux** `FlywheelConstants.TEST_PERCENT_OUTPUT` par
`FlywheelConstants.TARGET_RPM`.

## Étape 4 – Essayer et régler (15 min)

Lance la simulation, **Teleoperated**, Keyboard 0 → Joystick[0], et **maintiens Z**.
Trace **Volant/Vitesse (RPM)** et **Volant/Cible (RPM)** sur le même graphique.

✅ Le volant doit monter à **3000 RPM en environ 1 seconde** et y **rester**.
**Volant/A la vitesse** passe à `true`.

### 🔬 Expériences de réglage

Change **une seule** valeur à la fois dans `FlywheelConstants.java`, relance, et note ce que
tu observes. **Remets la valeur d'origine** après chaque essai.

| # | Réglage | Vitesse finale | Observation |
|---|---|---|---|
| 1 | Valeurs d'origine (KV = 1/473, KP = 0.0003) | | |
| 2 | `KV = 0.0` (seulement la correction) | | |
| 3 | `KV = 0.0` et `KP = 0.002` | | |
| 4 | `KP = 0.0` (seulement l'anticipation) | | |
| 5 | `KV = GEAR_RATIO / 400.0` (anticipation trop forte) et `KP = 0.0` | | |
| 6 | `TARGET_RPM = 4500.0` | | |
| 7 | `TARGET_RPM = 9000.0` (😈 que fait la sécurité ?) | | |

❓ **Questions :**
- Pourquoi la correction seule (essai 2) n'atteint-elle jamais vraiment 3000 RPM ?
- Lequel des deux, kV ou kP, fait **le plus gros** du travail ?
- Sur un vrai robot, qu'est-ce qui pourrait rendre kV un peu faux ? (Indice : friction, batterie...)

## Étape 5 – Sauvegarder (2 min)

[Guide 3](../guides/03-sauvegarder.md). Message : `Séance 7 : contrôle de vitesse en boucle fermée`

## ✅ J'ai réussi si...

- [ ] Je peux expliquer boucle ouverte / boucle fermée avec l'exemple de la voiture
- [ ] Le volant atteint 3000 RPM et y reste quand je maintiens Z
- [ ] **Volant/A la vitesse** devient `true`
- [ ] J'ai rempli le tableau de réglage
- [ ] Mes constantes sont revenues aux valeurs d'origine

## 📖 Mots appris

**Boucle ouverte** · **boucle fermée** · **consigne** (*setpoint*) · **anticipation**
(*feedforward*, kV) · **correction proportionnelle** (kP) · **PID**
