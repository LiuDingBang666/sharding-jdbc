package com.example.shardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shardingjdbc.entity.TUser;
import com.example.shardingjdbc.entity.TUserLog;
import com.example.shardingjdbc.mapper.TUserLogMapper;
import com.example.shardingjdbc.mapper.TUserMapper;
import jakarta.annotation.Resource;
import org.apache.shardingsphere.sharding.algorithm.sharding.datetime.temporal.type.LocalDateTemporalHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mayn
 * @version 1.0
 * @className com.example.shardingjdbc.UserTest
 * @description TODO 按月分表
 * @date 2025/9/24 13:17
 **/
@SpringBootTest
public class LogTest {

    @Resource
    TUserLogMapper mapper;

    @Test
    public void testCreate() {
        for (int i = 0; i < 10; i++) {
            TUserLog log = new TUserLog();
            log.setLogId("log-" + i);
            log.setUserId((long) i);
            log.setActionType("action-" + i);
            log.setActionDescription("action-" + i);
            log.setIpAddress("192.168.1." + i);
            log.setUserAgent("1");
            log.setRequestUrl("1");
            log.setRequestMethod("1");
            log.setRequestParams("1");
            log.setResponseStatus(200);
            mapper.insert(log);
        }
    }

    /**
     * @author mayn
     * @description //TODO 测试读
     * @date 2025/9/24 13:22
     **/
    @Test
    public void testGet() {
        LocalDateTime now = LocalDateTime.now();
        // 精确查询
        List<TUserLog> logs = mapper.selectList(new LambdaQueryWrapper<TUserLog>().eq(TUserLog::getCreateTime, now));
        System.out.println(logs);
        // 范围查询
        List<TUserLog> logs1 = mapper.selectList(new LambdaQueryWrapper<TUserLog>().between(TUserLog::getCreateTime, now.minusDays(1), now));
        System.out.println(logs1);
    }
}
