#!/bin/bash

# 重新构建并启动userCenter，带有TCP/SSE MCP支持

# 切换到项目目录
cd "$(dirname "$0")"

echo "=== 开始重新构建并启动userCenter MCP服务 ==="

# 停止现有服务
echo "停止现有userCenter服务..."
ps -ef | grep 'com.pajk.user.UserCenterApplication' | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null || true
sleep 2

# 创建日志目录
mkdir -p logs

# 重新编译userCenter
echo "编译userCenter模块..."
cd ..
mvn clean package -pl userCenter -DskipTests

# 启动userCenter服务
echo "启动userCenter服务..."
cd userCenter
nohup java -jar target/userCenter-1.0.1-SNAPSHOT.jar > logs/userCenter.log 2>&1 &

echo "等待服务启动..."
sleep 5

# 检查服务是否启动成功
if ps -ef | grep -q 'com.pajk.user.UserCenterApplication' | grep -v grep; then
    echo "userCenter服务已成功启动！"
    echo "服务日志: tail -f logs/userCenter.log"
else
    echo "userCenter服务启动失败！请检查日志: logs/userCenter.log"
fi

echo "=== 启动完成 ==="