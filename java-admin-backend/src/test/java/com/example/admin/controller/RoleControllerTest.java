package com.example.admin.controller;

import com.example.admin.entity.SysRole;
import com.example.admin.service.ISysRoleService;
import com.example.admin.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * RoleController 集成测试
 * 测试角色管理相关API接口
 * 
 * 对应前端API:
 * - GET /api/role - 获取角色列表
 * - POST /api/role - 创建角色
 * - PATCH /api/role/:id - 更新角色
 * - DELETE /api/role/:id - 删除角色
 *
 * @author example
 */
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ISysRoleService sysRoleService;

    private SysRole testRole;
    private List<SysRole> roleList;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testRole = new SysRole();
        testRole.setId("role-1");
        testRole.setRoleName("管理员");
        testRole.setRoleValue("admin");
        testRole.setStatus(1);
        testRole.setRemark("系统管理员");
        testRole.setCreateTime(LocalDateTime.now());

        SysRole role2 = new SysRole();
        role2.setId("role-2");
        role2.setRoleName("普通用户");
        role2.setRoleValue("user");
        role2.setStatus(1);
        role2.setRemark("普通用户");
        role2.setCreateTime(LocalDateTime.now());

        roleList = new ArrayList<>();
        roleList.add(testRole);
        roleList.add(role2);
    }

    @Test
    @WithMockUser(authorities = { "role:list" })
    void testGetRoleList() throws Exception {
        // 模拟服务层返回
        when(sysRoleService.findAll()).thenReturn(roleList);

        // 执行测试并验证
        mockMvc.perform(get("/role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].roleName").value("管理员"))
                .andExpect(jsonPath("$.data[1].roleName").value("普通用户"));

        verify(sysRoleService).findAll();
    }

    @Test
    @WithMockUser(authorities = { "role:list" })
    void testGetRoleListEmpty() throws Exception {
        // 模拟空列表
        when(sysRoleService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = { "role:view" })
    void testGetRoleById() throws Exception {
        // 模拟服务层返回
        when(sysRoleService.findById("role-1")).thenReturn(testRole);

        // 执行测试并验证
        mockMvc.perform(get("/role/role-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("role-1"))
                .andExpect(jsonPath("$.data.roleName").value("管理员"))
                .andExpect(jsonPath("$.data.roleValue").value("admin"));

        verify(sysRoleService).findById("role-1");
    }

    @Test
    @WithMockUser(authorities = { "role:view" })
    void testGetRoleByIdNotFound() throws Exception {
        // 模拟角色不存在
        when(sysRoleService.findById("non-existent")).thenReturn(null);

        mockMvc.perform(get("/role/non-existent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(authorities = { "role:add" })
    void testCreateRole() throws Exception {
        // 准备新角色数据
        SysRole newRole = new SysRole();
        newRole.setRoleName("测试角色");
        newRole.setRoleValue("test");
        newRole.setStatus(1);
        newRole.setRemark("测试用角色");

        SysRole savedRole = new SysRole();
        savedRole.setId("role-3");
        savedRole.setRoleName(newRole.getRoleName());
        savedRole.setRoleValue(newRole.getRoleValue());
        savedRole.setStatus(newRole.getStatus());
        savedRole.setRemark(newRole.getRemark());
        savedRole.setCreateTime(LocalDateTime.now());

        // 模拟服务层保存
        when(sysRoleService.saveRole(any(SysRole.class))).thenReturn(savedRole);

        // 执行测试并验证
        mockMvc.perform(post("/role")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRole)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("role-3"))
                .andExpect(jsonPath("$.data.roleName").value("测试角色"));

        verify(sysRoleService).saveRole(any(SysRole.class));
    }

    @Test
    @WithMockUser(authorities = { "role:add" })
    void testCreateRoleWithInvalidData() throws Exception {
        // 准备无效数据（缺少必填字段）
        SysRole invalidRole = new SysRole();
        invalidRole.setRoleName(""); // 空名称

        mockMvc.perform(post("/role")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRole)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "role:edit" })
    void testUpdateRole() throws Exception {
        // 准备更新数据
        SysRole updateRole = new SysRole();
        updateRole.setRoleName("更新后的管理员");
        updateRole.setRoleValue("admin-updated");
        updateRole.setStatus(1);
        updateRole.setRemark("更新后的备注");

        SysRole updatedRole = new SysRole();
        updatedRole.setId("role-1");
        updatedRole.setRoleName(updateRole.getRoleName());
        updatedRole.setRoleValue(updateRole.getRoleValue());
        updatedRole.setStatus(updateRole.getStatus());
        updatedRole.setRemark(updateRole.getRemark());
        updatedRole.setUpdateTime(LocalDateTime.now());

        // 模拟服务层更新
        when(sysRoleService.updateRole(any(SysRole.class))).thenReturn(updatedRole);

        // 执行测试并验证
        mockMvc.perform(put("/role/role-1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRole)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("role-1"))
                .andExpect(jsonPath("$.data.roleName").value("更新后的管理员"));

        verify(sysRoleService).updateRole(any(SysRole.class));
    }

    @Test
    @WithMockUser(authorities = { "role:delete" })
    void testDeleteRole() throws Exception {
        // 模拟删除操作
        doNothing().when(sysRoleService).deleteRole("role-1");

        // 执行测试并验证
        mockMvc.perform(delete("/role/role-1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysRoleService).deleteRole("role-1");
    }

    @Test
    @WithMockUser(authorities = { "role:delete" })
    void testBatchDeleteRole() throws Exception {
        // 准备批量删除的ID列表
        List<String> ids = Arrays.asList("role-1", "role-2", "role-3");

        // 模拟批量删除操作
        doNothing().when(sysRoleService).deleteBatch(anyList());

        // 执行测试并验证
        mockMvc.perform(delete("/role/batch")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysRoleService).deleteBatch(anyList());
    }

    @Test
    @WithMockUser(authorities = { "role:delete" })
    void testBatchDeleteRoleWithEmptyList() throws Exception {
        // 准备空列表
        List<String> emptyIds = new ArrayList<>();

        mockMvc.perform(delete("/role/batch")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyIds)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRoleListWithoutPermission() throws Exception {
        // 测试没有权限的情况
        mockMvc.perform(get("/role"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "other:permission" })
    void testCreateRoleWithWrongPermission() throws Exception {
        // 测试权限不足的情况
        SysRole newRole = new SysRole();
        newRole.setRoleName("测试角色");

        mockMvc.perform(post("/role")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRole)))
                .andExpect(status().isForbidden());
    }
}
