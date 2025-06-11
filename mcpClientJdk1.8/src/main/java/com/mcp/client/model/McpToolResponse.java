package com.mcp.client.model;

import lombok.Data;

/**
 * MCP工具响应
 */
@Data
public class McpToolResponse<T> {
    private boolean success;
    private String message;
    private T data;
    
    public static <T> McpToolResponse<T> success(T data) {
        McpToolResponse<T> response = new McpToolResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
    
    public static <T> McpToolResponse<T> error(String message) {
        McpToolResponse<T> response = new McpToolResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
} 