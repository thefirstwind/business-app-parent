package com.pajk.logistics.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * MCP服务注册中心
 * 负责将MCP服务注册到Nacos
 */
@Service
@Slf4j
public class McpServiceRegistry {

    @Value("${nacos.server-addr}")
    private String nacosAddress;

    @Value("${nacos.mcp.group}")
    private String mcpGroup;

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${nacos.mcp.version}")
    private String nacosMcpVersion;

    @Value("${nacos.mcp.protocal}")
    private String nacosMcpProtocal;

    @Value("${nacos.mcp.domain}")
    private String nacosMcpDomain;


    private NamingService namingService;

    @PostConstruct
    public void init() {
        try {
            // 初始化Nacos NamingService
            Properties properties = new Properties();
            properties.put("serverAddr", nacosAddress);
            namingService = NacosFactory.createNamingService(properties);

            log.info("MCP Service Registry initialized successfully");
        } catch (NacosException e) {
            log.error("Failed to initialize MCP Service Registry", e);
        }
    }

    /**
     * 注册MCP服务到Nacos
     */
    public void registerService(List<McpToolDiscoveryService.McpToolDefinition> tools) {
        try {
            log.info("Registering MCP service to Nacos with {} tools", tools.size());

            // 准备元数据，包含所有工具的信息
            Map<String, String> metadata = new HashMap<>();
            metadata.put("protocol", nacosMcpProtocal);
            metadata.put("mcp-version", nacosMcpVersion);
            metadata.put("mcp-tools-count", String.valueOf(tools.size()));
            metadata.put("domain", nacosMcpDomain);

            // 将工具列表转为JSON并添加到元数据
            for (int i = 0; i < tools.size(); i++) {
                McpToolDiscoveryService.McpToolDefinition tool = tools.get(i);
                metadata.put("tool-" + i + "-name", tool.getName());
                metadata.put("tool-" + i + "-description", tool.getDescription());
            }

            // 注册服务实例到Nacos
            Instance instance = new Instance();
            instance.setIp("127.0.0.1");
            instance.setPort(serverPort);
            instance.setMetadata(metadata);

            // 服务名使用应用名+MCP后缀
            String serviceName = applicationName + "-mcp";
            instance.setServiceName(serviceName);
            instance.setHealthy(true);
            instance.setEphemeral(true);  // 临时实例

            // 注册到MCP_GROUP组
            namingService.registerInstance(serviceName, mcpGroup, instance);
            log.info("MCP service registered to Nacos: {}@{}", serviceName, mcpGroup);
        } catch (NacosException e) {
            log.error("Failed to register MCP service to Nacos", e);
        }
    }
}