package com.mcp.logistics.mapper;

import com.mcp.logistics.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流信息数据访问接口
 */
@Mapper
public interface LogisticsMapper {
    
    /**
     * 根据ID查询物流信息
     * 
     * @param id 物流ID
     * @return 物流信息
     */
    Logistics getLogisticsById(@Param("id") Long id);
    
    /**
     * 根据物流单号查询物流信息
     * 
     * @param logisticsNo 物流单号
     * @return 物流信息
     */
    Logistics getLogisticsByLogisticsNo(@Param("logisticsNo") String logisticsNo);
    
    /**
     * 根据订单ID查询物流信息
     * 
     * @param orderId 订单ID
     * @return 物流信息
     */
    Logistics getLogisticsByOrderId(@Param("orderId") Long orderId);
    
    /**
     * 根据订单编号查询物流信息
     * 
     * @param orderNo 订单编号
     * @return 物流信息
     */
    Logistics getLogisticsByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 插入物流信息
     * 
     * @param logistics 物流信息
     * @return 影响行数
     */
    int insertLogistics(Logistics logistics);
    
    /**
     * 更新物流信息
     * 
     * @param logistics 物流信息
     * @return 影响行数
     */
    int updateLogistics(Logistics logistics);
    
    /**
     * 删除物流信息
     * 
     * @param id 物流ID
     * @return 影响行数
     */
    int deleteLogistics(@Param("id") Long id);
    
    /**
     * 批量查询订单物流信息
     * 
     * @param orderIds 订单ID列表
     * @return 物流信息列表
     */
    List<Logistics> getLogisticsByOrderIds(@Param("orderIds") List<Long> orderIds);
} 