//package com.pajk.user.controller;
//
//import com.pajk.user.service.McpServerService;
//import com.pajk.user.service.McpToolDiscoveryService;
//import com.pajk.user.service.McpToolDiscoveryService.McpToolDefinition;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * MCP工具管理控制器
// */
//@RestController
//@RequestMapping("/api/mcp")
//@RequiredArgsConstructor
//public class McpToolController {
//
//    private final McpToolDiscoveryService mcpToolDiscoveryService;
//    private final McpServerService mcpServerService;
//
//    /**
//     * 获取所有已发现的MCP工具
//     */
//    @GetMapping("/tools")
//    public List<McpToolDefinition> getAllTools() {
//        return mcpToolDiscoveryService.getAllTools();
//    }
//
//    /**
//     * 获取MCP服务状态信息
//     */
//    @GetMapping("/status")
//    public Map<String, Object> getStatus() {
//        Map<String, Object> status = new HashMap<>();
//
//        // 工具信息
//        status.put("toolsCount", mcpToolDiscoveryService.getAllTools().size());
//        status.put("tools", mcpToolDiscoveryService.getAllTools());
//
//        // 服务发现信息
//        status.put("discoveredServices", mcpServerService.getAllMcpServices());
//
//        return status;
//    }
//
//    /**
//     * 刷新MCP服务列表
//     */
//    @GetMapping("/refresh")
//    public Map<String, Object> refreshServices() {
//        Map<String, Object> result = new HashMap<>();
//
//        int servicesCount = mcpServerService.refreshMcpServices();
//        result.put("success", true);
//        result.put("servicesCount", servicesCount);
//        result.put("services", mcpServerService.getAllMcpServices());
//
//        return result;
//    }
//}