package com.example.shardingjdbc.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfigUpdateReq {
    @Size(max = 100, message = "配置键长度不能超过100")
    private String configKey;

    @Size(max = 2000, message = "配置值长度不能超过2000")
    private String configValue;


    private String description;

    @Min(0)
    @Max(1)
    private Integer status;
}
