package com.mcp.logistics.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流轨迹实体类
 */
@Data
public class LogisticsTrace implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 轨迹ID
     */
    private Long id;
    
    /**
     * 物流ID
     */
    private Long logisticsId;
    
    /**
     * 物流单号
     */
    private String logisticsNo;
    
    /**
     * 物流动作
     */
    private String action;
    
    /**
     * 地点
     */
    private String location;
    
    /**
     * 操作员
     */
    private String operator;
    
    /**
     * 创建时间
     */
    private Date createdAt;
} 