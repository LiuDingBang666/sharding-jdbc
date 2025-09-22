package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.UserLog;
import com.example.shardingjdbc.mapper.UserLogMapper;
import com.example.shardingjdbc.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户日志服务实现
 */
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public boolean recordLog(UserLog userLog) {
        if (userLog.getCreateTime() == null) {
            userLog.setCreateTime(LocalDateTime.now());
        }
        if (userLog.getOperationTime() == null) {
            userLog.setOperationTime(LocalDateTime.now());
        }
        return save(userLog);
    }

    @Override
    public List<UserLog> getByUserId(Long userId) {
        return userLogMapper.selectByUserId(userId);
    }

    @Override
    public List<UserLog> getByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return userLogMapper.selectByUserIdAndTimeRange(userId, startTime, endTime);
    }

    @Override
    public Integer countByOperationType(Integer operationType) {
        return userLogMapper.countByOperationType(operationType);
    }

    @Override
    public Integer countByModule(String module) {
        return userLogMapper.countByModule(module);
    }

    @Override
    public List<UserLog> getByUserIdAndOperationType(Long userId, Integer operationType) {
        return userLogMapper.selectByUserIdAndOperationType(userId, operationType);
    }

    @Override
    public List<UserLog> getByTimeRangeAndStatus(LocalDateTime startTime, LocalDateTime endTime, Integer status) {
        return userLogMapper.selectByTimeRangeAndStatus(startTime, endTime, status);
    }

    @Override
    public Integer countUserOperations(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return userLogMapper.countUserOperations(userId, startTime, endTime);
    }

    @Override
    public boolean batchRecordLogs(List<UserLog> userLogs) {
        if (userLogs == null || userLogs.isEmpty()) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        for (UserLog userLog : userLogs) {
            if (userLog.getCreateTime() == null) {
                userLog.setCreateTime(now);
            }
            if (userLog.getOperationTime() == null) {
                userLog.setOperationTime(now);
            }
        }
        
        return saveBatch(userLogs);
    }
}