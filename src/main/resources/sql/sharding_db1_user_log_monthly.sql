-- 用户日志按月分表SQL脚本
-- 创建用户日志表模板，按月分表
-- 表名格式：user_log_YYYYMM (例如：user_log_202401, user_log_202402)

-- 数据库2 (sharding_db1) 用户日志表创建脚本
USE sharding_db1;

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

-- 插入测试数据
INSERT INTO user_log_202401 (log_id, user_id, action_type, action_description, ip_address, user_agent, request_url, request_method, request_params, response_status, execution_time, create_time)
VALUES 
('LOG_202401_004', 2, 'REGISTER', '用户注册', '192.168.1.103', 'Mozilla/5.0', '/api/register', 'POST', '{"username":"user2","email":"user2@test.com"}', 200, 250, '2024-01-12 08:45:00'),
('LOG_202401_005', 4, 'SEARCH', '搜索商品', '192.168.1.104', 'Mozilla/5.0', '/api/search', 'GET', '{"keyword":"手机"}', 200, 60, '2024-01-12 13:20:00'),
('LOG_202401_006', 6, 'VIEW_ORDER', '查看订单', '192.168.1.105', 'Mozilla/5.0', '/api/orders/1', 'GET', '{}', 200, 90, '2024-01-12 15:30:00');

INSERT INTO user_log_202402 (log_id, user_id, action_type, action_description, ip_address, user_agent, request_url, request_method, request_params, response_status, execution_time, create_time)
VALUES 
('LOG_202402_003', 2, 'UPDATE_PASSWORD', '修改密码', '192.168.1.103', 'Mozilla/5.0', '/api/users/password', 'PUT', '{}', 200, 180, '2024-02-08 14:25:00'),
('LOG_202402_004', 4, 'DELETE_ITEM', '删除购物车商品', '192.168.1.104', 'Mozilla/5.0', '/api/cart/remove', 'DELETE', '{"productId":2}', 200, 75, '2024-02-08 19:40:00');

INSERT INTO user_log_202403 (log_id, user_id, action_type, action_description, ip_address, user_agent, request_url, request_method, request_params, response_status, execution_time, create_time)
VALUES 
('LOG_202403_002', 6, 'REFUND_REQUEST', '申请退款', '192.168.1.105', 'Mozilla/5.0', '/api/orders/refund', 'POST', '{"orderId":1,"reason":"商品质量问题"}', 200, 320, '2024-03-03 16:10:00');