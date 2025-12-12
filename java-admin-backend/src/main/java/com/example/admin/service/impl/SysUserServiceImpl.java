package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.entity.SysUser;
import com.example.admin.entity.SysRole;
import com.example.admin.entity.SysUserRole;
import com.example.admin.repository.SysUserRepository;
import com.example.admin.repository.SysUserRoleRepository;
import com.example.admin.repository.SysRoleRepository;
import com.example.admin.service.ISysUserService;
import com.example.admin.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author example
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public SysUser findById(String id) {
        return sysUserRepository.selectById(id);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.selectList(null);
    }

    @Override
    public Page<SysUser> findAll(com.baomidou.mybatisplus.extension.plugins.pagination.Page<SysUser> page) {
        return sysUserRepository.selectPage(page, null);
    }

    @Override
    public SysUser saveUser(SysUser user) {
        // 密码加密
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        sysUserRepository.insert(user);
        return user;
    }

    @Override
    public SysUser updateUser(SysUser user) {
        // 如果更新时提供了密码，则重新加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(PasswordUtil.encode(user.getPassword()));
        } else {
            // 否则保留原密码
            SysUser existingUser = findById(user.getId());
            if (existingUser != null) {
                user.setPassword(existingUser.getPassword());
            }
        }
        sysUserRepository.updateById(user);
        return user;
    }

    @Override
    public void deleteUser(String id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysUserRepository.deleteBatchIds(ids);
    }

    @Override
    public void assignRoles(String userId, List<String> roleIds) {
        // 暂时空实现，待角色关联关系建立后实现
        // 后续需要实现用户与角色的多对多关联
    }

    @Override
    public List<SysRole> findRolesByUserId(String userId) {
        // 根据用户ID查询所有关联的角色ID
        List<SysUserRole> userRoleList = sysUserRoleRepository.findByUserId(userId);
        
        // 从关联记录中提取角色ID列表
        List<String> roleIdList = userRoleList.stream()
                .map(role -> role.getRoleId())
                .collect(Collectors.toList());
        
        // 根据角色ID列表查询对应的角色对象
        return sysRoleRepository.selectBatchIds(roleIdList);
    }

}