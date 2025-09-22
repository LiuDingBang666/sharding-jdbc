-- =========================================
-- Sharding JDBC 数据库表结构脚本
-- 数据库：sharding_db1
-- =========================================

USE sharding_db1;

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

-- 订单表 t_order_0
DROP TABLE IF EXISTS t_order_0;
CREATE TABLE t_order_0 (
    order_id BIGINT NOT NULL COMMENT '订单ID - 主键，分表键',
    user_id BIGINT NOT NULL COMMENT '用户ID - 分库键',
    order_no VARCHAR(32) NOT NULL COMMENT '订单编号',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    order_status TINYINT DEFAULT 1 COMMENT '订单状态 1-待付款 2-已付款 3-已发货 4-已完成 5-已取消',
    receiver_name VARCHAR(50) COMMENT '收货人姓名',
    receiver_phone VARCHAR(20) COMMENT '收货人电话',
    receiver_address VARCHAR(200) COMMENT '收货地址',
    remark VARCHAR(500) COMMENT '订单备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (order_id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_order_status (order_status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表0';

-- 订单表 t_order_1
DROP TABLE IF EXISTS t_order_1;
CREATE TABLE t_order_1 (
    order_id BIGINT NOT NULL COMMENT '订单ID - 主键，分表键',
    user_id BIGINT NOT NULL COMMENT '用户ID - 分库键',
    order_no VARCHAR(32) NOT NULL COMMENT '订单编号',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    order_status TINYINT DEFAULT 1 COMMENT '订单状态 1-待付款 2-已付款 3-已发货 4-已完成 5-已取消',
    receiver_name VARCHAR(50) COMMENT '收货人姓名',
    receiver_phone VARCHAR(20) COMMENT '收货人电话',
    receiver_address VARCHAR(200) COMMENT '收货地址',
    remark VARCHAR(500) COMMENT '订单备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (order_id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_order_status (order_status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表1';

-- 订单项表 t_order_item_0
DROP TABLE IF EXISTS t_order_item_0;
CREATE TABLE t_order_item_0 (
    id BIGINT NOT NULL COMMENT '订单项ID - 主键',
    order_id BIGINT NOT NULL COMMENT '订单ID - 分表键',
    user_id BIGINT NOT NULL COMMENT '用户ID - 分库键',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    product_price DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    quantity INT NOT NULL COMMENT '购买数量',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    KEY idx_user_id (user_id),
    KEY idx_product_id (product_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单项表0';

-- 订单项表 t_order_item_1
DROP TABLE IF EXISTS t_order_item_1;
CREATE TABLE t_order_item_1 (
    id BIGINT NOT NULL COMMENT '订单项ID - 主键',
    order_id BIGINT NOT NULL COMMENT '订单ID - 分表键',
    user_id BIGINT NOT NULL COMMENT '用户ID - 分库键',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    product_price DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    quantity INT NOT NULL COMMENT '购买数量',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    KEY idx_user_id (user_id),
    KEY idx_product_id (product_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单项表1';

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