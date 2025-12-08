package com.example.admin.service.impl;

import com.example.admin.service.ISysAnalysisService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析服务实现类
 *
 * @author example
 */
@Service
public class SysAnalysisServiceImpl implements ISysAnalysisService {

    @Override
    public Map<String, Object> getTotal() {
        Map<String, Object> result = new HashMap<>();
        result.put("users", 102400);
        result.put("messages", 81212);
        result.put("moneys", 9280);
        result.put("shoppings", 13600);
        return result;
    }

    @Override
    public List<Map<String, Object>> getUserAccessSource() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(createMap(1000, "analysis.directAccess"));
        result.add(createMap(310, "analysis.mailMarketing"));
        result.add(createMap(234, "analysis.allianceAdvertising"));
        result.add(createMap(135, "analysis.videoAdvertising"));
        result.add(createMap(1548, "analysis.searchEngines"));
        return result;
    }

    @Override
    public List<Map<String, Object>> getWeeklyUserActivity() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(createMap(13253, "analysis.monday"));
        result.add(createMap(34235, "analysis.tuesday"));
        result.add(createMap(26321, "analysis.wednesday"));
        result.add(createMap(12340, "analysis.thursday"));
        result.add(createMap(24643, "analysis.friday"));
        result.add(createMap(1322, "analysis.saturday"));
        result.add(createMap(1324, "analysis.sunday"));
        return result;
    }

    @Override
    public List<Map<String, Object>> getMonthlySales() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(createSalesMap(100, 120, "analysis.january"));
        result.add(createSalesMap(120, 82, "analysis.february"));
        result.add(createSalesMap(161, 91, "analysis.march"));
        result.add(createSalesMap(134, 154, "analysis.april"));
        result.add(createSalesMap(105, 162, "analysis.may"));
        result.add(createSalesMap(160, 140, "analysis.june"));
        result.add(createSalesMap(165, 145, "analysis.july"));
        result.add(createSalesMap(114, 250, "analysis.august"));
        result.add(createSalesMap(163, 134, "analysis.september"));
        result.add(createSalesMap(185, 56, "analysis.october"));
        result.add(createSalesMap(118, 99, "analysis.november"));
        result.add(createSalesMap(123, 123, "analysis.december"));
        return result;
    }

    /**
     * 创建包含value和name的Map
     */
    private Map<String, Object> createMap(int value, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("value", value);
        map.put("name", name);
        return map;
    }

    /**
     * 创建包含estimate、actual和name的销售数据Map
     */
    private Map<String, Object> createSalesMap(int estimate, int actual, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("estimate", estimate);
        map.put("actual", actual);
        map.put("name", name);
        return map;
    }

}