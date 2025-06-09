package com.mcp.trade.service;

import com.mcp.trade.entity.Order;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService {
    
    /**
     * 根据ID查询订单
     * 
     * @param id 订单ID
     * @return 订单信息
     */
    Order getOrderById(Long id);
    
    /**
     * 根据订单编号查询订单
     * 
     * @param orderNo 订单编号
     * @return 订单信息
     */
    Order getOrderByOrderNo(String orderNo);
    
    /**
     * 根据用户ID查询订单列表
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getOrdersByUserId(Long userId);
    
    /**
     * 创建订单
     * 
     * @param order 订单信息
     * @return 订单ID
     */
    Long createOrder(Order order);
    
    /**
     * 更新订单
     * 
     * @param order 订单信息
     * @return 是否成功
     */
    boolean updateOrder(Order order);
    
    /**
     * 删除订单
     * 
     * @param id 订单ID
     * @return 是否成功
     */
    boolean deleteOrder(Long id);
} 