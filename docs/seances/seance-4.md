# Séance 4 – Le sous-système volant

**Semaine 2 · 1 heure**

## 🎯 Objectifs

- Découvrir le matériel : moteur **NEO 1.1**, contrôleur **SPARK MAX**, **encodeur**, réseau **CAN**
- Comprendre ce qu'est une **classe** et un **objet**
- Écrire `FlywheelSubsystem.java` : faire tourner le moteur et lire sa vitesse
- Voir la vitesse du volant dans le simulateur

---

## Étape 1 – Le matériel (10 min)

Ton animateur te montre les vraies pièces :

```
  roboRIO ══ fil CAN ══▶ SPARK MAX (n° CAN 10) ══ 3 gros fils ══▶ NEO 1.1 ══ arbre ══▶ 🛞 volant
  (le cerveau)           (le "traducteur")       + fil capteur    (moteur +          
                                                                   encodeur intégré)
```

| Pièce | Rôle |
|---|---|
| **roboRIO** | L'ordinateur du robot. Ton code Java s'exécute dessus. |
| **Réseau CAN** | Un fil jaune et vert qui relie le roboRIO à tous les contrôleurs. Chaque appareil a un **numéro CAN** unique, comme une adresse. Le nôtre : **10** (`MOTOR_CAN_ID`). |
| **SPARK MAX** | Le **contrôleur de moteur**. Le roboRIO lui dit « 50 % de puissance » et le SPARK MAX envoie le bon courant au moteur. |
| **NEO 1.1** | Un moteur **sans balais** (*brushless*) très puissant : jusqu'à environ 5676 RPM. |
| **Encodeur** | Un capteur **dans** le NEO qui compte les tours. Il permet de connaître la **vitesse** réelle du volant. |

## Étape 2 – Classes et objets (5 min)

- Une **classe**, c'est un **plan** (comme le plan d'une maison).
- Un **objet**, c'est une **chose construite** à partir du plan, avec le mot `new`.

```java
SparkMax motor = new SparkMax(10, MotorType.kBrushless);
//                 └── "construis-moi un objet SparkMax, numéro CAN 10, moteur sans balais"
```

Ensuite on peut demander des choses à l'objet avec un **point** : `motor.set(0.5);` →
« moteur, tourne à 50 % ».

## Étape 3 – Écrire le sous-système (30 min)

**1.** Clic droit sur le dossier `subsystems/flywheel` → **New File...** →
`FlywheelSubsystem.java`

**2.** Tape ce code **morceau par morceau**, en lisant les commentaires. Ne copie pas tout
d'un coup : comprendre, c'est le but !

```java
package frc.robot.subsystems.flywheel;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Le sous-système "volant d'inertie" : un moteur NEO 1.1 branché à un SPARK MAX, avec
 * l'encodeur intégré au moteur pour mesurer la vitesse.
 */
public class FlywheelSubsystem extends SubsystemBase {
  // Le contrôleur de moteur SPARK MAX. "kBrushless" = moteur sans balais (comme le NEO).
  private final SparkMax motor = new SparkMax(FlywheelConstants.MOTOR_CAN_ID, MotorType.kBrushless);

  // L'encodeur intégré au NEO : il mesure combien de tours fait le moteur, et à quelle vitesse.
  private final RelativeEncoder encoder = motor.getEncoder();

  // La simulation n'existe que lorsqu'on n'est pas sur un vrai robot.
  private FlywheelSimulation simulation;

  public FlywheelSubsystem() {
    // On prépare tous les réglages du SPARK MAX dans un objet "config"...
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .inverted(FlywheelConstants.MOTOR_INVERTED)
        .idleMode(IdleMode.kCoast) // "Coast" = roue libre : le volant ralentit doucement.
        .smartCurrentLimit(FlywheelConstants.CURRENT_LIMIT_AMPS);

    // L'encodeur compte les tours du MOTEUR. On divise par le rapport de réduction pour
    // obtenir directement la vitesse du VOLANT.
    config.encoder
        .positionConversionFactor(1.0 / FlywheelConstants.GEAR_RATIO)
        .velocityConversionFactor(1.0 / FlywheelConstants.GEAR_RATIO);

    // ... puis on envoie tous les réglages au SPARK MAX d'un seul coup.
    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    if (RobotBase.isSimulation()) {
      simulation =
          new FlywheelSimulation(
              motor, FlywheelConstants.GEAR_RATIO, FlywheelConstants.MOMENT_OF_INERTIA_KG_M2);
    }
  }

  /**
   * Fait tourner le volant à une puissance fixe, SANS contrôle de vitesse.
   *
   * @param percent entre -1.0 (pleine puissance à reculons) et 1.0 (pleine puissance)
   */
  public void setPercentOutput(double percent) {
    motor.set(MathUtil.clamp(percent, -1.0, 1.0));
  }

  /** Arrête le moteur. */
  public void stop() {
    motor.stopMotor();
  }

  /** Vitesse actuelle du volant, en RPM, mesurée par l'encodeur. */
  public double getVelocityRpm() {
    return encoder.getVelocity();
  }

  /** Appelé automatiquement toutes les 20 ms. On affiche nos valeurs dans le tableau de bord. */
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Volant/Vitesse (RPM)", getVelocityRpm());
    SmartDashboard.putNumber("Volant/Sortie moteur (-1 a 1)", motor.getAppliedOutput());
    SmartDashboard.putNumber("Volant/Courant (A)", motor.getOutputCurrent());
  }

  /** Appelé automatiquement toutes les 20 ms, seulement en simulation. */
  @Override
  public void simulationPeriodic() {
    simulation.update();
  }
}
```

### Ce qu'il faut comprendre

| Code | Explication |
|---|---|
| `import ...` | « Je vais utiliser des classes écrites par d'autres » (WPILib, REV). |
| `extends SubsystemBase` | Notre classe **est une sorte de** sous-système WPILib : elle en reçoit tous les pouvoirs. |
| `private final` | Le champ appartient à **cette** classe (`private`) et ne changera plus (`final`). |
| `FlywheelConstants.MOTOR_CAN_ID` | On lit la valeur **dans le fichier des constantes**. Jamais de nombre magique ! |
| `MathUtil.clamp(percent, -1.0, 1.0)` | **Sécurité** : si quelqu'un demande 150 %, on envoie 100 %. |
| `@Override public void periodic()` | On **remplace** la méthode `periodic()` de `SubsystemBase`. WPILib l'appelle toutes les 20 ms. |

## Étape 4 – Ajouter le volant au robot (5 min)

Ouvre `RobotContainer.java`. Remplace la ligne `// TODO (séance 4) : créer le sous-système du volant ici.`
par :

```java
  private final FlywheelSubsystem flywheel = new FlywheelSubsystem();
```

`FlywheelSubsystem` est souligné en rouge ? C'est normal : il manque l'`import`. Place ton
curseur sur le mot, appuie sur **Ctrl + .** et choisis
**Import 'FlywheelSubsystem' (frc.robot.subsystems.flywheel)**.

## Étape 5 – Tester dans le simulateur (10 min)

1. Ouvre le bureau du simulateur et lance la simulation ([guide 4](../guides/04-simulateur.md)).
2. Dans la fenêtre, ouvre **NetworkTables** → **SmartDashboard** : tu dois voir
   **Volant/Vitesse (RPM) = 0**.
3. Ouvre le panneau **Other Devices** : tu vois **SPARK MAX [10]**. C'est ton contrôleur
   simulé !

Le volant ne tourne pas encore : c'est normal, **aucune commande** ne lui demande de
tourner. C'est pour la prochaine séance ! 😉

## Étape 6 – Sauvegarder (2 min)

[Guide 3](../guides/03-sauvegarder.md). Message : `Séance 4 : sous-système volant`

## ✅ J'ai réussi si...

- [ ] Je sais à quoi servent le SPARK MAX, le NEO, l'encodeur et le numéro CAN
- [ ] `FlywheelSubsystem.java` compile sans erreur
- [ ] Je vois **Volant/Vitesse (RPM)** dans le simulateur
- [ ] Mon travail est sauvegardé sur GitHub

## 📖 Mots appris

**Classe** · **objet** · `new` · `import` · `extends` · `@Override` · **SPARK MAX** ·
**NEO** · **encodeur** · **CAN**
