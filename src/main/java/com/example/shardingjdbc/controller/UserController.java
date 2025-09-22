package com.example.shardingjdbc.controller;

import com.alibaba.fastjson2.JSON;
import com.example.shardingjdbc.entity.User;
import com.example.shardingjdbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户
     */
    @PostMapping
    public Map<String, Object> createUser(@Valid @RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = userService.createUser(user);
            if (success) {
                result.put("code", 200);
                result.put("message", "用户创建成功");
                result.put("data", user);
            } else {
                result.put("code", 400);
                result.put("message", "用户创建失败，用户名可能已存在");
            }
        } catch (Exception e) {
            log.error("创建用户异常", e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getById(id);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", user);
        } catch (Exception e) {
            log.error("查询用户异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public Map<String, Object> getUserByUsername(@PathVariable String username) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getByUsername(username);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", user);
        } catch (Exception e) {
            log.error("查询用户异常, username: {}", username, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据年龄范围查询用户
     */
    @GetMapping("/age")
    public Map<String, Object> getUsersByAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<User> users = userService.getByAgeRange(minAge, maxAge);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", users);
        } catch (Exception e) {
            log.error("查询用户异常, minAge: {}, maxAge: {}", minAge, maxAge, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 根据性别统计用户数量
     */
    @GetMapping("/count/gender/{gender}")
    public Map<String, Object> countUsersByGender(@PathVariable Integer gender) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer count = userService.countByGender(gender);
            result.put("code", 200);
            result.put("message", "统计成功");
            result.put("data", count);
        } catch (Exception e) {
            log.error("统计用户异常, gender: {}", gender, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            user.setId(id);
            boolean success = userService.updateById(user);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            log.error("更新用户异常, id: {}, user: {}", id, JSON.toJSONString(user), e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    public Map<String, Object> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = userService.updateUserStatus(id, status);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "状态更新成功" : "状态更新失败");
        } catch (Exception e) {
            log.error("更新用户状态异常, id: {}, status: {}", id, status, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 删除用户（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = userService.removeById(id);
            result.put("code", success ? 200 : 400);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            log.error("删除用户异常, id: {}", id, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    public Map<String, Object> getUsersPage(@RequestParam(defaultValue = "1") Long current,
                                           @RequestParam(defaultValue = "10") Long size) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 这里可以使用MyBatis Plus的分页功能
            List<User> users = userService.list();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", users);
        } catch (Exception e) {
            log.error("分页查询用户异常, current: {}, size: {}", current, size, e);
            result.put("code", 500);
            result.put("message", "系统异常");
        }
        return result;
    }
}