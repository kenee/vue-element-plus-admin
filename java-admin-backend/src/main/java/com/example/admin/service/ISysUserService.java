package com.example.admin.service;

import com.example.admin.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author example
 */
public interface ISysUserService {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return SysUser
     */
    SysUser findByUsername(String username);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return SysUser
     */
    SysUser findById(String id);

    /**
     * 查询所有用户
     *
     * @return List<SysUser>
     */
    List<SysUser> findAll();

    /**
     * 分页查询用户列表
     *
     * @param pageable 分页参数
     * @return Page<SysUser>
     */
    Page<SysUser> findAll(Pageable pageable);

    /**
     * 保存用户（包含密码加密）
     *
     * @param user 用户信息
     * @return SysUser
     */
    SysUser saveUser(SysUser user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return SysUser
     */
    SysUser updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(String id);

    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     */
    void deleteBatch(List<String> ids);

    /**
     * 为用户分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void assignRoles(String userId, List<String> roleIds);

}