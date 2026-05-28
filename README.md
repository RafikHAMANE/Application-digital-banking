# Application Digital Banking

## 1. Description

Digital Banking est une application de gestion bancaire développée avec Spring Boot.

Cette application permet de gérer les clients, les comptes bancaires ainsi que les opérations bancaires comme le débit, le crédit et le transfert d’argent.

---

## 2. Fonctionnalités

### Gestion des clients

* Ajouter un client
* Modifier les informations d’un client
* Supprimer un client
* Afficher la liste des clients

### Gestion des comptes bancaires

* Créer un compte courant (Current Account)
* Créer un compte épargne (Saving Account)
* Afficher tous les comptes bancaires
* Afficher les comptes d’un client

### Opérations bancaires

* Effectuer un débit
* Effectuer un crédit
* Effectuer un transfert entre deux comptes

---

## 3. Technologies utilisées

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* REST API

---

## 4. Architecture du projet

Le projet est organisé selon une architecture en couches :

* Controller Layer
* Service Layer
* Repository Layer
* DTO & Mapper

---

## 5. Exemples d’API REST

### Clients

* GET /customers
* POST /customers
* PUT /customers/{id}
* DELETE /customers/{id}

### Comptes bancaires

* GET /accounts
* POST /accounts

### Opérations

* POST /accounts/debit
* POST /accounts/credit
* POST /accounts/transfer

---

## 6. Lancer le projet

### Prérequis

Assurez-vous d’avoir installé :

* Java 17+
* Maven
* MySQL

### Configuration de la base de données

Configurer les informations MySQL dans le fichier `application.properties` :

```properties
server.port=8085

spring.datasource.url=jdbc:mysql://localhost:3306/E-BANK?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

### Étapes d’exécution

#### Cloner le repository

```bash
git clone <url-du-repository>
```

#### Exécuter le projet

```bash
mvn spring-boot:run
```

### Accéder à l’application

```bash
http://localhost:8085
```

---



