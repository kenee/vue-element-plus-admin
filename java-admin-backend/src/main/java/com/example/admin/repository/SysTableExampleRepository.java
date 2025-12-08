package com.example.admin.repository;

import com.example.admin.entity.SysTableExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 表格示例表 Repository 接口
 * </p>
 *
 * @author example
 */
@Repository
public interface SysTableExampleRepository extends JpaRepository<SysTableExample, String>, JpaSpecificationExecutor<SysTableExample> {

    /**
     * 查询所有根节点
     *
     * @return List<SysTableExample>
     */
    @Query("SELECT t FROM SysTableExample t WHERE t.parent IS NULL")
    List<SysTableExample> findRootNodes();

    /**
     * 根据父节点ID查询子节点
     *
     * @param parentId 父节点ID
     * @return List<SysTableExample>
     */
    List<SysTableExample> findByParentId(String parentId);

}