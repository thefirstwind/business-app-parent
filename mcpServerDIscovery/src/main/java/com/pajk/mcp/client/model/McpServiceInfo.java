package com.pajk.mcp.client.model;

import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Data;

import java.util.List;

/**
 * MCP服务信息
 */
@Data
public class McpServiceInfo {
    
    /**
     * 服务名称
     */
    private String serviceName;
    
    /**
     * 服务实例列表
     */
    private List<Instance> instances;
    
    /**
     * 服务提供的工具列表
     */
    private List<McpToolInfo> tools;
} 