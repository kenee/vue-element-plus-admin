package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.entity.SysTableExample;
import com.example.admin.repository.SysTableExampleRepository;
import com.example.admin.service.ISysTableExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return sysTableExampleRepository.selectById(id);
    }

    @Override
    public List<SysTableExample> findAll() {
        return sysTableExampleRepository.selectList(null);
    }

    @Override
    public List<SysTableExample> findRootNodes() {
        QueryWrapper<SysTableExample> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("parent_id");
        return sysTableExampleRepository.selectList(queryWrapper);
    }

    @Override
    public List<SysTableExample> findByParentId(String parentId) {
        QueryWrapper<SysTableExample> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return sysTableExampleRepository.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> findByPage(int page, int pageSize, String title) {
        Page<SysTableExample> pageable = new Page<>(page, pageSize);
        QueryWrapper<SysTableExample> queryWrapper = new QueryWrapper<>();
        
        if (title != null && !title.isEmpty()) {
            queryWrapper.like("title", title);
        }
        
        Page<SysTableExample> result = sysTableExampleRepository.selectPage(pageable, queryWrapper);
        
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        
        return data;
    }

    @Override
    public SysTableExample saveTableExample(SysTableExample tableExample) {
        sysTableExampleRepository.insert(tableExample);
        return tableExample;
    }

    @Override
    public SysTableExample updateTableExample(SysTableExample tableExample) {
        sysTableExampleRepository.updateById(tableExample);
        return tableExample;
    }

    @Override
    public void deleteTableExample(String id) {
        sysTableExampleRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysTableExampleRepository.deleteBatchIds(ids);
    }

}