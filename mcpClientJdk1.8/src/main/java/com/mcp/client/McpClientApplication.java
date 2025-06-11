package com.mcp.client;

import com.mcp.client.model.McpTool;
import com.mcp.client.model.McpToolResponse;
import com.mcp.client.service.McpClientService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * MCP客户端应用程序
 */
@Slf4j
public class McpClientApplication {
    
    public static void main(String[] args) {
        try {
            log.info("Starting MCP client application...");
            
            // 获取MCP客户端服务实例
            McpClientService mcpClient = McpClientFactory.getInstance();
            
            // 获取所有可用的MCP工具
            List<McpTool> tools = mcpClient.getAllTools();
            
            if (tools.isEmpty()) {
                log.error("No MCP tools available");
                return;
            }
            
            log.info("Available MCP tools:");
            for (McpTool tool : tools) {
                log.info("- {} : {}", tool.getName(), tool.getDescription());
            }
            
            // 使用命令行交互
            interactWithUser(mcpClient, tools);
            
        } catch (Exception e) {
            log.error("Error in MCP client application", e);
        }
    }
    
    /**
     * 与用户交互，接收用户输入并调用MCP工具
     */
    private static void interactWithUser(McpClientService mcpClient, List<McpTool> tools) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n请选择要使用的MCP工具 (输入 'exit' 退出):");
            for (int i = 0; i < tools.size(); i++) {
                System.out.printf("%d. %s: %s%n", i + 1, tools.get(i).getName(), tools.get(i).getDescription());
            }
            
            System.out.print("\n请输入工具编号: ");
            String input = scanner.nextLine().trim();
            
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            
            try {
                int index = Integer.parseInt(input) - 1;
                if (index < 0 || index >= tools.size()) {
                    System.out.println("无效的工具编号，请重新输入");
                    continue;
                }
                
                McpTool selectedTool = tools.get(index);
                System.out.printf("\n已选择工具: %s%n", selectedTool.getName());
                
                // 收集工具参数
                Map<String, Object> params = collectToolParams(scanner, selectedTool);
                
                // 调用MCP工具
                McpToolResponse<Object> response = mcpClient.invokeTool(selectedTool.getName(), params, Object.class);
                
                // 显示结果
                if (response.isSuccess()) {
                    System.out.println("\n工具执行成功:");
                    System.out.println("结果: " + response.getData());
                } else {
                    System.out.println("\n工具执行失败: " + response.getMessage());
                }
                
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字");
            } catch (Exception e) {
                System.out.println("发生错误: " + e.getMessage());
                log.error("Error invoking MCP tool", e);
            }
        }
        
        scanner.close();
    }
    
    /**
     * 收集工具所需的参数
     */
    private static Map<String, Object> collectToolParams(Scanner scanner, McpTool tool) {
        Map<String, Object> params = new HashMap<>();
        
        if (tool.getParams() == null || tool.getParams().isEmpty()) {
            System.out.println("该工具没有参数");
            return params;
        }
        
        System.out.println("\n请输入以下参数:");
        
        tool.getParams().forEach(param -> {
            System.out.printf("%s (%s)%s: ", 
                    param.getName(), 
                    param.getDescription(), 
                    param.isRequired() ? " (必填)" : "");
            
            String input = scanner.nextLine().trim();
            
            if (!input.isEmpty()) {
                // 根据参数类型转换输入
                Object value = convertParamValue(input, param.getType());
                params.put(param.getName(), value);
            } else if (param.isRequired()) {
                System.out.println("参数 " + param.getName() + " 为必填项，请输入有效值");
                // 重新请求输入
                System.out.printf("%s (%s) (必填): ", param.getName(), param.getDescription());
                input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    Object value = convertParamValue(input, param.getType());
                    params.put(param.getName(), value);
                }
            }
        });
        
        return params;
    }
    
    /**
     * 根据参数类型转换参数值
     */
    private static Object convertParamValue(String input, String type) {
        switch (type.toLowerCase()) {
            case "integer":
            case "int":
                return Integer.parseInt(input);
            case "long":
                return Long.parseLong(input);
            case "double":
            case "float":
                return Double.parseDouble(input);
            case "boolean":
                return Boolean.parseBoolean(input);
            case "string":
            default:
                return input;
        }
    }
} 