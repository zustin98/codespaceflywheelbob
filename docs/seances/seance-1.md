# Séance 1 – Mon atelier de programmation

**Semaine 1 · 1 heure**

## 🎯 Objectifs

- Avoir ton propre projet sur GitHub et ton Codespace
- Exécuter ton premier programme Java
- Sauvegarder ton travail sur GitHub

## 🧰 Avant de commencer

- [ ] J'ai un compte GitHub ([guide 1](../guides/01-compte-github.md))

---

## Étape 1 – Créer ton projet et ton Codespace (20 min)

Suis le [guide 2](../guides/02-codespace.md), **parties A, B et C**.

Pendant que ton Codespace s'installe (3 à 5 minutes), ton animateur va te présenter le robot
et le **volant d'inertie** que tu vas programmer.

> 🏎️ **Le volant d'inertie** (*flywheel*) est une roue lourde qui tourne très vite. Quand un
> ballon la touche, il est lancé comme par un canon. Pour que tous les tirs aillent à la même
> distance, la roue doit tourner **toujours à la même vitesse**, mesurée en **RPM** (tours par
> minute). C'est ça, ton défi des 4 prochaines semaines !

## Étape 2 – Explorer le projet (10 min)

Dans l'**Explorateur** 📄, ouvre les dossiers `src` → `main` → `java` → `frc` → `robot`.

1. Ouvre `Robot.java`. Ne t'inquiète pas si tu ne comprends pas tout : on va y revenir.
   - Les lignes **grises ou vertes** qui commencent par `//` ou `/*` sont des
     **commentaires** : des explications pour les humains, ignorées par l'ordinateur.
2. Ouvre `practice/JavaPractice.java` : c'est **ton terrain de pratique** pour les 3
   premières séances.

## Étape 3 – Exécuter ton premier programme (10 min)

Dans `JavaPractice.java`, trouve cette ligne :

```java
System.out.println("Bonjour, je programme un robot FRC !");
```

`System.out.println(...)` veut dire « **affiche** ce texte dans le terminal ».

1. Juste au-dessus de la ligne `public static void main(String[] args) {`, tu vois de petits
   liens gris : **Run | Debug**.

   > Pas de liens ? Attends une minute : l'éditeur Java est encore en train de démarrer (tu
   > vois un petit ⏳ ou « *Java: Loading* » tout en bas).

2. Clique sur **Run** (« Exécuter »).
3. Regarde le terminal en bas : ton message s'affiche ! 🎉

## Étape 4 – À toi de jouer (10 min)

1. **Modifie le message** pour mettre ton prénom :

   ```java
   System.out.println("Bonjour, je m'appelle Alex et je programme un robot FRC !");
   ```

   Clique sur **Run** de nouveau.

2. **Ajoute une deuxième ligne** juste en dessous, avec un autre message, par exemple ton
   équipe FRC préférée ou ce que tu aimerais que le robot fasse.

3. 🧪 **Expérience :** enlève le `;` à la fin d'une ligne. Que se passe-t-il ?
   - Le code est souligné en rouge 〰️. Passe la souris dessus : `Syntax error, insert ";"`.
   - Remets le `;`. Tu viens de corriger ton **premier bug** ! 🐛

4. 🧪 **Expérience :** écris `system.out.println` (avec un **s minuscule**). Que se passe-t-il ?
   Java fait la différence entre les majuscules et les minuscules !

## Étape 5 – Sauvegarder sur GitHub (5 min)

Suis le [guide 3](../guides/03-sauvegarder.md). Message de commit :

```
Séance 1 : mon premier programme
```

Puis arrête ton Codespace ([guide 2, partie E](../guides/02-codespace.md#partie-e--fermer-ton-codespace-à-la-fin-de-chaque-séance-️)).

## ✅ J'ai réussi si...

- [ ] Mon programme affiche mon prénom dans le terminal
- [ ] Je sais ce qu'est un commentaire
- [ ] Mon commit « Séance 1 » apparaît sur ma page GitHub
- [ ] J'ai arrêté mon Codespace

## 📖 Mots appris

**Dépôt** (*repository*) · **Codespace** · **terminal** · **commentaire** · **commit** ·
**push** · **bug**
