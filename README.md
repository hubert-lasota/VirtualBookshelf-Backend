# Virtual Bookshelf - Backend

REST API for a reading management application that helps users organize their library, track reading progress, and participate in reading challenges.

> **Frontend Repository:** https://github.com/hubert-lasota/VirtualBookshelf-Frontend

## Tech Stack

- **Java** - 17
- **Spring Boot** - 3.3.0
- **Spring Security** 
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Maven**
- **Docker** & **Docker Compose**

## Screenshots


## Features
- Book cataloging and management
- Reading progress tracking
- Reading challenges system
- User authentication and authorization
- Profile management
- RESTful API endpoints

## Installation & Setup

**Requirements:**
- Docker
- Docker Compose

**Steps:**

1. **Clone the repository**
```bash
git clone https://github.com/hubert-lasota/VirtualBookshelf-Frontend.git
cd WirtualnyRegalBackend
```

2. **Run the startup script**
```bash
chmod +x start-dev.sh  # Make script executable (first time only)
./start-dev.sh
```

The API will be available at `http://localhost:8080`

3. **Stop the application**
```bash
docker-compose down
```