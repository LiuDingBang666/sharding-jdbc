package com.example.shardingjdbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shardingjdbc.dto.UserLogCreateReq;
import com.example.shardingjdbc.dto.UserLogUpdateReq;
import com.example.shardingjdbc.entity.TUserLog;
import com.example.shardingjdbc.service.TUserLogService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/user-logs", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TUserLogController {

    private final TUserLogService userLogService;

    public TUserLogController(TUserLogService userLogService) {
        this.userLogService = userLogService;
    }

    /** 创建用户日志 */
    @PostMapping
    public TUserLog create(@Valid @RequestBody UserLogCreateReq req) {
        TUserLog log = new TUserLog();
        log.setLogId(req.getLogId());
        log.setUserId(req.getUserId());
        log.setActionType(req.getActionType());
        log.setActionDescription(req.getActionDescription());
        log.setIpAddress(req.getIpAddress());
        log.setUserAgent(req.getUserAgent());
        log.setRequestUrl(req.getRequestUrl());
        log.setRequestMethod(req.getRequestMethod());
        log.setRequestParams(req.getRequestParams());
        log.setResponseStatus(req.getResponseStatus());
        log.setExecutionTime(req.getExecutionTime());
        userLogService.save(log);
        return userLogService.getById(log.getId());
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public TUserLog get(@PathVariable @Min(1) Long id) {
        TUserLog log = userLogService.getById(id);
        if (log == null) {
            throw new IllegalArgumentException("日志不存在");
        }
        return log;
    }

    /** 分页查询，支持 userId、actionType、时间范围过滤 */
    @GetMapping
    public Page<TUserLog> list(@RequestParam(defaultValue = "1") @Min(1) long page,
                               @RequestParam(defaultValue = "10") @Min(1) long size,
                               @RequestParam(required = false) Long userId,
                               @RequestParam(required = false) String actionType,
                               @RequestParam(required = false) LocalDateTime startTime,
                               @RequestParam(required = false) LocalDateTime endTime) {
        Page<TUserLog> p = Page.of(page, size);
        LambdaQueryWrapper<TUserLog> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(TUserLog::getUserId, userId);
        if (actionType != null && !actionType.isBlank()) qw.eq(TUserLog::getActionType, actionType);

        return userLogService.page(p, qw);
    }

    /** 更新日志（部分字段） */
    @PutMapping("/{id}")
    public TUserLog update(@PathVariable @Min(1) Long id, @Valid @RequestBody UserLogUpdateReq req) {
        TUserLog exist = userLogService.getById(id);
        if (exist == null) {
            throw new IllegalArgumentException("日志不存在");
        }
        if (req.getActionType() != null) exist.setActionType(req.getActionType());
        if (req.getActionDescription() != null) exist.setActionDescription(req.getActionDescription());
        if (req.getIpAddress() != null) exist.setIpAddress(req.getIpAddress());
        if (req.getUserAgent() != null) exist.setUserAgent(req.getUserAgent());
        if (req.getRequestUrl() != null) exist.setRequestUrl(req.getRequestUrl());
        if (req.getRequestMethod() != null) exist.setRequestMethod(req.getRequestMethod());
        if (req.getRequestParams() != null) exist.setRequestParams(req.getRequestParams());
        if (req.getResponseStatus() != null) exist.setResponseStatus(req.getResponseStatus());
        if (req.getExecutionTime() != null) exist.setExecutionTime(req.getExecutionTime());
        userLogService.updateById(exist);
        return userLogService.getById(id);
    }

    /** 逻辑删除 */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable @Min(1) Long id) {
        return userLogService.removeById(id);
    }
}
