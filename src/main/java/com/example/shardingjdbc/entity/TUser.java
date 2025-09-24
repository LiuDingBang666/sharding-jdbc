package com.example.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体，对应逻辑表 t_user（实际分片表 t_user_0 / t_user_1）
 */
@Data
@TableName("t_user")
public class TUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 分片键 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 用户名 */
    private String username;

    /** 邮箱 */
    private String email;

    /** 年龄 */
    private Integer age;

    /** 性别 1-男 2-女 */
    private Integer gender;

    /** 状态 1-正常 0-禁用 */
    private Integer status;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除 0-未删除 1-已删除 */
    @TableLogic
    private Integer deleted;
}
