package com.pajk.logistics;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pajk.logistics.mapper")
@EnableDubbo
public class LogisticsCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogisticsCenterApplication.class, args);
    }
} 