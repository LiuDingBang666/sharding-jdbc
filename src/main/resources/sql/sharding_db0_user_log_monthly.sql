-- 用户日志按月分表SQL脚本
-- 创建用户日志表模板，按月分表
-- 表名格式：user_log_YYYYMM (例如：user_log_202401, user_log_202402)

-- 数据库1 (sharding_db0) 用户日志表创建脚本
USE sharding_db0;

-- 创建当前月和未来几个月的用户日志表
-- 2024年01月
CREATE TABLE IF NOT EXISTS user_log_202401 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    log_id VARCHAR(64) NOT NULL COMMENT '日志ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    action_type VARCHAR(50) NOT NULL COMMENT '操作类型',
    action_description TEXT COMMENT '操作描述',
    ip_address VARCHAR(45) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    request_url VARCHAR(500) COMMENT '请求URL',
    request_method VARCHAR(10) COMMENT '请求方法',
    request_params TEXT COMMENT '请求参数',
    response_status INT COMMENT '响应状态码',
    execution_time BIGINT COMMENT '执行时间(毫秒)',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_log_id (log_id),
    INDEX idx_action_type (action_type),
    INDEX idx_create_time (create_time),
    INDEX idx_ip_address (ip_address),
    UNIQUE KEY uk_log_id (log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户日志表_2024年01月';

-- 2024年02月
CREATE TABLE IF NOT EXISTS user_log_202402 LIKE user_log_202401;
ALTER TABLE user_log_202402 COMMENT='用户日志表_2024年02月';

-- 2024年03月
CREATE TABLE IF NOT EXISTS user_log_202403 LIKE user_log_202401;
ALTER TABLE user_log_202403 COMMENT='用户日志表_2024年03月';

-- 2024年04月
CREATE TABLE IF NOT EXISTS user_log_202404 LIKE user_log_202401;
ALTER TABLE user_log_202404 COMMENT='用户日志表_2024年04月';

-- 2024年05月
CREATE TABLE IF NOT EXISTS user_log_202405 LIKE user_log_202401;
ALTER TABLE user_log_202405 COMMENT='用户日志表_2024年05月';

-- 2024年06月
CREATE TABLE IF NOT EXISTS user_log_202406 LIKE user_log_202401;
ALTER TABLE user_log_202406 COMMENT='用户日志表_2024年06月';

-- 2024年07月
CREATE TABLE IF NOT EXISTS user_log_202407 LIKE user_log_202401;
ALTER TABLE user_log_202407 COMMENT='用户日志表_2024年07月';

-- 2024年08月
CREATE TABLE IF NOT EXISTS user_log_202408 LIKE user_log_202401;
ALTER TABLE user_log_202408 COMMENT='用户日志表_2024年08月';

-- 2024年09月
CREATE TABLE IF NOT EXISTS user_log_202409 LIKE user_log_202401;
ALTER TABLE user_log_202409 COMMENT='用户日志表_2024年09月';

-- 2024年10月
CREATE TABLE IF NOT EXISTS user_log_202410 LIKE user_log_202401;
ALTER TABLE user_log_202410 COMMENT='用户日志表_2024年10月';

-- 2024年11月
CREATE TABLE IF NOT EXISTS user_log_202411 LIKE user_log_202401;
ALTER TABLE user_log_202411 COMMENT='用户日志表_2024年11月';

-- 2024年12月
CREATE TABLE IF NOT EXISTS user_log_202412 LIKE user_log_202401;
ALTER TABLE user_log_202412 COMMENT='用户日志表_2024年12月';

-- 2025年01月
CREATE TABLE IF NOT EXISTS user_log_202501 LIKE user_log_202401;
ALTER TABLE user_log_202501 COMMENT='用户日志表_2025年01月';

-- 2025年02月
CREATE TABLE IF NOT EXISTS user_log_202502 LIKE user_log_202401;
ALTER TABLE user_log_202502 COMMENT='用户日志表_2025年02月';

-- 2025年03月
CREATE TABLE IF NOT EXISTS user_log_202503 LIKE user_log_202401;
ALTER TABLE user_log_202503 COMMENT='用户日志表_2025年03月';

CREATE TABLE user_log_202504 LIKE user_log_202503;
CREATE TABLE user_log_202505 LIKE user_log_202503;
CREATE TABLE user_log_202506 LIKE user_log_202503;
CREATE TABLE user_log_202507 LIKE user_log_202503;
CREATE TABLE user_log_202508 LIKE user_log_202503;
CREATE TABLE user_log_202509 LIKE user_log_202503;

-- 插入测试数据
INSERT INTO user_log_202401 (log_id, user_id, action_type, action_description, ip_address, user_agent, request_url, request_method, request_params, response_status, execution_time, create_time)
VALUES 
('LOG_202401_001', 1, 'LOGIN', '用户登录', '192.168.1.100', 'Mozilla/5.0', '/api/login', 'POST', '{"username":"user1"}', 200, 120, '2024-01-15 10:30:00'),
('LOG_202401_002', 3, 'VIEW_PRODUCT', '查看商品', '192.168.1.101', 'Mozilla/5.0', '/api/products/1', 'GET', '{}', 200, 80, '2024-01-15 14:20:00'),
('LOG_202401_003', 5, 'ADD_TO_CART', '添加到购物车', '192.168.1.102', 'Mozilla/5.0', '/api/cart/add', 'POST', '{"productId":1,"quantity":2}', 200, 150, '2024-01-15 16:45:00');

INSERT INTO user_log_202402 (log_id, user_id, action_type, action_description, ip_address, user_agent, request_url, request_method, request_params, response_status, execution_time, create_time)
VALUES 
('LOG_202402_001', 1, 'PURCHASE', '购买商品', '192.168.1.100', 'Mozilla/5.0', '/api/orders', 'POST', '{"productId":1,"quantity":1}', 200, 300, '2024-02-10 09:15:00'),
('LOG_202402_002', 3, 'LOGOUT', '用户登出', '192.168.1.101', 'Mozilla/5.0', '/api/logout', 'POST', '{}', 200, 50, '2024-02-10 18:30:00');

INSERT INTO user_log_202403 (log_id, user_id, action_type, action_description, ip_address, user_agent, request_url, request_method, request_params, response_status, execution_time, create_time)
VALUES 
('LOG_202403_001', 5, 'UPDATE_PROFILE', '更新个人信息', '192.168.1.102', 'Mozilla/5.0', '/api/users/profile', 'PUT', '{"name":"张三"}', 200, 200, '2024-03-05 11:20:00');