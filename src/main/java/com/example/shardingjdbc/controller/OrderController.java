package com.example.shardingjdbc.controller;

import com.alibaba.fastjson2.JSON;
import com.example.shardingjdbc.entity.Order;
import com.example.shardingjdbc.entity.OrderItem;
import com.example.shardingjdbc.service.OrderItemService;
import com.example.shardingjdbc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * 创建订单（包含订单项）
     */
    @PostMapping
    public Map<String, Object> createOrder(@Valid @RequestBody Map<String, Object> orderData) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 解析订单信息
            Order order = JSON.parseObject(JSON.toJSONString(orderData.get("order")), Order.class);
            // 解析订单项列表
            List<OrderItem> orderItems = JSON.parseArray(JSON.toJSONString(orderData.get("orderItems")), OrderItem.class);
            
            boolean success = orderService.createOrderWithItems(order, orderItems);
            if (success) {
                result.put("code", 200);
                result.put("message", "订单创建成功");
                result.put("data", order);
            } else {
                result.put("code", 400);
                result.put("message", "订单创建失败");
            }
        } catch (Exception e) {
            log.error("创建订单异常", e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据订单ID查询订单
     */
    @GetMapping("/{orderId}")
    public Map<String, Object> getOrderById(@PathVariable Long orderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Order order = orderService.getById(orderId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", order);
        } catch (Exception e) {
            log.error("查询订单异常, orderId: {}", orderId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据订单编号查询订单
     */
    @GetMapping("/orderNo/{orderNo}")
    public Map<String, Object> getOrderByOrderNo(@PathVariable String orderNo) {
        Map<String, Object> result = new HashMap<>();
        try {
            Order order = orderService.getByOrderNo(orderNo);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", order);
        } catch (Exception e) {
            log.error("查询订单异常, orderNo: {}", orderNo, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据用户ID查询订单列表
     */
    @GetMapping("/user/{userId}")
    public Map<String, Object> getOrdersByUserId(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Order> orders = orderService.getByUserId(userId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", orders);
        } catch (Exception e) {
            log.error("查询用户订单异常, userId: {}", userId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据时间范围查询用户订单
     */
    @GetMapping("/user/{userId}/timeRange")
    public Map<String, Object> getOrdersByTimeRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Order> orders = orderService.getByUserIdAndTimeRange(userId, startTime, endTime);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", orders);
        } catch (Exception e) {
            log.error("查询订单异常, userId: {}, startTime: {}, endTime: {}", userId, startTime, endTime, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据订单状态统计订单数量
     */
    @GetMapping("/count/status/{orderStatus}")
    public Map<String, Object> countOrdersByStatus(@PathVariable Integer orderStatus) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer count = orderService.countByOrderStatus(orderStatus);
            result.put("code", 200);
            result.put("message", "统计成功");
            result.put("data", count);
        } catch (Exception e) {
            log.error("统计订单异常, orderStatus: {}", orderStatus, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 更新订单状态
     */
    @PutMapping("/{orderId}/status")
    public Map<String, Object> updateOrderStatus(@PathVariable Long orderId, @RequestParam Integer orderStatus) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.updateOrderStatus(orderId, orderStatus);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "状态更新成功" : "状态更新失败");
        } catch (Exception e) {
            log.error("更新订单状态异常, orderId: {}, orderStatus: {}", orderId, orderStatus, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 取消订单
     */
    @PutMapping("/{orderId}/cancel")
    public Map<String, Object> cancelOrder(@PathVariable Long orderId, @RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.cancelOrder(orderId, userId);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "订单取消成功" : "订单取消失败");
        } catch (Exception e) {
            log.error("取消订单异常, orderId: {}, userId: {}", orderId, userId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 查询订单的订单项列表
     */
    @GetMapping("/{orderId}/items")
    public Map<String, Object> getOrderItems(@PathVariable Long orderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<OrderItem> orderItems = orderItemService.getByOrderId(orderId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", orderItems);
        } catch (Exception e) {
            log.error("查询订单项异常, orderId: {}", orderId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }
}