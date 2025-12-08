package com.example.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 部门创建请求DTO
 *
 * @author example
 */
@Schema(description = "部门创建请求参数")
public class CreateDepartmentDto {

    @NotBlank(message = "部门名称不能为空")
    @Size(max = 100, message = "部门名称长度不能超过100个字符")
    @Schema(description = "部门名称", example = "技术部")
    private String name;

    @Schema(description = "父部门ID")
    private String parentId;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Schema(description = "状态：0-禁用，1-启用", example = "1")
    private Integer status;

    @Size(max = 255, message = "备注长度不能超过255个字符")
    @Schema(description = "备注", example = "负责公司技术研发")
    private String remark;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}