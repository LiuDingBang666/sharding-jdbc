package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.entity.Config;
import com.example.shardingjdbc.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置控制器 - 广播表
 */
@Slf4j
@RestController
@RequestMapping("/api/configs")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 创建配置
     */
    @PostMapping
    public Map<String, Object> createConfig(@Valid @RequestBody Config config) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = configService.save(config);
            if (success) {
                result.put("code", 200);
                result.put("message", "配置创建成功");
                result.put("data", config);
            } else {
                result.put("code", 400);
                result.put("message", "配置创建失败");
            }
        } catch (Exception e) {
            log.error("创建配置异常", e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据配置键查询配置信息
     */
    @GetMapping("/key/{configKey}")
    public Map<String, Object> getConfigByKey(@PathVariable String configKey) {
        Map<String, Object> result = new HashMap<>();
        try {
            Config config = configService.getByConfigKey(configKey);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", config);
        } catch (Exception e) {
            log.error("查询配置异常, configKey: {}", configKey, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据配置键查询配置值
     */
    @GetMapping("/value/{configKey}")
    public Map<String, Object> getConfigValue(@PathVariable String configKey) {
        Map<String, Object> result = new HashMap<>();
        try {
            String configValue = configService.getConfigValue(configKey);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", configValue);
        } catch (Exception e) {
            log.error("查询配置值异常, configKey: {}", configKey, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 查询所有配置
     */
    @GetMapping
    public Map<String, Object> getAllConfigs() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Config> configs = configService.list();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", configs);
        } catch (Exception e) {
            log.error("查询所有配置异常", e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 更新配置值
     */
    @PutMapping("/value/{configKey}")
    public Map<String, Object> updateConfigValue(@PathVariable String configKey, @RequestParam String configValue) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = configService.updateConfigValue(configKey, configValue);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "配置更新成功" : "配置更新失败");
        } catch (Exception e) {
            log.error("更新配置值异常, configKey: {}, configValue: {}", configKey, configValue, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据ID查询配置
     */
    @GetMapping("/{id}")
    public Map<String, Object> getConfigById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Config config = configService.getById(id);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", config);
        } catch (Exception e) {
            log.error("查询配置异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 更新配置
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateConfig(@PathVariable Long id, @RequestBody Config config) {
        Map<String, Object> result = new HashMap<>();
        try {
            config.setId(id);
            boolean success = configService.updateById(config);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "配置更新成功" : "配置更新失败");
        } catch (Exception e) {
            log.error("更新配置异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 删除配置（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteConfig(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = configService.removeById(id);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "配置删除成功" : "配置删除失败");
        } catch (Exception e) {
            log.error("删除配置异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }
}