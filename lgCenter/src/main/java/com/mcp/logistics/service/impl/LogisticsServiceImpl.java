package com.mcp.logistics.service.impl;

import com.mcp.logistics.entity.Logistics;
import com.mcp.logistics.entity.LogisticsTrace;
import com.mcp.logistics.mapper.LogisticsMapper;
import com.mcp.logistics.mapper.LogisticsTraceMapper;
import com.mcp.logistics.service.LogisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物流服务实现类
 */
@Service
@DubboService(version = "1.0.0", group = "mcp")
@Slf4j
public class LogisticsServiceImpl implements LogisticsService {
    
    @Autowired
    private LogisticsMapper logisticsMapper;
    
    @Autowired
    private LogisticsTraceMapper logisticsTraceMapper;
    
    @Override
    public Logistics getLogisticsById(Long id) {
        log.info("根据ID查询物流信息：{}", id);
        Logistics logistics = logisticsMapper.getLogisticsById(id);
        if (logistics != null) {
            // 查询物流轨迹信息
            List<LogisticsTrace> traces = logisticsTraceMapper.getTracesByLogisticsId(id);
            logistics.setTraces(traces);
        }
        return logistics;
    }
    
    @Override
    public Logistics getLogisticsByLogisticsNo(String logisticsNo) {
        log.info("根据物流单号查询物流信息：{}", logisticsNo);
        Logistics logistics = logisticsMapper.getLogisticsByLogisticsNo(logisticsNo);
        if (logistics != null) {
            // 查询物流轨迹信息
            List<LogisticsTrace> traces = logisticsTraceMapper.getTracesByLogisticsNo(logisticsNo);
            logistics.setTraces(traces);
        }
        return logistics;
    }
    
    @Override
    public Logistics getLogisticsByOrderId(Long orderId) {
        log.info("根据订单ID查询物流信息：{}", orderId);
        Logistics logistics = logisticsMapper.getLogisticsByOrderId(orderId);
        if (logistics != null) {
            // 查询物流轨迹信息
            List<LogisticsTrace> traces = logisticsTraceMapper.getTracesByLogisticsId(logistics.getId());
            logistics.setTraces(traces);
        }
        return logistics;
    }
    
    @Override
    public Logistics getLogisticsByOrderNo(String orderNo) {
        log.info("根据订单编号查询物流信息：{}", orderNo);
        Logistics logistics = logisticsMapper.getLogisticsByOrderNo(orderNo);
        if (logistics != null) {
            // 查询物流轨迹信息
            List<LogisticsTrace> traces = logisticsTraceMapper.getTracesByLogisticsId(logistics.getId());
            logistics.setTraces(traces);
        }
        return logistics;
    }
    
    @Override
    public List<Logistics> getLogisticsByOrderIds(List<Long> orderIds) {
        log.info("批量查询订单物流信息：{}", orderIds);
        List<Logistics> logisticsList = logisticsMapper.getLogisticsByOrderIds(orderIds);
        // 查询每个物流的轨迹信息
        for (Logistics logistics : logisticsList) {
            List<LogisticsTrace> traces = logisticsTraceMapper.getTracesByLogisticsId(logistics.getId());
            logistics.setTraces(traces);
        }
        return logisticsList;
    }
    
    @Override
    @Transactional
    public Long createLogistics(Logistics logistics) {
        log.info("创建物流信息：{}", logistics.getLogisticsNo());
        logisticsMapper.insertLogistics(logistics);
        return logistics.getId();
    }
    
    @Override
    @Transactional
    public boolean updateLogistics(Logistics logistics) {
        log.info("更新物流信息：{}", logistics.getId());
        return logisticsMapper.updateLogistics(logistics) > 0;
    }
    
    @Override
    @Transactional
    public Long addLogisticsTrace(LogisticsTrace trace) {
        log.info("添加物流轨迹：{}", trace.getLogisticsNo());
        logisticsTraceMapper.insertTrace(trace);
        return trace.getId();
    }
    
    @Override
    @Transactional
    public boolean batchAddLogisticsTraces(List<LogisticsTrace> traces) {
        log.info("批量添加物流轨迹，数量：{}", traces.size());
        return logisticsTraceMapper.batchInsertTraces(traces) > 0;
    }
    
    @Override
    public List<LogisticsTrace> getLogisticsTraces(Long logisticsId) {
        log.info("查询物流轨迹列表：{}", logisticsId);
        return logisticsTraceMapper.getTracesByLogisticsId(logisticsId);
    }
} 