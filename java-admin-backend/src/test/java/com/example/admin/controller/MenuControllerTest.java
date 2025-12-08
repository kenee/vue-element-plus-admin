package com.example.admin.controller;

import com.example.admin.entity.SysMenu;
import com.example.admin.service.ISysMenuService;
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
 * MenuController 集成测试
 * 测试菜单管理相关API接口
 * 
 * 对应前端API:
 * - GET /api/menu - 获取菜单列表
 * - POST /api/menu - 创建菜单
 * - PATCH /api/menu/:id - 更新菜单
 * - DELETE /api/menu/:id - 删除菜单
 * - GET /api/menu/routes - 获取路由列表
 *
 * @author example
 */
@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ISysMenuService sysMenuService;

    private SysMenu testMenu;
    private List<SysMenu> menuList;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testMenu = new SysMenu();
        testMenu.setId("menu-1");
        testMenu.setTitle("系统管理");
        testMenu.setPath("/system");
        testMenu.setComponent("Layout");
        testMenu.setIcon("system");
        testMenu.setSort(1);
        testMenu.setStatus(1);
        testMenu.setParentId("0");
        testMenu.setCreateTime(LocalDateTime.now());

        SysMenu subMenu = new SysMenu();
        subMenu.setId("menu-2");
        subMenu.setTitle("用户管理");
        subMenu.setPath("/system/user");
        subMenu.setComponent("system/user/index");
        subMenu.setIcon("user");
        subMenu.setSort(1);
        subMenu.setStatus(1);
        subMenu.setParentId("menu-1");
        subMenu.setCreateTime(LocalDateTime.now());

        menuList = new ArrayList<>();
        menuList.add(testMenu);
        menuList.add(subMenu);
    }

    @Test
    @WithMockUser(authorities = { "menu:list" })
    void testGetMenuList() throws Exception {
        // 模拟服务层返回
        when(sysMenuService.findAll()).thenReturn(menuList);

        // 执行测试并验证
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("系统管理"))
                .andExpect(jsonPath("$.data[1].title").value("用户管理"));

        verify(sysMenuService).findAll();
    }

    @Test
    @WithMockUser(authorities = { "menu:list" })
    void testGetMenuListEmpty() throws Exception {
        // 模拟空列表
        when(sysMenuService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = { "menu:view" })
    void testGetMenuById() throws Exception {
        // 模拟服务层返回
        when(sysMenuService.findById("menu-1")).thenReturn(testMenu);

        // 执行测试并验证
        mockMvc.perform(get("/menu/menu-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("menu-1"))
                .andExpect(jsonPath("$.data.title").value("系统管理"))
                .andExpect(jsonPath("$.data.path").value("/system"));

        verify(sysMenuService).findById("menu-1");
    }

    @Test
    @WithMockUser(authorities = { "menu:view" })
    void testGetMenuByIdNotFound() throws Exception {
        // 模拟菜单不存在
        when(sysMenuService.findById("non-existent")).thenReturn(null);

        mockMvc.perform(get("/menu/non-existent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(authorities = { "menu:add" })
    void testCreateMenu() throws Exception {
        // 准备新菜单数据
        SysMenu newMenu = new SysMenu();
        newMenu.setTitle("角色管理");
        newMenu.setPath("/system/role");
        newMenu.setComponent("system/role/index");
        newMenu.setIcon("role");
        newMenu.setSort(2);
        newMenu.setStatus(1);
        newMenu.setParentId("menu-1");

        SysMenu savedMenu = new SysMenu();
        savedMenu.setId("menu-3");
        savedMenu.setTitle(newMenu.getTitle());
        savedMenu.setPath(newMenu.getPath());
        savedMenu.setComponent(newMenu.getComponent());
        savedMenu.setIcon(newMenu.getIcon());
        savedMenu.setSort(newMenu.getSort());
        savedMenu.setStatus(newMenu.getStatus());
        savedMenu.setParentId(newMenu.getParentId());
        savedMenu.setCreateTime(LocalDateTime.now());

        // 模拟服务层保存
        when(sysMenuService.saveMenu(any(SysMenu.class))).thenReturn(savedMenu);

        // 执行测试并验证
        mockMvc.perform(post("/menu")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMenu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("menu-3"))
                .andExpect(jsonPath("$.data.title").value("角色管理"));

        verify(sysMenuService).saveMenu(any(SysMenu.class));
    }

    @Test
    @WithMockUser(authorities = { "menu:add" })
    void testCreateMenuWithInvalidData() throws Exception {
        // 准备无效数据（缺少必填字段）
        SysMenu invalidMenu = new SysMenu();
        invalidMenu.setTitle(""); // 空标题

        mockMvc.perform(post("/menu")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMenu)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "menu:edit" })
    void testUpdateMenu() throws Exception {
        // 准备更新数据
        SysMenu updateMenu = new SysMenu();
        updateMenu.setTitle("系统管理（更新）");
        updateMenu.setPath("/system-updated");
        updateMenu.setComponent("Layout");
        updateMenu.setIcon("system-new");
        updateMenu.setSort(1);
        updateMenu.setStatus(1);
        updateMenu.setParentId("0");

        SysMenu updatedMenu = new SysMenu();
        updatedMenu.setId("menu-1");
        updatedMenu.setTitle(updateMenu.getTitle());
        updatedMenu.setPath(updateMenu.getPath());
        updatedMenu.setComponent(updateMenu.getComponent());
        updatedMenu.setIcon(updateMenu.getIcon());
        updatedMenu.setSort(updateMenu.getSort());
        updatedMenu.setStatus(updateMenu.getStatus());
        updatedMenu.setParentId(updateMenu.getParentId());
        updatedMenu.setUpdateTime(LocalDateTime.now());

        // 模拟服务层更新
        when(sysMenuService.updateMenu(any(SysMenu.class))).thenReturn(updatedMenu);

        // 执行测试并验证
        mockMvc.perform(put("/menu/menu-1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateMenu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("menu-1"))
                .andExpect(jsonPath("$.data.title").value("系统管理（更新）"));

        verify(sysMenuService).updateMenu(any(SysMenu.class));
    }

    @Test
    @WithMockUser(authorities = { "menu:delete" })
    void testDeleteMenu() throws Exception {
        // 模拟删除操作
        doNothing().when(sysMenuService).deleteMenu("menu-1");

        // 执行测试并验证
        mockMvc.perform(delete("/menu/menu-1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysMenuService).deleteMenu("menu-1");
    }

    @Test
    @WithMockUser(authorities = { "menu:delete" })
    void testBatchDeleteMenu() throws Exception {
        // 准备批量删除的ID列表
        List<String> ids = Arrays.asList("menu-1", "menu-2", "menu-3");

        // 模拟批量删除操作
        doNothing().when(sysMenuService).deleteBatch(anyList());

        // 执行测试并验证
        mockMvc.perform(delete("/menu/batch")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysMenuService).deleteBatch(anyList());
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "menu:list" })
    void testGetRoutes() throws Exception {
        // 准备路由数据
        List<SysMenu> routes = new ArrayList<>();
        routes.add(testMenu);

        // 模拟服务层返回路由
        when(sysMenuService.getRoutesByUser(anyString())).thenReturn(routes);

        // 执行测试并验证
        mockMvc.perform(get("/menu/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("系统管理"));

        verify(sysMenuService).getRoutesByUser(anyString());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = { "menu:list" })
    void testGetRoutesForDifferentUser() throws Exception {
        // 准备不同用户的路由数据
        List<SysMenu> userRoutes = new ArrayList<>();
        SysMenu userMenu = new SysMenu();
        userMenu.setId("menu-10");
        userMenu.setTitle("用户中心");
        userMenu.setPath("/user-center");
        userRoutes.add(userMenu);

        when(sysMenuService.getRoutesByUser("testuser")).thenReturn(userRoutes);

        mockMvc.perform(get("/menu/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("用户中心"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "menu:list" })
    void testGetRoutesEmpty() throws Exception {
        // 模拟空路由列表
        when(sysMenuService.getRoutesByUser(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/menu/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void testGetMenuListWithoutPermission() throws Exception {
        // 测试没有权限的情况
        mockMvc.perform(get("/menu"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "other:permission" })
    void testCreateMenuWithWrongPermission() throws Exception {
        // 测试权限不足的情况
        SysMenu newMenu = new SysMenu();
        newMenu.setTitle("测试菜单");

        mockMvc.perform(post("/menu")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMenu)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = { "menu:delete" })
    void testDeleteMenuWithChildren() throws Exception {
        // 测试删除有子菜单的菜单（应该抛出异常或返回错误）
        doThrow(new RuntimeException("该菜单下有子菜单，无法删除"))
                .when(sysMenuService).deleteMenu("menu-1");

        mockMvc.perform(delete("/menu/menu-1")
                .with(csrf()))
                .andExpect(status().isInternalServerError());
    }
}
