package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper
 *
 * @author example
 */
@Mapper
public interface SysRoleRepository extends BaseMapper<SysRole> {
}
