package com.example.shardingjdbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shardingjdbc.dto.UserCreateReq;
import com.example.shardingjdbc.dto.UserUpdateReq;
import com.example.shardingjdbc.entity.TUser;
import com.example.shardingjdbc.service.TUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TUserController {

    private final TUserService userService;

    public TUserController(TUserService userService) {
        this.userService = userService;
    }

    /** 创建用户 */
    @PostMapping
    public TUser create(@Valid @RequestBody TUser req) {
        TUser user = new TUser();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setAge(req.getAge());
        user.setGender(req.getGender());
        user.setStatus(req.getStatus());
        // id 为空，使用 MyBatis-Plus 雪花算法生成，便于分片
        userService.save(user);
        return userService.getById(user.getId());
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public TUser get(@PathVariable @Min(1) Long id) {
        TUser user = userService.getById(id);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return user;
    }

    /** 分页查询，支持按用户名模糊搜索 */
    @GetMapping
    public Page<TUser> list(@RequestParam(defaultValue = "1") @Min(1) long page,
                             @RequestParam(defaultValue = "10") @Min(1) long size,
                             @RequestParam(required = false) String username) {
        Page<TUser> p = Page.of(page, size);
        LambdaQueryWrapper<TUser> qw = new LambdaQueryWrapper<>();
        if (username != null && !username.isBlank()) {
            qw.like(TUser::getUsername, username);
        }
        qw.orderByDesc(TUser::getCreateTime);
        return userService.page(p, qw);
    }

    /** 更新用户（部分字段） */
    @PutMapping("/{id}")
    public TUser update(@PathVariable @Min(1) Long id, @Valid @RequestBody TUser req) {
        TUser exist = userService.getById(id);
        if (exist == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (req.getUsername() != null) exist.setUsername(req.getUsername());
        if (req.getEmail() != null) exist.setEmail(req.getEmail());
        if (req.getAge() != null) exist.setAge(req.getAge());
        if (req.getGender() != null) exist.setGender(req.getGender());
        if (req.getStatus() != null) exist.setStatus(req.getStatus());
        userService.updateById(exist);
        return userService.getById(id);
    }

    /** 逻辑删除 */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable @Min(1) Long id) {
        return userService.removeById(id);
    }
}
