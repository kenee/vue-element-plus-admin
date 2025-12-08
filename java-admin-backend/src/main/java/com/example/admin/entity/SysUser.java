package com.example.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "sys_user")
public class SysUser {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nickname", length = 50)
    private String nickname;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

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
     * 部门ID
     */
    @Column(name = "dept_id")
    private String deptId;

    // 实体关系映射（后续根据需要添加）
    // @ManyToOne
    // @JoinColumn(name = "dept_id")
    // private SysDepartment department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

}
