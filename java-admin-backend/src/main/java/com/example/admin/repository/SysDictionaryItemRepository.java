package com.example.admin.repository;

import com.example.admin.entity.SysDictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典项表 Repository 接口
 * </p>
 *
 * @author example
 */
@Repository
public interface SysDictionaryItemRepository extends JpaRepository<SysDictionaryItem, String>, JpaSpecificationExecutor<SysDictionaryItem> {

    /**
     * 根据字典ID查询字典项列表
     *
     * @param dictId 字典ID
     * @return List<SysDictionaryItem>
     */
    List<SysDictionaryItem> findByDictId(String dictId);

    /**
     * 根据字典ID删除字典项
     *
     * @param dictId 字典ID
     */
    void deleteByDictId(String dictId);

}