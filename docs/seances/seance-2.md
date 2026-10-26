# Séance 2 – Les bases de Java

**Semaine 1 · 1 heure**

## 🎯 Objectifs

- Ranger des informations dans des **variables**
- Faire des **calculs**
- Prendre des décisions avec des **conditions** (`if`)
- Répéter des actions avec une **boucle** (`for`)

## 🧰 Avant de commencer

Rouvre ton Codespace depuis **https://github.com/codespaces** ([guide 2, partie D](../guides/02-codespace.md#partie-d--revenir-dans-ton-codespace-à-chaque-séance)),
puis ouvre `practice/JavaPractice.java`.

---

## Étape 1 – Les variables (15 min)

Une **variable**, c'est une **boîte avec une étiquette** dans laquelle on range une valeur.
En Java, chaque boîte a un **type** qui dit ce qu'on peut mettre dedans.

```java
int teamNumber = 1234;
// │      │        │
// │      │        └── la valeur rangée dans la boîte
// │      └── le nom (l'étiquette) de la boîte
// └── le type : int = nombre entier
```

Sous le commentaire `// ===== Séance 2 : les variables =====`, **remplace** la ligne
`// TODO (séance 2)` par :

```java
    int teamNumber = 1234; // un nombre entier
    double targetRpm = 3000.0; // un nombre à virgule
    boolean isRobotEnabled = true; // vrai ou faux
    String robotName = "Volant"; // du texte

    System.out.println("Équipe " + teamNumber + ", robot : " + robotName);
    System.out.println("Vitesse visée : " + targetRpm + " RPM");
```

> 💡 Le `+` entre un texte et une variable **colle** les morceaux ensemble.

Clique sur **Run**. Puis :

- ✏️ Mets le vrai numéro de ton équipe FRC dans `teamNumber`.
- 🧪 Essaie `int teamNumber = 12.5;` → erreur ! Un `int` ne peut pas contenir de virgule.
  Remets un nombre entier.

> 🇬🇧 **Pourquoi les noms sont en anglais ?** Dans toutes les équipes FRC du monde, le code
> s'écrit en anglais : tu pourras lire le code des autres équipes et elles pourront lire le
> tien ! Les **commentaires** (`//`), eux, peuvent être en français.

## Étape 2 – Les calculs (10 min)

Un volant qui tourne à 3000 tours **par minute**, ça fait combien de tours **par seconde** ?

Sous `// ===== Séance 2 : les calculs =====` :

```java
    double rotationsPerSecond = targetRpm / 60.0;
    System.out.println("Ça fait " + rotationsPerSecond + " tours par seconde.");
```

**Run** → `50.0` tours par seconde. C'est rapide !

🧪 **Défi rapide :** le volant a un diamètre de 10 cm. Sa circonférence est
`3.14159 * 0.10` mètre. Calcule et affiche la **vitesse du bord de la roue** en mètres par
seconde (circonférence × tours par seconde). Indice : environ 15,7 m/s, soit 56 km/h !

## Étape 3 – Les conditions (15 min)

Un robot doit souvent **prendre des décisions** : « *si* le volant tourne assez vite, *alors*
on peut lancer le ballon ».

Sous `// ===== Séance 2 : les conditions =====` :

```java
    double measuredRpm = 2950.0;
    double error = targetRpm - measuredRpm;
    if (!isRobotEnabled) {
      System.out.println("Le robot est désactivé.");
    } else if (Math.abs(error) < 100.0) {
      System.out.println("Le volant est à la bonne vitesse !");
    } else if (error > 0) {
      System.out.println("Trop lent : il manque " + error + " RPM.");
    } else {
      System.out.println("Trop vite de " + (-error) + " RPM.");
    }
```

Explications :
- `error` = l'**erreur** : la différence entre ce qu'on veut et ce qu'on mesure. On
  reparlera de l'erreur à la séance 7, c'est très important pour contrôler un robot !
- `!isRobotEnabled` se lit « **pas** activé ».
- `Math.abs(error)` enlève le signe moins : `Math.abs(-50.0)` donne `50.0`.
- Java teste les conditions **dans l'ordre** et s'arrête à la **première** qui est vraie.

🧪 **Expériences :** change `measuredRpm` pour obtenir chacun des 4 messages. Quelles valeurs
as-tu utilisées ?

## Étape 4 – Les boucles (10 min)

Une **boucle** répète des instructions. Simulons un volant qui accélère de 500 RPM à chaque
étape.

Sous `// ===== Séance 2 : une boucle =====` :

```java
    double speed = 0.0;
    for (int step = 1; step <= 6; step++) {
      speed = speed + 500.0;
      System.out.println("Étape " + step + " : " + speed + " RPM");
    }
```

`for (int step = 1; step <= 6; step++)` se lit : « commence avec `step = 1`, **tant que**
`step <= 6`, répète le bloc, et après chaque tour ajoute 1 à `step` (`step++`) ».

🧪 **Défi :** modifie la boucle pour qu'elle **s'arrête dès que** `speed` atteint
`targetRpm`. Indice : change la condition du milieu.

> 🤖 **Le savais-tu ?** Ton robot a lui aussi une boucle géante : il répète son code
> **50 fois par seconde**, pendant tout le match ! On la découvre à la prochaine séance.

## Étape 5 – Sauvegarder (5 min)

[Guide 3](../guides/03-sauvegarder.md). Message : `Séance 2 : variables, conditions et boucles`

## ✅ J'ai réussi si...

- [ ] Je connais les types `int`, `double`, `boolean` et `String`
- [ ] J'ai obtenu les 4 messages différents de la condition
- [ ] Ma boucle s'arrête à la vitesse visée
- [ ] Mon travail est sauvegardé sur GitHub

## 📖 Mots appris

**Variable** · **type** · **condition** (`if` / `else`) · **boucle** (`for`) · **erreur**
(différence entre la cible et la mesure)
