package com.pajk.user.config;

import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
//@EnableDubbo(scanBasePackages = "${dubbo.scan.base-packages}")
@EnableDubbo
public class DubboConfig {
    @Bean
    public RegistryConfig registryConfig(@Value("${nacos.server-addr}") String nacosAddr) {
        RegistryConfig config = new RegistryConfig();
        config.setAddress("nacos://" + nacosAddr);
        config.setParameters(Collections.singletonMap("mcp.enabled", "true"));
        return config;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName("dubbo");
        config.setPort(-1); // 自动分配端口
        return config;
    }

}
