# 🏠 MAS Smart Home — Agents Intelligents

Ce projet est une implémentation **simple et pédagogique** d’un **système multi-agents (MAS)** appliqué à une **smart home**, réalisée dans le cadre du cours *Agents Intelligents*.

L’objectif est de mettre en œuvre les concepts fondamentaux du **modèle BDI (Belief – Desire – Intention)** à travers un agent autonome capable de raisonner et d’agir dans un environnement.

---

## 🎯 Objectifs du projet

- Comprendre le fonctionnement d’un **agent intelligent**
- Implémenter une architecture **BDI**
- Séparer clairement :
  - l’agent
  - l’environnement
  - les croyances, désirs et intentions
- Fournir une base simple, extensible et compréhensible

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
  - `Agent` : classe abstraite représentant un agent BDI générique
  - `CleaningAgent` : agent concret avec un comportement simple de nettoyage

- **bdi**
  - `BeliefBase` : base de croyances de l’agent
  - `Desire` : désirs possibles de l’agent (enum)
  - `Intentions` : intentions formées à partir des désirs

- **environment**
  - `Environment` : environnement partagé dans lequel évoluent les agents

---

## 🤖 Modèle BDI

Le comportement de l’agent suit le cycle BDI :

1. **Beliefs**  
   → informations que l’agent possède sur l’environnement

2. **Desires**  
   → objectifs que l’agent souhaite atteindre

3. **Intentions**  
   → actions concrètes que l’agent décide d’exécuter

Ce cycle est volontairement simplifié pour des raisons pédagogiques.

---

## ⚙️ Prérequis

- Java **11 ou plus**
- Aucun outil de build requis (ni Maven, ni Gradle)

---

## ▶️ Compilation du projet

Se placer dans le dossier `src` :

```bash
javac com/smarthome/agent/*.java com/smarthome/bdi/*.java com/smarthome/environment/*.java
