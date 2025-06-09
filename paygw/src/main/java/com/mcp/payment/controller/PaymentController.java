package com.mcp.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    /**
     * 获取支付信息
     * @param paymentId 支付ID
     * @return 支付信息
     */
    @GetMapping("/{paymentId}")
    public String getPaymentInfo(@PathVariable String paymentId) {
        return "Payment info for ID: " + paymentId;
    }

    /**
     * 创建支付
     * @return 支付结果
     */
    @PostMapping
    public String createPayment() {
        return "Payment created successfully";
    }
}