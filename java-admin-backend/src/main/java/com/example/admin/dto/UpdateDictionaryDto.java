package com.example.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 字典更新请求DTO
 *
 * @author example
 */
@Schema(description = "字典更新请求参数")
public class UpdateDictionaryDto {

    @Size(max = 100, message = "字典名称长度不能超过100个字符")
    @Schema(description = "字典名称", example = "用户状态")
    private String dictName;

    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    @Schema(description = "字典编码", example = "user_status")
    private String dictCode;

    @Schema(description = "状态：0-禁用，1-启用", example = "1")
    private Integer status;

    @Size(max = 255, message = "备注长度不能超过255个字符")
    @Schema(description = "备注", example = "用户状态字典")
    private String remark;

    // Getters and Setters
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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