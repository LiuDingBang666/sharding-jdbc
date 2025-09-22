package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据用户ID查询订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    @Select("SELECT * FROM t_order WHERE user_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<Order> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据订单编号查询订单
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @Select("SELECT * FROM t_order WHERE order_no = #{orderNo} AND deleted = 0")
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据订单状态统计订单数量
     * @param orderStatus 订单状态
     * @return 订单数量
     */
    @Select("SELECT COUNT(*) FROM t_order WHERE order_status = #{orderStatus} AND deleted = 0")
    Integer countByOrderStatus(@Param("orderStatus") Integer orderStatus);

    /**
     * 根据时间范围查询订单
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 订单列表
     */
    @Select("SELECT * FROM t_order WHERE user_id = #{userId} AND create_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0")
    List<Order> selectByUserIdAndTimeRange(@Param("userId") Long userId, 
                                          @Param("startTime") LocalDateTime startTime, 
                                          @Param("endTime") LocalDateTime endTime);
}