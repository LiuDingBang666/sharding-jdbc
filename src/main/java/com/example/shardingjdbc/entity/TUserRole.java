package com.example.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关联（演示复合主键 user_id + role_code）。
 * 逻辑表：t_user_role  实际分片：t_user_role_0 / t_user_role_1 （与用户 id 取模一致）
 */
@Data
@TableName("t_user_role")
public class TUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID（分库/分表键，与 t_user 的分片策略保持一致） */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /** 角色编码（复合主键部分） */
    @TableField(value = "role_code")
    private Long roleCode;

    /** 角色名称（冗余展示） */
    @TableField(value = "role_name")
    private String roleName;

    /** 分配时间 */
    @TableField(value = "assign_time")
    private LocalDateTime assignTime;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;
}
