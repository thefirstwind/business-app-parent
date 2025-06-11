package com.mcp.client.transport.impl;

import com.mcp.client.transport.McpTransport;
import com.mcp.client.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * HTTP传输实现
 * 使用HTTP协议与MCP服务器通信
 */
@Slf4j
public class HttpTransport implements McpTransport {
    
    private final String host;
    private final int port;
    private final String path;
    private final int timeout;
    private boolean connected;
    
    /**
     * 构造函数
     * 
     * @param host 主机地址
     * @param port 端口
     * @param path 路径
     * @param timeout 超时时间（毫秒）
     */
    public HttpTransport(String host, int port, String path, int timeout) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.timeout = timeout;
        this.connected = false;
    }
    
    @Override
    public boolean connect() {
        // HTTP是无状态的，没有真正的连接过程
        this.connected = true;
        return true;
    }
    
    @Override
    public void disconnect() {
        // HTTP是无状态的，没有真正的断开连接过程
        this.connected = false;
    }
    
    @Override
    public CompletableFuture<String> sendRequest(String request) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        try {
            // 构建URL
            String url = String.format("http://%s:%d%s", host, port, path);
            
            // 发送HTTP请求
            String response = HttpClientUtil.postJson(url, request, timeout);
            
            // 完成Future
            future.complete(response);
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
}