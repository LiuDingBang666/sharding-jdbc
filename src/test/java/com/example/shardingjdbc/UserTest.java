package com.example.shardingjdbc;

import com.example.shardingjdbc.entity.TUser;
import com.example.shardingjdbc.mapper.TUserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mayn
 * @version 1.0
 * @className com.example.shardingjdbc.UserTest
 * @description TODO 简单取模分库分表
 * @date 2025/9/24 13:17
 **/
@SpringBootTest
public class UserTest {

    @Resource
    TUserMapper mapper;



    /**
     * @author mayn
     * @description //TODO 测试写
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testAdd() {
        for (int i = 1; i <= 10; i++) {
            TUser user = new TUser();
            user.setId((long) i);
            user.setUsername("user" + i);
            user.setAge(20 + i);
            mapper.insert(user);
        }
    }


    /**
     * @author mayn
     * @description //TODO 测试读
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testGet() {
        for (int i = 1; i <= 10; i++) {
            TUser user = mapper.selectById(i);
            System.out.println(user);
        }
    }
}
