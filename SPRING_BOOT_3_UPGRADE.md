# Spring Boot 3 升级说明文档

## 主要变更

### 1. 依赖版本升级
- Spring Boot: 2.7.12 → 3.1.12
- JDK: 8 → 17 (最低要求)
- ShardingSphere: 5.1.1 → 5.4.1
- MyBatis Plus: 3.5.3.1 → 3.5.4.1
- Druid: 1.2.16 → 1.2.20
- MySQL Connector: mysql-connector-java → mysql-connector-j
- FastJSON: fastjson → fastjson2

### 2. 包名迁移 (javax → jakarta)
- javax.validation.Valid → jakarta.validation.Valid
- javax.servlet.* → jakarta.servlet.*
- javax.persistence.* → jakarta.persistence.*

### 3. 配置变更
- 时区配置优化：GMT%2B8 → Asia/Shanghai
- 数据库驱动类名保持不变：com.mysql.cj.jdbc.Driver

### 4. 代码变更
- 导入包从 javax.validation.Valid 改为 jakarta.validation.Valid
- FastJSON 导入从 com.alibaba.fastjson 改为 com.alibaba.fastjson2

## 升级注意事项

### 环境要求
- 确保系统安装了JDK 17或更高版本
- Maven版本建议3.6+
- IDE需要支持Java 17

### 编译和运行
```bash
# 检查Java版本
java -version

# 设置JAVA_HOME (如需要)
export JAVA_HOME=/path/to/jdk17

# 编译项目
mvn clean compile

# 运行测试
mvn test

# 启动应用
mvn spring-boot:run
```

### 兼容性说明
- Spring Boot 3不再支持Java 8和Java 11
- 所有 javax.* 包都已迁移到 jakarta.*
- 部分配置属性可能有细微调整
- 性能和安全性得到显著提升

### 迁移后的优势
1. **性能提升**: Spring Boot 3采用了Spring Framework 6，性能更好
2. **原生编译支持**: 支持GraalVM原生镜像编译
3. **更好的可观测性**: 改进的Micrometer和Actuator集成
4. **安全性增强**: 升级的安全框架和CVE修复
5. **更好的云原生支持**: 改进的Docker和Kubernetes集成

## 验证升级是否成功

启动应用后，检查以下内容：
1. 应用能够正常启动，无报错
2. 数据库连接正常
3. 分库分表功能正常工作
4. API接口响应正常
5. 测试用例全部通过

## 可能遇到的问题

### 问题1：JDK版本不兼容
**解决方案**: 升级到JDK 17或更高版本

### 问题2：导入包错误
**解决方案**: 将所有javax.*包替换为jakarta.*包

### 问题3：配置不兼容
**解决方案**: 检查application.yml配置，按照Spring Boot 3的文档进行调整

### 问题4：第三方依赖不兼容
**解决方案**: 升级第三方依赖到支持Spring Boot 3的版本