package com.example.admin.controller;

import com.example.admin.service.ISysWorkplaceService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作台控制器
 *
 * @author example
 */
@Tag(name = "workplace", description = "工作台相关接口")
@RestController
@RequestMapping("/workplace")
public class WorkplaceController {

    @Autowired
    private ISysWorkplaceService sysWorkplaceService;

    @Operation(summary = "获取总数据", description = "获取工作台总数据")
    @GetMapping("/total")
    @PreAuthorize("hasAuthority('workplace:view')")
    public ResponseResult<?> getTotal() {
        return ResponseResult.success(sysWorkplaceService.getTotal());
    }

    @Operation(summary = "获取项目列表", description = "获取项目列表")
    @GetMapping("/project")
    @PreAuthorize("hasAuthority('workplace:view')")
    public ResponseResult<?> getProject() {
        return ResponseResult.success(sysWorkplaceService.getProject());
    }

    @Operation(summary = "获取动态列表", description = "获取动态列表")
    @GetMapping("/dynamic")
    @PreAuthorize("hasAuthority('workplace:view')")
    public ResponseResult<?> getDynamic() {
        return ResponseResult.success(sysWorkplaceService.getDynamic());
    }

    @Operation(summary = "获取团队列表", description = "获取团队列表")
    @GetMapping("/team")
    @PreAuthorize("hasAuthority('workplace:view')")
    public ResponseResult<?> getTeam() {
        return ResponseResult.success(sysWorkplaceService.getTeam());
    }

    @Operation(summary = "获取雷达图数据", description = "获取雷达图数据")
    @GetMapping("/radar")
    @PreAuthorize("hasAuthority('workplace:view')")
    public ResponseResult<?> getRadar() {
        return ResponseResult.success(sysWorkplaceService.getRadar());
    }

}