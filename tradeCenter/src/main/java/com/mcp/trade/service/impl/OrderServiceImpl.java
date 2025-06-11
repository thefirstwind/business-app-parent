package com.mcp.trade.service.impl;

import com.mcp.trade.entity.Order;
import com.mcp.trade.mapper.OrderMapper;
import com.mcp.trade.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单服务实现类
 */
@Service
@DubboService(version = "1.0.0")
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Override
    public Order getOrderById(Long id) {
        log.info("根据ID查询订单：{}", id);
        return orderMapper.getOrderById(id);
    }
    
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        log.info("根据订单编号查询订单：{}", orderNo);
        return orderMapper.getOrderByOrderNo(orderNo);
    }
    
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        log.info("根据用户ID查询订单列表：{}", userId);
        return orderMapper.getOrdersByUserId(userId);
    }
    
    @Override
    public Long createOrder(Order order) {
        log.info("创建订单：{}", order.getOrderNo());
        orderMapper.insertOrder(order);
        return order.getId();
    }
    
    @Override
    public boolean updateOrder(Order order) {
        log.info("更新订单：{}", order.getId());
        return orderMapper.updateOrder(order) > 0;
    }
    
    @Override
    public boolean deleteOrder(Long id) {
        log.info("删除订单：{}", id);
        return orderMapper.deleteOrder(id) > 0;
    }
} 