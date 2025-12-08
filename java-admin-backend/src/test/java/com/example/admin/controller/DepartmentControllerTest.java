package com.example.admin.controller;

import com.example.admin.entity.SysDepartment;
import com.example.admin.service.ISysDepartmentService;
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
 * DepartmentController 集成测试
 * 测试部门管理相关API接口
 * 
 * 对应前端API:
 * - GET /api/department - 获取部门列表
 * - POST /api/department - 创建部门
 * - PATCH /api/department/:id - 更新部门
 * - POST /api/department/delete - 批量删除部门
 *
 * @author example
 */
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ISysDepartmentService sysDepartmentService;

    private SysDepartment testDepartment;
    private List<SysDepartment> departmentList;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testDepartment = new SysDepartment();
        testDepartment.setId("dept-1");
        testDepartment.setName("技术部");
        testDepartment.setParentId("0");
        testDepartment.setSort(1);
        testDepartment.setStatus(1);
        testDepartment.setLeader("张三");
        testDepartment.setPhone("13800138000");
        testDepartment.setEmail("tech@example.com");
        testDepartment.setCreateTime(LocalDateTime.now());

        SysDepartment subDept = new SysDepartment();
        subDept.setId("dept-2");
        subDept.setName("研发部");
        subDept.setParentId("dept-1");
        subDept.setSort(1);
        subDept.setStatus(1);
        subDept.setLeader("李四");
        subDept.setPhone("13900139000");
        subDept.setEmail("dev@example.com");
        subDept.setCreateTime(LocalDateTime.now());

        departmentList = new ArrayList<>();
        departmentList.add(testDepartment);
        departmentList.add(subDept);
    }

    @Test
    @WithMockUser(authorities = { "dept:list" })
    void testGetDepartmentList() throws Exception {
        // 模拟服务层返回
        when(sysDepartmentService.findAll()).thenReturn(departmentList);

        // 执行测试并验证
        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("技术部"))
                .andExpect(jsonPath("$.data[1].name").value("研发部"));

        verify(sysDepartmentService).findAll();
    }

    @Test
    @WithMockUser(authorities = { "dept:list" })
    void testGetDepartmentListWithParams() throws Exception {
        // 测试带参数的查询
        List<SysDepartment> filteredList = new ArrayList<>();
        filteredList.add(testDepartment);

        when(sysDepartmentService.findAll()).thenReturn(filteredList);

        mockMvc.perform(get("/department")
                .param("name", "技术部")
                .param("status", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @WithMockUser(authorities = { "dept:list" })
    void testGetDepartmentListEmpty() throws Exception {
        // 模拟空列表
        when(sysDepartmentService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = { "dept:view" })
    void testGetDepartmentById() throws Exception {
        // 模拟服务层返回
        when(sysDepartmentService.findById("dept-1")).thenReturn(testDepartment);

        // 执行测试并验证
        mockMvc.perform(get("/department/dept-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("dept-1"))
                .andExpect(jsonPath("$.data.name").value("技术部"))
                .andExpect(jsonPath("$.data.leader").value("张三"));

        verify(sysDepartmentService).findById("dept-1");
    }

    @Test
    @WithMockUser(authorities = { "dept:view" })
    void testGetDepartmentByIdNotFound() throws Exception {
        // 模拟部门不存在
        when(sysDepartmentService.findById("non-existent")).thenReturn(null);

        mockMvc.perform(get("/department/non-existent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(authorities = { "dept:add" })
    void testCreateDepartment() throws Exception {
        // 准备新部门数据
        SysDepartment newDept = new SysDepartment();
        newDept.setName("测试部");
        newDept.setParentId("dept-1");
        newDept.setSort(2);
        newDept.setStatus(1);
        newDept.setLeader("王五");
        newDept.setPhone("13700137000");
        newDept.setEmail("test@example.com");

        SysDepartment savedDept = new SysDepartment();
        savedDept.setId("dept-3");
        savedDept.setName(newDept.getName());
        savedDept.setParentId(newDept.getParentId());
        savedDept.setSort(newDept.getSort());
        savedDept.setStatus(newDept.getStatus());
        savedDept.setLeader(newDept.getLeader());
        savedDept.setPhone(newDept.getPhone());
        savedDept.setEmail(newDept.getEmail());
        savedDept.setCreateTime(LocalDateTime.now());

        // 模拟服务层保存
        when(sysDepartmentService.saveDepartment(any(SysDepartment.class))).thenReturn(savedDept);

        // 执行测试并验证
        mockMvc.perform(post("/department")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("dept-3"))
                .andExpect(jsonPath("$.data.name").value("测试部"));

        verify(sysDepartmentService).saveDepartment(any(SysDepartment.class));
    }

    @Test
    @WithMockUser(authorities = { "dept:add" })
    void testCreateDepartmentWithInvalidData() throws Exception {
        // 准备无效数据（缺少必填字段）
        SysDepartment invalidDept = new SysDepartment();
        invalidDept.setName(""); // 空名称

        mockMvc.perform(post("/department")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDept)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { "dept:edit" })
    void testUpdateDepartment() throws Exception {
        // 准备更新数据
        SysDepartment updateDept = new SysDepartment();
        updateDept.setName("技术部（更新）");
        updateDept.setParentId("0");
        updateDept.setSort(1);
        updateDept.setStatus(1);
        updateDept.setLeader("张三（新）");
        updateDept.setPhone("13800138001");
        updateDept.setEmail("tech-new@example.com");

        SysDepartment updatedDept = new SysDepartment();
        updatedDept.setId("dept-1");
        updatedDept.setName(updateDept.getName());
        updatedDept.setParentId(updateDept.getParentId());
        updatedDept.setSort(updateDept.getSort());
        updatedDept.setStatus(updateDept.getStatus());
        updatedDept.setLeader(updateDept.getLeader());
        updatedDept.setPhone(updateDept.getPhone());
        updatedDept.setEmail(updateDept.getEmail());
        updatedDept.setUpdateTime(LocalDateTime.now());

        // 模拟服务层更新
        when(sysDepartmentService.updateDepartment(any(SysDepartment.class))).thenReturn(updatedDept);

        // 执行测试并验证
        mockMvc.perform(patch("/department/dept-1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value("dept-1"))
                .andExpect(jsonPath("$.data.name").value("技术部（更新）"));

        verify(sysDepartmentService).updateDepartment(any(SysDepartment.class));
    }

    @Test
    @WithMockUser(authorities = { "dept:delete" })
    void testDeleteDepartment() throws Exception {
        // 准备批量删除的ID列表（前端使用POST /api/department/delete）
        List<String> ids = Arrays.asList("dept-1");

        // 模拟删除操作
        doNothing().when(sysDepartmentService).deleteBatch(anyList());

        // 执行测试并验证
        mockMvc.perform(post("/department/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysDepartmentService).deleteBatch(anyList());
    }

    @Test
    @WithMockUser(authorities = { "dept:delete" })
    void testBatchDeleteDepartment() throws Exception {
        // 准备批量删除的ID列表
        List<String> ids = Arrays.asList("dept-1", "dept-2", "dept-3");

        // 模拟批量删除操作
        doNothing().when(sysDepartmentService).deleteBatch(anyList());

        // 执行测试并验证
        mockMvc.perform(post("/department/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(sysDepartmentService).deleteBatch(anyList());
    }

    @Test
    @WithMockUser(authorities = { "dept:delete" })
    void testBatchDeleteDepartmentWithEmptyList() throws Exception {
        // 准备空列表
        List<String> emptyIds = new ArrayList<>();

        mockMvc.perform(post("/department/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyIds)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDepartmentListWithoutPermission() throws Exception {
        // 测试没有权限的情况
        mockMvc.perform(get("/department"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { "other:permission" })
    void testCreateDepartmentWithWrongPermission() throws Exception {
        // 测试权限不足的情况
        SysDepartment newDept = new SysDepartment();
        newDept.setName("测试部");

        mockMvc.perform(post("/department")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDept)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = { "dept:delete" })
    void testDeleteDepartmentWithChildren() throws Exception {
        // 测试删除有子部门的部门（应该抛出异常或返回错误）
        List<String> ids = Arrays.asList("dept-1");

        doThrow(new RuntimeException("该部门下有子部门，无法删除"))
                .when(sysDepartmentService).deleteBatch(anyList());

        mockMvc.perform(post("/department/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(authorities = { "dept:list" })
    void testGetDepartmentTreeStructure() throws Exception {
        // 测试获取树形结构的部门列表
        when(sysDepartmentService.findAll()).thenReturn(departmentList);

        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].parentId").value("0"))
                .andExpect(jsonPath("$.data[1].parentId").value("dept-1"));
    }
}
