-- =========================================
-- Sharding JDBC 初始化数据脚本
-- 插入测试数据
-- =========================================

-- 数据库0初始化数据
USE sharding_db0;

-- 插入配置数据 (广播表，所有库都会有相同数据)
INSERT INTO t_config (id, config_key, config_value, description, status) VALUES
(1, 'system.name', 'Sharding JDBC Demo', '系统名称', 1),
(2, 'system.version', '1.0.0', '系统版本', 1),
(3, 'max.order.amount', '10000.00', '订单最大金额限制', 1),
(4, 'enable.sms', 'true', '是否启用短信通知', 1),
(5, 'enable.email', 'true', '是否启用邮件通知', 1);

-- 数据库1初始化数据
USE sharding_db1;

-- 插入配置数据 (广播表，所有库都会有相同数据)
INSERT INTO t_config (id, config_key, config_value, description, status) VALUES
(1, 'system.name', 'Sharding JDBC Demo', '系统名称', 1),
(2, 'system.version', '1.0.0', '系统版本', 1),
(3, 'max.order.amount', '10000.00', '订单最大金额限制', 1),
(4, 'enable.sms', 'true', '是否启用短信通知', 1),
(5, 'enable.email', 'true', '是否启用邮件通知', 1);

-- 注意：由于分库分表的特性，用户和订单数据会根据分片规则自动路由到对应的数据库和表中
-- 这里不直接插入用户和订单数据，将通过API接口进行测试插入