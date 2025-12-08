package com.example.admin.repository;

import com.example.admin.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色菜单关联Repository
 *
 * @author example
 */
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, String> {
}
