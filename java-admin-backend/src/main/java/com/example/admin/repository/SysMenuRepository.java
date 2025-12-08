package com.example.admin.repository;

import com.example.admin.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 菜单Repository
 *
 * @author example
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, String>, JpaSpecificationExecutor<SysMenu> {
}
