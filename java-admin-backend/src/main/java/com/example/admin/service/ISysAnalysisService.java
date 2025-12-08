package com.example.admin.service;

import java.util.List;
import java.util.Map;

/**
 * 统计分析服务接口
 *
 * @author example
 */
public interface ISysAnalysisService {

    /**
     * 获取总数据
     *
     * @return 总数据
     */
    Map<String, Object> getTotal();

    /**
     * 获取用户访问来源
     *
     * @return 用户访问来源数据
     */
    List<Map<String, Object>> getUserAccessSource();

    /**
     * 获取每周用户活动
     *
     * @return 每周用户活动数据
     */
    List<Map<String, Object>> getWeeklyUserActivity();

    /**
     * 获取月度销售数据
     *
     * @return 月度销售数据
     */
    List<Map<String, Object>> getMonthlySales();

}