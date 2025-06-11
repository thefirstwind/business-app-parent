#!/bin/bash

# 运行MCP客户端测试程序

# 切换到项目目录
cd "$(dirname "$0")"

echo "=== 运行MCP客户端测试 ==="

# 创建日志目录
mkdir -p logs

# 启动Java测试应用程序
java -cp target/mcpClient-1.0.0-SNAPSHOT.jar:target/lib/* com.mcp.client.test.McpClientTest

echo "=== 测试完成 ===" 