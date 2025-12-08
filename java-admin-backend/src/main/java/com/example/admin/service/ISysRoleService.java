package com.example.admin.service;

import com.example.admin.entity.SysRole;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author example
 */
public interface ISysRoleService {

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return SysRole
     */
    SysRole findById(String id);

    /**
     * 查询所有角色
     *
     * @return List<SysRole>
     */
    List<SysRole> findAll();

    /**
     * 保存角色
     *
     * @param role 角色信息
     * @return SysRole
     */
    SysRole saveRole(SysRole role);

    /**
     * 更新角色
     *
     * @param role 角色信息
     * @return SysRole
     */
    SysRole updateRole(SysRole role);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteRole(String id);

    /**
     * 批量删除角色
     *
     * @param ids 角色ID列表
     */
    void deleteBatch(List<String> ids);

}