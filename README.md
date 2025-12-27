
# Microservices Library Application

Nom : **Khawla Khamma**

## Architecture
- Eureka Server
- API Gateway
- User Service (MySQL)
- Book Service (MySQL)
- Emprunter Service (MySQL + Kafka Producer)
- Notification Service (Kafka Consumer)

## Kafka
Topic: `emprunt-created`

## Base de données
username = root  
password = (vide)

## Lancement
```bash
docker-compose up --build
```
