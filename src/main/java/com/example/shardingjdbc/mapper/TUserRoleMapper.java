package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.TUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 复合主键表 t_user_role 的 Mapper。使用注解方式演示自定义 SQL。
 */
@Mapper
public interface TUserRoleMapper extends BaseMapper<TUserRole> {


}
