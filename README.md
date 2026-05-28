Application Digital Banking
Description

Digital Banking est une application de gestion bancaire développée avec Spring Boot.

Cette application permet de gérer les clients, les comptes bancaires ainsi que les opérations bancaires comme le débit, le crédit et le transfert d’argent.

Fonctionnalités:
Gestion des clients
Ajouter un client
Modifier les informations d’un client
Supprimer un client
Afficher la liste des clients
Gestion des comptes bancaires
Créer un compte courant (Current Account)
Créer un compte épargne (Saving Account)
Afficher tous les comptes bancaires
Afficher les comptes d’un client
Opérations bancaires
Effectuer un débit
Effectuer un crédit
Effectuer un transfert entre deux comptes
Technologies utilisées
Java
Spring Boot
Spring Data JPA
Hibernate
MySQL
Maven
REST API
Architecture du projet

Le projet est organisé selon une architecture en couches :

Controller Layer
Service Layer
Repository Layer
DTO & Mapper
Exemples d’API REST
Clients
GET /customers
POST /customers
PUT /customers/{id}
DELETE /customers/{id}
Comptes bancaires
GET /accounts
POST /accounts
Opérations
POST /accounts/debit
POST /accounts/credit
POST /accounts/transfer
Lancer le projet
Prérequis

Assurez-vous d’avoir installé :

Java 17+
Maven
MySQL
Étapes d’exécution
Cloner le repository :
      git clone <url-du-repository>
Configurer la base de données MySQL dans le fichier :
application.properties
Exécuter le projet :
mvn spring-boot:run
Accéder à l’application :
http://localhost:8085
