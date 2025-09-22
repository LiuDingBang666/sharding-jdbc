package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.entity.Transaction;
import com.example.shardingjdbc.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易记录控制器
 * 支持按月分表的交易查询和操作
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * 创建交易记录
     */
    @PostMapping
    public String createTransaction(@RequestBody Transaction transaction) {
        boolean success = transactionService.createTransaction(transaction);
        return success ? "创建成功" : "创建失败";
    }

    /**
     * 根据用户ID查询交易记录
     */
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUserId(@PathVariable Long userId) {
        return transactionService.getByUserId(userId);
    }

    /**
     * 根据交易流水号查询交易记录
     */
    @GetMapping("/transaction-no/{transactionNo}")
    public Transaction getTransactionByNo(@PathVariable String transactionNo) {
        return transactionService.getByTransactionNo(transactionNo);
    }

    /**
     * 根据订单ID查询交易记录
     */
    @GetMapping("/order/{orderId}")
    public List<Transaction> getTransactionsByOrderId(@PathVariable Long orderId) {
        return transactionService.getByOrderId(orderId);
    }

    /**
     * 根据用户ID和时间范围查询交易记录
     */
    @GetMapping("/user/{userId}/time-range")
    public List<Transaction> getTransactionsByTimeRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return transactionService.getByUserIdAndTimeRange(userId, startTime, endTime);
    }

    /**
     * 根据交易类型统计交易数量
     */
    @GetMapping("/type/{transactionType}/count")
    public Integer countTransactionsByType(@PathVariable Integer transactionType) {
        return transactionService.countByTransactionType(transactionType);
    }

    /**
     * 根据用户ID和交易类型查询交易记录
     */
    @GetMapping("/user/{userId}/type/{transactionType}")
    public List<Transaction> getTransactionsByUserIdAndType(
            @PathVariable Long userId,
            @PathVariable Integer transactionType) {
        return transactionService.getByUserIdAndTransactionType(userId, transactionType);
    }

    /**
     * 计算用户在指定时间范围内的交易总金额
     */
    @GetMapping("/user/{userId}/amount/sum")
    public BigDecimal sumTransactionAmount(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam Integer transactionType) {
        return transactionService.sumAmountByUserIdAndTimeRangeAndType(userId, startTime, endTime, transactionType);
    }

    /**
     * 根据支付方式统计交易金额
     */
    @GetMapping("/payment-method/{paymentMethod}/amount/sum")
    public BigDecimal sumAmountByPaymentMethod(
            @PathVariable Integer paymentMethod,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return transactionService.sumAmountByPaymentMethodAndTimeRange(paymentMethod, startTime, endTime);
    }

    /**
     * 查询用户最近的交易记录
     */
    @GetMapping("/user/{userId}/recent")
    public List<Transaction> getRecentTransactions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") Integer limit) {
        return transactionService.getRecentTransactionsByUserId(userId, limit);
    }

    /**
     * 更新交易状态
     */
    @PutMapping("/{transactionId}/status")
    public String updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestParam Integer status) {
        boolean success = transactionService.updateTransactionStatus(transactionId, status);
        return success ? "更新成功" : "更新失败";
    }

    /**
     * 生成交易流水号
     */
    @GetMapping("/generate-transaction-no")
    public String generateTransactionNo() {
        return transactionService.generateTransactionNo();
    }

    /**
     * 根据交易ID查询交易记录
     */
    @GetMapping("/{transactionId}")
    public Transaction getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getById(transactionId);
    }

    /**
     * 删除交易记录
     */
    @DeleteMapping("/{transactionId}")
    public String deleteTransaction(@PathVariable Long transactionId) {
        boolean success = transactionService.removeById(transactionId);
        return success ? "删除成功" : "删除失败";
    }
}