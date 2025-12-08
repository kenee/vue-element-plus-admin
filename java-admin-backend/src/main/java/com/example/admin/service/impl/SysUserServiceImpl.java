package com.example.admin.service.impl;

import com.example.admin.entity.SysUser;
import com.example.admin.repository.SysUserRepository;
import com.example.admin.service.ISysUserService;
import com.example.admin.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 *
 * @author example
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public SysUser findById(String id) {
        Optional<SysUser> optionalUser = sysUserRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Override
    public Page<SysUser> findAll(Pageable pageable) {
        return sysUserRepository.findAll(pageable);
    }

    @Override
    public SysUser saveUser(SysUser user) {
        // 密码加密
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        return sysUserRepository.save(user);
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
        return sysUserRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysUserRepository.deleteAllById(ids);
    }

    @Override
    public void assignRoles(String userId, List<String> roleIds) {
        // 暂时空实现，待角色关联关系建立后实现
        // 后续需要实现用户与角色的多对多关联
    }

}