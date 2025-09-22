package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.User;
import com.example.shardingjdbc.mapper.UserMapper;
import com.example.shardingjdbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public List<User> getByAgeRange(Integer minAge, Integer maxAge) {
        return baseMapper.selectByAgeRange(minAge, maxAge);
    }

    @Override
    public Integer countByGender(Integer gender) {
        return baseMapper.countByGender(gender);
    }

    @Override
    public boolean createUser(User user) {
        try {
            // 检查用户名是否已存在
            User existUser = getByUsername(user.getUsername());
            if (existUser != null) {
                log.warn("用户名已存在: {}", user.getUsername());
                return false;
            }
            
            // 设置默认值
            if (user.getStatus() == null) {
                user.setStatus(1); // 默认正常状态
            }
            if (user.getCreateTime() == null) {
                user.setCreateTime(LocalDateTime.now());
            }
            if (user.getUpdateTime() == null) {
                user.setUpdateTime(LocalDateTime.now());
            }
            if (user.getDeleted() == null) {
                user.setDeleted(0); // 默认未删除
            }
            
            return save(user);
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return false;
        }
    }

    @Override
    public boolean updateUserStatus(Long userId, Integer status) {
        try {
            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(User::getId, userId)
                        .set(User::getStatus, status)
                        .set(User::getUpdateTime, LocalDateTime.now());
            return update(updateWrapper);
        } catch (Exception e) {
            log.error("更新用户状态失败, userId: {}, status: {}", userId, status, e);
            return false;
        }
    }
}