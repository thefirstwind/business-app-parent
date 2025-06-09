package com.mcp.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mcp.item.mapper")
public class ItemCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemCenterApplication.class, args);
    }
} 