package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 配置Mapper接口 - 广播表
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

    /**
     * 根据配置键查询配置值
     * @param configKey 配置键
     * @return 配置值
     */
    @Select("SELECT config_value FROM t_config WHERE config_key = #{configKey} AND status = 1 AND deleted = 0")
    String getConfigValue(@Param("configKey") String configKey);

    /**
     * 根据配置键查询配置信息
     * @param configKey 配置键
     * @return 配置信息
     */
    @Select("SELECT * FROM t_config WHERE config_key = #{configKey} AND deleted = 0")
    Config selectByConfigKey(@Param("configKey") String configKey);
}