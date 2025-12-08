package com.example.admin.service;

import java.util.List;
import java.util.Map;

/**
 * 工作台服务接口
 *
 * @author example
 */
public interface ISysWorkplaceService {

    /**
     * 获取总数据
     *
     * @return 总数据
     */
    Map<String, Object> getTotal();

    /**
     * 获取项目列表
     *
     * @return 项目列表
     */
    List<Map<String, Object>> getProject();

    /**
     * 获取动态列表
     *
     * @return 动态列表
     */
    List<Map<String, Object>> getDynamic();

    /**
     * 获取团队列表
     *
     * @return 团队列表
     */
    List<Map<String, Object>> getTeam();

    /**
     * 获取雷达图数据
     *
     * @return 雷达图数据
     */
    List<Map<String, Object>> getRadar();

}