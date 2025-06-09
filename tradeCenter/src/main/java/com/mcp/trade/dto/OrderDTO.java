package com.mcp.trade.dto;

import com.mcp.trade.entity.Order;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单数据传输对象，包含物流信息
 */
@Data
public class OrderDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 订单ID
     */
    private Long id;
    
    /**
     * 订单编号
     */
    private String orderNo;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 商品ID
     */
    private Long itemId;
    
    /**
     * 商品名称
     */
    private String itemName;
    
    /**
     * 商品数量
     */
    private Integer quantity;
    
    /**
     * 订单金额
     */
    private BigDecimal amount;
    
    /**
     * 订单状态：0-待付款，1-已付款，2-已发货，3-已完成，4-已取消
     */
    private Integer status;
    
    /**
     * 收货地址
     */
    private String address;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    /**
     * 更新时间
     */
    private Date updatedAt;
    
    /**
     * 物流信息
     */
    private LogisticsDTO logistics;
    
    /**
     * 从订单实体转换为DTO
     */
    public static OrderDTO fromEntity(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNo(order.getOrderNo());
        dto.setUserId(order.getUserId());
        dto.setItemId(order.getItemId());
        dto.setQuantity(order.getQuantity());
        dto.setAmount(order.getAmount());
        dto.setStatus(order.getStatus());
        dto.setAddress(order.getAddress());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        
        return dto;
    }
} 