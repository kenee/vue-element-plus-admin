package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.entity.SysCardExample;
import com.example.admin.repository.SysCardExampleRepository;
import com.example.admin.service.ISysCardExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 卡片示例服务实现类
 *
 * @author example
 */
@Service
public class SysCardExampleServiceImpl implements ISysCardExampleService {

    @Autowired
    private SysCardExampleRepository sysCardExampleRepository;

    @Override
    public SysCardExample findById(String id) {
        return sysCardExampleRepository.selectById(id);
    }

    @Override
    public List<SysCardExample> findAll() {
        return sysCardExampleRepository.selectList(null);
    }

    @Override
    public List<SysCardExample> findByPage(int page, int pageSize, String name) {
        QueryWrapper<SysCardExample> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        int offset = (page - 1) * pageSize;
        return sysCardExampleRepository.selectList(queryWrapper.orderByDesc("created_at").last("LIMIT " + offset + "," + pageSize));
    }

    @Override
    public long getTotal(String name) {
        QueryWrapper<SysCardExample> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        return sysCardExampleRepository.selectCount(queryWrapper);
    }

    @Override
    public SysCardExample saveCardExample(SysCardExample cardExample) {
        sysCardExampleRepository.insert(cardExample);
        return cardExample;
    }

    @Override
    public SysCardExample updateCardExample(SysCardExample cardExample) {
        sysCardExampleRepository.updateById(cardExample);
        return cardExample;
    }

    @Override
    public void deleteCardExample(String id) {
        sysCardExampleRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysCardExampleRepository.deleteBatchIds(ids);
    }
}
