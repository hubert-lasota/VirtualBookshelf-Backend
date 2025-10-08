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
### My Bookshelves Page
<img width="1921" height="917" alt="bookshelves page" src="https://github.com/user-attachments/assets/254a904e-58cd-45df-ad88-351eb33f7707" />

### Statistics Page
<img width="1923" height="920" alt="statistics page" src="https://github.com/user-attachments/assets/61b18925-d39c-405a-89e8-31ba4347af66" />

### Challenges Page
<img width="1919" height="916" alt="challenges page" src="https://github.com/user-attachments/assets/15d642e3-b3ea-4f10-87a4-84eef21a00f3" />

### Profile Page
<img width="1924" height="917" alt="profile page" src="https://github.com/user-attachments/assets/ca56d61a-dedd-44e1-b492-a132b6a89918" />

### Login Page
<img width="1921" height="919" alt="login page" src="https://github.com/user-attachments/assets/ed64bea8-4849-4732-8bbe-3d85a60bf7cf" />

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
