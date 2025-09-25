package com.example.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户日志实体（逻辑表：t_user_log；实际分片：t_user_log_yyyyMM 按月分表）
 */
@Data
@TableName("t_user_log")
public class TUserLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID（可使用雪花），非分片键 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 业务日志ID，具备唯一性 */
    private String logId;

    /** 用户ID（用于分库策略） */
    private Long userId;

    /** 操作类型 */
    private String actionType;

    /** 操作描述 */
    private String actionDescription;

    /** IP 地址 */
    private String ipAddress;

    /** UA */
    private String userAgent;

    /** 请求 URL */
    private String requestUrl;

    /** 请求方法 */
    private String requestMethod;

    /** 请求参数（JSON） */
    private String requestParams;

    /** 响应状态码 */
    private Integer responseStatus;

    /** 执行时长（毫秒） */
    private Long executionTime;


    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
