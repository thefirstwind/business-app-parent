package com.mcp.logistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mcp.logistics.mapper")
public class LogisticsCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogisticsCenterApplication.class, args);
    }
} 