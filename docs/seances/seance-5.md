# Séance 5 – Ma première commande

**Semaine 3 · 1 heure**

## 🎯 Objectifs

- Comprendre le **cycle de vie** d'une commande
- Écrire la commande `SetFlywheelSpeed`
- Relier la commande au **bouton A** de la manette
- Faire tourner le volant dans le simulateur et tracer sa vitesse

---

## Étape 1 – La vie d'une commande (10 min)

Une commande passe par 4 étapes. Toutes sont des méthodes que **WPILib appelle pour toi** :

```
  Bouton A enfoncé
        │
        ▼
  initialize()  ──── une seule fois, au départ
        │
        ▼
  ┌─▶ execute()  ──── toutes les 20 ms
  │     │
  │     ▼
  │   isFinished() ? ── "est-ce que j'ai fini ?"
  │     │ non
  └─────┘
        │ oui  (ou bouton relâché = commande interrompue)
        ▼
  end(interrupted) ── une seule fois, à la fin
```

Pour notre volant :
- **initialize** : démarrer le moteur ;
- **execute** : rien à faire, le moteur continue de tourner ;
- **isFinished** : jamais fini tout seul (`false`), on attend que le pilote relâche le bouton ;
- **end** : arrêter le moteur. **Toujours** penser à arrêter ce qu'on a démarré !

## Étape 2 – Écrire la commande (20 min)

**1.** Clic droit sur le dossier `frc/robot` → **New File...** → tape :

```
commands/SetFlywheelSpeed.java
```

(Le dossier `commands` est créé automatiquement grâce au `/`.)

**2.** Tape ce code :

```java
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.flywheel.FlywheelSubsystem;

/** Commande qui fait tourner le volant tant qu'elle est active, puis l'arrête. */
public class SetFlywheelSpeed extends Command {
  private final FlywheelSubsystem flywheel;
  private final double percent;

  /**
   * @param flywheel le sous-système volant à contrôler
   * @param percent la puissance, entre -1.0 et 1.0
   */
  public SetFlywheelSpeed(FlywheelSubsystem flywheel, double percent) {
    this.flywheel = flywheel;
    this.percent = percent;

    // On "réserve" le volant : deux commandes ne peuvent pas l'utiliser en même temps.
    addRequirements(flywheel);
  }

  @Override
  public void initialize() {
    flywheel.setPercentOutput(percent);
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    // Jamais fini tout seul : la commande tourne tant que le bouton est enfoncé.
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    flywheel.stop();
  }
}
```

> 💡 **`this.flywheel = flywheel;`** Le paramètre et le champ ont le même nom. `this.flywheel`
> veut dire « **mon** champ `flywheel` » ; `flywheel` tout seul, c'est le paramètre reçu.

## Étape 3 – Relier la commande au bouton A (10 min)

Ouvre `RobotContainer.java`.

**1.** Dans `configureBindings()`, remplace `// TODO (séance 5) : quand le bouton A...` par :

```java
    // Tant que le bouton A est enfoncé, le volant tourne.
    // Quand on relâche le bouton, la commande se termine et le volant s'arrête.
    driverController.a().whileTrue(
        new SetFlywheelSpeed(flywheel, FlywheelConstants.TEST_PERCENT_OUTPUT));
```

**2.** Dans le constructeur `RobotContainer()`, remplace
`// TODO (séance 5) : ajouter un bouton "Lancer le volant"...` par :

```java
    // Ajoute un bouton cliquable dans le tableau de bord (pratique en simulation).
    SmartDashboard.putData(
        "Lancer le volant",
        new SetFlywheelSpeed(flywheel, FlywheelConstants.TEST_PERCENT_OUTPUT));
```

**3.** Ajoute les `import` manquants avec **Ctrl + .** sur chaque mot souligné :
`SetFlywheelSpeed`, `FlywheelConstants`, `SmartDashboard`.

## Étape 4 – Essayer dans le simulateur ! (15 min)

1. Lance la simulation ([guide 4](../guides/04-simulateur.md)).
2. **Robot State** → **Teleoperated**.
3. Glisse **Keyboard 0** dans **Joystick[0]**.
4. Clique dans la fenêtre du simulateur, puis **maintiens la touche Z** (= bouton A).
5. Regarde **Volant/Vitesse (RPM)** monter ! 🚀 Relâche Z : la vitesse redescend.
6. Trace un graphique de la vitesse (**Plot** → **New Plot Window**, voir le
   [guide 4](../guides/04-simulateur.md#tracer-un-graphique-)).

### 🔬 Expériences (note tes résultats !)

| Expérience | Vitesse atteinte (RPM) |
|---|---|
| `TEST_PERCENT_OUTPUT = 0.5` | |
| `TEST_PERCENT_OUTPUT = 0.25` | |
| `TEST_PERCENT_OUTPUT = 1.0` | |
| `TEST_PERCENT_OUTPUT = 1.5` (😈 que se passe-t-il ? pourquoi ?) | |
| `MOMENT_OF_INERTIA_KG_M2 = 0.01` (volant 5 fois plus lourd) : qu'est-ce qui change ? | |

> ⚠️ Remets `TEST_PERCENT_OUTPUT = 0.5` et `MOMENT_OF_INERTIA_KG_M2 = 0.002` après tes
> expériences.

❓ **Question piège :** à 50 % de puissance, le volant atteint environ 2900 RPM. Mais sur un
vrai robot, quand la **batterie est faible**, il tournera **moins vite**. Et quand un ballon
le touche, il **ralentit**. Comment garder **exactement** 3000 RPM ? → Réponse à la séance 7 !

## Étape 5 – Sauvegarder (2 min)

[Guide 3](../guides/03-sauvegarder.md). Message : `Séance 5 : commande SetFlywheelSpeed`

## ✅ J'ai réussi si...

- [ ] Je connais les 4 méthodes d'une commande
- [ ] Le volant tourne quand je maintiens Z, et s'arrête quand je relâche
- [ ] Le bouton **Lancer le volant** du tableau de bord fonctionne aussi
- [ ] J'ai rempli le tableau d'expériences

## 📖 Mots appris

**Commande** · `initialize` / `execute` / `isFinished` / `end` · `addRequirements` ·
**liaison de bouton** (`whileTrue`) · `this`
