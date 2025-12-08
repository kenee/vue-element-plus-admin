package com.example.admin.repository;

import com.example.admin.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 角色Repository
 *
 * @author example
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {
}
