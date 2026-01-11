# Guide de démarrage avec Docker

## 🐳 Démarrer l'application complète avec Docker

### 1. Démarrer tous les services (Backend + Frontend)
```bash
# Dans le répertoire du projet
docker-compose up -d

# Vérifier que tous les services sont démarrés
docker-compose ps

# Voir les logs de tous les services
docker-compose logs -f

# Voir les logs d'un service spécifique
docker-compose logs -f frontend
docker-compose logs -f gateway-service
```

### 2. Accéder à l'application
- **Frontend Angular** : http://localhost:4200
- **API Gateway** : http://localhost:8888
- **Eureka Discovery** : http://localhost:8761
- **Produit Service** : http://localhost:8081
- **Commande Service** : http://localhost:8082

### 3. Vérifier que les bases de données sont créées
```bash
# Se connecter au conteneur PostgreSQL des produits
docker exec -it postgres-product psql -U admin -d products_db

# Se connecter au conteneur PostgreSQL des commandes
docker exec -it postgres-command psql -U admin -d commands_db

# Lister les tables
\dt

# Quitter
\q
```

## 🛑 Arrêter les services

```bash
# Arrêter tous les conteneurs
docker-compose down

# Arrêter et supprimer les données (volumes)
docker-compose down -v
```

## 🔧 Commandes de développement

### Reconstruire un service spécifique
```bash
# Reconstruire le frontend
docker-compose build frontend

# Reconstruire et redémarrer le frontend
docker-compose up -d --build frontend
```

### Redémarrer un service
```bash
docker-compose restart frontend
docker-compose restart gateway-service
```

## 🔍 Commandes utiles

### Voir les logs
```bash
# Logs du frontend
docker-compose logs -f frontend

# Logs du gateway
docker-compose logs -f gateway-service

# Logs de tous les services
docker-compose logs -f
```

### Accéder à un conteneur
```bash
# Accéder au conteneur frontend
docker exec -it frontend-app sh

# Accéder à PostgreSQL
docker exec -it postgres-product psql -U admin -d products_db
```

### Vérifier la santé des services
```bash
# Vérifier tous les services
docker-compose ps

# Vérifier les healthchecks
docker inspect frontend-app | grep -A 10 Health
```

## 📊 Architecture Complète

```
┌─────────────────────────────────────────────────────┐
│              Frontend (Docker Container)            │
│                  Angular + Nginx                    │
│                   localhost:4200                    │
└──────────────────────┬──────────────────────────────┘
                       │
                       │ Proxy /api/* → gateway:8888
                       ▼
┌─────────────────────────────────────────────────────┐
│            Gateway Service (Docker)                 │
│                  localhost:8888                     │
└──────────────────────┬──────────────────────────────┘
                       │
        ┌──────────────┴──────────────┐
        ▼                             ▼
┌──────────────────┐         ┌──────────────────┐
│ Produit Service  │         │ Commande Service │
│   localhost:8081 │         │   localhost:8082 │
└────────┬─────────┘         └────────┬─────────┘
         │                            │
         ▼                            ▼
┌──────────────────┐         ┌──────────────────┐
│  PostgreSQL      │         │  PostgreSQL      │
│  products_db     │         │  commands_db     │
│  localhost:5432  │         │  localhost:5433  │
└──────────────────┘         └──────────────────┘

                       ▲
                       │
                       │ Service Discovery
                       │
┌─────────────────────────────────────────────────────┐
│          Discovery Service (Eureka)                 │
│                  localhost:8761                     │
└─────────────────────────────────────────────────────┘
```

## 🌐 Réseau Docker

Tous les services communiquent via le réseau `microservices-network` :
- Les services backend se découvrent via Eureka
- Le frontend communique avec le backend via le Gateway
- Nginx proxy les requêtes `/api/*` vers `http://gateway-service:8888`

## 🚀 Workflow de développement

### Développement local (sans Docker)
```bash
# Terminal 1 - Backend services
docker-compose up -d postgres-product postgres-command dicovery-service gateway-service produit-service commande-service

# Terminal 2 - Frontend en mode dev
cd frontend
npm start
```

### Production (avec Docker)
```bash
# Tout démarrer avec Docker
docker-compose up -d
```

## 📝 Notes importantes

1. **Ordre de démarrage** : Les services démarrent dans l'ordre grâce aux `depends_on` et `healthcheck`
2. **Healthchecks** : Tous les services ont des healthchecks pour garantir leur disponibilité
3. **Volumes** : Les données PostgreSQL sont persistées dans des volumes Docker
4. **Réseau** : Tous les services sont sur le même réseau `microservices-network`
5. **Frontend** : L'application Angular est buildée en mode production et servie par Nginx

