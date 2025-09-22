package com.example.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingjdbc.entity.Order;
import com.example.shardingjdbc.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService extends IService<Order> {

    /**
     * 根据用户ID查询订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getByUserId(Long userId);

    /**
     * 根据订单编号查询订单
     * @param orderNo 订单编号
     * @return 订单信息
     */
    Order getByOrderNo(String orderNo);

    /**
     * 根据订单状态统计订单数量
     * @param orderStatus 订单状态
     * @return 订单数量
     */
    Integer countByOrderStatus(Integer orderStatus);

    /**
     * 根据时间范围查询订单
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 订单列表
     */
    List<Order> getByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 创建订单（包含订单项）
     * @param order 订单信息
     * @param orderItems 订单项列表
     * @return 创建结果
     */
    boolean createOrderWithItems(Order order, List<OrderItem> orderItems);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param orderStatus 订单状态
     * @return 更新结果
     */
    boolean updateOrderStatus(Long orderId, Integer orderStatus);

    /**
     * 取消订单
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 取消结果
     */
    boolean cancelOrder(Long orderId, Long userId);
}