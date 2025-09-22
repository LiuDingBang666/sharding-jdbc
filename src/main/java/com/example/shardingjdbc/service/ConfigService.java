package com.example.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingjdbc.entity.Config;

/**
 * 配置服务接口 - 广播表
 */
public interface ConfigService extends IService<Config> {

    /**
     * 根据配置键查询配置值
     * @param configKey 配置键
     * @return 配置值
     */
    String getConfigValue(String configKey);

    /**
     * 根据配置键查询配置信息
     * @param configKey 配置键
     * @return 配置信息
     */
    Config getByConfigKey(String configKey);

    /**
     * 更新配置值
     * @param configKey 配置键
     * @param configValue 配置值
     * @return 更新结果
     */
    boolean updateConfigValue(String configKey, String configValue);
}