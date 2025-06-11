package com.pajk.mcp.client.service;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.pajk.mcp.client.model.McpServiceInfo;
import com.pajk.mcp.client.model.McpToolInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MCP客户端服务
 * 用于发现和调用Nacos上注册的MCP服务和工具
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class McpClientService {

    private final NamingService namingService;

    @Value("${nacos.mcp.group}")
    private String mcpGroup;

    // 存储发现的MCP服务
    private final Map<String, McpServiceInfo> mcpServices = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        try {
            // 初始化时发现MCP服务
            discoverMcpServices();
            
            // 启动定时任务，每30秒更新一次服务列表
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        discoverMcpServices();
                    } catch (Exception e) {
                        log.error("Failed to update MCP services", e);
                    }
                }
            }, 30000, 30000);
            
            log.info("MCP Client Service initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize MCP Client Service", e);
        }
    }

    /**
     * 从Nacos发现MCP服务
     */
    public void discoverMcpServices() {
        try {
            // 获取MCP_GROUP下的所有服务
            List<String> serviceNames = namingService.getServicesOfServer(1, 1000, mcpGroup).getData();
            
            // 清除旧的服务列表
            mcpServices.clear();
            
            // 获取每个服务的实例信息
            for (String serviceName : serviceNames) {
                List<Instance> instances = namingService.getAllInstances(serviceName, mcpGroup);
                if (!instances.isEmpty()) {
                    McpServiceInfo serviceInfo = createServiceInfo(serviceName, instances);
                    mcpServices.put(serviceName, serviceInfo);
                    log.info("Discovered MCP service: {}, tools: {}", serviceName, serviceInfo.getTools().size());
                }
            }
            
        } catch (NacosException e) {
            log.error("Failed to discover MCP services", e);
        }
    }

    /**
     * 从实例元数据中创建服务信息
     */
    private McpServiceInfo createServiceInfo(String serviceName, List<Instance> instances) {
        McpServiceInfo serviceInfo = new McpServiceInfo();
        serviceInfo.setServiceName(serviceName);
        serviceInfo.setInstances(instances);
        
        // 解析工具信息
        List<McpToolInfo> tools = new ArrayList<>();
        Instance instance = instances.get(0); // 获取第一个实例
        Map<String, String> metadata = instance.getMetadata();
        
        // 获取工具数量
        String toolsCountStr = metadata.get("mcp-tools-count");
        if (toolsCountStr != null) {
            int toolsCount = Integer.parseInt(toolsCountStr);
            
            // 遍历工具元数据
            for (int i = 0; i < toolsCount; i++) {
                String toolName = metadata.get("tool-" + i + "-name");
                String toolDesc = metadata.get("tool-" + i + "-description");
                
                if (toolName != null) {
                    McpToolInfo toolInfo = new McpToolInfo();
                    toolInfo.setName(toolName);
                    toolInfo.setDescription(toolDesc);
                    toolInfo.setServiceName(serviceName);
                    tools.add(toolInfo);
                }
            }
        }
        
        serviceInfo.setTools(tools);
        return serviceInfo;
    }

    /**
     * 获取所有MCP服务
     */
    public List<McpServiceInfo> getAllServices() {
        return new ArrayList<>(mcpServices.values());
    }

    /**
     * 获取所有MCP工具
     */
    public List<McpToolInfo> getAllTools() {
        List<McpToolInfo> allTools = new ArrayList<>();
        mcpServices.values().forEach(service -> allTools.addAll(service.getTools()));
        return allTools;
    }

    /**
     * 按名称获取MCP服务
     */
    public McpServiceInfo getServiceByName(String serviceName) {
        return mcpServices.get(serviceName);
    }

    /**
     * 按名称获取MCP工具
     */
    public McpToolInfo getToolByName(String toolName) {
        return getAllTools().stream()
                .filter(tool -> tool.getName().equals(toolName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 强制刷新MCP服务列表
     */
    public int refreshServices() {
        discoverMcpServices();
        return mcpServices.size();
    }
} 