package com.example.admin.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "sys_dictionary_item")
public class SysDictionaryItem {

    private static final long serialVersionUID = 1L;

    /**
     * 字典项ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 字典ID
     */
    @Column(name = "dict_id", nullable = false)
    private String dictId;

    /**
     * 标签
     */
    @Column(name = "label", nullable = false, length = 100)
    private String label;

    /**
     * 值
     */
    @Column(name = "value", nullable = false, length = 100)
    private String value;

    /**
     * 排序
     */
    @Column(name = "sort", nullable = false, columnDefinition = "int default 0")
    private Integer sort;

    /**
     * 状态：0-禁用，1-启用
     */
    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1")
    private Integer status;

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
     * 字典对象
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dict_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SysDictionary dictionary;

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

    public SysDictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(SysDictionary dictionary) {
        this.dictionary = dictionary;
    }

}