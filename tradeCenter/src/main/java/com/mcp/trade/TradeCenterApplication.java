package com.mcp.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mcp.trade.mapper")
public class TradeCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeCenterApplication.class, args);
    }
} 