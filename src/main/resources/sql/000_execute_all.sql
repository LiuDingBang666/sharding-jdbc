-- =========================================
-- Sharding JDBC 一键执行所有SQL脚本
-- 按顺序执行所有初始化脚本
-- =========================================

-- 1. 创建数据库
source 001_create_database.sql;

-- 2. 创建数据库0的表结构
source 002_create_tables_db0.sql;

-- 3. 创建数据库1的表结构  
source 003_create_tables_db1.sql;

-- 4. 初始化数据
source 004_init_data.sql;

-- 执行完成提示
SELECT '=== 数据库初始化完成 ===' AS message;
SELECT '请检查以下数据库和表是否创建成功：' AS message;
SELECT '数据库: sharding_db0, sharding_db1' AS message;
SELECT '表: t_user_0, t_user_1, t_order_0, t_order_1, t_order_item_0, t_order_item_1, t_config' AS message;