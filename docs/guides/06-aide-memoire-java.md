# Guide 6 – Aide-mémoire Java ☕

## Les règles de base

- Chaque instruction se termine par un **point-virgule** `;`
- Les **accolades** `{ }` regroupent un bloc de code. Chaque `{` a sa `}`.
- Java fait la différence entre **majuscules et minuscules** : `speed` ≠ `Speed`.
- Un **commentaire** n'est pas exécuté : `// une ligne` ou `/* plusieurs lignes */`

## Les types de variables

| Type | Contient | Exemple |
|---|---|---|
| `int` | un nombre entier | `int canId = 10;` |
| `double` | un nombre à virgule (avec un **point** !) | `double targetRpm = 3000.0;` |
| `boolean` | `true` (vrai) ou `false` (faux) | `boolean isEnabled = true;` |
| `String` | du texte, entre guillemets | `String name = "Volant";` |

## Les noms (conventions)

| Quoi | Style | Exemple |
|---|---|---|
| Variable, méthode | `camelCase` (commence par une minuscule) | `targetRpm`, `getVelocityRpm()` |
| Classe | `PascalCase` (commence par une majuscule) | `FlywheelSubsystem` |
| Constante | `MAJUSCULES_AVEC_SOULIGNÉS` | `MAX_RPM` |

## Les opérateurs

| Calcul | | Comparaison | | Logique | |
|---|---|---|---|---|---|
| `+` | plus | `==` | égal | `&&` | ET |
| `-` | moins | `!=` | différent | `\|\|` | OU |
| `*` | fois | `<` `>` | plus petit, plus grand | `!` | NON |
| `/` | divisé | `<=` `>=` | plus petit ou égal... | | |

`Math.abs(x)` donne la valeur absolue (enlève le signe moins) : `Math.abs(-5.0)` → `5.0`

## Les conditions

```java
if (speed > 3000.0) {
  System.out.println("Trop vite !");
} else if (speed > 2900.0) {
  System.out.println("Parfait !");
} else {
  System.out.println("Trop lent...");
}
```

## Les boucles

```java
for (int i = 1; i <= 5; i++) {
  System.out.println("Tour numéro " + i);
}
```

## Les méthodes

```java
//  type renvoyé   nom                   paramètre
static double rpmToRotationsPerSecond(double rpm) {
  return rpm / 60.0;  // renvoie le résultat
}

// "void" = la méthode ne renvoie rien, elle fait juste une action.
public void stop() {
  motor.stopMotor();
}
```

## Les classes et les objets

```java
// Une classe est un PLAN (comme un plan de maison).
public class FlywheelSubsystem extends SubsystemBase {
  private final SparkMax motor = ...;  // un champ : une variable qui appartient à l'objet

  public FlywheelSubsystem() { ... }    // le constructeur : appelé à la création de l'objet
}

// Un objet est une MAISON construite à partir du plan, avec "new".
FlywheelSubsystem flywheel = new FlywheelSubsystem();
flywheel.stop();  // on appelle une méthode de l'objet avec un point
```

| Mot-clé | Sens |
|---|---|
| `public` | utilisable par les autres classes |
| `private` | utilisable seulement dans cette classe |
| `static` | appartient à la classe elle-même, pas à un objet |
| `final` | ne peut plus changer après avoir reçu sa valeur |
| `new` | crée un nouvel objet |
| `this` | « moi-même », l'objet actuel |
| `extends` | « est une sorte de » (héritage) : `FlywheelSubsystem extends SubsystemBase` |
| `@Override` | « je remplace une méthode qui existe déjà dans la classe parent » |

## Raccourcis clavier utiles (dans le Codespace)

| Raccourci | Action |
|---|---|
| **Ctrl + S** | Enregistrer |
| **Ctrl + Z** / **Ctrl + Y** | Annuler / Rétablir |
| **Ctrl + Espace** | Suggestions d'autocomplétion |
| **Ctrl + .** | Correction rapide (ajouter un import...) |
| **Ctrl + /** | Mettre en commentaire / enlever le commentaire |
| **Maj + Alt + F** | Bien aligner (formater) le code |
| **F12** | Aller à la définition d'une méthode ou d'une classe |
| **Ctrl + `** | Afficher / cacher le terminal |
| **Ctrl + C** (dans le terminal) | Arrêter le programme en cours |

## Les mots du robot FRC

| Mot | Sens |
|---|---|
| **RPM** | *Revolutions Per Minute* : tours par minute |
| **NEO 1.1** | Le moteur électrique sans balais de REV Robotics |
| **SPARK MAX** | Le contrôleur qui dit au moteur NEO quelle puissance utiliser |
| **Encodeur** | Le capteur qui mesure la rotation (intégré dans le NEO) |
| **CAN** | Le réseau de fils qui relie le roboRIO aux contrôleurs de moteur. Chaque appareil a un **numéro CAN** unique. |
| **roboRIO** | L'ordinateur du robot, où ton code s'exécute |
| **Sous-système** | Une partie physique du robot (volant, bras, roues...) |
| **Commande** | Une action que le robot fait avec un ou plusieurs sous-systèmes |
| **Constante** | Une valeur fixe, rangée dans un fichier de constantes |
