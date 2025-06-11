package com.mcp.client.model;

import lombok.Data;

/**
 * MCP工具参数定义
 */
@Data
public class McpToolParam {
    private String name;
    private String description;
    private boolean required;
    private String type;
} 