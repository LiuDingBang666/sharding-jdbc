package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.Order;
import com.example.shardingjdbc.entity.OrderItem;
import com.example.shardingjdbc.mapper.OrderMapper;
import com.example.shardingjdbc.service.OrderItemService;
import com.example.shardingjdbc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 订单服务实现类
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public List<Order> getByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        return baseMapper.selectByOrderNo(orderNo);
    }

    @Override
    public Integer countByOrderStatus(Integer orderStatus) {
        return baseMapper.countByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> getByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByUserIdAndTimeRange(userId, startTime, endTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createOrderWithItems(Order order, List<OrderItem> orderItems) {
        try {
            // 设置订单默认值
            if (order.getOrderNo() == null) {
                order.setOrderNo(generateOrderNo());
            }
            if (order.getOrderStatus() == null) {
                order.setOrderStatus(1); // 默认待付款状态
            }
            if (order.getCreateTime() == null) {
                order.setCreateTime(LocalDateTime.now());
            }
            if (order.getUpdateTime() == null) {
                order.setUpdateTime(LocalDateTime.now());
            }
            if (order.getDeleted() == null) {
                order.setDeleted(0);
            }

            // 计算订单总金额
            BigDecimal totalAmount = orderItems.stream()
                    .map(OrderItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalAmount(totalAmount);

            // 保存订单
            boolean orderSaved = save(order);
            if (!orderSaved) {
                log.error("保存订单失败");
                return false;
            }

            // 设置订单项的订单ID和用户ID
            for (OrderItem item : orderItems) {
                item.setOrderId(order.getOrderId());
                item.setUserId(order.getUserId());
                if (item.getCreateTime() == null) {
                    item.setCreateTime(LocalDateTime.now());
                }
                if (item.getUpdateTime() == null) {
                    item.setUpdateTime(LocalDateTime.now());
                }
                if (item.getDeleted() == null) {
                    item.setDeleted(0);
                }
            }

            // 批量保存订单项
            boolean itemsSaved = orderItemService.batchCreate(orderItems);
            if (!itemsSaved) {
                log.error("保存订单项失败");
                throw new RuntimeException("保存订单项失败");
            }

            return true;
        } catch (Exception e) {
            log.error("创建订单失败", e);
            throw new RuntimeException("创建订单失败", e);
        }
    }

    @Override
    public boolean updateOrderStatus(Long orderId, Integer orderStatus) {
        try {
            LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Order::getOrderId, orderId)
                        .set(Order::getOrderStatus, orderStatus)
                        .set(Order::getUpdateTime, LocalDateTime.now());
            return update(updateWrapper);
        } catch (Exception e) {
            log.error("更新订单状态失败, orderId: {}, orderStatus: {}", orderId, orderStatus, e);
            return false;
        }
    }

    @Override
    public boolean cancelOrder(Long orderId, Long userId) {
        try {
            LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Order::getOrderId, orderId)
                        .eq(Order::getUserId, userId)
                        .set(Order::getOrderStatus, 5) // 5-已取消
                        .set(Order::getUpdateTime, LocalDateTime.now());
            return update(updateWrapper);
        } catch (Exception e) {
            log.error("取消订单失败, orderId: {}, userId: {}", orderId, userId, e);
            return false;
        }
    }

    /**
     * 生成订单编号
     * @return 订单编号
     */
    private String generateOrderNo() {
        return "ORDER_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) 
               + "_" + System.currentTimeMillis() % 10000;
    }
}