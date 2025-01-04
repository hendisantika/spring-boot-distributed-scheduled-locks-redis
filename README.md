# spring-boot-distributed-scheduled-locks-redis

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

### Required Tech Stack

Java Development Kit (JDK) 21: Ensure you have JDK 21 installed for the latest language features and improvements.

Spring Boot 3.4.1: Simplifies the development of stand-alone, production-grade Spring-based applications.

Redis: Acts as our distributed locking mechanism.

Spring Data Redis: Enables easy integration with Redis.

Maven or Gradle: Use as your build tool.

Lombok (Optional): Reduces boilerplate code.

### How to Test

Send a HTTP request to test the lock mechanism. You can use ab (ApacheBench) to simulate multiple requests:

```shell
ab -n 10 -c 5 "http://localhost:8080/perform/lock-key"
```

### Request Count: 20

Concurrent Request Count: 10

This setup ensures that only one operation is performed at a time with the given lockKey, demonstrating the
effectiveness of our distributed lock implementation.

### Understanding the Locking Operation

When we perform a locking operation using the first HTTP request:

1x => Lock acquire: The lock is successfully acquired.

9x => Lock acquire failed: Subsequent attempts to acquire the lock fail due to it being already locked.

