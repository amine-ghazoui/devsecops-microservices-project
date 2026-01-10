# Guide de démarrage avec Docker PostgreSQL

## 🐳 Démarrer PostgreSQL avec Docker

### 1. Démarrer PostgreSQL
```bash
# Dans le répertoire du projet
docker-compose up -d

# Vérifier que PostgreSQL est bien démarré
docker-compose ps

# Voir les logs
docker-compose logs -f postgres
```

### 2. Vérifier que les bases de données sont créées
```bash
# Se connecter au conteneur PostgreSQL
docker exec -it microservices-postgres psql -U postgres

# Lister les bases de données
\l

# Vous devriez voir :
# - produit_db
# - commande_db

# Quitter
\q
```

### 3. Démarrer les microservices
Les microservices se connecteront automatiquement à PostgreSQL sur `localhost:5432`

```bash
# Terminal 1 - Produit Service
cd produit-service
mvn spring-boot:run

# Terminal 2 - Commande Service
cd commande-service
mvn spring-boot:run
```

## 🛑 Arrêter PostgreSQL
```bash
# Arrêter le conteneur
docker-compose down

# Arrêter et supprimer les données
docker-compose down -v
```

## 🔍 Commandes utiles

### Voir les logs PostgreSQL
```bash
docker-compose logs -f postgres
```

### Redémarrer PostgreSQL
```bash
docker-compose restart postgres
```

### Accéder à PostgreSQL
```bash
# Via psql
docker exec -it microservices-postgres psql -U postgres -d produit_db

# Via pgAdmin ou DBeaver
# Host: localhost
# Port: 5432
# Username: postgres
# Password: postgres
# Database: produit_db ou commande_db
```

## 📊 Architecture

```
┌─────────────────────────────────────────┐
│         Microservices (Local)           │
├─────────────────────────────────────────┤
│  produit-service    commande-service    │
│      :8081              :8082           │
└──────────────┬──────────────────────────┘
               │
               │ localhost:5432
               ▼
┌─────────────────────────────────────────┐
│      PostgreSQL (Docker Container)      │
├─────────────────────────────────────────┤
│  produit_db         commande_db         │
└─────────────────────────────────────────┘
```
