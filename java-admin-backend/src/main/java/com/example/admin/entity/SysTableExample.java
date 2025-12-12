package com.example.admin.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

/**
 * <p>
 * 表格示例表
 * </p>
 *
 * @author example
 */
@Data
@TableName("sys_table_example")
public class SysTableExample {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @TableField("id")
    private String id;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 重要性
     */
    @TableField("importance")
    private Integer importance;

    /**
     * 显示时间
     */
    @TableField("display_time")
    private LocalDateTime displayTime;

    /**
     * 浏览量
     */
    @TableField("pageviews")
    private Integer pageviews;

    /**
     * 图片URI
     */
    @TableField("image_uri")
    private String imageUri;

    /**
     * 子节点（MyBatis-Plus不直接支持关联映射，后续通过业务逻辑处理）
     */
    // @TableField(exist = false) // 表示该字段不映射到数据库
    // private List<SysTableExample> children;

    /**
     * 父节点ID
     */
    @TableField(exist = false)
    private String parentId;

    /**
     * 父节点（MyBatis-Plus不直接支持关联映射，后续通过业务逻辑处理）
     */
    // @TableField(exist = false) // 表示该字段不映射到数据库
    // private SysTableExample parent;

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