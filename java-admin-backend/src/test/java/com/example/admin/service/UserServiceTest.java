package com.example.admin.service;

import com.example.admin.entity.SysUser;
import com.example.admin.repository.SysUserRepository;
import com.example.admin.service.impl.SysUserServiceImpl;
import com.example.admin.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserService 单元测试
 *
 * @author example
 */
public class UserServiceTest {

    @Mock
    private SysUserRepository sysUserRepository;

    @InjectMocks
    private SysUserServiceImpl userService;

    private SysUser testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 准备测试数据
        testUser = new SysUser();
        testUser.setId("123");
        testUser.setUsername("testuser");
        testUser.setPassword("testpassword");
        testUser.setEmail("test@example.com");
        testUser.setStatus(1);
        testUser.setDeptId("dept123");
    }

    @Test
    void testFindByUsername() {
        // 模拟依赖
        when(sysUserRepository.findByUsername("testuser")).thenReturn(testUser);
        
        // 执行测试
        SysUser result = userService.findByUsername("testuser");
        
        // 验证结果
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(sysUserRepository).findByUsername("testuser");
    }

    @Test
    void testFindById() {
        // 模拟依赖
        when(sysUserRepository.findById("123")).thenReturn(Optional.of(testUser));
        
        // 执行测试
        SysUser result = userService.findById("123");
        
        // 验证结果
        assertNotNull(result);
        assertEquals("123", result.getId());
        verify(sysUserRepository).findById("123");
    }

    @Test
    void testFindByIdNotFound() {
        // 模拟依赖
        when(sysUserRepository.findById("not-found-id")).thenReturn(Optional.empty());
        
        // 执行测试
        SysUser result = userService.findById("not-found-id");
        
        // 验证结果
        assertNull(result);
        verify(sysUserRepository).findById("not-found-id");
    }

    @Test
    void testFindAll() {
        // 准备测试数据
        List<SysUser> userList = new ArrayList<>();
        userList.add(testUser);
        
        // 模拟依赖
        when(sysUserRepository.findAll()).thenReturn(userList);
        
        // 执行测试
        List<SysUser> result = userService.findAll();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sysUserRepository).findAll();
    }

    @Test
    void testFindAllPageable() {
        // 准备测试数据
        List<SysUser> userList = new ArrayList<>();
        userList.add(testUser);
        Pageable pageable = PageRequest.of(0, 10);
        Page<SysUser> userPage = new PageImpl<>(userList, pageable, userList.size());
        
        // 模拟依赖
        when(sysUserRepository.findAll(pageable)).thenReturn(userPage);
        
        // 执行测试
        Page<SysUser> result = userService.findAll(pageable);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(sysUserRepository).findAll(pageable);
    }

    @Test
    void testSaveUser() {
        // 模拟依赖
        when(sysUserRepository.save(any(SysUser.class))).thenReturn(testUser);
        
        // 执行测试
        SysUser result = userService.saveUser(testUser);
        
        // 验证结果
        assertNotNull(result);
        // 验证密码被加密
        assertNotEquals("testpassword", result.getPassword());
        verify(sysUserRepository).save(any(SysUser.class));
    }

    @Test
    void testUpdateUser() {
        // 准备测试数据
        testUser.setEmail("updated@example.com");
        // 不提供密码，应该保留原密码
        testUser.setPassword(null);
        
        // 模拟依赖
        when(sysUserRepository.findById("123")).thenReturn(Optional.of(testUser));
        when(sysUserRepository.save(any(SysUser.class))).thenReturn(testUser);
        
        // 执行测试
        SysUser result = userService.updateUser(testUser);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        verify(sysUserRepository).findById("123");
        verify(sysUserRepository).save(any(SysUser.class));
    }

    @Test
    void testUpdateUserWithPassword() {
        // 准备测试数据
        testUser.setPassword("newpassword");
        
        // 模拟依赖
        // 注意：当提供了密码时，updateUser方法不会调用findById，所以不需要模拟findById
        when(sysUserRepository.save(any(SysUser.class))).thenReturn(testUser);
        
        // 执行测试
        SysUser result = userService.updateUser(testUser);
        
        // 验证结果
        assertNotNull(result);
        // 验证新密码被加密
        assertNotEquals("newpassword", result.getPassword());
        // 验证没有调用findById，只调用了save
        verify(sysUserRepository, never()).findById(anyString());
        verify(sysUserRepository).save(any(SysUser.class));
    }

    @Test
    void testDeleteUser() {
        // 执行测试
        userService.deleteUser("123");
        
        // 验证结果
        verify(sysUserRepository).deleteById("123");
    }

    @Test
    void testDeleteBatch() {
        // 准备测试数据
        List<String> ids = new ArrayList<>();
        ids.add("123");
        ids.add("456");
        
        // 执行测试
        userService.deleteBatch(ids);
        
        // 验证结果
        verify(sysUserRepository).deleteAllById(ids);
    }

    @Test
    void testAssignRoles() {
        // 准备测试数据
        List<String> roleIds = new ArrayList<>();
        roleIds.add("role1");
        roleIds.add("role2");
        
        // 执行测试 - 当前是空实现，应该不抛出异常
        assertDoesNotThrow(() -> userService.assignRoles("123", roleIds));
    }
}
