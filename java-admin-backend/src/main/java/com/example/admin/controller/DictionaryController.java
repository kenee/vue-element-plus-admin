package com.example.admin.controller;

import com.example.admin.entity.SysDictionary;
import com.example.admin.entity.SysDictionaryItem;
import com.example.admin.service.ISysDictionaryService;
import com.example.admin.utils.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典控制器
 *
 * @author example
 */
@Tag(name = "dictionary", description = "字典相关接口")
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private ISysDictionaryService sysDictionaryService;

    @Operation(summary = "获取字典列表", description = "获取字典列表")
    @GetMapping
    @PreAuthorize("hasAuthority('dictionary:list')")
    public ResponseResult<?> getDictionaryList() {
        return ResponseResult.success(sysDictionaryService.findAll());
    }

    @Operation(summary = "获取字典详情", description = "根据ID获取字典详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('dictionary:view')")
    public ResponseResult<?> getDictionaryById(@PathVariable String id) {
        SysDictionary sysDictionary = sysDictionaryService.findById(id);
        return ResponseResult.success(sysDictionary);
    }

    @Operation(summary = "创建字典", description = "创建新字典")
    @PostMapping
    @PreAuthorize("hasAuthority('dictionary:add')")
    public ResponseResult<?> createDictionary(@RequestBody SysDictionary sysDictionary) {
        SysDictionary savedDictionary = sysDictionaryService.saveDictionary(sysDictionary);
        return ResponseResult.success(savedDictionary);
    }

    @Operation(summary = "更新字典", description = "更新字典信息")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('dictionary:edit')")
    public ResponseResult<?> updateDictionary(@PathVariable String id, @RequestBody SysDictionary sysDictionary) {
        sysDictionary.setId(id);
        SysDictionary updatedDictionary = sysDictionaryService.updateDictionary(sysDictionary);
        return ResponseResult.success(updatedDictionary);
    }

    @Operation(summary = "删除字典", description = "根据ID删除字典")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('dictionary:delete')")
    public ResponseResult<?> deleteDictionary(@PathVariable String id) {
        sysDictionaryService.deleteDictionary(id);
        return ResponseResult.success();
    }

    @Operation(summary = "获取字典项列表", description = "根据字典ID获取字典项列表")
    @GetMapping("/detail")
    @PreAuthorize("hasAuthority('dictionary:list')")
    public ResponseResult<?> getDictionaryItems(@RequestParam String dictId) {
        return ResponseResult.success(sysDictionaryService.findItemsByDictId(dictId));
    }

    @Operation(summary = "创建字典项", description = "创建新字典项")
    @PostMapping("/detail")
    @PreAuthorize("hasAuthority('dictionary:add')")
    public ResponseResult<?> createDictionaryItem(@RequestBody SysDictionaryItem sysDictionaryItem) {
        SysDictionaryItem savedItem = sysDictionaryService.saveItem(sysDictionaryItem);
        return ResponseResult.success(savedItem);
    }

    @Operation(summary = "更新字典项", description = "更新字典项信息")
    @PutMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('dictionary:edit')")
    public ResponseResult<?> updateDictionaryItem(@PathVariable String id, @RequestBody SysDictionaryItem sysDictionaryItem) {
        sysDictionaryItem.setId(id);
        SysDictionaryItem updatedItem = sysDictionaryService.updateItem(sysDictionaryItem);
        return ResponseResult.success(updatedItem);
    }

    @Operation(summary = "删除字典项", description = "根据ID删除字典项")
    @DeleteMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('dictionary:delete')")
    public ResponseResult<?> deleteDictionaryItem(@PathVariable String id) {
        sysDictionaryService.deleteItem(id);
        return ResponseResult.success();
    }

}
