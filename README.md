# 微服务业务应用

本项目是一个基于Spring Boot和Dubbo的微服务应用，实现了用户管理、订单管理、物流管理、商品管理和支付网关等功能。

## 项目架构

项目包含以下五个微服务：

1. **用户中心(userCenter)** - 提供用户管理功能
2. **交易中心(tradeCenter)** - 提供订单管理功能
3. **物流中心(lgCenter)** - 提供物流管理功能
4. **商品中心(itemCenter)** - 提供商品管理功能
5. **支付网关(paygw)** - 提供支付处理功能

## 技术栈

- Spring Boot 2.7.18
- MyBatis 1.3.5
- Apache Dubbo 2.7.15
- MySQL 5.7+
- Logback

## 功能说明

本项目实现了根据用户ID查询订单及物流信息的功能：

1. 通过用户ID查询用户的所有订单信息
2. 通过订单ID查询订单对应的物流信息
3. 通过整合用户、订单和物流信息，提供一站式的查询服务

## 数据库设计

项目使用了5个数据库，分别对应5个微服务：

1. **usercenter** - 用户数据库
2. **tradecenter** - 订单数据库
3. **lgcenter** - 物流数据库
4. **itemcenter** - 商品数据库
5. **paygw** - 支付数据库

每个数据库的表结构可以在对应微服务的`src/main/resources/sql/schema.sql`文件中查看。

## 接口说明

### 查询用户订单及物流信息

- **接口路径**: `/api/orders/user/{userId}`
- **方法**: GET
- **功能**: 查询指定用户的所有订单及物流信息
- **返回示例**:
```json
{
  "userId": 1,
  "orders": [
    {
      "id": 1,
      "orderNo": "ORD20230001",
      "userId": 1,
      "username": "user1",
      "itemId": 1,
      "itemName": "智能手机",
      "quantity": 2,
      "amount": 199.00,
      "status": 1,
      "address": "北京市朝阳区xxx小区1号楼1单元101",
      "createdAt": "2023-01-01T10:00:00",
      "updatedAt": "2023-01-01T10:30:00",
      "logistics": {
        "id": 1,
        "logisticsNo": "LG20230001",
        "orderId": 1,
        "orderNo": "ORD20230001",
        "status": 1,
        "shippingCompany": "顺丰速运",
        "shippingAddress": "北京市朝阳区xxx小区1号楼1单元101",
        "createdAt": "2023-01-01T11:00:00",
        "updatedAt": "2023-01-01T11:30:00",
        "traces": [
          {
            "id": 1,
            "logisticsId": 1,
            "logisticsNo": "LG20230001",
            "action": "快件已揽收",
            "location": "北京市海淀区中关村分拣中心",
            "operator": "张三",
            "createdAt": "2023-01-01T11:00:00"
          },
          {
            "id": 2,
            "logisticsId": 1,
            "logisticsNo": "LG20230001",
            "action": "快件已发出",
            "location": "北京市海淀区中关村分拣中心",
            "operator": "李四",
            "createdAt": "2023-01-01T11:10:00"
          }
        ]
      }
    }
  ]
}
```

### 查询单个订单及物流信息

- **接口路径**: `/api/orders/{id}`
- **方法**: GET
- **功能**: 查询指定ID的订单及物流信息

### 根据订单编号查询订单及物流信息

- **接口路径**: `/api/orders/no/{orderNo}`
- **方法**: GET
- **功能**: 查询指定编号的订单及物流信息

## 部署与运行

### 1. 初始化数据库

运行以下命令初始化所有数据库：

```bash
cd business-app-parent
chmod +x setup_databases.sh
./setup_databases.sh
```

### 2. 编译项目

```bash
cd business-app-parent
mvn clean package
```

### 3. 运行服务

启动所有服务：

```bash
# 启动用户中心
java -jar userCenter/target/userCenter-1.0.0-SNAPSHOT.jar

# 启动物流中心
java -jar lgCenter/target/lgCenter-1.0.0-SNAPSHOT.jar

# 启动交易中心
java -jar tradeCenter/target/tradeCenter-1.0.0-SNAPSHOT.jar

# 启动商品中心
java -jar itemCenter/target/itemCenter-1.0.0-SNAPSHOT.jar

# 启动支付网关
java -jar paygw/target/paygw-1.0.0-SNAPSHOT.jar
```

## 访问服务

- 用户中心: http://localhost:8080
- 交易中心: http://localhost:8081
- 物流中心: http://localhost:8082
- 商品中心: http://localhost:8083
- 支付网关: http://localhost:8084

## 查询示例

1. 查询用户ID为1的所有订单及物流信息：
   - http://localhost:8081/api/orders/user/1

2. 查询订单ID为1的订单及物流信息：
   - http://localhost:8081/api/orders/1

3. 查询订单编号为ORD20230001的订单及物流信息：
   - http://localhost:8081/api/orders/no/ORD20230001
