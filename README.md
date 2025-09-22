# Sharding JDBC 分库分表项目

## 项目简介

这是一个完整的Spring Boot整合MyBatis Plus和Sharding JDBC的分库分表演示项目。项目展示了如何使用Sharding JDBC实现数据库的水平拆分，包括分库分表、广播表、绑定表等所有核心功能。

## 技术栈

- **Spring Boot 3.1.12** - 主框架
- **MyBatis Plus 3.5.4.1** - ORM框架
- **Sharding JDBC 5.4.1** - 分库分表中间件
- **MySQL 8.0+** - 数据库
- **Druid 1.2.20** - 数据库连接池
- **Lombok** - 减少样板代码
- **FastJSON2** - JSON处理
- **JDK 17+** - Java开发环境

## 项目特性

### 📊 分库分表策略

1. **用户表（t_user）分片规则**：
   - **分库策略**：按 `user_id % 2` 分库（ds0, ds1）
   - **分表策略**：按 `id % 2` 分表（t_user_0, t_user_1）

2. **订单表（t_order）分片规则**：
   - **分库策略**：按 `user_id % 2` 分库（ds0, ds1）
   - **分表策略**：按 `order_id % 2` 分表（t_order_0, t_order_1）

3. **订单项表（t_order_item）分片规则**：
   - **分库策略**：按 `user_id % 2` 分库（ds0, ds1）
   - **分表策略**：按 `order_id % 2` 分表（t_order_item_0, t_order_item_1）
   - **绑定表**：与订单表绑定，使用相同的分片策略

4. **配置表（t_config）**：
   - **广播表**：在所有数据库中保存相同的数据

5. **用户日志表（user_log）按月分表规则**：
   - **分库策略**：按 `user_id % 2` 分库（ds0, ds1）
   - **分表策略**：按 `create_time` 月份分表（user_log_YYYYMM）
   - **复合分片**：同时支持用户维度和时间维度的数据分片

6. **交易记录表（transaction）按月分表规则**：
   - **分库策略**：按 `user_id % 2` 分库（ds0, ds1）
   - **分表策略**：按 `create_time` 月份分表（transaction_YYYYMM）
   - **复合分片**：同时支持用户维度和时间维度的数据分片

### 🔧 核心功能

- ✅ **水平分库分表**：支持多数据源的水平扩展
- ✅ **绑定表关联**：订单表和订单项表关联查询优化
- ✅ **广播表支持**：配置表在所有库中同步
- ✅ **按月分表**：支持基于时间的月度分表策略
- ✅ **复合分片**：支持用户维度+时间维度的二维分片
- ✅ **自动路由**：根据分片键自动路由到对应的数据源和表
- ✅ **跨片查询**：支持跨数据源的聚合查询
- ✅ **时间范围查询**：支持跨月份的时间范围查询
- ✅ **事务支持**：支持分布式事务
- ✅ **SQL可视化**：开启SQL日志输出，便于调试

## 项目结构

```
sharding-jdbc-demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/shardingjdbc/
│   │   │   ├── config/              # 配置类
│   │   │   │   ├── MybatisPlusConfig.java
│   │   │   │   ├── WebConfig.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── controller/          # 控制器层
│   │   │   │   ├── UserController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── OrderItemController.java
│   │   │   │   ├── ConfigController.java
│   │   │   │   ├── UserLogController.java      # 用户日志控制器
│   │   │   │   └── TransactionController.java  # 交易记录控制器
│   │   │   ├── entity/              # 实体类
│   │   │   │   ├── User.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   ├── Config.java
│   │   │   │   ├── UserLog.java                # 用户日志实体
│   │   │   │   └── Transaction.java            # 交易记录实体
│   │   │   ├── mapper/              # Mapper接口
│   │   │   │   ├── UserMapper.java
│   │   │   │   ├── OrderMapper.java
│   │   │   │   ├── OrderItemMapper.java
│   │   │   │   ├── ConfigMapper.java
│   │   │   │   ├── UserLogMapper.java          # 用户日志Mapper
│   │   │   │   └── TransactionMapper.java      # 交易记录Mapper
│   │   │   ├── service/             # 服务层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── OrderItemService.java
│   │   │   │   ├── ConfigService.java
│   │   │   │   ├── UserLogService.java         # 用户日志服务
│   │   │   │   ├── TransactionService.java     # 交易记录服务
│   │   │   │   └── impl/            # 服务实现
│   │   │   └── ShardingJdbcApplication.java
│   │   └── resources/
│   │       ├── application.yml      # 主配置文件
│   │       └── sql/                 # SQL脚本
│   │           ├── 000_execute_all.sql
│   │           ├── 001_create_database.sql
│   │           ├── 002_create_tables_db0.sql
│   │           ├── 003_create_tables_db1.sql
│   │           ├── 004_init_data.sql
│   │           ├── sharding_db0_user_log_monthly.sql    # 数据库0用户日志月分表
│   │           ├── sharding_db1_user_log_monthly.sql    # 数据库1用户日志月分表
│   │           ├── sharding_db0_transaction_monthly.sql # 数据库0交易记录月分表
│   │           └── sharding_db1_transaction_monthly.sql # 数据库1交易记录月分表
│   └── test/                        # 测试类
│       └── java/com/example/shardingjdbc/
│           ├── service/
│           │   ├── UserServiceTest.java
│           │   ├── OrderServiceTest.java
│           │   ├── ConfigServiceTest.java
│           │   ├── UserLogServiceTest.java          # 用户日志服务测试
│           │   └── TransactionServiceTest.java      # 交易记录服务测试
│           ├── controller/
│           │   ├── UserLogControllerTest.java       # 用户日志API测试
│           │   └── TransactionControllerTest.java   # 交易记录API测试
│           └── ShardingJdbcIntegrationTest.java
├── pom.xml
└── README.md
```

## 环境要求

- **JDK 17+** (Spring Boot 3 要求Java 17或更高版本)
- **Maven 3.6+**
- **MySQL 8.0+**

## 快速开始

### 1. 环境准备

#### 安装MySQL并启动服务
确保MySQL服务正在运行，并且可以通过以下连接信息访问：
- 主机：localhost
- 端口：3306
- 用户名：root
- 密码：root

### 2. 数据库初始化

#### 方式一：使用提供的SQL脚本
```bash
# 进入项目目录
cd d:\learn\sharding-jdbc

# 使用MySQL客户端执行初始化脚本
mysql -u root -p < src/main/resources/sql/000_execute_all.sql
```

#### 方式二：手动执行SQL脚本
依次执行以下SQL文件：
1. `001_create_database.sql` - 创建数据库
2. `002_create_tables_db0.sql` - 创建数据库0的表结构
3. `003_create_tables_db1.sql` - 创建数据库1的表结构  
4. `004_init_data.sql` - 初始化基础数据
5. `sharding_db0_user_log_monthly.sql` - 创建数据库0的用户日志月分表
6. `sharding_db1_user_log_monthly.sql` - 创建数据库1的用户日志月分表
7. `sharding_db0_transaction_monthly.sql` - 创建数据库0的交易记录月分表
8. `sharding_db1_transaction_monthly.sql` - 创建数据库1的交易记录月分表

### 3. 配置修改

如果你的MySQL配置与默认配置不同，请修改 `src/main/resources/application.yml` 文件中的数据库连接信息：

```yaml
spring:
  shardingsphere:
    datasource:
      ds0:
        url: jdbc:mysql://localhost:3306/sharding_db0?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
        username: root
        password: root
      ds1:
        url: jdbc:mysql://localhost:3306/sharding_db1?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
        username: root
        password: root
```

### 4. 启动项目

#### 使用Maven启动
```bash
cd d:\learn\sharding-jdbc
mvn spring-boot:run
```

#### 使用IDE启动
直接运行 `ShardingJdbcApplication.java` 主类

### 5. 验证启动

启动成功后，控制台会显示：
```
===========================================
   Sharding JDBC Demo 启动成功！
   访问地址：http://localhost:8080
   API文档地址：http://localhost:8080/api
===========================================
```

## API 接口文档

### 用户管理接口

#### 创建用户
```http
POST /api/users
Content-Type: application/json

{
    "id": 1001,
    "username": "test_user",
    "email": "test@example.com",
    "age": 25,
    "gender": 1,
    "status": 1
}
```

#### 查询用户
```http
GET /api/users/{id}                    # 根据ID查询用户
GET /api/users/username/{username}     # 根据用户名查询用户
GET /api/users/age?minAge=20&maxAge=30 # 根据年龄范围查询用户
GET /api/users/count/gender/{gender}   # 统计性别用户数量
```

#### 更新用户
```http
PUT /api/users/{id}                    # 更新用户信息
PUT /api/users/{id}/status?status=0    # 更新用户状态
```

#### 删除用户
```http
DELETE /api/users/{id}                 # 逻辑删除用户
```

### 订单管理接口

#### 创建订单
```http
POST /api/orders
Content-Type: application/json

{
    "order": {
        "orderId": 10001,
        "userId": 1001,
        "receiverName": "张三",
        "receiverPhone": "13800138000",
        "receiverAddress": "北京市朝阳区测试地址",
        "remark": "测试订单"
    },
    "orderItems": [
        {
            "productId": 2001,
            "productName": "测试商品1",
            "productPrice": 99.99,
            "quantity": 2,
            "subtotal": 199.98
        },
        {
            "productId": 2002,
            "productName": "测试商品2",
            "productPrice": 59.99,
            "quantity": 1,
            "subtotal": 59.99
        }
    ]
}
```

#### 查询订单
```http
GET /api/orders/{orderId}                           # 根据订单ID查询
GET /api/orders/orderNo/{orderNo}                   # 根据订单号查询
GET /api/orders/user/{userId}                       # 查询用户订单列表
GET /api/orders/count/status/{orderStatus}          # 统计订单状态数量
GET /api/orders/{orderId}/items                     # 查询订单的订单项
```

#### 更新订单
```http
PUT /api/orders/{orderId}/status?orderStatus=2      # 更新订单状态
PUT /api/orders/{orderId}/cancel?userId={userId}    # 取消订单
```

### 配置管理接口（广播表）

#### 创建配置
```http
POST /api/configs
Content-Type: application/json

{
    "configKey": "system.name",
    "configValue": "Sharding JDBC Demo",
    "description": "系统名称",
    "status": 1
}
```

#### 查询配置
```http
GET /api/configs                          # 查询所有配置
GET /api/configs/{id}                     # 根据ID查询配置
GET /api/configs/key/{configKey}          # 根据配置键查询配置
GET /api/configs/value/{configKey}        # 根据配置键查询配置值
```

#### 更新配置
```http
PUT /api/configs/{id}                     # 更新配置信息
PUT /api/configs/value/{configKey}?configValue=newValue  # 更新配置值
```

### 用户日志管理接口（按月分表）

#### 创建用户日志
```http
POST /api/user-logs
Content-Type: application/json

{
    "userId": 1001,
    "actionType": "LOGIN",
    "actionDescription": "用户登录",
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0",
    "requestUrl": "/api/login",
    "requestMethod": "POST",
    "requestParams": "{\"username\":\"test\"}",
    "responseStatus": 200,
    "executionTime": 120
}
```

#### 查询用户日志
```http
GET /api/user-logs/user/{userId}                                    # 根据用户ID查询日志
GET /api/user-logs/log/{logId}                                      # 根据日志ID查询日志
GET /api/user-logs/user/{userId}/time-range?startTime=2024-01-01 00:00:00&endTime=2024-12-31 23:59:59  # 时间范围查询
GET /api/user-logs/action/{actionType}                              # 根据操作类型查询
GET /api/user-logs/user/{userId}/count                              # 统计用户日志数量
GET /api/user-logs/user/{userId}/recent?limit=10                    # 查询最近日志
GET /api/user-logs/ip/{ipAddress}                                   # 根据IP地址查询
```

### 交易记录管理接口（按月分表）

#### 创建交易记录
```http
POST /api/transactions
Content-Type: application/json

{
    "userId": 1001,
    "orderId": 10001,
    "transactionType": 1,
    "amount": 299.99,
    "currency": "CNY",
    "paymentMethod": 1,
    "status": 1,
    "description": "购买商品支付",
    "transactionTime": "2024-01-15 10:30:00",
    "merchantId": "MERCHANT_001",
    "channel": "WEB",
    "fee": 2.99
}
```

#### 查询交易记录
```http
GET /api/transactions/user/{userId}                                 # 根据用户ID查询交易
GET /api/transactions/transaction-no/{transactionNo}                # 根据交易流水号查询
GET /api/transactions/order/{orderId}                               # 根据订单ID查询交易
GET /api/transactions/user/{userId}/time-range?startTime=2024-01-01 00:00:00&endTime=2024-12-31 23:59:59  # 时间范围查询
GET /api/transactions/type/{transactionType}/count                  # 统计交易类型数量
GET /api/transactions/user/{userId}/amount/sum?startTime=2024-01-01 00:00:00&endTime=2024-12-31 23:59:59&transactionType=1  # 统计交易金额
GET /api/transactions/user/{userId}/recent?limit=10                 # 查询最近交易记录
```

#### 更新交易记录
```http
PUT /api/transactions/{transactionId}/status?status=2               # 更新交易状态

## 测试用例

项目包含完整的测试用例，验证分库分表功能：

### 运行所有测试
```bash
mvn test
```

### 运行特定测试
```bash
# 用户服务测试
mvn test -Dtest=UserServiceTest

# 订单服务测试  
mvn test -Dtest=OrderServiceTest

# 配置服务测试（广播表）
mvn test -Dtest=ConfigServiceTest

# 用户日志服务测试（月分表）
mvn test -Dtest=UserLogServiceTest

# 交易记录服务测试（月分表）
mvn test -Dtest=TransactionServiceTest

# 用户日志API测试
mvn test -Dtest=UserLogControllerTest

# 交易记录API测试
mvn test -Dtest=TransactionControllerTest

# 集成测试
mvn test -Dtest=ShardingJdbcIntegrationTest
```

### 测试覆盖的功能

1. **分库分表验证**：测试数据是否正确路由到预期的数据库和表
2. **绑定表关联**：验证订单和订单项的关联查询
3. **广播表同步**：验证配置表在所有库中的数据一致性
4. **按月分表验证**：验证用户日志和交易记录按月分表的正确性
5. **跨月查询**：验证跨月份的时间范围查询功能
6. **复合分片测试**：验证用户维度+时间维度的二维分片
7. **跨片查询**：验证跨数据源的聚合查询功能
8. **事务完整性**：验证分布式事务的一致性
9. **性能测试**：批量数据插入和查询的性能测试

## 分片规则说明

### 分库策略

```yaml
# 用户ID取模决定数据库
database-inline:
  type: INLINE
  props:
    algorithm-expression: ds$->{user_id % 2}
```

- 偶数user_id → ds0数据库
- 奇数user_id → ds1数据库

### 分表策略

```yaml
# 用户表：按用户ID取模分表
user-table-inline:
  type: INLINE
  props:
    algorithm-expression: t_user_$->{id % 2}

# 订单表：按订单ID取模分表
order-table-inline:
  type: INLINE
  props:
    algorithm-expression: t_order_$->{order_id % 2}

# 用户日志表：按月分表
user-log-monthly:
  type: INLINE
  props:
    algorithm-expression: user_log_$->{create_time.format("yyyyMM")}

# 交易记录表：按月分表
transaction-monthly:
  type: INLINE
  props:
    algorithm-expression: transaction_$->{create_time.format("yyyyMM")}
```

### 绑定表关系

```yaml
binding-tables:
  - t_order,t_order_item
```

订单表和订单项表使用相同的分片策略，确保关联数据在同一个分片中。

### 广播表

```yaml
broadcast-tables:
  - t_config
```

配置表作为广播表，在所有数据库实例中保持数据一致。

## 监控和日志

### SQL日志输出
项目配置了SQL日志输出，可以看到实际执行的SQL和路由信息：

```yaml
spring:
  shardingsphere:
    props:
      sql-show: true  # 显示SQL
```

### MyBatis Plus日志
```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

### 应用日志
```yaml
logging:
  level:
    com.example.shardingjdbc.mapper: debug
    org.apache.shardingsphere: info
```

## 性能优化

### 连接池配置
使用Druid连接池，已进行优化配置：

```yaml
spring:
  datasource:
    druid:
      initial-size: 5        # 初始连接数
      min-idle: 10          # 最小空闲连接数
      max-active: 20        # 最大活跃连接数
      max-wait: 60000       # 获取连接等待超时时间
```

### 分页优化
配置了MyBatis Plus分页插件，支持物理分页。

### 查询优化建议
1. **使用分片键查询**：尽量在WHERE条件中包含分片键，避免全表扫描
2. **避免跨片JOIN**：设计时考虑数据关联关系，减少跨片查询
3. **合理使用绑定表**：相关联的表使用相同的分片策略
4. **批量操作优化**：使用MyBatis Plus的批量插入功能

## 常见问题

### Q1: 启动时提示数据库连接失败
**A**: 检查MySQL服务是否启动，数据库是否已创建，用户名密码是否正确。

### Q2: 分片路由不正确
**A**: 检查分片键是否正确设置，分片算法表达式是否符合预期。

### Q3: 跨片查询性能较慢
**A**: 这是分库分表的正常现象，建议优化查询条件，尽量包含分片键。

### Q4: 事务回滚异常
**A**: 检查是否正确配置了分布式事务，确保使用@Transactional注解。

### Q5: 测试数据查询不到
**A**: 检查是否使用了逻辑删除，确认deleted字段的值。

## 扩展功能

### 添加新的分片表
1. 在application.yml中添加分片规则
2. 创建对应的实体类和Mapper
3. 在所有数据库中创建相应的表结构

### 修改分片策略
1. 修改application.yml中的分片算法
2. 重新创建表结构（注意数据迁移）
3. 更新测试用例

### 增加数据源
1. 在datasource配置中添加新的数据源
2. 修改分片算法的表达式
3. 创建对应的数据库和表结构

## 版本历史

- **v1.0.0** - 初始版本，实现基础分库分表功能
  - 支持用户表、订单表、订单项表的分库分表
  - 支持配置表广播功能
  - 完整的CRUD操作和测试用例

- **v1.1.0** - 月分表功能版本
  - 新增用户日志表按月分表功能
  - 新增交易记录表按月分表功能
  - 支持复合分片策略（用户维度+时间维度）
  - 支持跨月份的时间范围查询
  - 完整的月分表API接口和测试用例

- **v2.0.0** - Spring Boot 3 升级版本
  - 升级Spring Boot到3.1.12版本
  - 升级JDK要求到17+
  - 升级ShardingSphere到5.4.1版本
  - 升级MyBatis Plus到3.5.4.1版本
  - 升级相关依赖到兼容Spring Boot 3的版本
  - 将javax包迁移到jakarta包
  - 使用FastJSON2替代FastJSON

## 贡献指南

欢迎提交Issue和Pull Request来改进这个项目！

## 许可证

MIT License

## 联系方式

如有问题，请提交Issue或联系项目维护者。

---

**🎉 恭喜！你已经成功创建了一个完整的Sharding JDBC分库分表项目！**