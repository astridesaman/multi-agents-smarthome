# 🏠 MAS Smart Home — Agents Intelligents

Ce projet est une implémentation **complète** d'un **système multi-agents (MAS)** appliqué à une **smart home**, réalisée dans le cadre du cours *Agents Intelligents*.

L'objectif est de mettre en œuvre les concepts fondamentaux du **modèle BDI (Belief – Desire – Intention)** et **agents réactifs** à travers des agents autonomes capables de raisonner et d'agir dans un environnement partagé.

---

## 🎯 Objectifs du projet

- ✅ Comprendre le fonctionnement d'un **agent intelligent**
- ✅ Implémenter une architecture **BDI complète**
- ✅ Implémenter des **agents réactifs** avec règles condition-action
- ✅ Séparer clairement : l'agent, l'environnement, les croyances, désirs et intentions
- ✅ Mettre en place des **mécanismes de coordination** et **communication inter-agents**
- ✅ Implémenter une **organisation par rôles**
- ✅ Fournir une base simple, extensible et compréhensible

---

## 🧠 Architecture du projet

Le projet est structuré en packages Java :

```
src/
└── com/
    └── smarthome/
        ├── agent/
        │   ├── Agent.java                    # Classe abstraite de base
        │   ├── BDIAgent.java                 # Agent BDI générique
        │   ├── ReactiveAgent.java            # Agent réactif
        │   ├── CleaningAgent.java            # Agent spécialisé (nettoyage)
        │   ├── DishwasherAgent.java          # Agent spécialisé (vaisselle)
        │   ├── GarbageCollectorAgent.java    # Agent spécialisé (ordures)
        │   ├── OrganizerAgent.java           # Agent spécialisé (organisation)
        │   ├── CoordinatorAgent.java         # Agent coordinateur
        │   ├── Message.java                  # Classe de communication
        │   ├── MessageBroker.java            # Courtier de messages (Singleton)
        │   └── AgentRole.java                # Énumération des rôles
        ├── bdi/
        │   ├── BeliefBase.java               # Base de croyances améliorée
        │   ├── Desire.java                   # Énumération des désirs
        │   └── Intentions.java               # Intentions (actions à effectuer)
        ├── environment/                     
        │   ├── Environment.java              # Environnement principal
        │   ├── Room.java                     # Représentation d'une pièce
        │   ├── Task.java                     # Classe pour les tâches
        │   └── SmartObject.java              # Objets intelligents
        ├── planning/     # Logique décisionnelle complexe
        │   ├── State.java        # Représentation d'un état (S)
        │   ├── Action.java       # Représentation d'une action (A)
        │   └── MDPPlanner.java   # Le moteur de calcul (Solveur)
        └── simulator/
            └── SmartHomeSimulator.java       # Simulateur principal
```

---

## 🤖 Types d'Agents

### Agents BDI (Belief-Desire-Intention)

Utilisent un processus de délibération pour décider des actions :

- **CleaningAgent** : Responsable du nettoyage des pièces
  - Désir: Nettoyer l'environnement
  - Cible: Pièces sales
  
- **DishwasherAgent** : Responsable du lavage de la vaisselle
  - Désir: Compléter les tâches
  - Cible: Tâches WASH_DISHES
  
- **GarbageCollectorAgent** : Responsable de la gestion des ordures
  - Désir: Compléter les tâches
  - Cible: Tâches THROW_GARBAGE
  
- **OrganizerAgent** : Responsable de l'organisation des objets
  - Désir: Maintenir l'ordre
  - Cible: Tâches ORGANIZE_OBJECTS
  
- **CoordinatorAgent** : Coordonne les tâches entre les agents
  - Désir: Aider les autres
  - Fonctionnalité: Allocation de tâches centralisée

### Agents Réactifs

Répondent **immédiatement** aux stimuli environnementaux sans délibération :

- **ReactiveAgent** : Applique des règles condition-action
  - Pas de phase de délibération
  - Réaction directe aux perceptions

---

## 🔄 Cycle de l'Agent
```

┌─────────────────────────────────────────────┐  
│  AGENT PERCEPTION-DELIBERATION-ACTION CYCLE │
├─────────────────────────────────────────────┤
│ 1. PERCEIVE                                 │
│    └─ Mettre à jour les croyances           │
│       sur l'état de l'environnement         │
│                                             │
│ 2. PROCESS MESSAGES                         │
│    └─ Lire les messages d'autres agents     |
│                                             |  
│ 3. DELIBERATE                               │
│    └─ Analyser les croyances                │
│    └─ Activér les désirs                    │
│    └─ Former les intentions                 │
│                                             │
│ 4. ACT                                      │
│    └─ Exécuter les intentions               │ 
│    └─ Modifier l'environnement              │
│                                             │
│ 5. CONSUME ENERGY                           │
│    └─ Réduire l'énergie selon activité      │
└─────────────────────────────────────────────┘
```

## 🔄 Modèle BDI

### Beliefs (Croyances)
- Informations que l'agent possède sur l'état du monde
- Pièces sales, tâches en attente, tâches complétées
- Historique des messages reçus
- Capacités des autres agents
- Mis à jour à chaque cycle de perception

### Desires (Désirs)
- Objectifs que l'agent souhaite atteindre
- Chaque agent a un ensemble de désirs
- Chaque désir a une priorité
- **Exemples**: Nettoyer, Compléter les tâches, Minimiser l'énergie, Aider les autres

### Intentions (Intentions)
- Actions concrètes que l'agent s'engage à exécuter
- Formées pendant la phase de délibération
- Basées sur les croyances et désirs
- Exécutées pendant la phase d'action

---

## 📋 Environnement

### Composantes

- **Rooms** : Pièces (cuisine, salon, chambre, salle de bain)
- **Objects** : Objets (vaisselle, ordures, outils, meubles)
- **Tasks** : Tâches à accomplir (CLEAN_ROOM, WASH_DISHES, THROW_GARBAGE, ORGANIZE_OBJECTS)
- **Task States** : États des tâches (PENDING, ONGOING, COMPLETED, FAILED)

### Mécanismes de Perception

Les agents peuvent percevoir :
- Pièces sales
- Tâches en attente
- Tâches en cours
- Tâches complétées
- Objets disponibles

### Mécanismes d'Action

Les agents peuvent :
- Nettoyer une pièce
- Assigner une tâche
- Compléter une tâche
- Déplacer un objet
- Envoyer des messages

---

## 🎯 Mécanismes de Coordination

### 1. Communication Inter-Agents

**MessageBroker** (Pattern Singleton) :
- Courtier centralisé pour les messages
- Boîtes aux lettres individuelles pour chaque agent
- Types de messages: REQUEST, RESPONSE, INFORM, QUERY, NEGOTIATE

```java
// Exemple d'envoi de message
sendMessage("AgentB", Message.MessageType.REQUEST, "Peux-tu nettoyer la cuisine?");
```

### 2. Allocation de Tâches

**CoordinatorAgent** :
- Reçoit les tâches en attente
- Alloue les tâches aux agents appropriés
- Équilibre la charge de travail
- Considère les rôles et les capacités

### 3. Gestion des Rôles

**AgentRole** (Enum) :
- CLEANER: Nettoyeur
- DISHWASHER: Lave-vaisselle
- GARBAGE_COLLECTOR: Collecteur d'ordures
- ORGANIZER: Organisateur
- COORDINATOR: Coordinateur
- MONITOR: Moniteur

Les agents peuvent **uniquement exécuter** les tâches compatibles avec leur rôle.

---

## ⚙️ Prérequis

- Java 
- Aucun outil de build requis (ni Maven, ni Gradle)

---

## ▶️ Compilation du projet

Se placer dans le dossier `src` :

```bash
cd src
javac com/smarthome/agent/*.java com/smarthome/bdi/*.java com/smarthome/environment/*.java com/smarthome/simulator/*.java
```

---

## 🚀 Exécution du projet

Pour lancer la simulation :

```bash
java com.smarthome.simulator.SmartHomeSimulator
```

### Configuration par Défaut

La simulation démarre avec :
- **1 CoordinatorAgent** pour la coordination
- **4 BDI Agents** spécialisés (Cleaner, Dishwasher, Garbage Collector, Organizer)
- **1 Reactive Agent** pour démontrer les agents réactifs
- **30 pas** de simulation maximum

### Options de Personnalisation

Modifier `SmartHomeSimulator.main()` pour :
- Changer le nombre d'agents
- Modifier les rôles des agents
- Ajuster le nombre de pas de simulation
- Ajouter/supprimer des tâches initiales

---

## 📊 Exemple de Résultat de Simulation

```
╔══════════════════════════════════════════════════════════╗
║                                                          ║
║  SMART HOME MULTI-AGENT SYSTEM SIMULATION                ║
║                                                          ║
╚══════════════════════════════════════════════════════════╝

📋 Initial Configuration:
   Agents: 5
   Max Steps: 30
   Environment: Smart Apartment (4 rooms)

🤖 Agent Roster:
   - Cleaner-1 (Cleaner)
   - Washer-1 (Dishwasher)
   - Collector-1 (Garbage Collector)
   - Organizer-1 (Organizer)
   - ReactiveBot-1 (Cleaner)

============================================================
SIMULATION STEP: 1
============================================================

🔍 Agent Status:
   Cleaner-1       [Energy: 95] [Intentions: 0] [Active: ✓]
   Washer-1        [Energy: 95] [Intentions: 0] [Active: ✓]
   ...
```

---

## 🏗️ Architecture de Conception

### Patterns Utilisés

1. **Singleton Pattern**
   - `MessageBroker` : Instance unique pour toute la communication
   - `Environment` : Instance unique partagée par tous les agents

2. **Abstract Factory Pattern**
   - `Agent` : Classe abstraite avec usines de sous-classes

3. **Strategy Pattern**
   - Différentes stratégies de délibération pour BDI et Reactive

4. **Observer Pattern**
   - Agents observent l'environnement et réagissent aux changements

5. **State Pattern**
   - États des tâches (PENDING, ONGOING, COMPLETED, FAILED)
   - États des agents (ACTIVE, INACTIVE)

### Séparation des Responsabilités

```
Agent Component          Responsibility
─────────────────────────────────────────────
Agent                    Cycle PDA, comportement général
BDIAgent                 Délibération BDI spécialisée
ReactiveAgent            Règles condition-action
Specialized Agents       Comportements spécifiques aux rôles
CoordinatorAgent         Allocation de tâches et coordination
─────────────────────────────────────────────
Environment              État du monde
Room                     Gestion de pièce
Task                     Gestion de tâche
SmartObject              Gestion d'objet
─────────────────────────────────────────────
BeliefBase               Stockage des croyances
Desires                  Énumération des désirs
Intentions               Gestion des intentions
─────────────────────────────────────────────
MessageBroker            Communication
Message                  Structure de message
─────────────────────────────────────────────
SmartHomeSimulator       Orchestration globale
```

## 📝 Notes d'Implémentation

- Le système utilise une **simulation discrète par étapes**
- Les **croyances sont mises à jour** à chaque cycle de perception
- L'**énergie des agents** diminue avec les actions (5 points par intention, 1 point au repos)
- Les **tâches ont des priorités** et des durées estimées
- Les agents peuvent **communiquer directement** via le `MessageBroker`
- Le système est **thread-safe** pour les opérations basiques

---

## 🎓 Concepts Pédagogiques

Ce projet illustre :

1. **Architecture d'Agent**
   - Boucle de perception-délibération-action
   - Gestion de l'énergie et des ressources
   - Communication inter-agents

2. **BDI (Belief-Desire-Intention)**
   - Formation de croyances par perception
   - Gestion des désirs et priorités
   - Sélection des intentions

3. **Agents Réactifs**
   - Règles condition-action
   - Pas de mémoire ou historique
   - Réaction immédiate aux stimuli

4. **Coordination Multi-Agent**
   - Allocation centralisée de tâches
   - Communication par messages
   - Évitement des conflits

5. **Patterns de Conception**
   - Application pratique des design patterns
   - Modularité et extensibilité

---

## 👨‍💻 Auteur & Attribution

Réalisé dans le cadre du cours **Agents Intelligents** - L3 Informatique, Université Côte d'Azur

---

## 📚 Références

- **Wooldridge, M.** (2009). An Introduction to MultiAgent Systems
- **Rao, A. S., & Georgeff, M. P.** (1992). Modeling Rational Agents within a BDI Architecture
- **Reactive Agent Architecture**: Subsumption Architecture (Brooks, R.)
