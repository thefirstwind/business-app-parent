package com.mcp.client.test;

import com.mcp.client.McpClientFactory;
import com.mcp.client.model.McpTool;
import com.mcp.client.model.McpToolResponse;
import com.mcp.client.service.McpClientService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCP客户端测试类
 */
@Slf4j
public class McpClientTest {
    
    public static void main(String[] args) {
        try {
            log.info("Starting MCP client test...");
            
            // 获取MCP客户端服务实例
            McpClientService mcpClient = McpClientFactory.getInstance();
            
            // 获取所有可用的工具
            List<McpTool> tools = mcpClient.getAllTools();
            log.info("Found {} tools:", tools.size());
            for (McpTool tool : tools) {
                log.info("- {} : {}", tool.getName(), tool.getDescription());
            }
            
            // 测试sayHello工具
            testSayHello(mcpClient);
            
            // 关闭客户端
            McpClientFactory.closeInstance();
            
            log.info("MCP client test completed");
        } catch (Exception e) {
            log.error("Error in MCP client test", e);
        }
    }
    
    /**
     * 测试sayHello工具
     */
    private static void testSayHello(McpClientService mcpClient) {
        try {
            log.info("Testing sayHello tool...");
            
            // 获取工具信息
            McpTool tool = mcpClient.getToolByName("sayHello");
            if (tool == null) {
                log.error("sayHello tool not found");
                return;
            }
            
            // 准备参数
            Map<String, Object> params = new HashMap<>();
            params.put("name", "测试用户");
            
            // 调用工具
            McpToolResponse<String> response = mcpClient.invokeTool("sayHello", params, String.class);
            
            // 检查结果
            if (response.isSuccess()) {
                log.info("sayHello tool call succeeded: {}", response.getData());
            } else {
                log.error("sayHello tool call failed: {}", response.getMessage());
            }
        } catch (Exception e) {
            log.error("Error testing sayHello tool", e);
        }
    }
} 