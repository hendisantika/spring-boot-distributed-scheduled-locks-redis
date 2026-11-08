# spring-boot-distributed-scheduled-locks-redis

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-distributed-scheduled-locks-redis/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-distributed-scheduled-locks-redis/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot)
![Redis](https://img.shields.io/badge/Redis-8.10-DC382D?logo=redis)
[![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)](.github/dependabot.yml)

Implementing Distributed and Scheduled Locks Using Redis and Spring Boot (Without ShedLock)!

### Introduction

In modern applications, ensuring that scheduled tasks do not run concurrently across distributed systems is crucial for
maintaining data integrity and consistency. One common solution for this problem is using distributed locks. In this
guide, we will explore how to implement distributed and scheduled locks using Redis and Spring Boot, without relying on
the ShedLock library.

We'll start by understanding the concept of distributed locking and why it's necessary in a microservices architecture.
Next, we'll set up a Redis server, which will act as our locking mechanism.

https://www.youtube.com/watch?v=yyC5PIgdutw

Then, we'll dive into a step-by-step implementation in a Spring Boot application, demonstrating how to acquire and
release locks to ensure that scheduled tasks run safely and without interference.

By the end of this tutorial, you'll have a clear understanding of how to use Redis for distributed locking in your
Spring Boot applications, enabling you to manage task scheduling effectively in a distributed environment.

### What is a Distributed Lock?

A distributed lock is a technique used in distributed systems to coordinate access to a shared resource or critical
section across multiple nodes or processes. It ensures that only one node or process can access or modify the shared
resource at a time, preventing concurrent access and potential conflicts. This mechanism is essential for maintaining
data consistency and integrity in a distributed environment.

### How It Works

The lock is a single Redis key written with `SET key value NX PX <ttl>` (via `RedisTemplate#setIfAbsent`):

- `NX` → the key is only written if it does **not** exist yet, so only one caller can win the lock.
- `PX <ttl>` → the lock expires automatically (15 seconds here), so a crashed node can never hold it forever.

| Class                  | Responsibility                                                              |
|------------------------|-----------------------------------------------------------------------------|
| `RedisConfig`          | Lettuce connection factory + `RedisTemplate` (String keys, Jackson 3 JSON values) |
| `RedisDistributedLock` | `acquireLock(key, timeout, unit)` / `releaseLock(key)`                      |
| `LockService`          | Runs the (simulated) operation only when the lock is acquired               |
| `LockController`       | `GET /perform/{lockKey}` endpoint to trigger the operation                  |

### Required Tech Stack

| Tool                         | Version                    | Notes                                                       |
|------------------------------|----------------------------|-------------------------------------------------------------|
| Java Development Kit (JDK)   | **25**                     | Build and runtime target (`java.version` in `pom.xml`)      |
| Spring Boot                  | **4.1.2**                  | Spring Web MVC, Spring Data Redis, Docker Compose support   |
| Redis                        | **8.10** (`redis:8.10.3-alpine`) | Acts as our distributed locking mechanism              |
| RedisInsight (optional)      | 3.8.0                      | Web UI for Redis on http://localhost:5540                   |
| Maven                        | 3.10.x (via `./mvnw`)      | Maven Wrapper is included, no local Maven needed            |
| Lombok                       | 1.18.48                    | Reduces boilerplate code                                    |
| Docker & Docker Compose      | latest                     | Runs Redis locally (`compose.yaml`)                         |

### How to Run

1. Clone the repository

   ```shell
   git clone https://github.com/hendisantika/spring-boot-distributed-scheduled-locks-redis.git
   cd spring-boot-distributed-scheduled-locks-redis
   ```

2. Make sure JDK 25 is active

   ```shell
   java -version   # should print 25.x
   ```

3. Run the application. Thanks to `spring-boot-docker-compose`, Redis and RedisInsight from `compose.yaml` are
   started automatically (Docker must be running):

   ```shell
   ./mvnw spring-boot:run
   ```

   Or start Redis yourself and run the packaged jar:

   ```shell
   docker compose up -d
   ./mvnw clean package
   java -jar target/distributed-scheduled-locks-redis-0.0.1-SNAPSHOT.jar
   ```

4. Run the tests

   ```shell
   ./mvnw verify
   ```

### How to Test

Send a HTTP request to test the lock mechanism. You can use ab (ApacheBench) to simulate multiple requests:

```shell
ab -n 10 -c 5 "http://localhost:8080/perform/lock-key"
```

### Request Count: 10

Concurrent Request Count: 5

This setup ensures that only one operation is performed at a time with the given lockKey, demonstrating the
effectiveness of our distributed lock implementation.

### Understanding the Locking Operation

When we perform a locking operation using the first HTTP request:

1x => Lock acquire: The lock is successfully acquired.

9x => Lock acquire failed: Subsequent attempts to acquire the lock fail due to it being already locked.

Example log output:

```text
INFO  LockService : Lock acquired. Background Operation started.
ERROR LockService : Failed to acquire lock. Resource is busy.   (x9)
```

The lock expires after 15 seconds; inspect it in Redis with:

```shell
docker exec -it redis redis-cli PTTL lock-key
```

### CI/CD & Dependency Management

- **GitHub Actions** ([`.github/workflows/maven.yml`](.github/workflows/maven.yml)) builds and tests the project with
  Temurin JDK 25 on every push / pull request to `main`, and submits the Maven dependency graph on push.
- **Dependabot** ([`.github/dependabot.yml`](.github/dependabot.yml)) keeps everything up to date:
    - Maven dependencies, plugins & Maven Wrapper — daily, grouped (Spring, Maven plugins, minor/patch)
    - GitHub Actions — weekly, grouped
    - Docker images in `compose.yaml` — weekly
