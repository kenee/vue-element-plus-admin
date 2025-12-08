package com.example.admin.service;

import com.example.admin.entity.SysDepartment;

import java.util.List;

/**
 * 部门服务接口
 *
 * @author example
 */
public interface ISysDepartmentService {

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return SysDepartment
     */
    SysDepartment findById(String id);

    /**
     * 查询所有部门
     *
     * @return List<SysDepartment>
     */
    List<SysDepartment> findAll();

    /**
     * 保存部门
     *
     * @param department 部门信息
     * @return SysDepartment
     */
    SysDepartment saveDepartment(SysDepartment department);

    /**
     * 更新部门
     *
     * @param department 部门信息
     * @return SysDepartment
     */
    SysDepartment updateDepartment(SysDepartment department);

    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    void deleteDepartment(String id);

    /**
     * 批量删除部门
     *
     * @param ids 部门ID列表
     */
    void deleteBatch(List<String> ids);

}