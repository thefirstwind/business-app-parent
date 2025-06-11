package com.mcp.client;

import com.mcp.client.service.McpClientService;
import com.mcp.client.service.impl.McpClientServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * MCP客户端工厂类
 */
@Slf4j
public class McpClientFactory {
    
    private static volatile McpClientService INSTANCE;
    
    private McpClientFactory() {
        // 私有构造函数，防止实例化
    }
    
    /**
     * 获取MCP客户端服务实例（单例模式）
     */
    public static McpClientService getInstance() {
        if (INSTANCE == null) {
            synchronized (McpClientFactory.class) {
                if (INSTANCE == null) {
                    try {
                        // 从配置文件加载配置
                        Properties properties = loadProperties();
                        
                        // 创建服务实例
                        McpClientService service = createService(properties);
                        
                        // 初始化服务
                        service.init();
                        
                        INSTANCE = service;
                    } catch (Exception e) {
                        log.error("Failed to create MCP client", e);
                        throw new RuntimeException("Failed to create MCP client", e);
                    }
                }
            }
        }
        return INSTANCE;
    }
    
    /**
     * 关闭MCP客户端服务实例
     */
    public static void closeInstance() {
        if (INSTANCE != null) {
            synchronized (McpClientFactory.class) {
                if (INSTANCE != null) {
                    INSTANCE.close();
                    INSTANCE = null;
                    log.info("MCP client closed");
                }
            }
        }
    }
    
    /**
     * 从配置文件加载配置
     */
    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = McpClientFactory.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Cannot find application.properties");
            }
        }
        return properties;
    }
    
    /**
     * 创建MCP客户端服务实例
     */
    private static McpClientService createService(Properties properties) {
        String nacosAddress = properties.getProperty("nacos.server-addr", "127.0.0.1:8848");
        String nacosNamespace = properties.getProperty("nacos.namespace", "public");
        String mcpGroup = properties.getProperty("nacos.mcp.group", "MCP_GROUP");
        String mcpServiceName = properties.getProperty("mcp.server.service-name", "userCenter-mcp");
        int mcpTimeout = Integer.parseInt(properties.getProperty("mcp.timeout-ms", "3000"));
        String transportProtocol = properties.getProperty("mcp.transport.protocol", "tcp");
        
        return new McpClientServiceImpl(nacosAddress, nacosNamespace, mcpGroup, mcpServiceName, mcpTimeout, transportProtocol);
    }
} 