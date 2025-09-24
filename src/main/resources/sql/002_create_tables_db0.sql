-- =========================================
-- Sharding JDBC 数据库表结构脚本
-- 数据库：sharding_db0
-- =========================================

USE sharding_db0;

-- 用户表 t_user_0
DROP TABLE IF EXISTS t_user_0;
CREATE TABLE t_user_0 (
    id BIGINT NOT NULL COMMENT '用户ID - 主键，分片键',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    email VARCHAR(100) COMMENT '用户邮箱',
    age INT COMMENT '用户年龄',
    gender TINYINT COMMENT '用户性别 1-男 2-女',
    status TINYINT DEFAULT 1 COMMENT '用户状态 1-正常 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_age (age),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表0';

-- 用户表 t_user_1
DROP TABLE IF EXISTS t_user_1;
CREATE TABLE t_user_1 (
    id BIGINT NOT NULL COMMENT '用户ID - 主键，分片键',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    email VARCHAR(100) COMMENT '用户邮箱',
    age INT COMMENT '用户年龄',
    gender TINYINT COMMENT '用户性别 1-男 2-女',
    status TINYINT DEFAULT 1 COMMENT '用户状态 1-正常 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_age (age),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表1';


-- 配置表 t_config (广播表)
DROP TABLE IF EXISTS t_config;
CREATE TABLE t_config (
    id BIGINT NOT NULL COMMENT '配置ID - 主键',
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    description VARCHAR(200) COMMENT '配置描述',
    status TINYINT DEFAULT 1 COMMENT '配置状态 1-启用 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_key (config_key),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='配置表(广播表)';

-- 复合主键 用户角色关联表 t_user_role_0 ds0.t_user_role_${0..1}_${1...4}
DROP TABLE IF EXISTS t_user_role_0_1;
CREATE TABLE t_user_role_0_1 (
    user_id BIGINT NOT NULL COMMENT '用户ID - 分片键',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码 - 复合主键',
    role_name VARCHAR(100) COMMENT '角色名称',
    assign_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
    PRIMARY KEY (user_id, role_code),
    KEY idx_role_code (role_code),
    KEY idx_assign_time (assign_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表0 - 复合主键';

create table t_user_role_0_2 like t_user_role_0_1;
create table t_user_role_0_3 like t_user_role_0_1;
create table t_user_role_0_4 like t_user_role_0_1;
create table t_user_role_1_1 like t_user_role_0_1;
create table t_user_role_1_2 like t_user_role_0_1;
create table t_user_role_1_3 like t_user_role_0_1;
create table t_user_role_1_4 like t_user_role_0_1;
create table t_user_role like t_user_role_0_1;