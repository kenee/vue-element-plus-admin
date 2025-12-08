package com.example.admin.repository;

import com.example.admin.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Repository 接口
 * </p>
 *
 * @author example
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return SysUser
     */
    SysUser findByUsername(String username);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return boolean
     */
    boolean existsByUsername(String username);

}
