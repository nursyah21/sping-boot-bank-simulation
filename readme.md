# Microservices Bank Simulasi Spring Boot + Quarkus

> Microservices Bank Simulation – A showcase of modern microservices architecture using Spring Boot, Quarkus, RabbitMQ, PostgreSQL, and MongoDB.

### Tech Stack

- Spring Boot
- Quarkus
- RabbitMQ
- NextJS
- Postgresql
- MongoDB

### System Architecture

![image](/images/system-architecture.png)

### Feature

- Rate Limiter in login
- Role Based Access Control (Admin, User)
- Soft Delete (Admin can Deactivating Account User)
- Pagination, Search and Sort
- Fast path / Slow path (under 100ms when without search)
- Unit testing for AccountID Generator
- Load testing pass (no memory leak)
- Read Write Database (to ensure no degrade performance when data is high)
- Microservices with rabbitmq for storing log transaction

### Behind Scene
this project is next version of [spring-boot-simulation](https://github.com/nursyah21/spring-boot-bank-simulation) 

the problem with previous version 
1. when a transaction have massive traffic, performance read drop significant 
2. not have log services


to fix this problem, we create
1. master-slave database (fix performance read)
2. microservices for log (ensure primary service not have performance drop)

### Possible Improvement

- High Availability setup with Eureka for service discovery and clustering.

### Project Structure
```
- backend
  - src/main/java/com/nurs/backend/
    - config/          # Security, Rate Limiter
    - controller/      # REST API endpoints
    - dto/             # Data Transfer Objects
    - exception/       # Custom Exception
    - model/           # JPA Entitiy
    - repository/      # Spring Data JPA interfaces
    - service/         # Business Logic
  - src/main/resources/ # application.properties
- microservices
  - src/main/java/com/nurs/backend/
    - config/          # Security, Rate Limiter
    - controller/      # REST API endpoints
    - messaging/       # Consume Message From Rabbitmq
    - dto/             # Data Transfer Objects
    - model/           # JPA Entitiy
    - repository/      # Spring Data JPA interfaces
    - service/         # Business Logic
  - src/main/resources/ # application.properties
- frontend
- bruno                # Folder bruno app for Api testing
- docker-compose.yml
```

### Screenshot
![image](/images/login.png)
![image](/images/actuator.png)
![image](/images/home.png)
![image](/images/transaction.png)
![image](/images/account.png)
![image](/images/loadtest.png)

result load test 60connection in 1minutes (after several iteration).

it have stabil result,

| Operation       | Avg Response |Notes                |
| -------         | ------------ | -----               |
| Spring Boot raw | ~7ms         | health check        |
| Quarkus raw     | ~2ms         | health check        |
| Read            | ~570ms       | with DB replication |
| Write           | ~735ms       | with DB master      |

### Requirements
- Java 17+
- NodeJS 24+
- Docker & Docker Compose

### how to run

1. run 3 database and rabbitmq.
```sh
docker compose up -d
```

2. run backend spring boot

```sh
cd backend
./mvnw clean package
java -jar target/backend-0.1.0-SNAPSHOT.jar --spring.profiles.active=prod
```

3. run backend quarkus
```sh
cd microservices
./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
```

if you only need to test backend, you can use app bruno and open folder bruno.

4. run frontend (optional)
```sh
cd frontend
npm install
npm run lint
npm run start
```

5. run load testing (optional)

you need to install [bombardier](https://github.com/codesenberg/bombardier) to run this

and modify [loadtest.sh](./loadtest.sh) to replace token and destinationId

to get best result and find bottleneck you need to run at least 4x. 

you will get bias result if you run loadtest in same device with your services

```sh
sh loadtest.sh
```

### Default Credentials
- ADMIN: username: admin, password: password
- USER: username: user, password: password
