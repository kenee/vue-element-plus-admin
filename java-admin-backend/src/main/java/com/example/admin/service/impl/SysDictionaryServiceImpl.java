package com.example.admin.service.impl;

import com.example.admin.entity.SysDictionary;
import com.example.admin.entity.SysDictionaryItem;
import com.example.admin.repository.SysDictionaryItemRepository;
import com.example.admin.repository.SysDictionaryRepository;
import com.example.admin.service.ISysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典服务实现类
 *
 * @author example
 */
@Service
public class SysDictionaryServiceImpl implements ISysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @Autowired
    private SysDictionaryItemRepository sysDictionaryItemRepository;

    @Override
    public SysDictionary findById(String id) {
        return sysDictionaryRepository.selectById(id);
    }

    @Override
    public List<SysDictionary> findAll() {
        return sysDictionaryRepository.selectList(null);
    }

    @Override
    public SysDictionary saveDictionary(SysDictionary dictionary) {
        sysDictionaryRepository.insert(dictionary);
        return dictionary;
    }

    @Override
    public SysDictionary updateDictionary(SysDictionary dictionary) {
        sysDictionaryRepository.updateById(dictionary);
        return dictionary;
    }

    @Override
    public void deleteDictionary(String id) {
        // 先删除关联的字典项
        sysDictionaryItemRepository.deleteByDictId(id);
        // 再删除字典
        sysDictionaryRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        // 批量删除字典项
        for (String id : ids) {
            sysDictionaryItemRepository.deleteByDictId(id);
        }
        // 批量删除字典
        sysDictionaryRepository.deleteBatchIds(ids);
    }

    @Override
    public List<SysDictionaryItem> findItemsByDictId(String dictId) {
        return sysDictionaryItemRepository.findByDictId(dictId);
    }

    @Override
    public SysDictionaryItem saveItem(SysDictionaryItem item) {
        sysDictionaryItemRepository.insert(item);
        return item;
    }

    @Override
    public SysDictionaryItem updateItem(SysDictionaryItem item) {
        sysDictionaryItemRepository.updateById(item);
        return item;
    }

    @Override
    public void deleteItem(String id) {
        sysDictionaryItemRepository.deleteById(id);
    }

    @Override
    public void deleteItemBatch(List<String> ids) {
        sysDictionaryItemRepository.deleteBatchIds(ids);
    }

}