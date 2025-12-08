package com.example.admin.controller;

import com.example.admin.entity.SysUser;
import com.example.admin.service.ISysUserService;
import com.example.admin.utils.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * UserController 集成测试
 * 测试用户管理相关API接口
 * 
 * 对应前端API:
 * - GET /api/user - 获取用户列表（支持分页和筛选）
 * - POST /api/user - 创建用户
 * - PATCH /api/user/:id - 更新用户
 * - POST /api/user/delete - 批量删除用户
 *
 * @author example
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ISysUserService sysUserService;

    private SysUser testUser;
    private List<SysUser> userList;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testUser = new SysUser();
        testUser.setId("user-1");
        testUser.setUsername("admin");
        testUser.setNickname("管理员");
        testUser.setEmail("admin@example.com");
        testUser.setPhone("13800138000");
        testUser.setGender(1);
        testUser.setStatus(1);
        testUser.setDeptId("dept-1");
        testUser.setCreateTime(LocalDateTime.now());

        SysUser user2 = new SysUser();
        user2.setId("user-2");
        user2.setUsername("testuser");
        user2.setNickname("测试用户");
        user2.setEmail("test@example.com");
        user2.setPhone("13900139000");
        user2.setGender(0);
        user2.setStatus(1);
        user2.setDeptId("dept-2");
        user2.setCreateTime(LocalDateTime.now());

        userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(user2);
    }

    @Test
    @WithMockUser(authorities = { "user:list" })
    void testGetUserList() throws Exception {
        // 模拟分页数据
        Page<SysUser> userPage = new PageImpl<>(userList, PageRequest.of(0, 10), userList.size());
        when(sysUserService.findAll(any())).thenReturn(userPage);

        // 执行测试并验证
        mockMvc.perform(get("/user")
                .param("page", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list.length()").value(2))
                .andExpect(jsonPath("$.data.total").value(2))
                .andExpect(jsonPath("$.data.list[0].username").value("admin"));

        verify(sysUserService).findAll(any());
    }

    @Test
    @WithMockUser(authorities = { "user:list" })
    void testGetUserListWithFilters() throws Exception {
        // 测试带筛选条件的查询
        List<SysUser> filteredList = new ArrayList<>();
        filteredList.add(testUser);
        Page<SysUser> userPage = new PageImpl<>(filteredList, PageRequest.of(0, 10), filteredList.size());

        when(sysUserService.findAll(any())).thenReturn(userPage);

        mockMvc.perform(get("/user")
                .param("page", "1")
                .param("pageSize", "10")
                .param("username", "admin")
                .param("deptId", "dept-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.list.length()").value(1))
                .andExpect(jsonPath("$.data.list[0].username").value("admin"));
    }

    @Test
    @WithMockUser(authorities = { "user:list" })
    void testGetUserListEmpty() throws Exception {
        // 模拟空列表
        Page<SysUser> emptyPage = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 10), 0);
        when(sysUserService.findAll(any())).thenReturn(emptyPage);

        mockMvc.perform(get("/user")
                .param("page", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list.length()").value(0))
                .andExpect(jsonPath("$.data.total").value(0));
    }

    @Test
    @WithMockUser(authorities = { "user:list" })
    void testGetUserListPagination() throws Exception {
        // 测试分页功能
        Page<SysUser> page2 = new PageImpl<>(new ArrayList<>(), PageRequest.of(1, 10), 15);
        when(sysUserService.findAll(any())).thenReturn(page2);

        mockMvc.perform(get("/user")
                .param("page", "2")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.total").value(15));
    }

    @Test
    @WithMockUser(authorities = { "user:view" })
    void testGetUserById() throws Exception {
        // 模拟服务层返回
        when(sysUserService.findById("user-1")).thenReturn(testUser);

        // 执行测试并验证
        mockMvc.perform(get("/user/user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("user-1"))
                .andExpect(jsonPath("$.data.username").value("admin"))
                .andExpect(jsonPath("$.data.email").value("admin@example.com"));

        verify(sysUserService).findById("user-1");
    }

    @Test
    @WithMockUser(authorities = { "user:view" })
    void testGetUserByIdNotFound() throws Exception {
        // 模拟用户不存在
        when(sysUserService.findById("non-existent")).thenReturn(null);

        mockMvc.perform(get("/user/non-existent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(authorities = { "user:add" })
    void testCreateUser() throws Exception {
        // 准备新用户数据
        SysUser newUser = new SysUser();
        newUser.setUsername("newuser");
        newUser.setPassword("password123");
        newUser.setNickname("新用户");
        newUser.setEmail("newuser@example.com");
        newUser.setPhone("13700137000");
        newUser.setGender(1);
        newUser.setStatus(1);
        newUser.setDeptId("dept-1");

        SysUser savedUser = new SysUser();
        savedUser.setId("user-3");
        savedUser.setUsername(newUser.getUsername());
        savedUser.setNickname(newUser.getNickname());
        savedUser.setEmail(newUser.getEmail());
        savedUser.setPhone(newUser.getPhone());
        savedUser.setGender(newUser.getGender());
        savedUser.setStatus(newUser.getStatus());
        savedUser.setDeptId(newUser.getDeptId());
        savedUser.setCreateTime(LocalDateTime.now());

        // 模拟服务层保存
        when(sysUserService.saveUser(any(SysUser.class))).thenReturn(savedUser);

        // 执行测试并验证
        mockMvc.perform(post("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("user-3"))
                .andExpect(jsonPath("$.data.username").value("newuser"));

        verify(sysUserService).saveUser(any(SysUser.class));
    }

    @Test
    @WithMockUser(authorities = { "user:add" })
    void testCreateUserWithInvalidData() throws Exception {
        // 准备无效数据（缺少必填字段）
        SysUser invalidUser = new SysUser();
        invalidUser.setUsername(""); // 空用户名

        mockMvc.perform(post("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "user:add" })
    void testCreateUserWithDuplicateUsername() throws Exception {
        // 测试创建重复用户名的用户
        SysUser duplicateUser = new SysUser();
        duplicateUser.setUsername("admin");
        duplicateUser.setPassword("password123");

        when(sysUserService.saveUser(any(SysUser.class)))
                .thenThrow(new RuntimeException("用户名已存在"));

        mockMvc.perform(post("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateUser)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(authorities = { "user:edit" })
    void testUpdateUser() throws Exception {
        // 准备更新数据
        SysUser updateUser = new SysUser();
        updateUser.setNickname("管理员（更新）");
        updateUser.setEmail("admin-new@example.com");
        updateUser.setPhone("13800138001");
        updateUser.setGender(1);
        updateUser.setStatus(1);
        updateUser.setDeptId("dept-1");

        SysUser updatedUser = new SysUser();
        updatedUser.setId("user-1");
        updatedUser.setUsername("admin");
        updatedUser.setNickname(updateUser.getNickname());
        updatedUser.setEmail(updateUser.getEmail());
        updatedUser.setPhone(updateUser.getPhone());
        updatedUser.setGender(updateUser.getGender());
        updatedUser.setStatus(updateUser.getStatus());
        updatedUser.setDeptId(updateUser.getDeptId());
        updatedUser.setUpdateTime(LocalDateTime.now());

        // 模拟服务层更新
        when(sysUserService.updateUser(any(SysUser.class))).thenReturn(updatedUser);

        // 执行测试并验证
        mockMvc.perform(patch("/user/user-1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("user-1"))
                .andExpect(jsonPath("$.data.nickname").value("管理员（更新）"));

        verify(sysUserService).updateUser(any(SysUser.class));
    }

    @Test
    @WithMockUser(authorities = { "user:edit" })
    void testUpdateUserPassword() throws Exception {
        // 测试更新密码
        SysUser updateUser = new SysUser();
        updateUser.setPassword("newpassword123");

        SysUser updatedUser = new SysUser();
        updatedUser.setId("user-1");
        updatedUser.setUsername("admin");

        when(sysUserService.updateUser(any(SysUser.class))).thenReturn(updatedUser);

        mockMvc.perform(patch("/user/user-1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @WithMockUser(authorities = { "user:delete" })
    void testDeleteUser() throws Exception {
        // 准备批量删除的ID列表（前端使用POST /api/user/delete）
        List<String> ids = Arrays.asList("user-1");

        // 模拟删除操作
        doNothing().when(sysUserService).deleteBatch(anyList());

        // 执行测试并验证
        mockMvc.perform(post("/user/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysUserService).deleteBatch(anyList());
    }

    @Test
    @WithMockUser(authorities = { "user:delete" })
    void testBatchDeleteUser() throws Exception {
        // 准备批量删除的ID列表
        List<String> ids = Arrays.asList("user-1", "user-2", "user-3");

        // 模拟批量删除操作
        doNothing().when(sysUserService).deleteBatch(anyList());

        // 执行测试并验证
        mockMvc.perform(post("/user/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysUserService).deleteBatch(anyList());
    }

    @Test
    @WithMockUser(authorities = { "user:delete" })
    void testBatchDeleteUserWithEmptyList() throws Exception {
        // 准备空列表
        List<String> emptyIds = new ArrayList<>();

        mockMvc.perform(post("/user/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyIds)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserListWithoutPermission() throws Exception {
        // 测试没有权限的情况
        mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "other:permission" })
    void testCreateUserWithWrongPermission() throws Exception {
        // 测试权限不足的情况
        SysUser newUser = new SysUser();
        newUser.setUsername("testuser");

        mockMvc.perform(post("/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = { "user:delete" })
    void testDeleteCurrentUser() throws Exception {
        // 测试删除当前登录用户（应该抛出异常或返回错误）
        List<String> ids = Arrays.asList("current-user-id");

        doThrow(new RuntimeException("不能删除当前登录用户"))
                .when(sysUserService).deleteBatch(anyList());

        mockMvc.perform(post("/user/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isInternalServerError());
    }
}
