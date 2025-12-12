package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysDictionaryItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 字典项表 Mapper 接口
 * </p>
 *
 * @author example
 */
@Mapper
public interface SysDictionaryItemRepository extends BaseMapper<SysDictionaryItem> {

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