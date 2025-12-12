package com.example.admin.controller;

import com.example.admin.service.ISysCardExampleService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 卡片示例控制器
 *
 * @author example
 */
@Tag(name = "card", description = "卡片相关接口")
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private ISysCardExampleService sysCardExampleService;

    @Operation(summary = "获取卡片示例列表", description = "获取卡片示例列表，支持分页和搜索")
    @GetMapping("/list")

    public ResponseResult<?> list(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name) {
        var cardList = sysCardExampleService.findByPage(page, pageSize, name);
        var total = sysCardExampleService.getTotal(name);
        // 创建Map包装分页数据
        Map<String, Object> data = new HashMap<>();
        data.put("list", cardList);
        data.put("total", total);
        return ResponseResult.success(data);
    }
}
