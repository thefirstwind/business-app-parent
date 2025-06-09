package com.mcp.trade.service;

import com.mcp.trade.dto.OrderDTO;

import java.util.List;

/**
 * 订单查询服务接口，整合订单和物流信息
 */
public interface OrderQueryService {
    
    /**
     * 根据用户ID查询订单列表，包含物流信息
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderDTO> getOrdersWithLogisticsByUserId(Long userId);
    
    /**
     * 根据订单ID查询订单详情，包含物流信息
     * 
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderDTO getOrderWithLogisticsById(Long orderId);
    
    /**
     * 根据订单编号查询订单详情，包含物流信息
     * 
     * @param orderNo 订单编号
     * @return 订单详情
     */
    OrderDTO getOrderWithLogisticsByOrderNo(String orderNo);
} 