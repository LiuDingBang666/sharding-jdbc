package com.example.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shardingjdbc.entity.TUser;
import com.example.shardingjdbc.entity.TUserRole;
import com.example.shardingjdbc.mapper.TUserMapper;
import com.example.shardingjdbc.mapper.TUserRoleMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author mayn
 * @version 1.0
 * @className com.example.shardingjdbc.UserTest
 * @description TODO 复合分库分表
 * @date 2025/9/24 13:17
 **/
@SpringBootTest
public class TUserRoleTest {

    @Resource
    TUserRoleMapper mapper;



    /**
     * @author mayn
     * @description //TODO 测试写
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testAdd() {
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 5; j++) {
                TUserRole entity = new TUserRole();
                entity.setUserId((long) i);
                entity.setRoleCode((long) j);
                entity.setRoleName("role" + j);
                entity.setAssignTime(LocalDateTime.now());
                entity.setRemark("remark" + j);
                mapper.insert(entity);
            }
        }
    }


    /**
     * @author mayn
     * @description //TODO 测试读
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testGet() {
//        TUserRole entity = mapper.selectOne(new LambdaQueryWrapper<TUserRole>()
//                .eq(TUserRole::getUserId, 1)
//                .eq(TUserRole::getRoleCode, 2)
//        );
        mapper.selectList(new LambdaQueryWrapper<TUserRole>()
                .in(TUserRole::getUserId, 1,2,3,4)
                .in(TUserRole::getRoleCode, 1,2,3,4)
        );
    }
}
