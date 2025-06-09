package com.mcp.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 */
@Data
public class Order implements Serializable {
    
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
     * 商品ID
     */
    private Long itemId;
    
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
} 