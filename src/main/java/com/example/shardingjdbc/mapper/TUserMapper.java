package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.TUser;

/**
 * t_user 表 Mapper（逻辑表）
 * 实际路由到 t_user_0 / t_user_1
 */
public interface TUserMapper extends BaseMapper<TUser> {
    // 额外自定义查询在此定义（如需XML或注解SQL）
}
