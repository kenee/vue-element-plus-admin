package com.example.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author example
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "sys_user_role")
public class SysUserRole {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id", columnDefinition = "VARCHAR(36)")
    private String userId;

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id", columnDefinition = "VARCHAR(36)")
    private String roleId;

}