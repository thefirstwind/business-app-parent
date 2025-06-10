package com.pajk.userqk.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * Dubbo配置类
 */
@Configuration
public class DubboConfig {

    @Value("${nacos.server-addr}")
    private String nacosAddress;

    @Value("${nacos.namespace}")
    private String nacosNamespace;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${nacos.mcp.enabled}")
    private Boolean nacosMcpEnabled;

    @Value("${nacos.mcp.version}")
    private String nacosMcpVersion;

    @Value("${dubbo.application.qos-enable}")
    private Boolean dubboQosEnable;



    /**
     * 消费者配置
     */
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig config = new ConsumerConfig();
        config.setCheck(false); // 关闭启动时的检查，解决服务依赖问题
        config.setTimeout(5000);
        config.setRetries(0);
        return config;
    }

    /**
     * 应用配置
     */
    @Bean
    @Primary
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setName(applicationName);
        config.setQosEnable(dubboQosEnable);
        return config;
    }

    /**
     * 注册中心配置
     */
    @Bean
    @Primary
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setAddress("nacos://" + nacosAddress);
        config.setRegister(true);
        
        // 使用参数方式设置命名空间
        Map<String, String> parameters = new HashMap<>();
        parameters.put("namespace", nacosNamespace);
        parameters.put("check", "false");
        parameters.put("mcp.enabled", nacosMcpEnabled + "");
        parameters.put("mcp.version", nacosMcpVersion);
        config.setParameters(parameters);
        config.setCheck(false); // 关闭注册中心启动时检查
        return config;
    }
}
