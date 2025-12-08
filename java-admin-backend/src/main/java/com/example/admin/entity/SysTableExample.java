package com.example.admin.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 表格示例表
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "sys_table_example")
public class SysTableExample {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 作者
     */
    @Column(name = "author", nullable = false, length = 100)
    private String author;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /**
     * 内容
     */
    @Column(name = "content", columnDefinition = "text")
    private String content;

    /**
     * 重要性
     */
    @Column(name = "importance", nullable = false, columnDefinition = "int default 1")
    private Integer importance;

    /**
     * 显示时间
     */
    @Column(name = "display_time", nullable = false)
    private LocalDateTime displayTime;

    /**
     * 浏览量
     */
    @Column(name = "pageviews", nullable = false, columnDefinition = "int default 0")
    private Integer pageviews;

    /**
     * 图片URI
     */
    @Column(name = "image_uri", length = 500)
    private String imageUri;

    /**
     * 子节点
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private List<SysTableExample> children;

    /**
     * 父节点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private SysTableExample parent;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public LocalDateTime getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(LocalDateTime displayTime) {
        this.displayTime = displayTime;
    }

    public Integer getPageviews() {
        return pageviews;
    }

    public void setPageviews(Integer pageviews) {
        this.pageviews = pageviews;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public List<SysTableExample> getChildren() {
        return children;
    }

    public void setChildren(List<SysTableExample> children) {
        this.children = children;
    }

    public SysTableExample getParent() {
        return parent;
    }

    public void setParent(SysTableExample parent) {
        this.parent = parent;
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