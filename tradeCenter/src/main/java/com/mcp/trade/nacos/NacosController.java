package com.mcp.trade.nacos;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NacosController {
    private final NacosService nacosService;

    @GetMapping("/config")
    public String getConfig() throws Exception {
        return nacosService.getConfig();
    }

    @PostMapping("/config")
    public Boolean publishConfig(@RequestBody String content) throws Exception {
        nacosService.publishConfig(content);
        return true;
    }

    @GetMapping("/instances")
    public Object getInstances() throws Exception {
        return nacosService.getServiceInstances();
    }

}
