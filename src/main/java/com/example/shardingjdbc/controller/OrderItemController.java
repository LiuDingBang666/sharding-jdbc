package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.entity.OrderItem;
import com.example.shardingjdbc.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单项控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    /**
     * 根据订单ID查询订单项列表
     */
    @GetMapping("/order/{orderId}")
    public Map<String, Object> getOrderItemsByOrderId(@PathVariable Long orderId) {
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

    /**
     * 根据用户ID查询订单项列表
     */
    @GetMapping("/user/{userId}")
    public Map<String, Object> getOrderItemsByUserId(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<OrderItem> orderItems = orderItemService.getByUserId(userId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", orderItems);
        } catch (Exception e) {
            log.error("查询用户订单项异常, userId: {}", userId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据用户ID和商品ID查询订单项
     */
    @GetMapping("/user/{userId}/product/{productId}")
    public Map<String, Object> getOrderItemsByUserIdAndProductId(@PathVariable Long userId, @PathVariable Long productId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<OrderItem> orderItems = orderItemService.getByUserIdAndProductId(userId, productId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", orderItems);
        } catch (Exception e) {
            log.error("查询订单项异常, userId: {}, productId: {}", userId, productId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据商品ID统计购买数量
     */
    @GetMapping("/product/{productId}/quantity")
    public Map<String, Object> sumQuantityByProductId(@PathVariable Long productId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer totalQuantity = orderItemService.sumQuantityByProductId(productId);
            result.put("code", 200);
            result.put("message", "统计成功");
            result.put("data", totalQuantity);
        } catch (Exception e) {
            log.error("统计商品购买数量异常, productId: {}", productId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据订单ID计算订单总金额
     */
    @GetMapping("/order/{orderId}/total")
    public Map<String, Object> sumSubtotalByOrderId(@PathVariable Long orderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            BigDecimal totalAmount = orderItemService.sumSubtotalByOrderId(orderId);
            result.put("code", 200);
            result.put("message", "计算成功");
            result.put("data", totalAmount);
        } catch (Exception e) {
            log.error("计算订单总金额异常, orderId: {}", orderId, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据ID查询订单项
     */
    @GetMapping("/{id}")
    public Map<String, Object> getOrderItemById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            OrderItem orderItem = orderItemService.getById(id);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", orderItem);
        } catch (Exception e) {
            log.error("查询订单项异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 更新订单项
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        Map<String, Object> result = new HashMap<>();
        try {
            orderItem.setId(id);
            boolean success = orderItemService.updateById(orderItem);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            log.error("更新订单项异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 删除订单项（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOrderItem(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderItemService.removeById(id);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            log.error("删除订单项异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }
}