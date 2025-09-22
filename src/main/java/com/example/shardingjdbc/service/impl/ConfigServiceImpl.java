package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.Config;
import com.example.shardingjdbc.mapper.ConfigMapper;
import com.example.shardingjdbc.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 配置服务实现类 - 广播表
 */
@Slf4j
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public String getConfigValue(String configKey) {
        return baseMapper.getConfigValue(configKey);
    }

    @Override
    public Config getByConfigKey(String configKey) {
        return baseMapper.selectByConfigKey(configKey);
    }

    @Override
    public boolean updateConfigValue(String configKey, String configValue) {
        try {
            LambdaUpdateWrapper<Config> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Config::getConfigKey, configKey)
                        .set(Config::getConfigValue, configValue)
                        .set(Config::getUpdateTime, LocalDateTime.now());
            return update(updateWrapper);
        } catch (Exception e) {
            log.error("更新配置值失败, configKey: {}, configValue: {}", configKey, configValue, e);
            return false;
        }
    }
}