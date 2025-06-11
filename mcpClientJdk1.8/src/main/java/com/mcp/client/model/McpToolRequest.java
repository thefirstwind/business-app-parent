package com.mcp.client.model;

import lombok.Data;

import java.util.Map;

/**
 * MCP工具请求
 */
@Data
public class McpToolRequest {
    private String toolName;
    private Map<String, Object> params;
} 