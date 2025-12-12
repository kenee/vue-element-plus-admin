package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author example
 */
@Mapper
public interface SysUserRepository extends BaseMapper<SysUser> {

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
