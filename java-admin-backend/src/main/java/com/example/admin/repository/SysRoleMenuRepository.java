package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关联Mapper
 *
 * @author example
 */
@Mapper
public interface SysRoleMenuRepository extends BaseMapper<SysRoleMenu> {
}
