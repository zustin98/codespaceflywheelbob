# Séance 8 – Tests automatiques et vrai robot 🏁

**Semaine 4 · 1 heure**

## 🎯 Objectifs

- Écrire un **test automatique** qui vérifie ton volant
- Voir GitHub vérifier ton code tout seul (le crochet vert ✅)
- Essayer ton code sur le **vrai robot** avec ton animateur
- Relever un défi bonus

---

## Étape 1 – Un test automatique (20 min)

Un **test** est un petit programme qui vérifie que ton code fait bien ce qu'il doit faire.
Les équipes FRC s'en servent pour être sûres de ne rien casser en modifiant le code... la
veille d'une compétition ! 😅

**1.** Dans l'Explorateur, fais un clic droit sur le dossier `src` → **New File...** → tape
le chemin complet :

```
test/java/frc/robot/subsystems/flywheel/FlywheelSubsystemTest.java
```

**2.** Tape ce code :

```java
package frc.robot.subsystems.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import org.junit.jupiter.api.Test;

/**
 * Tests automatiques du volant. Ils sont lancés par GitHub à chaque "push" (le crochet vert ou
 * le X rouge à côté de ton commit) et avec la commande : ./gradlew test
 */
class FlywheelSubsystemTest {
  @Test
  void constantsAreValid() {
    assertTrue(FlywheelConstants.TARGET_RPM > 0, "La vitesse visée doit être positive");
    assertTrue(
        FlywheelConstants.TARGET_RPM <= FlywheelConstants.MAX_RPM,
        "La vitesse visée ne doit pas dépasser la vitesse maximale");
    assertTrue(FlywheelConstants.GEAR_RATIO > 0, "Le rapport de réduction doit être positif");
  }

  @Test
  void flywheelReachesTargetSpeedInSimulation() {
    // Démarre la simulation et "active" le robot, comme dans la Driver Station.
    assertTrue(HAL.initialize(500, 0));
    DriverStationSim.setEnabled(true);
    DriverStationSim.notifyNewData();

    FlywheelSubsystem flywheel = new FlywheelSubsystem();
    flywheel.setTargetRpm(FlywheelConstants.TARGET_RPM);

    // Simule 3 secondes : 150 cycles de 20 ms.
    for (int i = 0; i < 150; i++) {
      flywheel.simulationPeriodic();
      flywheel.periodic();
      if (i % 10 == 0) {
        System.out.printf("t=%.1f s  vitesse=%.0f RPM%n", i * 0.02, flywheel.getVelocityRpm());
      }
    }

    assertEquals(
        FlywheelConstants.TARGET_RPM,
        flywheel.getVelocityRpm(),
        FlywheelConstants.RPM_TOLERANCE,
        "Après 3 secondes, le volant devrait être à la vitesse visée");
    assertTrue(flywheel.isAtTargetSpeed());

    flywheel.stop();
  }
}
```

Ce que fait le second test :
1. il **active** un robot simulé ;
2. il demande `TARGET_RPM` au volant ;
3. il fait avancer la simulation de **3 secondes** (150 cycles de 20 ms) **en un éclair** ;
4. il **vérifie** (`assertEquals`) que la vitesse est à moins de `RPM_TOLERANCE` de la cible.

**3.** Lance les tests :

```bash
./gradlew test
```

Tu dois voir :

```
FlywheelSubsystemTest > constantsAreValid() PASSED
    t=0.0 s  vitesse=0 RPM
    t=0.2 s  vitesse=198 RPM
    ...
FlywheelSubsystemTest > flywheelReachesTargetSpeedInSimulation() PASSED
```

**4.** 🧪 **Casse ton code exprès !** Mets `KV = 0.0` dans `FlywheelConstants`, puis relance
`./gradlew test`. Le test échoue (`FAILED`) et t'explique pourquoi. **Remets `KV` comme
avant** et vérifie que tout repasse en `PASSED`.

## Étape 2 – Le crochet vert de GitHub ✅ (5 min)

Sauvegarde ([guide 3](../guides/03-sauvegarder.md)) avec le message
`Séance 8 : tests automatiques`.

Va sur la page GitHub de ton dépôt. À côté de ton commit, un **point jaune 🟡** apparaît :
GitHub compile ton code et lance tes tests **sur ses propres ordinateurs**. Après 1 à 2
minutes : **crochet vert ✅** !

> 🔍 Clique sur le ✅ (ou sur l'onglet **Actions**) pour voir tout ce que GitHub a fait.

## Étape 3 – Sur le vrai robot ! 🤖 (25 min)

Ton Chromebook ne peut pas se brancher directement au robot. C'est l'**ordinateur du club**
qui va **télécharger ton code depuis GitHub** et l'envoyer au roboRIO (*deploy*).
Voilà pourquoi c'est si important d'avoir fait ton push !

### 🦺 Règles de sécurité (NON négociables)

- **Lunettes de sécurité** pour tout le monde autour du robot.
- **Personne** ne s'approche du volant quand le robot est activé.
- Le **protecteur** du volant est en place.
- Une personne est toujours prête à appuyer sur **Désactiver** (barre d'espace = **arrêt
  d'urgence** dans la Driver Station).
- **Mains loin du volant**, même après la désactivation : il continue de tourner en roue
  libre pendant plusieurs secondes !

### Déroulement (avec ton animateur)

1. Ton animateur télécharge ton dépôt et déploie ton code sur le robot.
2. Dans la Driver Station : mode **TeleOperated** → **Enable**.
3. **Premier essai en boucle ouverte** (vérifie le sens de rotation) : ton animateur
   peut remettre `TEST_PERCENT_OUTPUT` sur le bouton A.
   - Le volant tourne dans le **mauvais sens** ? → `MOTOR_INVERTED = true` dans
     `FlywheelConstants`. Tu vois l'avantage d'avoir toutes les constantes au même endroit ?
4. **Essai en boucle fermée** : maintiens A. Le volant atteint-il 3000 RPM ? Regarde la
   vitesse dans le tableau de bord.
5. Si le volant n'atteint pas tout à fait la vitesse, **ajustez ensemble `KV`** (un peu plus
   grand), puis `KS` (friction, environ 0,1 à 0,2 V).

| Essai | Réglages | Vitesse atteinte | Remarques |
|---|---|---|---|
| 1 | | | |
| 2 | | | |
| 3 | | | |

> 🤔 Le vrai volant se comporte-t-il exactement comme dans la simulation ? Pourquoi ?

## Étape 4 – Défis bonus 🏆 (s'il reste du temps)

Choisis un défi. Écris d'abord le code, puis teste-le dans le simulateur.

1. **Deux vitesses** : ajoute une constante `LOW_TARGET_RPM = 1500.0` et relie le bouton **B**
   (`driverController.b()`, touche **X** du clavier) à une deuxième `SetFlywheelSpeed`.
2. **Prêt à tirer !** : affiche dans le terminal « 🎯 Prêt à tirer ! » une seule fois, au
   moment où le volant atteint sa vitesse. Indice : une variable `boolean` qui se souvient
   de l'état précédent.
3. **Commande qui se termine toute seule** : écris une commande `SpinUpFlywheel` dont
   `isFinished()` renvoie `flywheel.isAtTargetSpeed()`. Pourquoi ne faut-il **pas** arrêter
   le moteur dans son `end()` ?
4. **Ajuster en direct** : lis la vitesse visée depuis le tableau de bord avec
   `SmartDashboard.getNumber("Volant/Cible voulue", FlywheelConstants.TARGET_RPM)`.

## Étape 5 – Bilan 🎉 (5 min)

Félicitations ! En 4 semaines, tu as :
- ✅ appris les bases de Java (variables, conditions, boucles, méthodes, classes) ;
- ✅ programmé un **sous-système** et une **commande** comme les équipes FRC ;
- ✅ contrôlé un moteur NEO en **boucle fermée** ;
- ✅ appris à **déboguer** et à **tester** ton code ;
- ✅ utilisé **Git et GitHub** comme les vrais programmeurs.

C'est **exactement** la même structure de code que dans un robot de compétition FRC. Tu es
prêt·e pour la suite ! 🚀

## ✅ J'ai réussi si...

- [ ] `./gradlew test` affiche `PASSED` pour les 2 tests
- [ ] Mon dernier commit a un **crochet vert ✅** sur GitHub
- [ ] J'ai vu mon code faire tourner le vrai volant (ou j'ai aidé à le tester)
- [ ] J'ai arrêté mon Codespace

## 📖 Mots appris

**Test automatique** · `assertEquals` · **intégration continue** (le ✅ de GitHub) ·
**déployer** (*deploy*) · **Driver Station**
