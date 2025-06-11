package com.mcp.client.model;

import lombok.Data;

import java.util.List;

/**
 * MCP工具定义
 */
@Data
public class McpTool {
    private String name;
    private String description;
    private String beanClass;
    private String methodName;
    private List<McpToolParam> params;
} 