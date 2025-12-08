package com.example.admin.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 菜单更新请求DTO
 *
 * @author example
 */
@Schema(description = "菜单更新请求参数")
public class UpdateMenuDto {

    @Size(max = 200, message = "路径长度不能超过200个字符")
    @Schema(description = "路径", example = "/system/user")
    private String path;

    @Size(max = 200, message = "组件长度不能超过200个字符")
    @Schema(description = "组件", example = "system/user/index")
    private String component;

    @Size(max = 200, message = "重定向长度不能超过200个字符")
    @Schema(description = "重定向", example = "noRedirect")
    private String redirect;

    @Size(max = 50, message = "菜单标题长度不能超过50个字符")
    @Schema(description = "菜单标题", example = "用户管理")
    private String title;

    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @Schema(description = "菜单名称", example = "User")
    private String name;

    @Size(max = 50, message = "图标长度不能超过50个字符")
    @Schema(description = "图标", example = "ri:user-line")
    private String icon;

    @Schema(description = "菜单元信息")
    private JsonNode meta;

    @Schema(description = "类型：0-目录，1-菜单，2-按钮", example = "1")
    private Integer type;

    @Schema(description = "状态：0-禁用，1-启用", example = "1")
    private Integer status;

    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    @Schema(description = "权限标识", example = "user:list")
    private String permission;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Size(max = 36, message = "父菜单ID长度不能超过36个字符")
    @Schema(description = "父菜单ID")
    private String parentId;

    // Getters and Setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public JsonNode getMeta() {
        return meta;
    }

    public void setMeta(JsonNode meta) {
        this.meta = meta;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}