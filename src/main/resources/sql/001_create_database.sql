-- =========================================
-- Sharding JDBC 数据库初始化脚本
-- 创建数据库
-- =========================================

-- 创建第一个分库
DROP DATABASE IF EXISTS sharding_db0;
CREATE DATABASE sharding_db0 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建第二个分库
DROP DATABASE IF EXISTS sharding_db1;
CREATE DATABASE sharding_db1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;