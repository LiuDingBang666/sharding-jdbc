package com.example.shardingjdbc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLogCreateReq {
    @NotBlank
    private String logId;
    @NotNull
    private Long userId;
    @NotBlank
    private String actionType;
    private String actionDescription;
    private String ipAddress;
    private String userAgent;
    private String requestUrl;
    private String requestMethod;
    private String requestParams;
    private Integer responseStatus;
    private Long executionTime;
    /** 若不传则用当前时间，作为分片字段 */
    private LocalDateTime operationTime;
}
