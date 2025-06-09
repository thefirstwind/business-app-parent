package com.mcp.logistics.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 物流信息实体类
 */
@Data
public class Logistics implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 物流ID
     */
    private Long id;
    
    /**
     * 物流单号
     */
    private String logisticsNo;
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 订单编号
     */
    private String orderNo;
    
    /**
     * 物流状态：0-待发货，1-已发货，2-运输中，3-已签收，4-异常
     */
    private Integer status;
    
    /**
     * 快递公司
     */
    private String shippingCompany;
    
    /**
     * 收货地址
     */
    private String shippingAddress;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    /**
     * 更新时间
     */
    private Date updatedAt;
    
    /**
     * 物流轨迹信息
     */
    private List<LogisticsTrace> traces;
} 