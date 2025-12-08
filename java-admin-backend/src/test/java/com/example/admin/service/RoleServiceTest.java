package com.example.admin.service;

import com.example.admin.entity.SysRole;
import com.example.admin.repository.SysRoleRepository;
import com.example.admin.service.impl.SysRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * RoleService 单元测试
 * 测试角色服务层业务逻辑
 *
 * @author example
 */
public class RoleServiceTest {

    @Mock
    private SysRoleRepository sysRoleRepository;

    @InjectMocks
    private SysRoleServiceImpl roleService;

    private SysRole testRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 准备测试数据
        testRole = new SysRole();
        testRole.setId("role-1");
        testRole.setRoleName("管理员");
        testRole.setRoleValue("admin");
        testRole.setStatus(1);
        testRole.setRemark("系统管理员");
        testRole.setCreateTime(LocalDateTime.now());
    }

    @Test
    void testFindById() {
        // 模拟依赖
        when(sysRoleRepository.findById("role-1")).thenReturn(Optional.of(testRole));

        // 执行测试
        SysRole result = roleService.findById("role-1");

        // 验证结果
        assertNotNull(result);
        assertEquals("role-1", result.getId());
        assertEquals("管理员", result.getRoleName());
        verify(sysRoleRepository).findById("role-1");
    }

    @Test
    void testFindByIdNotFound() {
        // 模拟依赖
        when(sysRoleRepository.findById("not-found")).thenReturn(Optional.empty());

        // 执行测试
        SysRole result = roleService.findById("not-found");

        // 验证结果
        assertNull(result);
        verify(sysRoleRepository).findById("not-found");
    }

    @Test
    void testFindAll() {
        // 准备测试数据
        List<SysRole> roleList = new ArrayList<>();
        roleList.add(testRole);

        SysRole role2 = new SysRole();
        role2.setId("role-2");
        role2.setRoleName("普通用户");
        roleList.add(role2);

        // 模拟依赖
        when(sysRoleRepository.findAll()).thenReturn(roleList);

        // 执行测试
        List<SysRole> result = roleService.findAll();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("管理员", result.get(0).getRoleName());
        verify(sysRoleRepository).findAll();
    }

    @Test
    void testSaveRole() {
        // 准备新角色
        SysRole newRole = new SysRole();
        newRole.setRoleName("测试角色");
        newRole.setRoleValue("test");
        newRole.setStatus(1);

        SysRole savedRole = new SysRole();
        savedRole.setId("role-3");
        savedRole.setRoleName(newRole.getRoleName());
        savedRole.setRoleValue(newRole.getRoleValue());
        savedRole.setStatus(newRole.getStatus());
        savedRole.setCreateTime(LocalDateTime.now());

        // 模拟依赖
        when(sysRoleRepository.save(any(SysRole.class))).thenReturn(savedRole);

        // 执行测试
        SysRole result = roleService.saveRole(newRole);

        // 验证结果
        assertNotNull(result);
        assertEquals("role-3", result.getId());
        assertEquals("测试角色", result.getRoleName());
        assertNotNull(result.getCreateTime());
        verify(sysRoleRepository).save(any(SysRole.class));
    }

    @Test
    void testUpdateRole() {
        // 准备更新数据
        SysRole updateRole = new SysRole();
        updateRole.setId("role-1");
        updateRole.setRoleName("更新后的管理员");
        updateRole.setRoleValue("admin-updated");
        updateRole.setStatus(1);

        // 模拟依赖
        when(sysRoleRepository.findById("role-1")).thenReturn(Optional.of(testRole));
        when(sysRoleRepository.save(any(SysRole.class))).thenReturn(updateRole);

        // 执行测试
        SysRole result = roleService.updateRole(updateRole);

        // 验证结果
        assertNotNull(result);
        assertEquals("role-1", result.getId());
        assertEquals("更新后的管理员", result.getRoleName());
        verify(sysRoleRepository).findById("role-1");
        verify(sysRoleRepository).save(any(SysRole.class));
    }

    @Test
    void testUpdateRoleNotFound() {
        // 准备更新数据
        SysRole updateRole = new SysRole();
        updateRole.setId("not-found");
        updateRole.setRoleName("不存在的角色");

        // 模拟依赖
        when(sysRoleRepository.findById("not-found")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            roleService.updateRole(updateRole);
        });

        verify(sysRoleRepository).findById("not-found");
        verify(sysRoleRepository, never()).save(any(SysRole.class));
    }

    @Test
    void testDeleteRole() {
        // 执行测试
        roleService.deleteRole("role-1");

        // 验证结果
        verify(sysRoleRepository).deleteById("role-1");
    }

    @Test
    void testDeleteBatch() {
        // 准备批量删除的ID列表
        List<String> ids = Arrays.asList("role-1", "role-2", "role-3");

        // 执行测试
        roleService.deleteBatch(ids);

        // 验证结果
        verify(sysRoleRepository).deleteAllById(ids);
    }

    @Test
    void testDeleteBatchWithEmptyList() {
        // 准备空列表
        List<String> emptyIds = new ArrayList<>();

        // 执行测试
        roleService.deleteBatch(emptyIds);

        // 验证结果
        verify(sysRoleRepository).deleteAllById(emptyIds);
    }

    @Test
    void testFindByRoleValue() {
        // 模拟依赖
        when(sysRoleRepository.findByRoleValue("admin")).thenReturn(testRole);

        // 执行测试
        SysRole result = roleService.findByRoleValue("admin");

        // 验证结果
        assertNotNull(result);
        assertEquals("admin", result.getRoleValue());
        verify(sysRoleRepository).findByRoleValue("admin");
    }

    @Test
    void testFindByRoleValueNotFound() {
        // 模拟依赖
        when(sysRoleRepository.findByRoleValue("not-found")).thenReturn(null);

        // 执行测试
        SysRole result = roleService.findByRoleValue("not-found");

        // 验证结果
        assertNull(result);
        verify(sysRoleRepository).findByRoleValue("not-found");
    }

    @Test
    void testSaveRoleWithNullName() {
        // 准备无效数据
        SysRole invalidRole = new SysRole();
        invalidRole.setRoleValue("test");
        // roleName为null

        // 执行测试并验证异常
        assertThrows(Exception.class, () -> {
            roleService.saveRole(invalidRole);
        });
    }

    @Test
    void testSaveRoleWithDuplicateRoleValue() {
        // 准备重复的角色值
        SysRole duplicateRole = new SysRole();
        duplicateRole.setRoleName("重复角色");
        duplicateRole.setRoleValue("admin");

        // 模拟已存在相同roleValue的角色
        when(sysRoleRepository.findByRoleValue("admin")).thenReturn(testRole);

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            roleService.saveRole(duplicateRole);
        });
    }

    @Test
    void testUpdateRoleStatus() {
        // 测试更新角色状态
        testRole.setStatus(0); // 禁用

        when(sysRoleRepository.findById("role-1")).thenReturn(Optional.of(testRole));
        when(sysRoleRepository.save(any(SysRole.class))).thenReturn(testRole);

        SysRole result = roleService.updateRole(testRole);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
    }
}
