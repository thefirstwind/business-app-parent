package com.mcp.client.transport.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcp.client.transport.McpTransport;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * TCP/SSE传输实现
 * 使用TCP协议与MCP服务器通信，并支持SSE事件流
 */
@Slf4j
public class TcpSseTransport implements McpTransport {
    
    private final String host;
    private final int port;
    private final int timeout;
    
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private volatile boolean connected = false;
    
    private final Map<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    /**
     * 构造函数
     * 
     * @param host 主机地址
     * @param port 端口
     * @param timeout 超时时间（毫秒）
     */
    public TcpSseTransport(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }
    
    @Override
    public boolean connect() {
        try {
            log.info("Connecting to MCP server at {}:{}", host, port);
            
            // 创建Socket连接
            socket = new Socket(host, port);
            socket.setSoTimeout(timeout);
            
            // 创建读写器
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            
            // 启动读取线程
            executorService.submit(this::readResponseLoop);
            
            connected = true;
            log.info("Connected to MCP server successfully");
            
            return true;
        } catch (IOException e) {
            log.error("Failed to connect to MCP server", e);
            return false;
        }
    }
    
    @Override
    public void disconnect() {
        connected = false;
        
        // 关闭Socket
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            log.error("Error closing socket", e);
        }
        
        // 关闭读写器
        if (writer != null) {
            writer.close();
        }
        
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            log.error("Error closing reader", e);
        }
        
        // 关闭线程池
        executorService.shutdownNow();
        
        // 完成所有待处理的请求
        for (Map.Entry<String, CompletableFuture<String>> entry : pendingRequests.entrySet()) {
            entry.getValue().completeExceptionally(new IOException("Connection closed"));
        }
        pendingRequests.clear();
        
        log.info("Disconnected from MCP server");
    }
    
    @Override
    public CompletableFuture<String> sendRequest(String request) {
        if (!connected) {
            CompletableFuture<String> future = new CompletableFuture<>();
            future.completeExceptionally(new IOException("Not connected to MCP server"));
            return future;
        }
        
        try {
            // 解析请求中的ID
            Map<String, Object> requestMap = objectMapper.readValue(request, Map.class);
            String id = (String) requestMap.get("id");
            
            // 创建Future
            CompletableFuture<String> future = new CompletableFuture<>();
            pendingRequests.put(id, future);
            
            // 发送请求
            writer.println(request);
            
            return future;
        } catch (IOException e) {
            CompletableFuture<String> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected && socket != null && !socket.isClosed();
    }
    
    /**
     * 持续读取响应的循环
     */
    private void readResponseLoop() {
        try {
            String line;
            StringBuilder eventData = new StringBuilder();
            String eventName = null;
            
            while (connected && (line = reader.readLine()) != null) {
                // 处理SSE格式
                if (line.startsWith("event:")) {
                    // 事件名称
                    eventName = line.substring(6).trim();
                } else if (line.startsWith("data:")) {
                    // 事件数据
                    eventData.append(line.substring(5).trim());
                } else if (line.isEmpty() && eventData.length() > 0) {
                    // 空行表示事件结束
                    processEvent(eventName, eventData.toString());
                    eventData.setLength(0);
                    eventName = null;
                } else if (!line.isEmpty()) {
                    // 普通JSON-RPC响应
                    processJsonRpcResponse(line);
                }
            }
        } catch (IOException e) {
            if (connected) {
                log.error("Error reading from MCP server", e);
                disconnect();
            }
        }
    }
    
    /**
     * 处理JSON-RPC响应
     */
    private void processJsonRpcResponse(String response) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            String id = (String) responseMap.get("id");
            
            if (id != null) {
                // 完成对应的Future
                CompletableFuture<String> future = pendingRequests.remove(id);
                if (future != null) {
                    future.complete(response);
                }
            }
        } catch (IOException e) {
            log.error("Error processing JSON-RPC response", e);
        }
    }
    
    /**
     * 处理SSE事件
     */
    private void processEvent(String eventName, String eventData) {
        if ("message".equals(eventName)) {
            // 普通消息事件
            processJsonRpcResponse(eventData);
        }
    }
} 