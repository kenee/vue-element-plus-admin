package com.example.admin.service;

import com.example.admin.entity.SysTableExample;

import java.util.List;
import java.util.Map;

/**
 * 表格示例服务接口
 *
 * @author example
 */
public interface ISysTableExampleService {

    /**
     * 根据ID查询表格示例
     *
     * @param id 表格示例ID
     * @return SysTableExample
     */
    SysTableExample findById(String id);

    /**
     * 查询所有表格示例
     *
     * @return List<SysTableExample>
     */
    List<SysTableExample> findAll();

    /**
     * 查询所有根节点
     *
     * @return List<SysTableExample>
     */
    List<SysTableExample> findRootNodes();

    /**
     * 根据父节点ID查询子节点
     *
     * @param parentId 父节点ID
     * @return List<SysTableExample>
     */
    List<SysTableExample> findByParentId(String parentId);

    /**
     * 分页查询表格示例
     *
     * @param page 页码
     * @param pageSize 每页数量
     * @param title 搜索关键词
     * @return Map<String, Object> 包含list和total的分页数据
     */
    Map<String, Object> findByPage(int page, int pageSize, String title);

    /**
     * 保存表格示例
     *
     * @param tableExample 表格示例信息
     * @return SysTableExample
     */
    SysTableExample saveTableExample(SysTableExample tableExample);

    /**
     * 更新表格示例
     *
     * @param tableExample 表格示例信息
     * @return SysTableExample
     */
    SysTableExample updateTableExample(SysTableExample tableExample);

    /**
     * 删除表格示例
     *
     * @param id 表格示例ID
     */
    void deleteTableExample(String id);

    /**
     * 批量删除表格示例
     *
     * @param ids 表格示例ID列表
     */
    void deleteBatch(List<String> ids);

}