package com.example.admin.entity;

import java.time.LocalDateTime;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author example
 */
@Data
@TableName("sys_dictionary_item")
public class SysDictionaryItem {

    private static final long serialVersionUID = 1L;

    /**
     * 字典项ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @TableField("id")
    private String id;

    /**
     * 字典ID
     */
    @TableField("dict_id")
    private String dictId;

    /**
     * 标签
     */
    @TableField("label")
    private String label;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态：0-禁用，1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 字典对象（MyBatis-Plus不直接支持关联映射，后续通过业务逻辑处理）
     */
    // private SysDictionary dictionary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



}