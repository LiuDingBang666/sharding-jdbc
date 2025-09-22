package com.example.shardingjdbc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("timestamp", LocalDateTime.now());
        result.put("service", "Sharding JDBC Demo");
        result.put("version", "1.0.0");
        return result;
    }

    /**
     * 应用信息接口
     */
    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> result = new HashMap<>();
        result.put("app", "Sharding JDBC Demo");
        result.put("version", "1.0.0");
        result.put("spring-boot", "2.7.12");
        result.put("java-version", System.getProperty("java.version"));
        result.put("description", "Spring Boot 2.7.12 分片应用演示");
        return result;
    }
}