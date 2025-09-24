package com.example.shardingjdbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shardingjdbc.dto.ConfigCreateReq;
import com.example.shardingjdbc.dto.ConfigUpdateReq;
import com.example.shardingjdbc.entity.TConfig;
import com.example.shardingjdbc.service.TConfigService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/configs", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TConfigController {

    private final TConfigService configService;

    public TConfigController(TConfigService configService) {
        this.configService = configService;
    }

    // 创建配置
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TConfig create(@Valid @RequestBody ConfigCreateReq req) {
        TConfig cfg = new TConfig();
        cfg.setConfigKey(req.getConfigKey());
        cfg.setConfigValue(req.getConfigValue());
        cfg.setDescription(req.getDescription());
        cfg.setStatus(req.getStatus());
        configService.save(cfg);
        return configService.getById(cfg.getId());
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public TConfig get(@PathVariable @Min(1) Long id) {
        TConfig cfg = configService.getById(id);
        if (cfg == null) {
            throw new IllegalArgumentException("配置不存在");
        }
        return cfg;
    }

    // 分页查询，支持按key模糊
    @GetMapping
    public Page<TConfig> list(@RequestParam(defaultValue = "1") @Min(1) long page,
                              @RequestParam(defaultValue = "10") @Min(1) long size,
                              @RequestParam(required = false) String key) {
        Page<TConfig> p = Page.of(page, size);
        LambdaQueryWrapper<TConfig> qw = new LambdaQueryWrapper<>();
        if (key != null && !key.isBlank()) {
            qw.like(TConfig::getConfigKey, key);
        }
        qw.orderByDesc(TConfig::getCreateTime);
        return configService.page(p, qw);
    }

    // 更新配置（部分字段）
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TConfig update(@PathVariable @Min(1) Long id, @Valid @RequestBody ConfigUpdateReq req) {
        TConfig exist = configService.getById(id);
        if (exist == null) {
            throw new IllegalArgumentException("配置不存在");
        }
        if (req.getConfigKey() != null) exist.setConfigKey(req.getConfigKey());
        if (req.getConfigValue() != null) exist.setConfigValue(req.getConfigValue());
        if (req.getDescription() != null) exist.setDescription(req.getDescription());
        if (req.getStatus() != null) exist.setStatus(req.getStatus());
        configService.updateById(exist);
        return configService.getById(id);
    }

    // 删除（逻辑删除）
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable @Min(1) Long id) {
        return configService.removeById(id);
    }
}
