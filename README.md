# Application de Gestion de Bibliothèque - Architecture Microservices

**Auteur :** Khawla hamma

## 📋 Description du Projet

Ce projet implémente une application de gestion de bibliothèque basée sur une architecture microservices. L'application permet de gérer les utilisateurs, les livres et les emprunts, avec un système de notifications asynchrone via Kafka.

## 🏗️ Architecture

Le projet est composé des microservices suivants :

### 1. **Eureka Server** (Port 8761)
- **Rôle :** Service de découverte des services
- **Fonctionnalité :** Permet aux microservices de s'enregistrer et de découvrir les autres services
- **URL :** http://localhost:8761

### 2. **API Gateway** (Port 8080)
- **Rôle :** Point d'entrée unique pour toutes les requêtes
- **Fonctionnalité :** Routage dynamique vers les microservices appropriés
- **URL :** http://localhost:8080

### 3. **User Service** (Port 8081)
- **Rôle :** Gestion des utilisateurs
- **Base de données :** MySQL (`db_user`)
- **Fonctionnalité :** CRUD des utilisateurs

### 4. **Book Service** (Port 8082)
- **Rôle :** Gestion des livres
- **Base de données :** MySQL (`db_book`)
- **Fonctionnalité :** CRUD des livres

### 5. **Emprunter Service** (Port 8083)
- **Rôle :** Gestion des emprunts
- **Base de données :** MySQL (`db_emprunter`)
- **Fonctionnalité :** 
  - CRUD des emprunts
  - Production d'événements Kafka lors de la création d'un emprunt

### 6. **Notification Service** (Port 8084)
- **Rôle :** Gestion des notifications de manière asynchrone
- **Fonctionnalité :** 
  - Consommation des événements Kafka
  - Aucun appel REST entrant (découplé)
  - Notification simulée par log/console

## 🗄️ Bases de Données MySQL

Chaque microservice possède sa propre base de données (Database per Service) :

| Service | Base de données | Port MySQL |
|---------|----------------|------------|
| user-service | `db_user` | 3307 |
| book-service | `db_book` | 3308 |
| emprunter-service | `db_emprunter` | 3309 |

**Identifiants de connexion :**
- Username : `root`
- Password : (vide)

## 📨 Kafka - Communication Asynchrone

### Configuration
- **Topic :** `emprunt-created`
- **Zookeeper :** Port 2181
- **Kafka Broker :** Port 9092

### Flux de Communication

1. **Producteur :** `emprunter-service`
   - Envoie des événements lors de la création d'un emprunt

2. **Consommateur :** `notification-service`
   - Consomme les événements du topic `emprunt-created`
   - Group ID : `notification-group`

### Format du Message Kafka

```json
{
  "empruntId": 1,
  "userId": 3,
  "bookId": 5,
  "eventType": "EMPRUNT_CREATED",
  "timestamp": "2025-01-01T14:00:00"
}
```

## 🚀 Déploiement avec Docker Compose

### Prérequis
- Docker
- Docker Compose

### Lancement de l'application

```bash
# Construire et démarrer tous les services
docker-compose up --build

# Démarrer en arrière-plan
docker-compose up -d --build

# Arrêter tous les services
docker-compose down
```

### Ordre de démarrage

Les services démarrent dans l'ordre suivant grâce aux dépendances configurées :

1. **Eureka Server** (découverte des services)
2. **MySQL** (bases de données)
3. **Zookeeper** (coordination Kafka)
4. **Kafka** (messaging)
5. **API Gateway** (point d'entrée)
6. **Microservices métier** (user, book, emprunter)
7. **Notification Service** (consommateur Kafka)

## 🔍 Vérification du Déploiement

### Services disponibles

- **Eureka Dashboard :** http://localhost:8761
- **API Gateway :** http://localhost:8080
- **User Service :** http://localhost:8081
- **Book Service :** http://localhost:8082
- **Emprunter Service :** http://localhost:8083
- **Notification Service :** http://localhost:8084

### Vérification des logs

```bash
# Voir les logs de tous les services
docker-compose logs -f

# Voir les logs d'un service spécifique
docker-compose logs -f notification-service
docker-compose logs -f emprunter-service
```

## 📦 Structure du Projet

```
microservice/
├── eureka-server/          # Service de découverte
├── api-gateway/            # Point d'entrée unique
├── user-service/           # Gestion des utilisateurs
├── book-service/           # Gestion des livres
├── emprunter-service/      # Gestion des emprunts + Kafka Producer
├── notification-service/   # Kafka Consumer
├── docker-compose.yml      # Configuration Docker
└── README.md              # Documentation
```

## 🔧 Technologies Utilisées

- **Spring Boot 3.4.1**
- **Spring Cloud 2024.0.0**
- **Netflix Eureka** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **Spring Data JPA** (Persistence)
- **MySQL 8** (Base de données)
- **Apache Kafka** (Messaging asynchrone)
- **Docker & Docker Compose** (Conteneurisation)



##  Dépannage

### Problèmes de connexion MySQL
- Vérifier que les conteneurs MySQL sont démarrés : `docker-compose ps`
- Vérifier les logs : `docker-compose logs mysql-user`

### Problèmes Kafka
- Vérifier que Zookeeper et Kafka sont démarrés
- Vérifier les logs : `docker-compose logs kafka`

### Services non enregistrés dans Eureka
- Attendre quelques secondes après le démarrage
- Vérifier les logs des services pour les erreurs de connexion

---

**Développé par :** Khawla hamma
