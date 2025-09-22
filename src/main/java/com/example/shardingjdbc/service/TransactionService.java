package com.example.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingjdbc.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易记录服务接口
 * 支持按月分表查询
 */
public interface TransactionService extends IService<Transaction> {

    /**
     * 创建交易记录
     * @param transaction 交易信息
     * @return 创建结果
     */
    boolean createTransaction(Transaction transaction);

    /**
     * 根据用户ID查询交易记录
     * @param userId 用户ID
     * @return 交易记录列表
     */
    List<Transaction> getByUserId(Long userId);

    /**
     * 根据交易流水号查询交易记录
     * @param transactionNo 交易流水号
     * @return 交易记录
     */
    Transaction getByTransactionNo(String transactionNo);

    /**
     * 根据订单ID查询交易记录
     * @param orderId 订单ID
     * @return 交易记录列表
     */
    List<Transaction> getByOrderId(Long orderId);

    /**
     * 根据用户ID和时间范围查询交易记录
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易记录列表
     */
    List<Transaction> getByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据交易类型统计交易数量
     * @param transactionType 交易类型
     * @return 交易数量
     */
    Integer countByTransactionType(Integer transactionType);

    /**
     * 根据用户ID和交易类型查询交易记录
     * @param userId 用户ID
     * @param transactionType 交易类型
     * @return 交易记录列表
     */
    List<Transaction> getByUserIdAndTransactionType(Long userId, Integer transactionType);

    /**
     * 计算用户在指定时间范围内的交易总金额
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param transactionType 交易类型
     * @return 交易总金额
     */
    BigDecimal sumAmountByUserIdAndTimeRangeAndType(Long userId, LocalDateTime startTime, LocalDateTime endTime, Integer transactionType);

    /**
     * 根据支付方式统计交易金额
     * @param paymentMethod 支付方式
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易总金额
     */
    BigDecimal sumAmountByPaymentMethodAndTimeRange(Integer paymentMethod, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询用户最近的交易记录
     * @param userId 用户ID
     * @param limit 限制条数
     * @return 交易记录列表
     */
    List<Transaction> getRecentTransactionsByUserId(Long userId, Integer limit);

    /**
     * 更新交易状态
     * @param transactionId 交易ID
     * @param status 新状态
     * @return 更新结果
     */
    boolean updateTransactionStatus(Long transactionId, Integer status);

    /**
     * 生成交易流水号
     * @return 交易流水号
     */
    String generateTransactionNo();
}