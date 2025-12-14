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

    public ResponseResult<?> getDepartmentList() {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("list", sysDepartmentService.findAll());
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取部门详情", description = "根据ID获取部门详情")
    @GetMapping("/{id}")
    public ResponseResult<?> getDepartmentById(@PathVariable String id) {
        SysDepartment sysDepartment = sysDepartmentService.findById(id);
        return ResponseResult.success(sysDepartment);
    }

    @Operation(summary = "创建部门", description = "创建新部门")
    @PostMapping
    public ResponseResult<?> createDepartment(@RequestBody SysDepartment sysDepartment) {
        SysDepartment savedDepartment = sysDepartmentService.saveDepartment(sysDepartment);
        return ResponseResult.success(savedDepartment);
    }

    @Operation(summary = "更新部门", description = "更新部门信息")
    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    public ResponseResult<?> updateDepartment(@PathVariable String id, @RequestBody SysDepartment sysDepartment) {
        sysDepartment.setId(id);
        SysDepartment updatedDepartment = sysDepartmentService.updateDepartment(sysDepartment);
        return ResponseResult.success(updatedDepartment);
    }

    @Operation(summary = "删除部门", description = "根据ID删除部门")
    @DeleteMapping("/{id}")
    public ResponseResult<?> deleteDepartment(@PathVariable String id) {
        sysDepartmentService.deleteDepartment(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除部门", description = "批量删除部门")
    @DeleteMapping("/batch")
    public ResponseResult<?> batchDeleteDepartment(@RequestBody List<String> ids) {
        sysDepartmentService.deleteBatch(ids);
        return ResponseResult.success();
    }

}