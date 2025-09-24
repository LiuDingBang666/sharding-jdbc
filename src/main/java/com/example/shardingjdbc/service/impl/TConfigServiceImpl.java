package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.TConfig;
import com.example.shardingjdbc.mapper.TConfigMapper;
import com.example.shardingjdbc.service.TConfigService;
import org.springframework.stereotype.Service;

@Service
public class TConfigServiceImpl extends ServiceImpl<TConfigMapper, TConfig> implements TConfigService {
}
