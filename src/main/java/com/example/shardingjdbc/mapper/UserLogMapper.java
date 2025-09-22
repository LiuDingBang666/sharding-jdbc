package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户日志Mapper接口
 * 支持按月分表查询
 */
@Mapper
public interface UserLogMapper extends BaseMapper<UserLog> {

    /**
     * 根据用户ID查询操作日志
     * @param userId 用户ID
     * @return 日志列表
     */
    @Select("SELECT * FROM t_user_log WHERE user_id = #{userId} AND deleted = 0 ORDER BY operation_time DESC")
    List<UserLog> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和时间范围查询日志
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日志列表
     */
    @Select("SELECT * FROM t_user_log WHERE user_id = #{userId} AND operation_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 ORDER BY operation_time DESC")
    List<UserLog> selectByUserIdAndTimeRange(@Param("userId") Long userId,
                                            @Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据操作类型统计日志数量
     * @param operationType 操作类型
     * @return 日志数量
     */
    @Select("SELECT COUNT(*) FROM t_user_log WHERE operation_type = #{operationType} AND deleted = 0")
    Integer countByOperationType(@Param("operationType") Integer operationType);

    /**
     * 根据模块统计日志数量
     * @param module 模块名
     * @return 日志数量
     */
    @Select("SELECT COUNT(*) FROM t_user_log WHERE module = #{module} AND deleted = 0")
    Integer countByModule(@Param("module") String module);

    /**
     * 根据用户ID和操作类型查询日志
     * @param userId 用户ID
     * @param operationType 操作类型
     * @return 日志列表
     */
    @Select("SELECT * FROM t_user_log WHERE user_id = #{userId} AND operation_type = #{operationType} AND deleted = 0 ORDER BY operation_time DESC")
    List<UserLog> selectByUserIdAndOperationType(@Param("userId") Long userId, @Param("operationType") Integer operationType);

    /**
     * 根据时间范围和状态查询日志
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param status 状态
     * @return 日志列表
     */
    @Select("SELECT * FROM t_user_log WHERE operation_time BETWEEN #{startTime} AND #{endTime} AND status = #{status} AND deleted = 0 ORDER BY operation_time DESC")
    List<UserLog> selectByTimeRangeAndStatus(@Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime,
                                            @Param("status") Integer status);

    /**
     * 查询指定用户在指定时间范围内的操作次数
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作次数
     */
    @Select("SELECT COUNT(*) FROM t_user_log WHERE user_id = #{userId} AND operation_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0")
    Integer countUserOperations(@Param("userId") Long userId,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);
}