package com.example.admin.service.impl;

import com.example.admin.entity.SysDictionary;
import com.example.admin.entity.SysDictionaryItem;
import com.example.admin.repository.SysDictionaryItemRepository;
import com.example.admin.repository.SysDictionaryRepository;
import com.example.admin.service.ISysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<SysDictionary> optionalDictionary = sysDictionaryRepository.findById(id);
        return optionalDictionary.orElse(null);
    }

    @Override
    public List<SysDictionary> findAll() {
        return sysDictionaryRepository.findAll();
    }

    @Override
    public SysDictionary saveDictionary(SysDictionary dictionary) {
        return sysDictionaryRepository.save(dictionary);
    }

    @Override
    public SysDictionary updateDictionary(SysDictionary dictionary) {
        return sysDictionaryRepository.save(dictionary);
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
        sysDictionaryRepository.deleteAllById(ids);
    }

    @Override
    public List<SysDictionaryItem> findItemsByDictId(String dictId) {
        return sysDictionaryItemRepository.findByDictId(dictId);
    }

    @Override
    public SysDictionaryItem saveItem(SysDictionaryItem item) {
        return sysDictionaryItemRepository.save(item);
    }

    @Override
    public SysDictionaryItem updateItem(SysDictionaryItem item) {
        return sysDictionaryItemRepository.save(item);
    }

    @Override
    public void deleteItem(String id) {
        sysDictionaryItemRepository.deleteById(id);
    }

    @Override
    public void deleteItemBatch(List<String> ids) {
        sysDictionaryItemRepository.deleteAllById(ids);
    }

}