package com.mcp.trade.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class NacosConfig {

    @Value("${nacos.server-addr}")
    private String serverAddr;

    @Value("${nacos.namespace:public}")
    private String namespace;

    @Value("${nacos.auth.enable}")
    private boolean authEnable;

    @Value("${nacos.auth.key}")
    private String username;

    @Value("${nacos.auth.value}")
    private String password;

    /**
     * 创建Nacos配置服务实例
     */
    @Bean
    public ConfigService configService() throws NacosException {
        Properties properties = createNacosProperties();
        return NacosFactory.createConfigService(properties);
    }

    /**
     * 创建Nacos命名服务实例
     */
    @Bean
    public NamingService namingService() throws NacosException {
        Properties properties = createNacosProperties();
        return NacosFactory.createNamingService(properties);
    }

    /**
     * 创建Nacos配置属性，支持认证
     */
    private Properties createNacosProperties() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);

        // 配置认证信息
        if (authEnable) {
            if (username != null && !username.isEmpty() &&
                    password != null && !password.isEmpty()) {
                // 使用用户名/密码认证
                properties.put(PropertyKeyConst.USERNAME, username);
                properties.put(PropertyKeyConst.PASSWORD, password);
            }
        }

        return properties;
    }
} 