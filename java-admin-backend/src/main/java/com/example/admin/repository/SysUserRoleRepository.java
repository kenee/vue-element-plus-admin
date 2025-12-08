package com.example.admin.repository;

import com.example.admin.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联Repository
 *
 * @author example
 */
@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String> {
}
