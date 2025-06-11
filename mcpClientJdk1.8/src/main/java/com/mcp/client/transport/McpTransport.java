package com.mcp.client.transport;

import java.util.concurrent.CompletableFuture;

/**
 * MCP传输接口
 * 定义与MCP服务器通信的基本方法
 */
public interface McpTransport {
    
    /**
     * 连接到MCP服务器
     * 
     * @return 连接是否成功
     */
    boolean connect();
    
    /**
     * 断开与MCP服务器的连接
     */
    void disconnect();
    
    /**
     * 发送请求并异步获取响应
     * 
     * @param request JSON-RPC请求
     * @return 异步的响应内容
     */
    CompletableFuture<String> sendRequest(String request);
    
    /**
     * 检查连接是否活跃
     * 
     * @return 连接是否活跃
     */
    boolean isConnected();
}