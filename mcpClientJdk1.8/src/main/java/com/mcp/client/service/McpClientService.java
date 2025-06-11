package com.mcp.client.service;

import com.mcp.client.model.McpTool;
import com.mcp.client.model.McpToolResponse;

import java.util.List;
import java.util.Map;

/**
 * MCP客户端服务接口
 */
public interface McpClientService {
    
    /**
     * 初始化MCP客户端
     */
    void init();
    
    /**
     * 获取所有可用的MCP工具
     */
    List<McpTool> getAllTools();
    
    /**
     * 根据名称获取MCP工具
     */
    McpTool getToolByName(String toolName);
    
    /**
     * 调用MCP工具
     * 
     * @param toolName 工具名称
     * @param params 工具参数
     * @return 工具执行结果
     */
    <T> McpToolResponse<T> invokeTool(String toolName, Map<String, Object> params, Class<T> returnType);
    
    /**
     * 关闭MCP客户端
     * 释放所有资源
     */
    void close();
} 