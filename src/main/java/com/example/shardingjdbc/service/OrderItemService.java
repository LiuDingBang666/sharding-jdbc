/*
 * @Author: ldb 2993442750@qq.com
 * @Date: 2025-09-22 15:15:38
 * @LastEditors: ldb 2993442750@qq.com
 * @LastEditTime: 2025-09-22 17:30:34
 * @FilePath: \sharding-jdbc\src\main\java\com\example\shardingjdbc\service\OrderItemService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.example.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingjdbc.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单项服务接口
 */
public interface OrderItemService extends IService<OrderItem> {

    /**
     * 根据订单ID查询订单项列表
     * @param orderId 订单ID
     * @return 订单项列表
     */
    List<OrderItem> getByOrderId(Long orderId);

    /**
     * 根据用户ID查询订单项列表
     * @param userId 用户ID
     * @return 订单项列表
     */
    List<OrderItem> getByUserId(Long userId);

    /**
     * 根据商品ID统计购买数量
     * @param productId 商品ID
     * @return 购买总数量
     */
    Integer sumQuantityByProductId(Long productId);

    /**
     * 根据订单ID计算订单总金额
     * @param orderId 订单ID
     * @return 订单总金额
     */
    BigDecimal sumSubtotalByOrderId(Long orderId);

    /**
     * 根据用户ID和商品ID查询订单项
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 订单项列表
     */
    List<OrderItem> getByUserIdAndProductId(Long userId, Long productId);

    /**
     * 批量创建订单项
     * @param orderItems 订单项列表
     * @return 创建结果
     */
    boolean batchCreate(List<OrderItem> orderItems);
}