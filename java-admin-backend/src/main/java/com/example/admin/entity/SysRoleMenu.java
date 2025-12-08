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
 * 角色菜单关联表
 * </p>
 *
 * @author example
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id", columnDefinition = "VARCHAR(36)")
    private String roleId;

    /**
     * 菜单ID
     */
    @Id
    @Column(name = "menu_id", columnDefinition = "VARCHAR(36)")
    private String menuId;

}