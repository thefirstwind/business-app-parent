package com.pajk.logistics.service;

import com.pajk.logistics.entity.Logistics;
import com.pajk.logistics.entity.LogisticsTrace;

import java.util.List;

/**
 * 物流服务接口
 */
public interface LogisticsService {
    
    /**
     * 根据ID查询物流信息
     * 
     * @param id 物流ID
     * @return 物流信息
     */
    Logistics getLogisticsById(Long id);
    
    /**
     * 根据物流单号查询物流信息
     * 
     * @param logisticsNo 物流单号
     * @return 物流信息
     */
    Logistics getLogisticsByLogisticsNo(String logisticsNo);
    
    /**
     * 根据订单ID查询物流信息
     * 
     * @param orderId 订单ID
     * @return 物流信息
     */
    Logistics getLogisticsByOrderId(Long orderId);
    
    /**
     * 根据订单编号查询物流信息
     * 
     * @param orderNo 订单编号
     * @return 物流信息
     */
    Logistics getLogisticsByOrderNo(String orderNo);
    
    /**
     * 批量查询订单物流信息
     * 
     * @param orderIds 订单ID列表
     * @return 物流信息列表
     */
    List<Logistics> getLogisticsByOrderIds(List<Long> orderIds);
    
    /**
     * 创建物流信息
     * 
     * @param logistics 物流信息
     * @return 物流ID
     */
    Long createLogistics(Logistics logistics);
    
    /**
     * 更新物流信息
     * 
     * @param logistics 物流信息
     * @return 是否成功
     */
    boolean updateLogistics(Logistics logistics);
    
    /**
     * 添加物流轨迹
     * 
     * @param trace 物流轨迹
     * @return 轨迹ID
     */
    Long addLogisticsTrace(LogisticsTrace trace);
    
    /**
     * 批量添加物流轨迹
     * 
     * @param traces 物流轨迹列表
     * @return 是否成功
     */
    boolean batchAddLogisticsTraces(List<LogisticsTrace> traces);
    
    /**
     * 查询物流轨迹列表
     * 
     * @param logisticsId 物流ID
     * @return 物流轨迹列表
     */
    List<LogisticsTrace> getLogisticsTraces(Long logisticsId);
} 