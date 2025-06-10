package com.mcp.trade.controller;

import com.mcp.trade.dto.OrderDTO;
import com.mcp.trade.service.OrderQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单查询控制器，提供REST API接口
 */
@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderQueryController {

    @Autowired
    private OrderQueryService orderQueryService;

    /**
     * 根据用户ID查询订单列表，包含物流信息
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        log.info("接收到查询用户订单的请求：{}", userId);

        try {
            List<OrderDTO> orders = orderQueryService.getOrdersWithLogisticsByUserId(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("orders", orders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("查询用户订单失败", e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "查询用户订单失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * 根据订单ID查询订单详情，包含物流信息
     *
     * @param id 订单ID
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        log.info("接收到查询订单详情的请求：{}", id);

        try {
            OrderDTO order = orderQueryService.getOrderWithLogisticsById(id);
            if (order == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "订单不存在：" + id);
                return ResponseEntity.badRequest().body(error);
            }

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("查询订单详情失败", e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "查询订单详情失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * 根据订单编号查询订单详情，包含物流信息
     *
     * @param orderNo 订单编号
     * @return 订单详情
     */
    @GetMapping("/no/{orderNo}")
    public ResponseEntity<?> getOrderByOrderNo(@PathVariable String orderNo) {
        log.info("接收到查询订单详情的请求：{}", orderNo);

        try {
            OrderDTO order = orderQueryService.getOrderWithLogisticsByOrderNo(orderNo);
            if (order == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "订单不存在：" + orderNo);
                return ResponseEntity.badRequest().body(error);
            }

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("查询订单详情失败", e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "查询订单详情失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}