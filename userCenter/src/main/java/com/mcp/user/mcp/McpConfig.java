package com.mcp.user.mcp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mcp")
public class McpConfig {
    private String appName;
    private String configGroup;
    private Long timeoutMs;
}

