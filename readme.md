# Bank Simulasi Spring Boot + NextJS

Portfolio project to showcase spring boot and nextjs

### Tech Stack

- Spring Boot
- NextJS
- Postgresql

### Feature

- Rate Limiter in login
- Role Based Access Control (Admin, User)
- Soft Delete (Admin can Deactivating Account User)
- Actuator (For monitoring backend spring boot)
- Pagination, Search and Sort
- Fast path / Slow path (under 100ms when without search)
- Unit testing for AccountID Generator
- Load testing pass (no memory leak)

### Behind Scene
this project initially from [https://github.com/nursyah21/bank-dotnet-java](https://github.com/nursyah21/bank-dotnet-java) but i stopped it, why?

I initially prioritized rigid planning (OpenAPI, Schema, strict DDD) and multi-platform development (Dotnet/Java). I learned that this approach created severe bottlenecks:

1. Rigidity and Rework
Designed the entire OpenAPI schema and database structure upfront, resulted in designs that inevitably had flaws once I started coding.

I found that every necessary change required time-consuming and inefficient rework across all the rigid schema documents.

2. Cognitive Overhead and Fragmentation
I spent too much effort on Domain-Driven Design (DDD) dogma, often resulting in agonizing over service names and placing single files into dedicated domain folders.

I realized this over-organization shifted my focus from solving business problems to solving architectural structure problems, adding unnecessary cognitive burden.

3. Inefficiency of Incremental Churn
I found the forced incremental approach required me to constantly refactor stable code. For example, building a feature without the database first, then completely rewriting the code to integrate the DB, created code churn in every iteration.

I learned it's more efficient to focus on delivering one complete, perfectly working service first, and then simply adapting that proven structure for all subsequent services.

4. Code Switching Penalty
I developed simultaneously in Dotnet and Java, which inflicted a high cognitive switching cost.

I found this constant context-switching reduced my focus and caused me to frequently forget implementation details from one platform when working on the other.

### After one week, I shifted my focus to a pragmatic and speed oriented 


1. Tech Stack & Priority
I chose Next.js and Spring Boot as the core technologies, prioritizing career alignment due to their popularity and high demand in the enterprise world.

Given my initial lack of experience with Spring Boot, I began with a focused learning sprint to master fundamentals (REST APIs, database connection, authentication) before starting the main project.

2. Methodology Change
I immediately abandoned the strict Domain-Driven Design (DDD) approach.

I adopted a pragmatic architecture, grouping files simply by type (Controller, Service, Repository) for maximum clarity and speed.

3. My new priority was Minimum Viable Product (MVP) and delivery speed, leading me to severely limit the project’s initial scope.

Result
This focused strategy allowed me to successfully complete the entire  project in just 10 days.

### Future Work

- Read Write split database
- Logging

you can find how i implement future work in [this](https://github.com/nursyah21/spring-boot-bank-simulation/tree/v0.1.0)

### Project Structure
```
- backend
  - src/main/java/com/nurs/backend/
    - config/          # Security, Rate Limiter
    - controller/      # REST API endpoints
    - dto/             # Data Transfer Objects
    - exception/       # Custom Exception
    - model/           # JPA Entitie
    - repository/      # Spring Data JPA interfaces
    - service/         # Business Logic
  - src/main/resources/ # application.properties
- frontend
- bruno                # Folder bruno app for Api testing
- actuator
- docker-compose.yml
```

### Screenshot
![image](/images/login.png)
![image](/images/actuator.png)
![image](/images/home.png)
![image](/images/transaction.png)
![image](/images/account.png)
![image](/images/loadtest-first-iteration.png)
![image](/images/loadtest-forth-iteration.png)

result load test 30connection in 3minutes (first iteration vs forth iteration)
- raw health: from 67k req/s -> 83k req/s 
- write test: from 8k req/s -> 12k req/s
- write test (rate error): 5 timeout 0.06% -> 0 timeout 0%
- read test: from 18k req/s -> 5k req/s (anomaly)

from this result, we already achive best result because for next iteration result raw health and write test is a same. 
but for read test, the result degrade almost like half, every each iteration. this is need future work, **read-write split database**

### how to run

For Information i use, java 17 and node 24 to run this program. while we can use docker to simplify. i find this method more reliable. because i notice bug like my backend cant connect to database if i use docker.
you need to run this in sequence, because backend depends on actuator and database, and frontend depends on backend

1. run database.
```sh
docker compose up -d
```

2. run actuator
```sh
cd actuator
./mvnw clean package
java -jar target/actuator-0.0.1-SNAPSHOT.jar
```

3. run backend
```sh
cd backend
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

4. run frontend
```sh
cd frontend
npm install
npm run lint
npm run start
```

5. run load testing 

you need to install [bombardier](https://github.com/codesenberg/bombardier) to run this

and modify loadtest.sh to replace token and destinationId

to get best result and find bootleneck you need to run at least 4x.


```sh
sh loadtest.sh
```

### Default Credentials
- ADMIN: username: admin, password: password
- USER: username: user, password: password
