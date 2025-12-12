package com.example.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.SysDepartment;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author example
 */
@Mapper
public interface SysDepartmentRepository extends BaseMapper<SysDepartment> {

}