package com.example.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 角色更新请求DTO
 *
 * @author example
 */
@Schema(description = "角色更新请求参数")
public class UpdateRoleDto {

    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    @Schema(description = "角色名称", example = "管理员")
    private String roleName;

    @Size(max = 50, message = "角色值长度不能超过50个字符")
    @Schema(description = "角色值", example = "admin")
    private String roleValue;

    @Schema(description = "状态：0-禁用，1-启用", example = "1")
    private Integer status;

    @Size(max = 255, message = "备注长度不能超过255个字符")
    @Schema(description = "备注", example = "系统管理员")
    private String remark;

    // Getters and Setters
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
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