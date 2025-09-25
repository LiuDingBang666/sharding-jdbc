package com.example.shardingjdbc.service.impl;

import com.example.shardingjdbc.entity.TUserRole;
import com.example.shardingjdbc.mapper.TUserRoleMapper;
import com.example.shardingjdbc.service.TUserRoleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TUserRoleServiceImpl implements TUserRoleService {
    private final TUserRoleMapper mapper;

    public TUserRoleServiceImpl(TUserRoleMapper mapper) {
        this.mapper = mapper;
    }


}
