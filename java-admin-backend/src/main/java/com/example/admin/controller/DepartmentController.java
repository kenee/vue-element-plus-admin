package com.example.admin.controller;

import com.example.admin.entity.SysDepartment;
import com.example.admin.service.ISysDepartmentService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author example
 */
@Tag(name = "department", description = "部门相关接口")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private ISysDepartmentService sysDepartmentService;

    @Operation(summary = "获取部门列表", description = "获取部门列表")
    @GetMapping
    @PreAuthorize("hasAuthority('department:list')")
    public ResponseResult<?> getDepartmentList() {
        return ResponseResult.success(sysDepartmentService.findAll());
    }

    @Operation(summary = "获取部门详情", description = "根据ID获取部门详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('department:view')")
    public ResponseResult<?> getDepartmentById(@PathVariable String id) {
        SysDepartment sysDepartment = sysDepartmentService.findById(id);
        return ResponseResult.success(sysDepartment);
    }

    @Operation(summary = "创建部门", description = "创建新部门")
    @PostMapping
    @PreAuthorize("hasAuthority('department:add')")
    public ResponseResult<?> createDepartment(@RequestBody SysDepartment sysDepartment) {
        SysDepartment savedDepartment = sysDepartmentService.saveDepartment(sysDepartment);
        return ResponseResult.success(savedDepartment);
    }

    @Operation(summary = "更新部门", description = "更新部门信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('department:edit')")
    public ResponseResult<?> updateDepartment(@PathVariable String id, @RequestBody SysDepartment sysDepartment) {
        sysDepartment.setId(id);
        SysDepartment updatedDepartment = sysDepartmentService.updateDepartment(sysDepartment);
        return ResponseResult.success(updatedDepartment);
    }

    @Operation(summary = "删除部门", description = "根据ID删除部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseResult<?> deleteDepartment(@PathVariable String id) {
        sysDepartmentService.deleteDepartment(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除部门", description = "批量删除部门")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseResult<?> batchDeleteDepartment(@RequestBody List<String> ids) {
        sysDepartmentService.deleteBatch(ids);
        return ResponseResult.success();
    }

}