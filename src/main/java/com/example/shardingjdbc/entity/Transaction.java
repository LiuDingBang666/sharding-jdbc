package com.example.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易记录实体类
 * 支持联合分表：按用户ID分库 + 按交易时间月份分表
 * 分片策略：
 * - 分库：user_id % 2 决定数据库 (ds0, ds1)
 * - 分表：按交易时间的年月分表 (如：t_transaction_202401, t_transaction_202402)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易ID - 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID - 分库键
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 交易流水号
     */
    @TableField("transaction_no")
    private String transactionNo;

    /**
     * 关联订单ID
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 交易类型
     * 1-支付 2-退款 3-转账 4-充值 5-提现
     */
    @TableField("transaction_type")
    private Integer transactionType;

    /**
     * 交易金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 交易前余额
     */
    @TableField("balance_before")
    private BigDecimal balanceBefore;

    /**
     * 交易后余额
     */
    @TableField("balance_after")
    private BigDecimal balanceAfter;

    /**
     * 支付方式
     * 1-余额支付 2-支付宝 3-微信 4-银行卡 5-其他
     */
    @TableField("payment_method")
    private Integer paymentMethod;

    /**
     * 第三方交易号
     */
    @TableField("third_party_no")
    private String thirdPartyNo;

    /**
     * 交易状态
     * 1-待处理 2-处理中 3-成功 4-失败 5-已取消
     */
    @TableField("status")
    private Integer status;

    /**
     * 交易描述
     */
    @TableField("description")
    private String description;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 交易时间 - 分表键（按年月分表）
     */
    @TableField(value = "transaction_time", fill = FieldFill.INSERT)
    private LocalDateTime transactionTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 0-未删除 1-已删除
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}