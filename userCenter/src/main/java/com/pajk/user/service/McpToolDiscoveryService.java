package com.pajk.user.service;

import com.mycompany.aigw.sdk.tool.annotation.Tool;
import com.mycompany.aigw.sdk.tool.annotation.ToolParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MCP工具发现服务
 * 用于发现应用中所有标记了@Tool注解的方法，并将其注册到Nacos MCP管理中
 */
@Service
@Slf4j
public class McpToolDiscoveryService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    // 使用setter注入，避免循环依赖
    private McpServiceRegistry mcpServiceRegistry;

    @Autowired
    public void setMcpServiceRegistry(McpServiceRegistry mcpServiceRegistry) {
        this.mcpServiceRegistry = mcpServiceRegistry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // 存储发现的所有MCP工具
    private final List<McpToolDefinition> discoveredTools = new ArrayList<>();

    // 是否已初始化完成
    private boolean initialized = false;

    /**
     * 在Spring上下文完全初始化后执行工具发现和注册
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initialized) {
            try {
                // 发现所有MCP工具
                discoverMcpTools();

                // 注册MCP服务到Nacos
                mcpServiceRegistry.registerService(discoveredTools);

                initialized = true;
                log.info("MCP tools discovery and registration completed");
            } catch (Exception e) {
                log.error("Failed to discover and register MCP tools", e);
            }
        }
    }

    /**
     * 发现应用中所有标记了@Tool注解的方法
     */
    private void discoverMcpTools() {
        log.info("Discovering MCP tools in application context");

        // 获取所有的Spring Bean
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            try {
                Object bean = applicationContext.getBean(beanName);
                Class<?> beanClass = bean.getClass();

                // 跳过控制器，避免循环依赖
                if (beanClass.isAnnotationPresent(RestController.class) ||
                    beanClass.isAnnotationPresent(Controller.class) ||
                    beanName.toLowerCase().contains("controller")) {
                    continue;
                }

                // 获取所有方法
                Method[] methods = beanClass.getDeclaredMethods();

                for (Method method : methods) {
                    // 检查方法是否有@Tool注解
                    Tool toolAnnotation = method.getAnnotation(Tool.class);
                    if (toolAnnotation != null) {
                        McpToolDefinition toolDefinition = createToolDefinition(bean, method, toolAnnotation);
                        discoveredTools.add(toolDefinition);
                        log.info("Discovered MCP tool: {}", toolDefinition.getName());
                    }
                }
            } catch (Exception e) {
                // 忽略获取Bean时的异常，可能是因为循环依赖等原因
                log.debug("Skipping bean '{}' during MCP tool discovery: {}", beanName, e.getMessage());
            }
        }

        log.info("Discovered {} MCP tools", discoveredTools.size());
    }

    /**
     * 创建工具定义
     */
    private McpToolDefinition createToolDefinition(Object bean, Method method, Tool toolAnnotation) {
        McpToolDefinition toolDef = new McpToolDefinition();

        // 设置工具名称和描述
        String toolName = toolAnnotation.name().isEmpty() ? method.getName() : toolAnnotation.name();
        toolDef.setName(toolName);
        toolDef.setDescription(toolAnnotation.description());

        // 设置Bean和方法信息，用于后续调用
        toolDef.setBeanClass(bean.getClass().getName());
        toolDef.setMethodName(method.getName());

        // 解析参数信息
        List<McpToolParamDefinition> params = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            ToolParam paramAnnotation = parameter.getAnnotation(ToolParam.class);
            if (paramAnnotation != null) {
                McpToolParamDefinition paramDef = new McpToolParamDefinition();
                paramDef.setName(parameter.getName());
                paramDef.setDescription(paramAnnotation.description());
                paramDef.setRequired(paramAnnotation.required());
                paramDef.setType(parameter.getType().getSimpleName());
                params.add(paramDef);
            }
        }

        toolDef.setParams(params);

        return toolDef;
    }

    /**
     * 获取所有已发现的MCP工具
     */
    public List<McpToolDefinition> getAllTools() {
        return Collections.unmodifiableList(discoveredTools);
    }

    /**
     * MCP工具定义
     */
    @Data
    public static class McpToolDefinition {
        private String name;
        private String description;
        private String beanClass;
        private String methodName;
        private List<McpToolParamDefinition> params;
    }

    /**
     * MCP工具参数定义
     */
    @Data
    public static class McpToolParamDefinition {
        private String name;
        private String description;
        private boolean required;
        private String type;
    }
}