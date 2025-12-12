package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysTableExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 表格示例表 Mapper 接口
 * </p>
 *
 * @author example
 */
@Mapper
public interface SysTableExampleRepository extends BaseMapper<SysTableExample> {

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

}