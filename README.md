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
./gradlew :config-server:bootJar :employee-service:bootJar
docker compose up -d --build
```

Собственная конфигурация `config-server` теперь хранится только в `config-server/src/main/resources/application.yml`.
Через Spring Cloud Config он отдает клиентским сервисам настройки из `config-server/src/main/resources/config/`, например `employee-service.properties`.

Порядок старта сервисов в текущем `docker-compose.yml`:

1. Сначала запускается `config-server`.
2. `config-server` считается готовым только после успешного `healthcheck` по `http://localhost:8071/actuator/health`.
3. После статуса `healthy` стартует `employee-service`.
4. На старте `employee-service` забирает конфигурацию из Spring Cloud Config по адресу `configserver:http://config-server:8071`.

Проверить состояние контейнеров можно так:

```bash
docker compose ps
docker compose logs -f config-server employee-service
```

Остановить контейнеры можно так:

```bash
docker compose down
```

## Kubernetes

```bash
kubectl apply -f k8s/
```