package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色关联Mapper
 *
 * @author example
 */
@Mapper
public interface SysUserRoleRepository extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID查询所有关联的角色ID
     *
     * @param userId 用户ID
     * @return List<SysUserRole>
     */
    List<SysUserRole> findByUserId(String userId);

}
