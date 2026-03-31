# cloud-employee-workstation

Учебный мультимодульный проект на Spring Boot / Spring Cloud.

## Сервисы

Инфраструктура:
- `config-server` — централизованная конфигурация
- `service-registry` — discovery service (Eureka)
- `balancer` — API Gateway

Бизнес:
- `employee-service` (Java) — получение employee по id
- `workstation-service` (Kotlin) — получение workstation по employee-id

## Особенности

- Gradle multi-module project
- build scripts на Groovy DSL
- in-memory storage
- `workstation-service` запускается в 2 инстансах
- Docker images + Docker Compose
- Kubernetes manifests для Minikube

## Сборка

```bash
./gradlew clean build
```

## Docker Compose

```bash
docker compose up --build
```

## Kubernetes

```bash
kubectl apply -f k8s/
```