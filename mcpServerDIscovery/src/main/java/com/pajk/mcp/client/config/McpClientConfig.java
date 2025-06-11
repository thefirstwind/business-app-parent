package com.pajk.mcp.client.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * MCP客户端配置
 */
@Configuration
@Slf4j
public class McpClientConfig {

    @Value("${nacos.server-addr}")
    private String nacosAddress;

    @Value("${nacos.mcp.group}")
    private String mcpGroup;

    /**
     * 创建Nacos NamingService实例
     */
    @Bean
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", nacosAddress);
        return NacosFactory.createNamingService(properties);
    }
} 