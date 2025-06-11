package com.mcp.trade.service.impl;

import com.mycompany.aigw.sdk.tool.annotation.Tool;
import com.pajk.logistics.entity.Logistics;
import com.pajk.logistics.entity.LogisticsTrace;
import com.pajk.logistics.service.LogisticsService;
import com.mcp.trade.dto.LogisticsDTO;
import com.mcp.trade.dto.LogisticsTraceDTO;
import com.mcp.trade.dto.OrderDTO;
import com.mcp.trade.entity.Order;
import com.mcp.trade.service.OrderQueryService;
import com.mcp.trade.service.OrderService;
import com.pajk.user.entity.User;
import com.pajk.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单查询服务实现类，整合订单和物流信息
 */
@Service
@DubboService(version = "1.0.0")
@Slf4j
public class OrderQueryServiceImpl implements OrderQueryService {
    
    @Autowired
    private OrderService orderService;
    
    @DubboReference(version = "1.0.0", check = false, timeout = 3000)
    private UserService userService;
    
    @DubboReference(version = "1.0.0", check = false, timeout = 3000)
    private LogisticsService logisticsService;


    @Tool(description = "根据用户ID查询代物流信息的订单")
    @Override
    public List<OrderDTO> getOrdersWithLogisticsByUserId(Long userId) {
        log.info("根据用户ID查询订单列表，包含物流信息：{}", userId);
        
        // 查询用户信息
        User user = userService.getUserById(userId);
        if (user == null) {
            log.warn("用户不存在：{}", userId);
            return new ArrayList<>();
        }
        
        // 查询订单列表
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            log.info("用户没有订单：{}", userId);
            return new ArrayList<>();
        }
        
        // 转换为DTO，并填充用户信息
        List<OrderDTO> orderDTOs = orders.stream()
                .map(order -> {
                    OrderDTO dto = OrderDTO.fromEntity(order);
                    dto.setUsername(user.getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
        
        // 批量查询物流信息
        List<Long> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());
        List<Logistics> logisticsList = logisticsService.getLogisticsByOrderIds(orderIds);
        
        // 将物流信息关联到订单
        for (OrderDTO orderDTO : orderDTOs) {
            for (Logistics logistics : logisticsList) {
                if (orderDTO.getId().equals(logistics.getOrderId())) {
                    orderDTO.setLogistics(convertToLogisticsDTO(logistics));
                    break;
                }
            }
        }
        
        return orderDTOs;
    }

    @Tool(description = "根据订单ID查询订单号")
    @Override
    public OrderDTO getOrderWithLogisticsById(Long orderId) {
        log.info("根据订单ID查询订单详情，包含物流信息：{}", orderId);
        
        // 查询订单信息
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            log.warn("订单不存在：{}", orderId);
            return null;
        }
        
        return enrichOrderWithUserAndLogistics(order);
    }

    @Tool(description = "根据订单No查询订单信息")
    @Override
    public OrderDTO getOrderWithLogisticsByOrderNo(String orderNo) {
        log.info("根据订单编号查询订单详情，包含物流信息：{}", orderNo);
        
        // 查询订单信息
        Order order = orderService.getOrderByOrderNo(orderNo);
        if (order == null) {
            log.warn("订单不存在：{}", orderNo);
            return null;
        }
        
        return enrichOrderWithUserAndLogistics(order);
    }
    
    /**
     * 订单信息关联用户和物流信息
     */
    private OrderDTO enrichOrderWithUserAndLogistics(Order order) {
        OrderDTO orderDTO = OrderDTO.fromEntity(order);
        
        // 查询用户信息
        User user = userService.getUserById(order.getUserId());
        if (user != null) {
            orderDTO.setUsername(user.getUsername());
        }
        
        // 查询物流信息
        Logistics logistics = logisticsService.getLogisticsByOrderId(order.getId());
        if (logistics != null) {
            orderDTO.setLogistics(convertToLogisticsDTO(logistics));
        }
        
        return orderDTO;
    }
    
    /**
     * 转换物流实体为DTO
     */
    private LogisticsDTO convertToLogisticsDTO(Logistics logistics) {
        if (logistics == null) {
            return null;
        }
        
        LogisticsDTO dto = new LogisticsDTO();
        dto.setId(logistics.getId());
        dto.setLogisticsNo(logistics.getLogisticsNo());
        dto.setOrderId(logistics.getOrderId());
        dto.setOrderNo(logistics.getOrderNo());
        dto.setStatus(logistics.getStatus());
        dto.setShippingCompany(logistics.getShippingCompany());
        dto.setShippingAddress(logistics.getShippingAddress());
        dto.setCreatedAt(logistics.getCreatedAt());
        dto.setUpdatedAt(logistics.getUpdatedAt());
        
        // 转换物流轨迹信息
        if (logistics.getTraces() != null && !logistics.getTraces().isEmpty()) {
            List<LogisticsTraceDTO> traceDTOs = logistics.getTraces().stream()
                    .map(this::convertToTraceDTO)
                    .collect(Collectors.toList());
            dto.setTraces(traceDTOs);
        }
        
        return dto;
    }
    
    /**
     * 转换物流轨迹实体为DTO
     */
    private LogisticsTraceDTO convertToTraceDTO(LogisticsTrace trace) {
        if (trace == null) {
            return null;
        }
        
        LogisticsTraceDTO dto = new LogisticsTraceDTO();
        dto.setId(trace.getId());
        dto.setLogisticsId(trace.getLogisticsId());
        dto.setLogisticsNo(trace.getLogisticsNo());
        dto.setAction(trace.getAction());
        dto.setLocation(trace.getLocation());
        dto.setOperator(trace.getOperator());
        dto.setCreatedAt(trace.getCreatedAt());
        
        return dto;
    }
} 