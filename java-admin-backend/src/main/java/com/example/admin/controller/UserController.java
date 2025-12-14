package com.example.admin.controller;

import com.example.admin.entity.SysUser;
import com.example.admin.service.ISysUserService;
import com.example.admin.utils.ResponseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 *
 * @author example
 */
@Tag(name = "user", description = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;

    @Operation(summary = "获取用户列表", description = "获取用户列表")
    @GetMapping

    public ResponseResult<?> getUserList(
            @RequestParam(name = "page", defaultValue = "1") Long page,
            @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String deptId) {
        Page<SysUser> pageParam = new Page<>(page, pageSize);
        SysUser userQuery = new SysUser();
        if (username != null) {
            userQuery.setUsername(username);
        }
        if (nickname != null) {
            userQuery.setNickname(nickname);
        }
        if (deptId != null) {
            userQuery.setDeptId(deptId);
        }

        Page<SysUser> result = sysUserService.getUserList(pageParam, userQuery);

        Map<String, Object> map = new HashMap<>();
        map.put("list", result.getRecords());
        map.put("total", result.getTotal());

        return ResponseResult.success(map);
    }

    @Operation(summary = "获取用户详情", description = "根据ID获取用户详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:view')")
    public ResponseResult<?> getUserById(@PathVariable String id) {
        SysUser sysUser = sysUserService.findById(id);
        return ResponseResult.success(sysUser);
    }

    @Operation(summary = "创建用户", description = "创建新用户")
    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    public ResponseResult<?> createUser(@RequestBody SysUser sysUser) {
        SysUser savedUser = sysUserService.saveUser(sysUser);
        return ResponseResult.success(savedUser);
    }

    @Operation(summary = "更新用户", description = "更新用户信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:edit')")
    public ResponseResult<?> updateUser(@PathVariable String id, @RequestBody SysUser sysUser) {
        sysUser.setId(id);
        SysUser updatedUser = sysUserService.updateUser(sysUser);
        return ResponseResult.success(updatedUser);
    }

    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseResult<?> deleteUser(@PathVariable String id) {
        sysUserService.deleteUser(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除用户", description = "批量删除用户")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseResult<?> batchDeleteUser(@RequestBody List<String> ids) {
        sysUserService.deleteBatch(ids);
        return ResponseResult.success();
    }

    @Operation(summary = "获取用户个人信息", description = "获取当前登录用户的个人信息")
    @GetMapping("/profile")
    public ResponseResult<?> getProfile() {
        // 从SecurityContext中获取当前用户
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        // 假设用户名即为用户ID，实际项目中可能需要从UserDetails中获取用户ID
        String userId = currentUser.getUsername();
        SysUser user = sysUserService.findById(userId);
        return ResponseResult.success(user);
    }

}