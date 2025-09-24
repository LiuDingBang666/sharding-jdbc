package com.example.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 应用启动类
 * 整合 MyBatis Plus 和 Sharding JDBC
 */
@SpringBootApplication
@MapperScan("com.example.shardingjdbc.mapper")
public class ShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
        System.out.println("===========================================");
        System.out.println("   Sharding JDBC Demo 启动成功！");
        System.out.println("   访问地址：http://localhost:8080");
        System.out.println("   API文档地址：http://localhost:8080/api");
        System.out.println("===========================================");
    }
}