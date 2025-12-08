package com.example.admin.service.impl;

import com.example.admin.entity.SysTableExample;
import com.example.admin.repository.SysTableExampleRepository;
import com.example.admin.service.ISysTableExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 表格示例服务实现类
 *
 * @author example
 */
@Service
public class SysTableExampleServiceImpl implements ISysTableExampleService {

    @Autowired
    private SysTableExampleRepository sysTableExampleRepository;

    @Override
    public SysTableExample findById(String id) {
        Optional<SysTableExample> optionalTableExample = sysTableExampleRepository.findById(id);
        return optionalTableExample.orElse(null);
    }

    @Override
    public List<SysTableExample> findAll() {
        return sysTableExampleRepository.findAll();
    }

    @Override
    public List<SysTableExample> findRootNodes() {
        return sysTableExampleRepository.findRootNodes();
    }

    @Override
    public List<SysTableExample> findByParentId(String parentId) {
        return sysTableExampleRepository.findByParentId(parentId);
    }

    @Override
    public SysTableExample saveTableExample(SysTableExample tableExample) {
        return sysTableExampleRepository.save(tableExample);
    }

    @Override
    public SysTableExample updateTableExample(SysTableExample tableExample) {
        return sysTableExampleRepository.save(tableExample);
    }

    @Override
    public void deleteTableExample(String id) {
        sysTableExampleRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysTableExampleRepository.deleteAllById(ids);
    }

}