//package com.mcp.user.config;
//
//import com.alibaba.nacos.api.config.ConfigService;
//import com.alibaba.nacos.api.config.listener.Listener;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executor;
//
//@Service
//@Slf4j
//public class NacosService {
//
//    @Autowired
//    private ConfigService configService;
//
//    @Autowired
//    private NamingService namingService;
//
//    @Value("${nacos.server-addr}")
//    private String serverAddr;
//
//    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";
//    private static final String APP_NAME = "nacos-auth-demo";
//    private static final String CONFIG_DATA_ID = "application-config";
//
//    private boolean nacosAvailable = false;
//
//    /**
//     * 初始化方法，注册服务并添加配置监听器
//     */
//    @PostConstruct
//    public void init() {
//        try {
//            // 首先检查Nacos服务器是否可用
//            if (!isNacosServerAvailable()) {
//                log.warn("Nacos服务器不可用 ({}), 应用将在本地模式下运行", serverAddr);
//                return;
//            }
//
//            nacosAvailable = true;
//
//            // 注册服务实例
//            registerService();
//
//            // 添加配置监听器
//            addConfigListener();
//
//            log.info("Nacos服务初始化完成");
//        } catch (Exception e) {
//            log.error("Nacos服务初始化失败，但应用程序将继续运行", e);
//            // 应用程序将继续运行，但Nacos相关功能将不可用
//        }
//    }
//
//    /**
//     * 检查Nacos服务器是否可用
//     */
//    private boolean isNacosServerAvailable() {
//        try {
//            // 尝试获取配置，设置较短的超时时间
//            configService.getConfig(CONFIG_DATA_ID, DEFAULT_GROUP, 1000);
//            return true;
//        } catch (NacosException e) {
//            log.warn("Nacos服务器连接检查失败: {}", e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 注册服务实例到Nacos
//     */
//    private void registerService() {
//        try {
//            Instance instance = new Instance();
//            instance.setIp("127.0.0.1");
//            instance.setPort(8081); // 更新端口为8081
//            instance.setWeight(1.0);
//            instance.setHealthy(true);
//
//            // 设置元数据
//            Map<String,String> metadata = new HashMap<>();
//            metadata.put("version", "1.0.0");
//            metadata.put("environment", "dev");
//            instance.setMetadata(metadata);
//
//            namingService.registerInstance(APP_NAME, DEFAULT_GROUP, instance);
//            log.info("服务实例注册成功: {}", APP_NAME);
//        } catch (NacosException e) {
//            log.error("服务实例注册失败", e);
//        }
//    }
//
//    /**
//     * 添加配置监听器
//     */
//    private void addConfigListener() {
//        try {
//            configService.addListener(CONFIG_DATA_ID, DEFAULT_GROUP, new Listener() {
//                @Override
//                public Executor getExecutor() {
//                    return null; // 使用默认的执行器
//                }
//
//                @Override
//                public void receiveConfigInfo(String configInfo) {
//                    log.info("接收到配置更新: {}", configInfo);
//                    // 处理配置更新
//                }
//            });
//
//            log.info("配置监听器添加成功: {}", CONFIG_DATA_ID);
//        } catch (NacosException e) {
//            log.error("配置监听器添加失败", e);
//        }
//    }
//
//    /**
//     * 获取配置信息
//     */
//    public String getConfig() {
//        if (!nacosAvailable) {
//            return "Nacos服务器不可用，无法获取配置";
//        }
//
//        try {
//            String config = configService.getConfig(CONFIG_DATA_ID, DEFAULT_GROUP, 5000);
//            return config != null ? config : "未找到配置";
//        } catch (NacosException e) {
//            log.error("获取配置失败", e);
//            return "获取配置失败: " + e.getMessage();
//        }
//    }
//
//    /**
//     * 发布配置
//     */
//    public boolean publishConfig(String content) {
//        if (!nacosAvailable) {
//            log.warn("Nacos服务器不可用，无法发布配置");
//            return false;
//        }
//
//        try {
//            return configService.publishConfig(CONFIG_DATA_ID, DEFAULT_GROUP, content);
//        } catch (NacosException e) {
//            log.error("发布配置失败", e);
//            return false;
//        }
//    }
//
//    /**
//     * 获取服务实例列表
//     */
//    public List<Instance> getInstances() {
//        if (!nacosAvailable) {
//            log.warn("Nacos服务器不可用，无法获取服务实例列表");
//            return Collections.emptyList();
//        }
//
//        try {
//            return namingService.getAllInstances(APP_NAME, DEFAULT_GROUP);
//        } catch (NacosException e) {
//            log.error("获取服务实例列表失败", e);
//            return Collections.emptyList();
//        }
//    }
//
//    /**
//     * 检查Nacos服务是否可用
//     */
//    public boolean isNacosAvailable() {
//        return nacosAvailable;
//    }
//}