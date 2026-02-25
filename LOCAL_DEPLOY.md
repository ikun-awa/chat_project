# 本地部署说明（可作为局域网服务端）

## 1. 环境准备
- JDK 17
- Maven 3.9+
- MySQL 8+

## 2. 创建数据库和账号
```sql
CREATE DATABASE IF NOT EXISTS user_info DEFAULT CHARACTER SET utf8mb4;
CREATE DATABASE IF NOT EXISTS chat DEFAULT CHARACTER SET utf8mb4;

CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'app_pwd_123';
CREATE USER IF NOT EXISTS 'chat_user'@'%' IDENTIFIED BY 'chat_pwd_456';

GRANT ALL PRIVILEGES ON user_info.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON chat.* TO 'chat_user'@'%';
FLUSH PRIVILEGES;
```

> 如果你本机 MySQL 账号不同，请改 `src/main/resources/application.properties` 中的用户名密码。

## 3. 启动项目
```bash
mvn clean package -DskipTests
java -jar target/chat_project-11.45.14.jar
```

默认端口：`8080`。

## 4. 局域网访问
1. 在本机执行：`ipconfig`（Windows）或 `ifconfig/ip a`（macOS/Linux）查看局域网 IP，例如 `192.168.1.23`。
2. 其他设备访问：`http://192.168.1.23:8080/`
3. 开启系统防火墙放行 `8080` 入站。

## 5. 已实现的聊天路径
- 登录页：`/login`
- 大厅页：`/lobby`（可创建聊天室）
- 聊天页：`/chat?groupId=<房间ID>`
- 历史消息接口：`GET /api/chat/history?roomId=<房间ID>`
- WebSocket：`/ws-chat`，主题 `/topic/group.<roomId>`，发送地址 `/app/chat.sendMessage/<roomId>`
