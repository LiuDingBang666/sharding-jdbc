# Sharding-JDBC Demo

## t_user CRUD APIs

Base URL: http://localhost:8080

- POST /users
  - Create user
  - Body (JSON):
    {
      "username": "alice",
      "email": "alice@example.com",
      "age": 28,
      "gender": 1,
      "status": 1
    }
- GET /users/{id}
  - Get user by id
- GET /users?page=1&size=10&username=ali
  - Page list with optional fuzzy username filter
- PUT /users/{id}
  - Partial update
  - Body (JSON): any subset of fields from create request
- DELETE /users/{id}
  - Logical delete

## Build

- JDK 21
- Maven 3.9+

Build:

```
cmd /c mvn -DskipTests clean package
```

Run:

```
cmd /c java -jar target\sharding-jdbc-1.0.0.jar
```

## Database

- Two MySQL schemas required: sharding_db0, sharding_db1
- Credentials: root/root (configurable in `src/main/resources/sharding-jdbc-config.yml`)
- Initialize tables:
  - Run the SQL files under `src/main/resources/sql/` in order:
    - 001_create_database.sql
    - 002_create_tables_db0.sql
    - 003_create_tables_db1.sql
    - 004_init_data.sql (optional)

## Notes

- Uses MyBatis-Plus IdType.ASSIGN_ID (snowflake) so `id` can be omitted when creating users; sharding uses `id` for db/table routing.
- Logical delete enabled via `deleted` field and MyBatis-Plus global config.
- Jakarta Validation is enabled (`spring-boot-starter-validation`), invalid requests return 400 with details.

