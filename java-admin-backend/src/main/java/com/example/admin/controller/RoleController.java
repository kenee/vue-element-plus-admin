package com.example.admin.controller;

import com.example.admin.entity.SysRole;
import com.example.admin.service.ISysRoleService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author example
 */
@Tag(name = "role", description = "角色相关接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @Operation(summary = "获取角色列表", description = "获取角色列表")
    @GetMapping

    public ResponseResult<?> getRoleList() {
        return ResponseResult.success(sysRoleService.findAll());
    }

    @Operation(summary = "获取角色详情", description = "根据ID获取角色详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role:view')")
    public ResponseResult<?> getRoleById(@PathVariable String id) {
        SysRole sysRole = sysRoleService.findById(id);
        return ResponseResult.success(sysRole);
    }

    @Operation(summary = "创建角色", description = "创建新角色")
    @PostMapping
    @PreAuthorize("hasAuthority('role:add')")
    public ResponseResult<?> createRole(@RequestBody SysRole sysRole) {
        SysRole savedRole = sysRoleService.saveRole(sysRole);
        return ResponseResult.success(savedRole);
    }

    @Operation(summary = "更新角色", description = "更新角色信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role:edit')")
    public ResponseResult<?> updateRole(@PathVariable String id, @RequestBody SysRole sysRole) {
        sysRole.setId(id);
        SysRole updatedRole = sysRoleService.updateRole(sysRole);
        return ResponseResult.success(updatedRole);
    }

    @Operation(summary = "删除角色", description = "根据ID删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseResult<?> deleteRole(@PathVariable String id) {
        sysRoleService.deleteRole(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除角色", description = "批量删除角色")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseResult<?> batchDeleteRole(@RequestBody List<String> ids) {
        sysRoleService.deleteBatch(ids);
        return ResponseResult.success();
    }

}