# ChatServer Spring Boot - Messagerie JPA

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-blue)
![JPA](https://img.shields.io/badge/JPA-Hibernate-green)
![REST API](https://img.shields.io/badge/REST%20API-✔-success)

Application de chat serveur développée en Spring Boot avec architecture 3-tiers et persistance JPA. Implémente des services d'envoi/réception de messages non-bloquants et bloquants.

## 🚀 Fonctionnalités

- ✅ **Envoi de messages** non-bloquant (retour immédiat)
- ✅ **Réception de messages** bloquante (attend indéfiniment)
- ✅ **Réception avec timeout** (retourne 204 si aucun message)
- ✅ **Persistance JPA** avec base de données H2
- ✅ **Architecture 3-tiers** complète (Controller-Service-DAO)
- ✅ **API RESTful** avec gestion des erreurs HTTP
- ✅ **Thread-safe** avec `LinkedBlockingQueue` (version mémoire)
- ✅ **Console H2** pour visualisation des données

## 📋 Prérequis

- Java 21 ou supérieur
- Maven 3.6+
- Spring Boot 3.2.0

## 🛠️ Installation

```bash
# Cloner le projet
git clone https://github.com/mouhaned372/Chat_mem_jpa.git
cd Chat_mem_jpa

# Compiler
./mvnw clean compile

# Lancer l'application
./mvnw spring-boot:run
