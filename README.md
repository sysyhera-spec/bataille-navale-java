# Bataille Navale

Implémentation en Java du jeu de bataille navale, avec interface console et interface graphique Swing. Projet réalisé dans le cadre du cours de Programmation (Master 1 MIASHS – Université Grenoble Alpes, 2025-2026).

## Description

Le jeu se joue à deux joueurs (humain vs humain, humain vs ordinateur, ou ordinateur vs ordinateur) sur des grilles carrées. Chaque joueur place ses navires et tente de couler la flotte adverse en choisissant des coordonnées à attaquer.

## Fonctionnalités

- Placement automatique et aléatoire des navires sur la grille
- Trois modes de jeu : joueur graphique (clic souris), joueur texte (console), joueur automatique (IA)
- Interface graphique Swing avec deux grilles par joueur (grille de tir + grille de défense)
- Coloration dynamique des tirs (rouge = touché, bleu = à l'eau, vert = navire)
- Notifications en temps réel (touché, coulé, victoire)
- Gestion complète du déroulement de partie dans un thread dédié

## Architecture

Le projet est structuré en une hiérarchie de classes orientée objet :

```
Joueur (abstraite)
├── JoueurAvecGrille (abstraite)
│   ├── JoueurTexte       → jeu en console
│   ├── JoueurAuto        → joueur automatique (IA)
│   └── JoueurGraphique   → jeu via interface Swing

GrilleNavale
└── GrilleNavaleGraphique → grille avec rendu visuel

Coordonnee               → gestion des coordonnées (lettres/chiffres ↔ indices Java)
Navire                   → représentation et état d'un navire (touché, coulé)
BatailleNavale           → fenêtre principale de configuration et lancement
FenetreJoueur            → fenêtre de jeu par joueur
```

## Technologies

- Java (POO, héritage, classes abstraites)
- Swing (interface graphique)
- Multithreading (déroulement de partie dans un thread séparé)

## Lancer le projet

```bash
# Compiler
javac batailleNavale/*.java

# Lancer l'interface graphique
java batailleNavale.BatailleNavale
```
