package com.example.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 字典项更新请求DTO
 *
 * @author example
 */
@Schema(description = "字典项更新请求参数")
public class UpdateDictionaryItemDto {

    @Size(max = 36, message = "字典ID长度不能超过36个字符")
    @Schema(description = "字典ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String dictId;

    @Size(max = 100, message = "标签长度不能超过100个字符")
    @Schema(description = "标签", example = "启用")
    private String label;

    @Size(max = 100, message = "值长度不能超过100个字符")
    @Schema(description = "值", example = "1")
    private String value;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Schema(description = "状态：0-禁用，1-启用", example = "1")
    private Integer status;

    // Getters and Setters
    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}