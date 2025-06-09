# 业务应用模块

## 模块说明

项目包含以下业务模块：

- **tradeCenter**: 订单中心服务，处理订单相关业务
- **userCenter**: 用户中心服务，处理用户相关业务
- **lgCenter**: 物流中心服务，处理物流相关业务
- **itemCenter**: 商品中心服务，处理商品相关业务

## 系统架构

每个业务模块都是独立的Spring Boot应用，通过REST API进行通信。

## 数据库配置

项目使用MySQL数据库，需要创建以下数据库：

- tradecenter: 订单中心数据库
- usercenter: 用户中心数据库
- lgcenter: 物流中心数据库
- itemcenter: 商品中心数据库

### 数据库初始化

每个模块都包含SQL初始化脚本，位于`src/main/resources/sql/schema.sql`。

执行初始化脚本的方法：

```bash
# 进入MySQL控制台
mysql -u root -p

# 在MySQL控制台中执行以下命令
source /path/to/schema.sql
```

或者使用图形化工具（如Navicat、MySQL Workbench等）导入并执行SQL文件。

## 启动顺序

推荐按以下顺序启动服务：

1. 用户中心 (userCenter)
2. 商品中心 (itemCenter)
3. 物流中心 (lgCenter)
4. 订单中心 (tradeCenter)

## 端口配置

- 用户中心: 8082
- 商品中心: 8084
- 物流中心: 8083
- 订单中心: 8081

## 业务功能

### 用户订单查询功能

可以根据用户ID查询该用户购买的订单，并同时查询订单对应的物流信息。

接口：
- GET `/api/user/orders/{userId}` - 查询用户的所有订单及物流信息
- GET `/api/user/orders/{userId}/{orderId}` - 查询用户特定订单详情及物流信息 # mcp-tool-manager
# business-app-parent
