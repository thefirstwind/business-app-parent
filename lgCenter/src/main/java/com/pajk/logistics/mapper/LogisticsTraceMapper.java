package com.pajk.logistics.mapper;

import com.pajk.logistics.entity.LogisticsTrace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流轨迹数据访问接口
 */
@Mapper
public interface LogisticsTraceMapper {
    
    /**
     * 根据ID查询物流轨迹
     * 
     * @param id 轨迹ID
     * @return 物流轨迹
     */
    LogisticsTrace getTraceById(@Param("id") Long id);
    
    /**
     * 根据物流ID查询物流轨迹列表
     * 
     * @param logisticsId 物流ID
     * @return 物流轨迹列表
     */
    List<LogisticsTrace> getTracesByLogisticsId(@Param("logisticsId") Long logisticsId);
    
    /**
     * 根据物流单号查询物流轨迹列表
     * 
     * @param logisticsNo 物流单号
     * @return 物流轨迹列表
     */
    List<LogisticsTrace> getTracesByLogisticsNo(@Param("logisticsNo") String logisticsNo);
    
    /**
     * 插入物流轨迹
     * 
     * @param trace 物流轨迹
     * @return 影响行数
     */
    int insertTrace(LogisticsTrace trace);
    
    /**
     * 批量插入物流轨迹
     * 
     * @param traces 物流轨迹列表
     * @return 影响行数
     */
    int batchInsertTraces(@Param("traces") List<LogisticsTrace> traces);
} 