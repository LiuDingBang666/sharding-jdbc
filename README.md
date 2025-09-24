# Sharding-JDBC Demo

## 主要演示功能

    取模分片-TUser
    根据月份分片-UserLog
    复合分片-TUserRole
    广播表-TConfig
    读写分离-全表，环境搭建见mysql，需要docker及docker compose环境
    分布式事务-

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



