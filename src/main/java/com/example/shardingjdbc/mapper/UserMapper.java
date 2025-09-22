package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM t_user WHERE username = #{username} AND deleted = 0")
    User selectByUsername(@Param("username") String username);

    /**
     * 根据年龄范围查询用户
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return 用户列表
     */
    @Select("SELECT * FROM t_user WHERE age BETWEEN #{minAge} AND #{maxAge} AND deleted = 0")
    List<User> selectByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);

    /**
     * 根据性别统计用户数量
     * @param gender 性别
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE gender = #{gender} AND deleted = 0")
    Integer countByGender(@Param("gender") Integer gender);
}