package com.example.shardingjdbc.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLogUpdateReq {
    private String actionType;
    private String actionDescription;
    private String ipAddress;
    private String userAgent;
    private String requestUrl;
    private String requestMethod;
    private String requestParams;
    private Integer responseStatus;
    private Long executionTime;
    private LocalDateTime operationTime;
}
