# MCP 工具管理实现

本项目实现了基于Nacos的MCP（服务/工具）管理功能，包括服务端和客户端组件。

## 系统架构

系统由以下几个主要组件组成：

1. **MCP服务端（userCenter模块）**：
   - 自动发现标记了`@Tool`注解的方法
   - 将这些工具注册到Nacos的MCP_GROUP组中
   - 提供API接口查询和管理MCP工具

2. **MCP客户端（mcpClient模块）**：
   - 从Nacos发现注册在MCP_GROUP中的服务
   - 解析服务元数据中的工具信息
   - 提供API接口查询可用的MCP服务和工具

3. **Nacos服务注册中心**：
   - 作为MCP服务和工具的注册中心
   - 提供服务发现功能

## 主要功能

### MCP服务端（userCenter）

1. **工具发现**：
   - `McpToolDiscoveryService`自动发现所有标记了`@Tool`注解的方法
   - 解析工具名称、描述和参数信息
   - 将工具信息注册到内存中的工具注册表

2. **服务注册**：
   - 将当前服务实例和工具信息注册到Nacos的MCP_GROUP中
   - 工具信息作为服务实例的元数据存储

3. **API接口**：
   - `/api/mcp/tools` - 获取所有已发现的MCP工具
   - `/api/mcp/status` - 获取MCP服务状态信息
   - `/api/mcp/refresh` - 刷新MCP服务列表

### MCP客户端（mcpClient）

1. **服务发现**：
   - 从Nacos发现注册在MCP_GROUP中的所有服务
   - 解析服务元数据中的工具信息
   - 定期刷新服务和工具列表

2. **API接口**：
   - `/api/mcp-client/services` - 获取所有MCP服务
   - `/api/mcp-client/tools` - 获取所有MCP工具
   - `/api/mcp-client/service/{serviceName}` - 按名称获取MCP服务
   - `/api/mcp-client/tool/{toolName}` - 按名称获取MCP工具
   - `/api/mcp-client/refresh` - 刷新MCP服务列表
   - `/api/mcp-client/status` - 获取MCP客户端状态

## 使用方法

### 创建MCP工具

在任何Spring Bean的方法上添加`@Tool`注解，即可将其声明为MCP工具：

```java
@Service
public class UserServiceImpl implements UserService {
    
    @Override
    @Tool(description = "根据ID查询用户")
    public User getUserById(@ToolParam(description = "用户ID") Long id) {
        // 实现逻辑
    }
}
```

### 启动服务

1. 启动Nacos服务（确保在127.0.0.1:8848可访问）
2. 启动userCenter模块（MCP服务端）
3. 启动mcpClient模块（MCP客户端）

### 验证功能

通过访问以下API接口验证功能：

- `http://localhost:8084/api/mcp/tools` - 查看服务端发现的工具
- `http://localhost:8089/api/mcp-client/tools` - 查看客户端发现的工具

## 配置参数

### MCP服务端配置

```properties
# Nacos配置
nacos.server-addr=127.0.0.1:8848
nacos.namespace=public
nacos.mcp.group=MCP_GROUP

# MCP配置
mcp.app-name=${spring.application.name}
mcp.config-group=MCP_GROUP
mcp.timeout-ms=3000
```

### MCP客户端配置

```properties
# Nacos配置
nacos.server-addr=127.0.0.1:8848
nacos.namespace=public
nacos.mcp.group=MCP_GROUP

# MCP客户端配置
mcp.client.connection-timeout=3000
mcp.client.read-timeout=5000
``` 