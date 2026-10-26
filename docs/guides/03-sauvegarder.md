# Guide 3 – Sauvegarder ton travail (commit et push)

⏱️ 2 minutes, **à la fin de CHAQUE séance**.

## Pourquoi c'est important

Quand tu modifies un fichier dans ton Codespace, le changement existe seulement **dans ton
Codespace**. Pour le mettre en sécurité sur GitHub, il faut deux actions :

| Mot | Ce que ça veut dire | Comparaison |
|---|---|---|
| **Commit** | Prendre une « photo » de ton code, avec un petit message qui explique ce que tu as fait | Coller une photo dans un album, avec une légende |
| **Push** | Envoyer tes commits sur GitHub | Ranger l'album dans un coffre-fort |

Avec l'historique des commits, tu peux toujours voir (et retrouver) ce que ton code était à
chaque séance.

## Étape 1 : enregistrer les fichiers

Un fichier modifié **et pas encore enregistré** a un **point blanc ●** sur son onglet.
Appuie sur **Ctrl + S** pour l'enregistrer.

> 💡 Astuce : menu ☰ → **File** → coche **Auto Save** (« Enregistrement automatique »).

## Étape 2 : ouvrir le Contrôle de code source

Clique sur l'icône **Source Control** 🌿 dans la barre de gauche (elle a une petite pastille
bleue avec le nombre de fichiers modifiés).

Tu vois la liste des fichiers modifiés :
- **M** = *Modified* (modifié)
- **U** = *Untracked* (nouveau fichier)
- **D** = *Deleted* (supprimé)

> 👀 Clique sur un fichier de la liste pour voir **exactement** ce que tu as changé : en rouge
> ce qui a été enlevé, en vert ce qui a été ajouté.

## Étape 3 : écrire le message du commit

Dans la case **Message** en haut, écris une phrase courte qui dit **ce que tu as fait**.

✅ Bons messages :
- `Séance 2 : variables et conditions`
- `Ajout du sous-système volant`
- `Correction : le volant tournait à l'envers`

❌ Mauvais messages : `trucs`, `asdf`, `ça marche enfin`

## Étape 4 : Commit

Clique sur le bouton bleu **✓ Commit**.

> Si une fenêtre te demande « *There are no staged changes. Would you like to stage all your
> changes and commit them directly?* », clique sur **Yes** (ou **Always**).

## Étape 5 : Push

Clique sur le bouton bleu **Sync Changes ↑1** (« Synchroniser les modifications »). Le
chiffre est le nombre de commits à envoyer.

> Si une fenêtre te prévient que l'action va envoyer et recevoir des commits, clique sur
> **OK** (ou **OK, Don't Show Again**).

## Étape 6 : vérifier sur GitHub

1. Va sur la page de ton dépôt sur https://github.com.
2. En haut de la liste des fichiers, tu dois voir ton message de commit et « *now* » ou
   « *1 minute ago* ».
3. À côté de ton commit, tu vas voir un petit symbole :
   - 🟡 un **point jaune** : GitHub est en train de vérifier ton code ;
   - ✅ un **crochet vert** : ton code compile (et les tests passent) ;
   - ❌ un **X rouge** : il y a une erreur. Clique dessus pour voir laquelle.

## ✅ Vérifie

- [ ] Aucun onglet n'a de point blanc ●
- [ ] L'icône Source Control n'a plus de pastille bleue
- [ ] Mon message de commit apparaît sur la page GitHub de mon dépôt
