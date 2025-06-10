#!/bin/bash

echo "Starting userCenter application..."

# 确保我们在正确的目录
cd "$(dirname "$0")"

# 使用指定的JVM选项运行应用
JAVA_OPTS="-Xmx512m -Xms256m -XX:+HeapDumpOnOutOfMemoryError -Dspring.profiles.active=local"

# 添加dubbo和spring的配置，解决循环依赖和服务检查问题
JAVA_OPTS="$JAVA_OPTS -Dspring.main.allow-circular-references=true -Dspring.main.allow-bean-definition-overriding=true"
JAVA_OPTS="$JAVA_OPTS -Ddubbo.consumer.check=false -Ddubbo.reference.check=false -Ddubbo.registry.check=false"

# 额外的Dubbo配置，禁用Qos服务（远程管理接口）
JAVA_OPTS="$JAVA_OPTS -Ddubbo.application.qos.enable=false"

# 使用Maven运行Spring Boot应用
echo "Using JAVA_OPTS: $JAVA_OPTS"
mvn clean spring-boot:run -Dspring-boot.run.jvmArguments="$JAVA_OPTS" 