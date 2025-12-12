package com.example.admin.controller;

import com.example.admin.entity.SysMenu;
import com.example.admin.service.ISysMenuService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器
 *
 * @author example
 */
@Tag(name = "menu", description = "菜单相关接口")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @Operation(summary = "获取菜单列表", description = "获取菜单列表")
    @GetMapping

    public ResponseResult<?> getMenuList() {
        return ResponseResult.success(sysMenuService.findAll());
    }

    @Operation(summary = "获取菜单详情", description = "根据ID获取菜单详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('menu:view')")
    public ResponseResult<?> getMenuById(@PathVariable String id) {
        SysMenu sysMenu = sysMenuService.findById(id);
        return ResponseResult.success(sysMenu);
    }

    @Operation(summary = "创建菜单", description = "创建新菜单")
    @PostMapping
    @PreAuthorize("hasAuthority('menu:add')")
    public ResponseResult<?> createMenu(@RequestBody SysMenu sysMenu) {
        SysMenu savedMenu = sysMenuService.saveMenu(sysMenu);
        return ResponseResult.success(savedMenu);
    }

    @Operation(summary = "更新菜单", description = "更新菜单信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('menu:edit')")
    public ResponseResult<?> updateMenu(@PathVariable String id, @RequestBody SysMenu sysMenu) {
        sysMenu.setId(id);
        SysMenu updatedMenu = sysMenuService.updateMenu(sysMenu);
        return ResponseResult.success(updatedMenu);
    }

    @Operation(summary = "删除菜单", description = "根据ID删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('menu:delete')")
    public ResponseResult<?> deleteMenu(@PathVariable String id) {
        sysMenuService.deleteMenu(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除菜单", description = "批量删除菜单")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('menu:delete')")
    public ResponseResult<?> batchDeleteMenu(@RequestBody List<String> ids) {
        sysMenuService.deleteBatch(ids);
        return ResponseResult.success();
    }

    @Operation(summary = "获取路由列表", description = "获取前端路由列表")
    @GetMapping("/routes")

    public ResponseResult<?> getRoutes() {
        // 从SecurityContext中获取当前用户
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        // 假设用户名即为用户ID，实际项目中可能需要从UserDetails中获取用户ID
        String userId = currentUser.getUsername();
        return ResponseResult.success(sysMenuService.getRoutesByUser(userId));
    }

    @Operation(summary = "获取用户菜单列表", description = "获取当前用户的菜单列表")
    @GetMapping("/user")
    public ResponseResult<?> getUserMenus() {
        // 从SecurityContext中获取当前用户
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        // 假设用户名即为用户ID，实际项目中可能需要从UserDetails中获取用户ID
        String userId = currentUser.getUsername();
        return ResponseResult.success(sysMenuService.getUserMenus(userId));
    }

}