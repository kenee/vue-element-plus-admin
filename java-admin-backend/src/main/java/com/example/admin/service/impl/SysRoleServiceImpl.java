package com.example.admin.service.impl;

import com.example.admin.entity.SysRole;
import com.example.admin.repository.SysRoleRepository;
import com.example.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<SysRole> optionalRole = sysRoleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleRepository.findAll();
    }

    @Override
    public SysRole saveRole(SysRole role) {
        return sysRoleRepository.save(role);
    }

    @Override
    public SysRole updateRole(SysRole role) {
        return sysRoleRepository.save(role);
    }

    @Override
    public void deleteRole(String id) {
        sysRoleRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysRoleRepository.deleteAllById(ids);
    }

}