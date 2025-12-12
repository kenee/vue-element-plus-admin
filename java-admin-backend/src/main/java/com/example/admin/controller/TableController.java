package com.example.admin.controller;

import com.example.admin.entity.SysCardExample;
import com.example.admin.entity.SysTableExample;
import com.example.admin.service.ISysCardExampleService;
import com.example.admin.service.ISysTableExampleService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表格示例控制器
 *
 * @author example
 */
@Tag(name = "table", description = "表格相关接口")
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private ISysTableExampleService sysTableExampleService;

    @Autowired
    private ISysCardExampleService sysCardExampleService;

    @Operation(summary = "获取表格示例列表", description = "获取表格示例列表")
    @GetMapping
    @PreAuthorize("hasAuthority('table:list')")
    public ResponseResult<?> getTableList() {
        return ResponseResult.success(sysTableExampleService.findAll());
    }

    @Operation(summary = "获取表格示例详情", description = "根据ID获取表格示例详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('table:view')")
    public ResponseResult<?> getTableById(@PathVariable String id) {
        SysTableExample tableExample = sysTableExampleService.findById(id);
        return ResponseResult.success(tableExample);
    }

    @Operation(summary = "获取根节点列表", description = "获取表格示例的根节点列表")
    @GetMapping("/root-nodes")
    @PreAuthorize("hasAuthority('table:list')")
    public ResponseResult<?> getRootNodes() {
        return ResponseResult.success(sysTableExampleService.findRootNodes());
    }

    @Operation(summary = "获取子节点列表", description = "根据父节点ID获取子节点列表")
    @GetMapping("/children/{parentId}")
    @PreAuthorize("hasAuthority('table:list')")
    public ResponseResult<?> getChildren(@PathVariable String parentId) {
        return ResponseResult.success(sysTableExampleService.findByParentId(parentId));
    }

    @Operation(summary = "创建表格示例", description = "创建新的表格示例")
    @PostMapping
    @PreAuthorize("hasAuthority('table:add')")
    public ResponseResult<?> createTable(@RequestBody SysTableExample tableExample) {
        SysTableExample savedTable = sysTableExampleService.saveTableExample(tableExample);
        return ResponseResult.success(savedTable);
    }

    @Operation(summary = "更新表格示例", description = "更新表格示例信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('table:edit')")
    public ResponseResult<?> updateTable(@PathVariable String id, @RequestBody SysTableExample tableExample) {
        tableExample.setId(id);
        SysTableExample updatedTable = sysTableExampleService.updateTableExample(tableExample);
        return ResponseResult.success(updatedTable);
    }

    @Operation(summary = "删除表格示例", description = "根据ID删除表格示例")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('table:delete')")
    public ResponseResult<?> deleteTable(@PathVariable String id) {
        sysTableExampleService.deleteTableExample(id);
        return ResponseResult.success();
    }

    @Operation(summary = "批量删除表格示例", description = "批量删除表格示例")
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('table:delete')")
    public ResponseResult<?> batchDeleteTable(@RequestBody List<String> ids) {
        sysTableExampleService.deleteBatch(ids);
        return ResponseResult.success();
    }

    @Operation(summary = "获取表格示例列表(带分页)", description = "获取表格示例列表，支持分页和搜索")
    @GetMapping("/example/list")
    @PreAuthorize("hasAuthority('table:list')")
    public ResponseResult<?> list(@RequestParam(defaultValue = "1") int page, 
                                  @RequestParam(defaultValue = "10") int pageSize, 
                                  @RequestParam(required = false) String title) {
        return ResponseResult.success(sysTableExampleService.findByPage(page, pageSize, title));
    }

    @Operation(summary = "保存表格示例", description = "创建或更新表格示例")
    @PostMapping("/example/save")
    @PreAuthorize("hasAuthority('table:add')")
    public ResponseResult<?> save(@RequestBody SysTableExample tableExample) {
        if (tableExample.getId() != null && !tableExample.getId().isEmpty()) {
            return ResponseResult.success(sysTableExampleService.updateTableExample(tableExample));
        } else {
            return ResponseResult.success(sysTableExampleService.saveTableExample(tableExample));
        }
    }

    @Operation(summary = "删除表格示例", description = "删除表格示例")
    @PostMapping("/example/delete")
    @PreAuthorize("hasAuthority('table:delete')")
    public ResponseResult<?> delete(@RequestBody List<String> ids) {
        sysTableExampleService.deleteBatch(ids);
        return ResponseResult.success();
    }

    @Operation(summary = "获取表格示例详情", description = "根据ID获取表格示例详情")
    @GetMapping("/example/detail/{id}")
    @PreAuthorize("hasAuthority('table:view')")
    public ResponseResult<?> get(@PathVariable String id) {
        return ResponseResult.success(sysTableExampleService.findById(id));
    }
}