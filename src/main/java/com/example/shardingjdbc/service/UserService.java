package com.example.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingjdbc.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 根据年龄范围查询用户
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return 用户列表
     */
    List<User> getByAgeRange(Integer minAge, Integer maxAge);

    /**
     * 根据性别统计用户数量
     * @param gender 性别
     * @return 用户数量
     */
    Integer countByGender(Integer gender);

    /**
     * 创建用户
     * @param user 用户信息
     * @return 创建结果
     */
    boolean createUser(User user);

    /**
     * 更新用户状态
     * @param userId 用户ID
     * @param status 用户状态
     * @return 更新结果
     */
    boolean updateUserStatus(Long userId, Integer status);
}