# cloud-employee-workstation

Учебный мультимодульный проект на Spring Boot / Spring Cloud.

## Сервисы

Инфраструктура:
- `config-server` — централизованная конфигурация
- `service-registry` — discovery service (Eureka)

Бизнес:
- `employee-service` (Java) — получение сотрудника и агрегированного ответа `employee + workstation`
- `workstation-service` (Kotlin) — получение одной `workstation (id, name, os)` по `employee-id`

## Особенности

- Gradle multi-module project
- build scripts на Groovy DSL
- in-memory storage
- `employee-service` вызывает `workstation-service` через OpenFeign по service-id `workstation-service`
- `workstation-service` запускается в 2 инстансах и регистрируется в Eureka
- Docker images + Docker Compose
- Kubernetes manifests для Minikube

## Сборка

```bash
./gradlew clean build
```

## Docker Compose

```bash
./gradlew :config-server:bootJar :service-registry:bootJar :employee-service:bootJar :workstation-service:bootJar
docker compose up -d --build --scale workstation-service=2
```

Собственная конфигурация `config-server` теперь хранится только в `config-server/src/main/resources/application.yml`.
Через Spring Cloud Config он отдает клиентским сервисам настройки из `config-server/src/main/resources/config/`, например `employee-service.yml`, `service-registry.yml` и `workstation-service.yml`.

## API

### `workstation-service`

Получить workstation сотрудника:

```text
GET /api/workstations/employee/{employeeId}
```

Пример ответа:

```json
{
  "id": 1,
  "name": "Lenovo ThinkPad T14",
  "os": "Windows 11"
}
```

Если workstation для сотрудника не найдена, сервис возвращает `404 Not Found`.

### `employee-service`

Получить агрегированный ответ по сотруднику:

```text
GET /api/employees/{id}/details
```

`employee-service` получает данные сотрудника локально, затем делает внутренний HTTP-вызов в `workstation-service` через OpenFeign по имени сервиса `workstation-service`. Разрешение имени сервиса выполняется через Eureka, а Spring Cloud LoadBalancer выбирает один из доступных инстансов `workstation-service`.

Пример ответа:

```json
{
  "employee": {
	"id": 1,
	"name": "Иван",
	"surname": "Петров",
	"dateOfEmployment": "2024-01-15"
  },
  "workstation": {
	"id": 1,
	"name": "Lenovo ThinkPad T14",
	"os": "Windows 11"
  }
}
```

Порядок старта сервисов в текущем `docker-compose.yml`:

1. Сначала запускается `config-server`.
2. `config-server` считается готовым только после успешного `healthcheck` по `http://localhost:8071/actuator/health`.
3. После статуса `healthy` стартует `service-registry`.
4. `service-registry` забирает конфигурацию из Spring Cloud Config по адресу `configserver:http://config-server:8071`.
5. После этого стартуют `employee-service` и сервис `workstation-service`.
6. `workstation-service` поднимается в двух репликах командой `docker compose up --scale workstation-service=2`.
7. `employee-service` и обе реплики `workstation-service` также забирают конфигурацию из Spring Cloud Config по адресу `configserver:http://config-server:8071`.
8. Обе реплики `workstation-service` регистрируются в `service-registry`, после чего становятся доступны для балансировки запросов.

Проверить состояние контейнеров можно так:

```bash
docker compose ps
docker compose logs -f config-server service-registry employee-service workstation-service
```

Остановить контейнеры можно так:

```bash
docker compose down
```

## Kubernetes

```bash
kubectl apply -f k8s/
```