package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单Mapper
 *
 * @author example
 */
@Mapper
public interface SysMenuRepository extends BaseMapper<SysMenu> {
}
