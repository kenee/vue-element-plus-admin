package com.example.admin.service;

import com.example.admin.entity.SysDictionary;
import com.example.admin.entity.SysDictionaryItem;

import java.util.List;

/**
 * 字典服务接口
 *
 * @author example
 */
public interface ISysDictionaryService {

    /**
     * 根据ID查询字典
     *
     * @param id 字典ID
     * @return SysDictionary
     */
    SysDictionary findById(String id);

    /**
     * 查询所有字典
     *
     * @return List<SysDictionary>
     */
    List<SysDictionary> findAll();

    /**
     * 保存字典
     *
     * @param dictionary 字典信息
     * @return SysDictionary
     */
    SysDictionary saveDictionary(SysDictionary dictionary);

    /**
     * 更新字典
     *
     * @param dictionary 字典信息
     * @return SysDictionary
     */
    SysDictionary updateDictionary(SysDictionary dictionary);

    /**
     * 删除字典
     *
     * @param id 字典ID
     */
    void deleteDictionary(String id);

    /**
     * 批量删除字典
     *
     * @param ids 字典ID列表
     */
    void deleteBatch(List<String> ids);

    /**
     * 根据字典ID查询字典项列表
     *
     * @param dictId 字典ID
     * @return List<SysDictionaryItem>
     */
    List<SysDictionaryItem> findItemsByDictId(String dictId);

    /**
     * 保存字典项
     *
     * @param item 字典项信息
     * @return SysDictionaryItem
     */
    SysDictionaryItem saveItem(SysDictionaryItem item);

    /**
     * 更新字典项
     *
     * @param item 字典项信息
     * @return SysDictionaryItem
     */
    SysDictionaryItem updateItem(SysDictionaryItem item);

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    void deleteItem(String id);

    /**
     * 批量删除字典项
     *
     * @param ids 字典项ID列表
     */
    void deleteItemBatch(List<String> ids);

}