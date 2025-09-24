package com.example.shardingjdbc.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfigCreateReq {
    @NotBlank(message = "配置键不能为空")
    @Size(max = 100, message = "配置键长度不能超过100")
    private String configKey;

    // 配置值可为空，给个合理上限
    @Size(max = 2000, message = "配置值长度不能超过2000")
    private String configValue;

    @Size(max = 200, message = "描述长度不能超过200")
    private String description;

    /** 状态 1-启用 0-禁用 */
    @Min(0)
    @Max(1)
    private Integer status = 1;
}
