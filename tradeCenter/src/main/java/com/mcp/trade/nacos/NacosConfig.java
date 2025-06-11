//package com.mcp.trade.nacos;
//
//import com.alibaba.nacos.api.NacosFactory;
//import com.alibaba.nacos.api.config.ConfigService;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//@Slf4j
//
//@Configuration
//public class NacosConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "nacos")
//    public NacosProperties nacosProperties() {
//        return new NacosProperties();
//    }
//
//    @Bean
//    public ConfigService configService(NacosProperties properties) throws NacosException {
//        return NacosFactory.createConfigService(properties.getServerAddr());
//    }
//
//    @Bean
//    public NamingService namingService(NacosProperties properties) throws NacosException {
//        return NacosFactory.createNamingService(properties.getServerAddr());
//    }
//
//    @Data
//    public static class NacosProperties {
//        private String serverAddr;
//        private String namespace;
//        private Config config = new Config();
//
//        @Data
//        public static class Config {
//            private String dataId;
//            private String group;
//            private long timeout;
//        }
//    }
//
//}
