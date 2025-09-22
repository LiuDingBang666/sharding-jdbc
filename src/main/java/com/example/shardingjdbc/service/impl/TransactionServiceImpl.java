package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.Transaction;
import com.example.shardingjdbc.mapper.TransactionMapper;
import com.example.shardingjdbc.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 交易记录服务实现
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public boolean createTransaction(Transaction transaction) {
        if (transaction.getCreateTime() == null) {
            transaction.setCreateTime(LocalDateTime.now());
        }
        if (transaction.getTransactionNo() == null || transaction.getTransactionNo().isEmpty()) {
            transaction.setTransactionNo(generateTransactionNo());
        }
        return save(transaction);
    }

    @Override
    public List<Transaction> getByUserId(Long userId) {
        return transactionMapper.selectByUserId(userId);
    }

    @Override
    public Transaction getByTransactionNo(String transactionNo) {
        return transactionMapper.selectByTransactionNo(transactionNo);
    }

    @Override
    public List<Transaction> getByOrderId(Long orderId) {
        return transactionMapper.selectByOrderId(orderId);
    }

    @Override
    public List<Transaction> getByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return transactionMapper.selectByUserIdAndTimeRange(userId, startTime, endTime);
    }

    @Override
    public Integer countByTransactionType(Integer transactionType) {
        return transactionMapper.countByTransactionType(transactionType);
    }

    @Override
    public List<Transaction> getByUserIdAndTransactionType(Long userId, Integer transactionType) {
        return transactionMapper.selectByUserIdAndTransactionType(userId, transactionType);
    }

    @Override
    public BigDecimal sumAmountByUserIdAndTimeRangeAndType(Long userId, LocalDateTime startTime, LocalDateTime endTime, Integer transactionType) {
        BigDecimal result = transactionMapper.sumAmountByUserIdAndTimeRangeAndType(userId, startTime, endTime, transactionType);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumAmountByPaymentMethodAndTimeRange(Integer paymentMethod, LocalDateTime startTime, LocalDateTime endTime) {
        BigDecimal result = transactionMapper.sumAmountByPaymentMethodAndTimeRange(paymentMethod, startTime, endTime);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public List<Transaction> getRecentTransactionsByUserId(Long userId, Integer limit) {
        return transactionMapper.selectRecentTransactionsByUserId(userId, limit);
    }

    @Override
    public boolean updateTransactionStatus(Long transactionId, Integer status) {
        Transaction transaction = getById(transactionId);
        if (transaction != null) {
            transaction.setStatus(status);
            return updateById(transaction);
        }
        return false;
    }

    @Override
    public String generateTransactionNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "TXN_" + timestamp + "_" + uuid;
    }
}