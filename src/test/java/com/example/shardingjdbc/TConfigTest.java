package com.example.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shardingjdbc.entity.TConfig;
import com.example.shardingjdbc.entity.TUserRole;
import com.example.shardingjdbc.mapper.TConfigMapper;
import com.example.shardingjdbc.mapper.TUserRoleMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author mayn
 * @version 1.0
 * @className com.example.shardingjdbc.UserTest
 * @description TODO 广播表
 * @date 2025/9/24 13:17
 **/
@SpringBootTest
public class TConfigTest {

    @Resource
    TConfigMapper mapper;



    /**
     * @author mayn
     * @description //TODO 测试写
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testAdd() {
        for (int i = 0; i < 2; i++) {
            TConfig entity = new TConfig();
            entity.setConfigKey("config" + i);
            entity.setConfigValue("value" + i);
            entity.setCreateTime(LocalDateTime.now());
            mapper.insert(entity);
        }
    }


    /**
     * @author mayn
     * @description //TODO 测试读
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testGet() {
        mapper.selectList(new LambdaQueryWrapper<TConfig>());
    }
}
