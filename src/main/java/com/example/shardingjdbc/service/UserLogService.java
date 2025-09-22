package com.example.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingjdbc.entity.UserLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户日志服务接口
 * 支持按月分表查询
 */
public interface UserLogService extends IService<UserLog> {

    /**
     * 记录用户操作日志
     * @param userLog 日志信息
     * @return 保存结果
     */
    boolean recordLog(UserLog userLog);

    /**
     * 根据用户ID查询操作日志
     * @param userId 用户ID
     * @return 日志列表
     */
    List<UserLog> getByUserId(Long userId);

    /**
     * 根据用户ID和时间范围查询日志
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志列表
     */
    List<UserLog> getByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据操作类型统计日志数量
     * @param operationType 操作类型
     * @return 日志数量
     */
    Integer countByOperationType(Integer operationType);

    /**
     * 根据模块统计日志数量
     * @param module 模块名
     * @return 日志数量
     */
    Integer countByModule(String module);

    /**
     * 根据用户ID和操作类型查询日志
     * @param userId 用户ID
     * @param operationType 操作类型
     * @return 日志列表
     */
    List<UserLog> getByUserIdAndOperationType(Long userId, Integer operationType);

    /**
     * 根据时间范围和状态查询日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param status 状态
     * @return 日志列表
     */
    List<UserLog> getByTimeRangeAndStatus(LocalDateTime startTime, LocalDateTime endTime, Integer status);

    /**
     * 查询指定用户在指定时间范围内的操作次数
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作次数
     */
    Integer countUserOperations(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 批量记录用户操作日志
     * @param userLogs 日志列表
     * @return 保存结果
     */
    boolean batchRecordLogs(List<UserLog> userLogs);
}