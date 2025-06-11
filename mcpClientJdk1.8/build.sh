#!/bin/bash

# 构建MCP客户端项目

# 切换到项目目录
cd "$(dirname "$0")"
echo "Working directory: $(pwd)"

# 创建日志目录
mkdir -p logs

# 使用Maven构建项目
echo "=== 开始构建MCP客户端 ==="

# 检查父项目pom是否包含当前模块
cd ..
echo "Checking parent POM..."
if ! grep -q "<module>mcpClient</module>" pom.xml; then
    echo "Adding mcpClient module to parent POM..."
    sed -i '' 's/<module>tradeCenter<\/module>/<module>tradeCenter<\/module>\n        <module>mcpClient<\/module>/g' pom.xml
fi

# 编译父项目
echo "Building parent project..."
mvn clean install -N

# 编译当前项目
echo "Building mcpClient module..."
mvn clean package -pl mcpClient

echo "=== 构建完成 ===" 