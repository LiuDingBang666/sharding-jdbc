-- 交易记录按月分表SQL脚本
-- 创建交易记录表模板，按月分表
-- 表名格式：transaction_YYYYMM (例如：transaction_202401, transaction_202402)

-- 数据库1 (sharding_db0) 交易记录表创建脚本
USE sharding_db0;

-- 创建当前月和未来几个月的交易记录表
-- 2024年01月
CREATE TABLE IF NOT EXISTS transaction_202401 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    transaction_no VARCHAR(64) NOT NULL COMMENT '交易流水号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    order_id BIGINT COMMENT '订单ID',
    transaction_type INTEGER NOT NULL COMMENT '交易类型：1-支付，2-退款，3-转账',
    amount DECIMAL(10,2) NOT NULL COMMENT '交易金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '货币类型',
    payment_method INTEGER COMMENT '支付方式：1-支付宝，2-微信，3-银行卡，4-余额',
    status INTEGER NOT NULL DEFAULT 0 COMMENT '交易状态：0-处理中，1-成功，2-失败，3-取消',
    description TEXT COMMENT '交易描述',
    transaction_time DATETIME NOT NULL COMMENT '交易时间',
    completion_time DATETIME COMMENT '完成时间',
    failure_reason VARCHAR(500) COMMENT '失败原因',
    merchant_id VARCHAR(64) COMMENT '商户ID',
    channel VARCHAR(50) COMMENT '交易渠道',
    fee DECIMAL(8,2) DEFAULT 0.00 COMMENT '手续费',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_transaction_no (transaction_no),
    INDEX idx_order_id (order_id),
    INDEX idx_transaction_type (transaction_type),
    INDEX idx_status (status),
    INDEX idx_payment_method (payment_method),
    INDEX idx_transaction_time (transaction_time),
    INDEX idx_create_time (create_time),
    UNIQUE KEY uk_transaction_no (transaction_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易记录表_2024年01月';

-- 2024年02月
CREATE TABLE IF NOT EXISTS transaction_202402 LIKE transaction_202401;
ALTER TABLE transaction_202402 COMMENT='交易记录表_2024年02月';

-- 2024年03月
CREATE TABLE IF NOT EXISTS transaction_202403 LIKE transaction_202401;
ALTER TABLE transaction_202403 COMMENT='交易记录表_2024年03月';

-- 2024年04月
CREATE TABLE IF NOT EXISTS transaction_202404 LIKE transaction_202401;
ALTER TABLE transaction_202404 COMMENT='交易记录表_2024年04月';

-- 2024年05月
CREATE TABLE IF NOT EXISTS transaction_202405 LIKE transaction_202401;
ALTER TABLE transaction_202405 COMMENT='交易记录表_2024年05月';

-- 2024年06月
CREATE TABLE IF NOT EXISTS transaction_202406 LIKE transaction_202401;
ALTER TABLE transaction_202406 COMMENT='交易记录表_2024年06月';

-- 2024年07月
CREATE TABLE IF NOT EXISTS transaction_202407 LIKE transaction_202401;
ALTER TABLE transaction_202407 COMMENT='交易记录表_2024年07月';

-- 2024年08月
CREATE TABLE IF NOT EXISTS transaction_202408 LIKE transaction_202401;
ALTER TABLE transaction_202408 COMMENT='交易记录表_2024年08月';

-- 2024年09月
CREATE TABLE IF NOT EXISTS transaction_202409 LIKE transaction_202401;
ALTER TABLE transaction_202409 COMMENT='交易记录表_2024年09月';

-- 2024年10月
CREATE TABLE IF NOT EXISTS transaction_202410 LIKE transaction_202401;
ALTER TABLE transaction_202410 COMMENT='交易记录表_2024年10月';

-- 2024年11月
CREATE TABLE IF NOT EXISTS transaction_202411 LIKE transaction_202401;
ALTER TABLE transaction_202411 COMMENT='交易记录表_2024年11月';

-- 2024年12月
CREATE TABLE IF NOT EXISTS transaction_202412 LIKE transaction_202401;
ALTER TABLE transaction_202412 COMMENT='交易记录表_2024年12月';

-- 2025年01月
CREATE TABLE IF NOT EXISTS transaction_202501 LIKE transaction_202401;
ALTER TABLE transaction_202501 COMMENT='交易记录表_2025年01月';

-- 2025年02月
CREATE TABLE IF NOT EXISTS transaction_202502 LIKE transaction_202401;
ALTER TABLE transaction_202502 COMMENT='交易记录表_2025年02月';

-- 2025年03月
CREATE TABLE IF NOT EXISTS transaction_202503 LIKE transaction_202401;
ALTER TABLE transaction_202503 COMMENT='交易记录表_2025年03月';

-- 插入测试数据
INSERT INTO transaction_202401 (transaction_no, user_id, order_id, transaction_type, amount, payment_method, status, description, transaction_time, completion_time, create_time)
VALUES 
('TXN_20240115_001', 1, 1, 1, 299.99, 1, 1, '购买手机支付', '2024-01-15 10:35:00', '2024-01-15 10:35:30', '2024-01-15 10:35:00'),
('TXN_20240115_002', 3, 2, 1, 159.80, 2, 1, '购买耳机支付', '2024-01-15 14:25:00', '2024-01-15 14:25:15', '2024-01-15 14:25:00'),
('TXN_20240115_003', 5, 3, 1, 89.90, 3, 1, '购买数据线支付', '2024-01-15 16:50:00', '2024-01-15 16:50:45', '2024-01-15 16:50:00');

INSERT INTO transaction_202402 (transaction_no, user_id, order_id, transaction_type, amount, payment_method, status, description, transaction_time, completion_time, create_time)
VALUES 
('TXN_20240210_001', 1, 4, 1, 199.99, 1, 1, '购买充电器支付', '2024-02-10 09:20:00', '2024-02-10 09:20:20', '2024-02-10 09:20:00'),
('TXN_20240210_002', 3, 2, 2, 159.80, 2, 1, '耳机退款', '2024-02-10 18:35:00', '2024-02-10 18:35:50', '2024-02-10 18:35:00');

INSERT INTO transaction_202403 (transaction_no, user_id, order_id, transaction_type, amount, payment_method, status, description, transaction_time, completion_time, create_time)
VALUES 
('TXN_20240305_001', 5, 5, 1, 399.99, 4, 1, '购买平板电脑支付', '2024-03-05 11:25:00', '2024-03-05 11:25:35', '2024-03-05 11:25:00');