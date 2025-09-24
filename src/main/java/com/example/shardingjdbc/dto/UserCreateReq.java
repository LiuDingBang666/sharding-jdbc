package com.example.shardingjdbc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateReq {
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名长度不能超过50")
    private String username;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    private String email;

    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 200, message = "年龄太大")
    private Integer age;

    /** 性别 1-男 2-女 */
    @Min(0)
    @Max(2)
    private Integer gender;

    /** 状态 1-正常 0-禁用 */
    @Min(0)
    @Max(1)
    private Integer status = 1;
}
