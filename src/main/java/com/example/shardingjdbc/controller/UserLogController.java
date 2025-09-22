package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.entity.UserLog;
import com.example.shardingjdbc.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户日志控制器
 * 支持按月分表的日志查询和操作
 */
@RestController
@RequestMapping("/api/user-logs")
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    /**
     * 记录用户日志
     */
    @PostMapping
    public String recordUserLog(@RequestBody UserLog userLog) {
        boolean success = userLogService.recordLog(userLog);
        return success ? "记录成功" : "记录失败";
    }

    /**
     * 根据用户ID查询日志
     */
    @GetMapping("/user/{userId}")
    public List<UserLog> getUserLogs(@PathVariable Long userId) {
        return userLogService.getByUserId(userId);
    }

    /**
     * 根据用户ID和时间范围查询日志
     */
    @GetMapping("/user/{userId}/time-range")
    public List<UserLog> getUserLogsByTimeRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return userLogService.getByUserIdAndTimeRange(userId, startTime, endTime);
    }

    /**
     * 根据操作类型统计日志数量
     */
    @GetMapping("/count/operation-type/{operationType}")
    public Integer countByOperationType(@PathVariable Integer operationType) {
        return userLogService.countByOperationType(operationType);
    }

    /**
     * 根据模块统计日志数量
     */
    @GetMapping("/count/module/{module}")
    public Integer countByModule(@PathVariable String module) {
        return userLogService.countByModule(module);
    }

    /**
     * 根据用户ID和操作类型查询日志
     */
    @GetMapping("/user/{userId}/operation-type/{operationType}")
    public List<UserLog> getUserLogsByOperationType(@PathVariable Long userId, @PathVariable Integer operationType) {
        return userLogService.getByUserIdAndOperationType(userId, operationType);
    }

    /**
     * 根据时间范围和状态查询日志
     */
    @GetMapping("/time-range-status")
    public List<UserLog> getLogsByTimeRangeAndStatus(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam Integer status) {
        return userLogService.getByTimeRangeAndStatus(startTime, endTime, status);
    }

    /**
     * 统计用户在指定时间范围内的操作次数
     */
    @GetMapping("/user/{userId}/operations/count")
    public Integer countUserOperations(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return userLogService.countUserOperations(userId, startTime, endTime);
    }

    /**
     * 批量记录用户日志
     */
    @PostMapping("/batch")
    public String batchRecordUserLogs(@RequestBody List<UserLog> userLogs) {
        boolean success = userLogService.batchRecordLogs(userLogs);
        return success ? "批量记录成功" : "批量记录失败";
    }
}