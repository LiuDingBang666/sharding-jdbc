# 如果已经启动过
docker compose down
删除data目录


# 开始

启动服务 注意从库第一次先开启基本配置，不然root无法登录
docker compose up -d

主库冲库修正文件权限
# 假设你挂载的文件在 ./slave/conf/my.cnf
docker exec -it mysql-master /bin/bash

chmod 644 /etc/mysql/conf.d/my.cnf
# 确保拥有者是当前用户（或者 root）
chown $(whoami):$(whoami) /etc/mysql/conf.d/my.cnf

exit

docker exec -it mysql-slave /bin/bash

chmod 644 /etc/mysql/conf.d/my.cnf
# 确保拥有者是当前用户（或者 root）
chown $(whoami):$(whoami) /etc/mysql/conf.d/my.cnf

exit

重启
docker compose restart 

# 如果主库和从库到这里还都可以访问，则打开从库同步配置，重新再启动
docker restart mysql-slave



在主库创建复制用户
docker exec -it mysql-master mysql -uroot -proot

CREATE USER 'repl'@'%' IDENTIFIED WITH mysql_native_password BY 'replpwd';
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
FLUSH PRIVILEGES;

exit


在从库配置复制
docker exec -it mysql-slave mysql -uroot -proot

CHANGE REPLICATION SOURCE TO
SOURCE_HOST='mysql-master',
SOURCE_USER='repl',
SOURCE_PASSWORD='replpwd',
SOURCE_AUTO_POSITION=1;
START REPLICA;

# 因为两个容器默认在同一 docker 网络桥接上，可直接用 mysql-master 作为主机名。如果单机独立端口，改为宿主机 IP 和 3307 端口。
验证： SHOW REPLICA STATUS\G
Replica_IO_Running 与 Replica_SQL_Running 都为 Yes 即成功。


# 从库同步失败，重新同步
-- 1. 停止复制
STOP REPLICA;

-- 2. 彻底清理复制元数据
RESET REPLICA ALL;

-- 3. 重新开始复制
CHANGE REPLICATION SOURCE TO
SOURCE_HOST='mysql-master',
SOURCE_USER='repl',
SOURCE_PASSWORD='replpwd',
SOURCE_AUTO_POSITION=1;
START REPLICA;