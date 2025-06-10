package com.pajk.mcp.client.model;

import lombok.Data;

import java.util.Map;

/**
 * MCP工具信息
 */
@Data
public class McpToolInfo {
    
    /**
     * 工具名称
     */
    private String name;
    
    /**
     * 工具描述
     */
    private String description;
    
    /**
     * 提供该工具的服务名称
     */
    private String serviceName;
    
    /**
     * 工具参数信息
     */
    private Map<String, String> parameters;
} 