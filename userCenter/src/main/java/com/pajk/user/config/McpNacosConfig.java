package com.pajk.user.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class McpNacosConfig {
    @Value("${nacos.server-addr}")
    private String nacosAddress;

    @Value("${server.port}")
    private int serverPort;

    @Value("${nacos.mcp.group}")
    private String mcpGroup;


    @Bean
    public void registerMcpService() throws NacosException {
        // 1. 创建Nacos NamingService
        Properties properties = new Properties();
        properties.put("serverAddr", nacosAddress);
        NamingService namingService = NacosFactory.createNamingService(properties);

        // 2. 构建MCP服务的元数据
        Map<String, String> metadata = new HashMap<>();
        metadata.put("protocol", "SSE");  // 标明使用SSE协议
        metadata.put("mcp-version", "v1alpha1");
        metadata.put("context-path", "/mcp-events");

        // 3. 注册服务实例到Nacos（MCP专属分组）
        Instance instance = new Instance();
        instance.setIp("127.0.0.1");
        instance.setPort(serverPort);
        instance.setMetadata(metadata);
//        instance.setServiceName("com.pajk.user.service.UserService");

        String serviceName = "com.pajk.user.service.UserService" + ":" + "v1alpha1";
        instance.setServiceName(serviceName);
        instance.setHealthy(true);
        instance.setEphemeral(true);  // 临时实例

//        namingService.registerInstance("MCP_GROUP@@mcp-server", instance);
//        namingService.registerInstance(mcpGroup + "@@" + serviceName, instance);
        namingService.registerInstance(serviceName, mcpGroup, instance);
        System.out.println("MCP Server 注册成功");
    }

}
