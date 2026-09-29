# Order Notification System

Full-stack sipariş yönetimi ve bildirim sistemi.

Proje; Spring Boot, PostgreSQL, React, TypeScript ve Docker kullanılarak geliştirilmiştir. Sipariş durum değişikliklerinde Observer Design Pattern kullanılarak birbirinden bağımsız bildirim, audit ve stok işlemleri tetiklenir.

## Features

- Yeni sipariş oluşturma
- Siparişleri listeleme
- Sipariş detaylarını görüntüleme
- Sipariş durumlarını güncelleme
- Geçersiz durum geçişlerini engelleme
- Request validation
- Global exception handling
- Observer Design Pattern
- Audit logging
- E-mail notification simulation
- Stock operation simulation
- Swagger / OpenAPI documentation
- PostgreSQL persistence
- React + TypeScript dashboard
- Docker Compose ile full-stack çalışma
- Unit tests

## Technologies

### Backend

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Jakarta Validation
- PostgreSQL
- Maven
- JUnit
- Mockito
- SpringDoc OpenAPI

### Frontend

- React
- TypeScript
- Vite
- Fetch API
- Nginx

### DevOps

- Docker
- Docker Compose
- Multi-stage Docker builds

## Architecture

```text
Browser
   |
   v
React + TypeScript
   |
   v
Nginx
   |
   | /api/*
   v
Spring Boot REST API
   |
   v
OrderController
   |
   v
OrderService
   |
   +-------------------------+
   |                         |
   v                         v
PostgreSQL           OrderEventPublisher
                             |
              +--------------+--------------+
              |              |              |
              v              v              v
        AuditObserver   EmailObserver   StockObserver