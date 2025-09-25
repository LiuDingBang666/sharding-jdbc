package com.example.shardingjdbc.controller;

import com.example.shardingjdbc.entity.TUserRole;
import com.example.shardingjdbc.service.TUserRoleService;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/user-roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TUserRoleController {

    private final TUserRoleService userRoleService;

    public TUserRoleController(TUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }


}
