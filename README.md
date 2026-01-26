# 🏠 MAS Smart Home — Agents Intelligents

Ce projet est une implémentation **simple et pédagogique** d’un **système multi-agents (MAS)** appliqué à une **smart home**, réalisée dans le cadre du cours *Agents Intelligents*.

L’objectif est de mettre en œuvre les concepts fondamentaux du **modèle BDI (Belief – Desire – Intention)** à travers un agent autonome capable de raisonner et d’agir dans un environnement domestique, en tenant compte de contraintes réalistes comme l'énergie limitée.

L'agent peut effectuer plusieurs tâches : nettoyage des pièces, vidage des poubelles, lancement de la lessive, et sortie des déchets, tout en gérant son niveau d'énergie.

---

## 🎯 Objectifs du projet

- Comprendre le fonctionnement d’un **agent intelligent**
- Implémenter une architecture **BDI** complète
- Séparer clairement :
  - l’agent
  - l’environnement
  - les croyances, désirs et intentions
- Intégrer des **contraintes réalistes** (énergie limitée, priorisation des tâches)
- Fournir une base simple, extensible et compréhensible pour l'étude des MAS

---

## 🧠 Architecture du projet

Le projet est structuré en packages Java :

src/
└── com/
└── smarthome/
├── agent/
│ ├── Agent.java
│ └── CleaningAgent.java
├── bdi/
│ ├── BeliefBase.java
│ ├── Desire.java
│ └── Intentions.java
└── environment/
    └── Environment.java


### 🔹 Description des packages

- **agent**
  - `Agent` : classe abstraite représentant un agent BDI générique avec gestion d'énergie
  - `CleaningAgent` : agent concret capable de nettoyer, gérer les poubelles, faire la lessive et se reposer

- **bdi**
  - `BeliefBase` : base de croyances incluant l'état des pièces, poubelles, lessive et poubelle centrale
  - `Desire` : désirs possibles (CLEAN, DO_LAUNDRY, TAKE_OUT_TRASH, REST)
  - `Intentions` : intentions formées à partir des désirs, avec une action par cycle

- **environment**
  - `Environment` : environnement simulé avec pièces sales, poubelles à vider, lessive à faire et poubelle centrale

---

## 🤖 Modèle BDI

Le comportement de l’agent suit le cycle BDI avec contraintes :

1. **Beliefs**  
   → Informations sur l'environnement : pièces sales, poubelles pleines, lessive nécessaire, poubelle centrale pleine

2. **Desires**  
   → Objectifs : nettoyer, vider poubelles, faire lessive, sortir déchets, se reposer

3. **Intentions**  
   → Actions concrètes choisies en fonction de la priorité et de l'énergie disponible (une action par cycle)

**Contraintes intégrées :**
- Énergie limitée (10 points max) : chaque action consomme de l'énergie, le repos la recharge
- Priorisation : nettoyage > vidage poubelles > lessive > sortie déchets > repos
- Si énergie insuffisante, l'agent se repose automatiquement

Le cycle se répète jusqu'à satisfaction de tous les désirs et recharge complète.

---

## ✨ Fonctionnalités

- **Tâches multiples** : Nettoyage, gestion des poubelles (vidage et sortie), lessive
- **Contraintes énergétiques** : Gestion réaliste de l'énergie avec repos automatique
- **Priorisation intelligente** : Choix des actions basé sur l'urgence et l'énergie
- **Simulation aléatoire** : État initial variable pour tester différents scénarios
- **Sortie détaillée** : Affichage des cycles, actions et état restant

---

## ⚙️ Prérequis

- Java **11 ou plus**
- Aucun outil de build requis (ni Maven, ni Gradle)

---

## ▶️ Compilation du projet

Se placer dans le dossier `src` :

```bash
javac com/smarthome/agent/*.java com/smarthome/bdi/*.java com/smarthome/environment/*.java
`
##  Ex�cution du projet

Après compilation :

`Bash
java com.smarthome.agent.CleaningAgent
` 

L'exécution affiche l'état initial, puis chaque cycle avec l'action effectuée, l'énergie restante et l'état des tâches. Le programme se termine quand toutes les tâches sont accomplies et l'agent est reposé.