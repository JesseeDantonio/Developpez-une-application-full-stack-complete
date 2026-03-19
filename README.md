# 🚀 MDD – Application Full Stack

**Projet n°6 OpenClassrooms** -> ORION souhaite créer le prochain réseau social dédié aux développeurs : MDD (Monde de Dév). Le but du réseau social MDD est d’aider les développeurs qui cherchent un travail, grâce à la mise en relation, en encourageant les liens et la collaboration entre pairs qui ont des intérêts communs. MDD pourrait devenir un vivier pour le recrutement des profils manquant des entreprises.

---

## ✨ Fonctionnalités clés


- **Authentification & gestion des utilisateurs** (inscription, connexion, consultation et modification du profil).
- **Abonnements** : Visualisation, abonnement et désabonnement à des thèmes.
- **Thèmes** : Visualisation des thèmes disponibles.
- **Articles** : Création et visualisation d'articles liés aux thèmes.
- **Commentaires** : Création et visualisation des commentaires sous les articles.
- **Sécurité** : Connexion sécurisée avec le front-end (JWT/headers, CORS, etc.).
- **Documentation API** : OpenAPI / Swagger UI pour tester rapidement les endpoints.

---

## 🧱 Stack technique

| Couche          | Outils / Frameworks                     |
| --------------- | --------------------------------------- |
| **Front-end**   | Angular (TypeScript)    | 
| **Back-end**    | Java 21, Spring Boot (Web, Security, Data JPA) |
| **Base de données** | MySQL                               |
| **Outils**      | Maven, Docker, Docker Compose           |
| **Documentation**| Springdoc OpenAPI / Swagger UI         |

---

## 📦 Prérequis
### back-end
- **Java 21** + **Maven**
- **Docker & Docker Compose** (pour lancer les services auxiliaires)
- **IDE** : IntelliJ IDEA (recommandé) ou VS Code
- **Postman / HTTPie** pour tester l’API
- **PhpMyAdmin** (optionnel) pour l’inspection MySQL

### front-end
- **Node.js** + **NPM**
- **IDE** : VS Code

---

## 🚀 Installation & lancement

### 1. Cloner le dépôt

```bash
git clone https://github.com/JesseeDantonio/Developpez-une-application-full-stack-complete
cd Developpez-une-application-full-stack-complete
```

### 2. Lancer le fichier docker compose

```bash
cd Developpez-une-application-full-stack-complete


sudo docker compose up -d
```

### 3. Créer un fichier .env à la racine

Il faut fournir les informations confidentiels à Spring Boot pour la connexion à la base de données MySQL
A l'intérieur, il faudra insérer le nom des variables (clés) dans le fichier resources\application.properties
Ainsi que leur valeur

```bash
# ⚠️ Attention :
# Les identifiants de connexion fournis dans ce projet sont exclusivement réservés à un usage local, pour l'essai et la validation du projet.
# Ils ne doivent en aucun cas être utilisés en production ou pour des données sensibles.

# Les identifiants sont dans le fichier docker compose
```

### 4. Démarrer pour la première fois le back-end

Avec IntelliJ, cliquer sur le bouton Run qui lance la classe principal. - Ici, MddApiApplication.java
- Note : Au premier démarrage, Hibernate va générer automatiquement la structure de la base de données.

### 5. Introduiser des thèmes manuellement
L'application dans sa version actuelle ne permet pas la création de thèmes.

### 6. Initialiser, Démarrer le front end

```bash
cd front
npm i
npm run start
```

# 🌐 Accès à l'application

Une fois les deux serveurs lancés, vous pouvez accéder à :

- L'application Web (Front-end) : http://localhost:4200
    
- L'API (Back-end) : http://localhost:8080/api
    
- Documentation Swagger UI : http://localhost:8081/swagger-ui.html
