package com.example.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbc.entity.TUserLog;
import com.example.shardingjdbc.mapper.TUserLogMapper;
import com.example.shardingjdbc.service.TUserLogService;
import org.springframework.stereotype.Service;

@Service
public class TUserLogServiceImpl extends ServiceImpl<TUserLogMapper, TUserLog> implements TUserLogService {
}
