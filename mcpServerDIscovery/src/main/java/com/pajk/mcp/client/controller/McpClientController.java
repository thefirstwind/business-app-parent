package com.pajk.mcp.client.controller;

import com.pajk.mcp.client.model.McpServiceInfo;
import com.pajk.mcp.client.model.McpToolInfo;
import com.pajk.mcp.client.service.McpClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCP客户端控制器
 */
@RestController
@RequestMapping("/api/mcp-client")
@RequiredArgsConstructor
public class McpClientController {

    private final McpClientService mcpClientService;

    /**
     * 获取所有MCP服务
     */
    @GetMapping("/services")
    public List<McpServiceInfo> getAllServices() {
        return mcpClientService.getAllServices();
    }

    /**
     * 获取所有MCP工具
     */
    @GetMapping("/tools")
    public List<McpToolInfo> getAllTools() {
        return mcpClientService.getAllTools();
    }

    /**
     * 按名称获取MCP服务
     */
    @GetMapping("/service/{serviceName}")
    public McpServiceInfo getServiceByName(@PathVariable String serviceName) {
        return mcpClientService.getServiceByName(serviceName);
    }

    /**
     * 按名称获取MCP工具
     */
    @GetMapping("/tool/{toolName}")
    public McpToolInfo getToolByName(@PathVariable String toolName) {
        return mcpClientService.getToolByName(toolName);
    }

    /**
     * 刷新MCP服务列表
     */
    @PostMapping("/refresh")
    public Map<String, Object> refreshServices() {
        Map<String, Object> result = new HashMap<>();
        int count = mcpClientService.refreshServices();
        result.put("success", true);
        result.put("servicesCount", count);
        return result;
    }

    /**
     * 获取MCP客户端状态
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("servicesCount", mcpClientService.getAllServices().size());
        status.put("toolsCount", mcpClientService.getAllTools().size());
        status.put("services", mcpClientService.getAllServices());
        return status;
    }
} 