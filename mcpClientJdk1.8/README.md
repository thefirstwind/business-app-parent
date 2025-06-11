# MCP 客户端

MCP（MicroService Client Protocol）客户端是一个简单的工具，用于调用注册在Nacos上的MCP服务。此客户端基于JDK 1.8开发，不依赖Spring框架。

## 项目结构

```
mcpClient/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── mcp/
│   │   │           └── client/
│   │   │               ├── model/        # 数据模型
│   │   │               ├── service/      # 服务接口和实现
│   │   │               ├── util/         # 工具类
│   │   │               ├── McpClientApplication.java  # 主应用类
│   │   │               └── McpClientFactory.java      # 客户端工厂类
│   │   └── resources/
│   │       ├── application.properties    # 配置文件
│   │       └── logback.xml               # 日志配置
├── build.sh        # 构建脚本
├── start.sh        # 启动脚本
└── pom.xml         # Maven配置
```

## 功能特性

- 通过Nacos自动发现MCP服务
- 获取MCP服务提供的所有工具列表
- 调用MCP工具，传递参数并获取结果
- 交互式命令行界面，方便测试和使用

## 配置文件说明

配置文件位于 `src/main/resources/application.properties`，主要配置项：

```properties
# Nacos配置
nacos.server-addr=127.0.0.1:8848   # Nacos服务地址
nacos.namespace=public             # Nacos命名空间
nacos.mcp.group=MCP_GROUP          # MCP服务组

# MCP服务配置
mcp.server.service-name=userCenter-mcp  # MCP服务名称
mcp.server.port=8084                    # MCP服务端口
mcp.timeout-ms=3000                     # 超时时间(毫秒)
```

## 使用方法

### 构建项目

```bash
./build.sh
```

### 运行客户端

```bash
./start.sh
```

### 调用示例

```java
// 获取MCP客户端服务实例
McpClientService mcpClient = McpClientFactory.getInstance();

// 获取所有可用的MCP工具
List<McpTool> tools = mcpClient.getAllTools();

// 调用MCP工具
Map<String, Object> params = new HashMap<>();
params.put("paramName", "paramValue");
McpToolResponse<String> response = mcpClient.invokeTool("toolName", params, String.class);

if (response.isSuccess()) {
    System.out.println("工具执行成功: " + response.getData());
} else {
    System.out.println("工具执行失败: " + response.getMessage());
}
```

## 注意事项

1. 使用前请确保Nacos服务已启动
2. 确保userCenter服务已启动并已注册到Nacos
3. 如需修改配置，编辑 `application.properties` 文件

## 依赖说明

- Nacos Client - 用于服务发现
- HttpClient - 用于HTTP通信
- Jackson - 用于JSON处理
- Lombok - 简化代码
- SLF4J/Logback - 日志框架 