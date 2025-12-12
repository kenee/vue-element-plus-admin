package com.example.admin.service.impl;

import com.example.admin.entity.SysRole;
import com.example.admin.repository.SysRoleRepository;
import com.example.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色服务实现类
 *
 * @author example
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public SysRole findById(String id) {
        return sysRoleRepository.selectById(id);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleRepository.selectList(null);
    }

    @Override
    public SysRole saveRole(SysRole role) {
        sysRoleRepository.insert(role);
        return role;
    }

    @Override
    public SysRole updateRole(SysRole role) {
        sysRoleRepository.updateById(role);
        return role;
    }

    @Override
    public void deleteRole(String id) {
        sysRoleRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysRoleRepository.deleteBatchIds(ids);
    }

}