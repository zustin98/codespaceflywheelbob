# Guide 2 – Créer ton projet et ouvrir ton Codespace

⏱️ Environ 15 minutes la première fois (dont 3 à 5 minutes d'attente).

## C'est quoi, un Codespace ?

Un **Codespace**, c'est un **ordinateur dans le nuage** que GitHub te prête. Tu le contrôles
depuis un onglet de Chrome. Il contient déjà tout ce qu'il faut pour programmer un robot :
Java, les bibliothèques **WPILib** (pour les robots FRC) et **REVLib** (pour le moteur NEO).

Ton Chromebook n'a donc rien à installer. 🎉

> ⏳ **Tes heures sont limitées** : GitHub te donne environ **60 heures par mois**
> gratuitement. C'est largement assez pour le club, à condition d'**arrêter ton Codespace à
> la fin de chaque séance** (voir la partie « Fermer ton Codespace » plus bas).

---

## Partie A – Créer TON projet (une seule fois)

Ton animateur a préparé un **modèle** (*template*) : un projet de départ avec les fichiers
dont tu as besoin. Tu vas en faire **ta propre copie**.

1. Connecte-toi à https://github.com.
2. Ouvre le lien du modèle que ton animateur t'a donné.
3. Clique sur le bouton vert **Use this template** (« Utiliser ce modèle »), puis sur
   **Create a new repository** (« Créer un nouveau dépôt »).

   > ⚠️ Ne choisis **pas** « *Open in a codespace* » ici : ton travail ne serait pas sauvegardé
   > dans ton propre dépôt.

4. Remplis le formulaire :
   - **Owner** (« Propriétaire ») : ton nom d'utilisateur
   - **Repository name** (« Nom du dépôt ») : `volant-` suivi de ton prénom, ex. `volant-alex`
   - Choisis **Private** (« Privé ») si ton animateur te le demande, sinon **Public**.
5. Clique sur **Create repository** (« Créer le dépôt »).

🎉 Tu as maintenant ton propre **dépôt** (*repository*), c'est-à-dire ton dossier de projet
sur GitHub. Son adresse ressemble à : `https://github.com/ton-nom/volant-alex`.

> 📝 **Si ton dépôt est privé**, ajoute ton animateur pour qu'il puisse voir ton travail :
> **Settings** → **Collaborators** → **Add people** → tape son nom d'utilisateur.

---

## Partie B – Créer ton Codespace (une seule fois)

1. Sur la page de **ton** dépôt, clique sur le bouton vert **<> Code**.
2. Choisis l'onglet **Codespaces**.
3. Clique sur **Create codespace on main** (« Créer un codespace sur main »).
4. Un nouvel onglet s'ouvre avec le message *Setting up your codespace* (« Préparation de ton
   codespace »). **La première fois, c'est long (3 à 5 minutes)** : le Codespace installe Java
   et télécharge WPILib. Clique sur **View logs** (« Voir le journal ») si tu veux voir le
   texte défiler.
5. Quand tout est prêt, le journal affiche le message ci-dessous et l'éditeur s'ouvre tout
   seul :

   ```
   ==========================================
     Ton Codespace est prêt ! Bonne séance :)
   ==========================================
   ```

---

## Partie C – Faire le tour de ton atelier

Ton Codespace ressemble à **Visual Studio Code**, l'éditeur utilisé par les équipes FRC.

```
┌────┬────────────────────┬───────────────────────────────────────┐
│ 📄 │  EXPLORATEUR       │   ÉDITEUR                              │
│ 🔍 │  ▸ src             │   (le fichier ouvert s'affiche ici)    │
│ 🌿 │  ▸ docs            │                                        │
│ ▶️ │  README.md         │                                        │
│    │                    ├───────────────────────────────────────┤
│    │                    │   TERMINAL   PORTS   PROBLÈMES         │
│    │                    │   $ _                                  │
└────┴────────────────────┴───────────────────────────────────────┘
```

| Icône à gauche | Nom | À quoi ça sert |
|---|---|---|
| 📄 | **Explorer** (Explorateur) | Voir et ouvrir les fichiers du projet |
| 🔍 | **Search** (Rechercher) | Chercher un mot dans tous les fichiers |
| 🌿 | **Source Control** (Contrôle de code source) | **Sauvegarder ton travail sur GitHub** |
| ▶️ | **Run and Debug** (Exécuter et déboguer) | Lancer le programme avec le débogueur |

En bas, plusieurs onglets :

- **TERMINAL** : pour taper des commandes (comme `./gradlew build`).
- **PORTS** : pour ouvrir le **bureau du simulateur** (port 6080).
- **PROBLEMS** (Problèmes) : la liste des erreurs dans ton code.

> 💡 Si le panneau du bas est caché : menu ☰ → **View** → **Terminal**, ou le raccourci
> **Ctrl + `** (la touche à gauche du 1).

---

## Partie D – Revenir dans ton Codespace (à chaque séance)

**Ne crée pas un nouveau Codespace à chaque fois !** Rouvre le même :

1. Va sur **https://github.com/codespaces**.
2. Tu vois la liste de tes Codespaces. Clique sur celui de ton projet `volant-...`.
3. Il redémarre en environ 30 secondes, avec tes fichiers comme tu les avais laissés.

---

## Partie E – Fermer ton Codespace (à la fin de chaque séance) ⚠️

1. **Sauvegarde ton travail sur GitHub d'abord** (voir le [guide 3](03-sauvegarder.md)).
2. Clique sur le bouton bleu **Codespaces** tout en bas à gauche de l'écran.
3. Choisis **Stop Current Codespace** (« Arrêter le Codespace actuel »).

Si tu oublies, il s'arrête tout seul après 30 minutes sans activité, mais il consomme tes
heures pendant ce temps.

> 🧹 **Important :** un Codespace inutilisé pendant **30 jours** est effacé automatiquement.
> Tout ce qui n'a pas été **sauvegardé sur GitHub** (commit + push) serait perdu. D'où
> l'importance du [guide 3](03-sauvegarder.md) !

## ✅ Vérifie

- [ ] J'ai mon propre dépôt `volant-...` sur GitHub
- [ ] Mon Codespace s'ouvre et je vois les fichiers du projet dans l'Explorateur
- [ ] Je sais rouvrir mon Codespace depuis https://github.com/codespaces
- [ ] Je sais arrêter mon Codespace

➡️ Prochaine étape : [Séance 1](../seances/seance-1.md)
