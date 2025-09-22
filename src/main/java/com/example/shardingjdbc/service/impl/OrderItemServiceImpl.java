package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.OrderItem;
import com.example.shardingjdbc.mapper.OrderItemMapper;
import com.example.shardingjdbc.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单项服务实现类
 */
@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        return baseMapper.selectByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public Integer sumQuantityByProductId(Long productId) {
        return baseMapper.sumQuantityByProductId(productId);
    }

    @Override
    public BigDecimal sumSubtotalByOrderId(Long orderId) {
        return baseMapper.sumSubtotalByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getByUserIdAndProductId(Long userId, Long productId) {
        return baseMapper.selectByUserIdAndProductId(userId, productId);
    }

    @Override
    public boolean batchCreate(List<OrderItem> orderItems) {
        try {
            // 设置默认值
            for (OrderItem item : orderItems) {
                if (item.getCreateTime() == null) {
                    item.setCreateTime(LocalDateTime.now());
                }
                if (item.getUpdateTime() == null) {
                    item.setUpdateTime(LocalDateTime.now());
                }
                if (item.getDeleted() == null) {
                    item.setDeleted(0);
                }
                // 计算小计金额
                if (item.getSubtotal() == null) {
                    item.setSubtotal(item.getProductPrice().multiply(new BigDecimal(item.getQuantity())));
                }
            }
            return saveBatch(orderItems);
        } catch (Exception e) {
            log.error("批量创建订单项失败", e);
            return false;
        }
    }
}