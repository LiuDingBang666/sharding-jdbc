package com.example.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户操作日志实体类
 * 支持联合分表：按用户ID分库 + 按操作时间月份分表
 * 分片策略：
 * - 分库：user_id % 2 决定数据库 (ds0, ds1)
 * - 分表：按操作时间的年月分表 (如：t_user_log_202401, t_user_log_202402)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_log")
public class UserLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID - 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID - 分库键
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作类型
     * 1-登录 2-登出 3-创建订单 4-支付 5-查询 6-更新资料 7-其他
     */
    @TableField("operation_type")
    private Integer operationType;

    /**
     * 操作描述
     */
    @TableField("operation_desc")
    private String operationDesc;

    /**
     * 操作模块
     */
    @TableField("module")
    private String module;

    /**
     * 操作方法
     */
    @TableField("method")
    private String method;

    /**
     * 请求参数
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 响应结果
     */
    @TableField("response_result")
    private String responseResult;

    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 操作状态 1-成功 0-失败
     */
    @TableField("status")
    private Integer status;

    /**
     * 错误信息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 执行耗时（毫秒）
     */
    @TableField("execution_time")
    private Long executionTime;

    /**
     * 操作时间 - 分表键（按年月分表）
     */
    @TableField(value = "operation_time", fill = FieldFill.INSERT)
    private LocalDateTime operationTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 逻辑删除标记 0-未删除 1-已删除
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}