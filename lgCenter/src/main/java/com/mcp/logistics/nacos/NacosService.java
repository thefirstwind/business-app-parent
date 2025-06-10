package com.mcp.logistics.nacos;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NacosService {

    private final ConfigService configService;
    private final NamingService namingService;
    private final NacosConfig.NacosProperties properties;

    @Value("${nacos.server-addr}")
    private String serverAddr;

    @Value("${server.port}")
    private int port;

    @Value("${spring.application.name}")
    private String serviceName;

    @PostConstruct
    public void registerService() throws NacosException {
        namingService.registerInstance(serviceName, serverAddr, port);
    }

    public String getConfig() throws NacosException {
        return configService.getConfig(
                properties.getConfig().getDataId(),
                properties.getConfig().getGroup(),
                properties.getConfig().getTimeout()
        );
    }

    public List<Instance> getServiceInstances() throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    public void publishConfig(String content) throws NacosException {
        configService.publishConfig(
                properties.getConfig().getDataId(),
                properties.getConfig().getGroup(),
                content
        );
    }

}
