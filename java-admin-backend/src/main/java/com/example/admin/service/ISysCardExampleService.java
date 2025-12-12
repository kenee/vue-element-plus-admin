package com.example.admin.service;

import com.example.admin.entity.SysCardExample;

import java.util.List;

/**
 * 卡片示例服务接口
 *
 * @author example
 */
public interface ISysCardExampleService {

    /**
     * 根据ID查询卡片示例
     *
     * @param id 卡片示例ID
     * @return SysCardExample
     */
    SysCardExample findById(String id);

    /**
     * 查询所有卡片示例
     *
     * @return List<SysCardExample>
     */
    List<SysCardExample> findAll();

    /**
     * 查询卡片示例列表（带分页和搜索）
     *
     * @param page     页码
     * @param pageSize 每页大小
     * @param name     搜索关键词（名称）
     * @return List<SysCardExample>
     */
    List<SysCardExample> findByPage(int page, int pageSize, String name);

    /**
     * 获取卡片示例总数
     *
     * @param name 搜索关键词（名称）
     * @return 总数
     */
    long getTotal(String name);

    /**
     * 保存卡片示例
     *
     * @param cardExample 卡片示例信息
     * @return SysCardExample
     */
    SysCardExample saveCardExample(SysCardExample cardExample);

    /**
     * 更新卡片示例
     *
     * @param cardExample 卡片示例信息
     * @return SysCardExample
     */
    SysCardExample updateCardExample(SysCardExample cardExample);

    /**
     * 删除卡片示例
     *
     * @param id 卡片示例ID
     */
    void deleteCardExample(String id);

    /**
     * 批量删除卡片示例
     *
     * @param ids 卡片示例ID列表
     */
    void deleteBatch(List<String> ids);
}
