//package com.pajk.user.controller;
//
//import com.alibaba.fastjson2.JSON;
//import com.pajk.user.service.UserService;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.util.concurrent.Executors;
//
//@RestController
//@RequestMapping("/mcp-events")
//public class McpSseController {
//
//    @DubboReference(version = "1.0.0", group="mcp")
//    private UserService userService;
//
//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter streamEvents() {
//        SseEmitter emitter = new SseEmitter(30_000L);
//
//        Executors.newSingleThreadExecutor().submit(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    String data = JSON.toJSONString(userService.getUserById(1L)); // 调用Dubbo服务
//                    emitter.send(SseEmitter.event()
//                            .id(String.valueOf(i))
//                            .data(data)
//                            .name("mcp-event"));
//                    Thread.sleep(1000);
//                }
//                emitter.complete();
//            } catch (Exception e) {
//                emitter.completeWithError(e);
//            }
//        });
//
//        return emitter;
//    }
//}
//
