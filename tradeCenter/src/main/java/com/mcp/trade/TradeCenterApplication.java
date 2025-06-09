package com.mcp.trade;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mcp.trade.mapper")
@EnableDubbo
public class TradeCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeCenterApplication.class, args);
    }
} 