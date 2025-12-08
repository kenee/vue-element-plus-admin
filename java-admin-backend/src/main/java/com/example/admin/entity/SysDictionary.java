package com.example.admin.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "sys_dictionary")
public class SysDictionary {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 字典名称
     */
    @Column(name = "dict_name", nullable = false, length = 100)
    private String dictName;

    /**
     * 字典编码
     */
    @Column(name = "dict_code", nullable = false, length = 100, unique = true)
    private String dictCode;

    /**
     * 状态：0-禁用，1-启用
     */
    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1")
    private Integer status;

    /**
     * 备注
     */
    @Column(name = "remark", length = 255)
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at", nullable = false, columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
    private LocalDateTime updatedAt;

    /**
     * 字典项列表
     */
    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SysDictionaryItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<SysDictionaryItem> getItems() {
        return items;
    }

    public void setItems(List<SysDictionaryItem> items) {
        this.items = items;
    }

}