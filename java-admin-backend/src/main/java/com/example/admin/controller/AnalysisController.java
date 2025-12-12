package com.example.admin.controller;

import com.example.admin.service.ISysAnalysisService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计分析控制器
 *
 * @author example
 */
@Tag(name = "analysis", description = "统计分析相关接口")
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private ISysAnalysisService sysAnalysisService;

    @Operation(summary = "获取总数据", description = "获取统计分析的总数据")
    @GetMapping("/total")

    public ResponseResult<?> getTotal() {
        return ResponseResult.success(sysAnalysisService.getTotal());
    }

    @Operation(summary = "获取用户访问来源", description = "获取用户访问来源数据")
    @GetMapping("/userAccessSource")

    public ResponseResult<?> getUserAccessSource() {
        return ResponseResult.success(sysAnalysisService.getUserAccessSource());
    }

    @Operation(summary = "获取每周用户活动", description = "获取每周用户活动数据")
    @GetMapping("/weeklyUserActivity")

    public ResponseResult<?> getWeeklyUserActivity() {
        return ResponseResult.success(sysAnalysisService.getWeeklyUserActivity());
    }

    @Operation(summary = "获取月度销售数据", description = "获取月度销售数据")
    @GetMapping("/monthlySales")

    public ResponseResult<?> getMonthlySales() {
        return ResponseResult.success(sysAnalysisService.getMonthlySales());
    }

}
