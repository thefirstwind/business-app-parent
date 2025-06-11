#!/bin/bash

# 启动MCP客户端应用程序

# 切换到项目目录
cd "$(dirname "$0")"

echo "=== 启动MCP客户端 ==="

# 创建日志目录
mkdir -p logs

# 启动Java应用程序
java -cp target/mcpClient-1.0.0-SNAPSHOT.jar:target/lib/* com.mcp.client.McpClientApplication

echo "=== 应用程序已退出 ===" 