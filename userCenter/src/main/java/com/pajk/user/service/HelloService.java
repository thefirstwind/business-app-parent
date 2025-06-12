package com.pajk.user.service;

import com.mycompany.aigw.sdk.tool.annotation.Tool;
import com.mycompany.aigw.sdk.tool.annotation.ToolParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 示例服务，提供sayHello工具
 */
@Service
@Slf4j
public class HelloService {
    
    /**
     * 简单的打招呼工具
     * 
     * @param name 用户名
     * @return 问候消息
     */
    @Tool(name = "sayHello", description = "对指定的用户名打招呼")
    public Map<String, Object> sayHello(@ToolParam(description = "用户名称") String name) {
        log.info("sayHello工具被调用，参数name={}", name);
        
        Map<String, Object> result = new HashMap<>();
        result.put("message", "你好，" + name + "！欢迎使用MCP工具！");
        result.put("timestamp", System.currentTimeMillis());
        
        return result;
    }
} 