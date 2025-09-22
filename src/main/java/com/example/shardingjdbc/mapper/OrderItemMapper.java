package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单项Mapper接口
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    /**
     * 根据订单ID查询订单项列表
     * @param orderId 订单ID
     * @return 订单项列表
     */
    @Select("SELECT * FROM t_order_item WHERE order_id = #{orderId} AND deleted = 0")
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据用户ID查询订单项列表
     * @param userId 用户ID
     * @return 订单项列表
     */
    @Select("SELECT * FROM t_order_item WHERE user_id = #{userId} AND deleted = 0")
    List<OrderItem> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据商品ID统计购买数量
     * @param productId 商品ID
     * @return 购买总数量
     */
    @Select("SELECT COALESCE(SUM(quantity), 0) FROM t_order_item WHERE product_id = #{productId} AND deleted = 0")
    Integer sumQuantityByProductId(@Param("productId") Long productId);

    /**
     * 根据订单ID计算订单总金额
     * @param orderId 订单ID
     * @return 订单总金额
     */
    @Select("SELECT COALESCE(SUM(subtotal), 0) FROM t_order_item WHERE order_id = #{orderId} AND deleted = 0")
    BigDecimal sumSubtotalByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据用户ID和商品ID查询订单项
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 订单项列表
     */
    @Select("SELECT * FROM t_order_item WHERE user_id = #{userId} AND product_id = #{productId} AND deleted = 0")
    List<OrderItem> selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}