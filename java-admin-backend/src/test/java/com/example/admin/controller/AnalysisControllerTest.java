package com.example.admin.controller;

import com.example.admin.service.ISysAnalysisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AnalysisController 集成测试
 * 测试数据分析相关API接口
 * 
 * 对应前端API:
 * - GET /api/analysis/total - 获取总览数据
 * - GET /api/analysis/userAccessSource - 获取用户访问来源
 * - GET /api/analysis/weeklyUserActivity - 获取每周用户活动
 * - GET /api/analysis/monthlySales - 获取月度销售数据
 *
 * @author example
 */
@WebMvcTest(AnalysisController.class)
public class AnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ISysAnalysisService sysAnalysisService;

    private Map<String, Object> totalData;
    private List<Map<String, Object>> userAccessSourceData;
    private List<Map<String, Object>> weeklyUserActivityData;
    private List<Map<String, Object>> monthlySalesData;

    @BeforeEach
    void setUp() {
        // 准备总览数据
        totalData = new HashMap<>();
        totalData.put("users", 1234);
        totalData.put("messages", 5678);
        totalData.put("money", 98765.43);
        totalData.put("shoppings", 432);

        // 准备用户访问来源数据
        userAccessSourceData = new ArrayList<>();
        Map<String, Object> source1 = new HashMap<>();
        source1.put("value", 335);
        source1.put("name", "直接访问");
        userAccessSourceData.add(source1);

        Map<String, Object> source2 = new HashMap<>();
        source2.put("value", 310);
        source2.put("name", "邮件营销");
        userAccessSourceData.add(source2);

        Map<String, Object> source3 = new HashMap<>();
        source3.put("value", 234);
        source3.put("name", "联盟广告");
        userAccessSourceData.add(source3);

        // 准备每周用户活动数据
        weeklyUserActivityData = new ArrayList<>();
        Map<String, Object> activity1 = new HashMap<>();
        activity1.put("value", 13253);
        activity1.put("name", "周一");
        weeklyUserActivityData.add(activity1);

        Map<String, Object> activity2 = new HashMap<>();
        activity2.put("value", 34235);
        activity2.put("name", "周二");
        weeklyUserActivityData.add(activity2);

        // 准备月度销售数据
        monthlySalesData = new ArrayList<>();
        Map<String, Object> sales1 = new HashMap<>();
        sales1.put("estimate", 100);
        sales1.put("actual", 120);
        sales1.put("name", "一月");
        monthlySalesData.add(sales1);

        Map<String, Object> sales2 = new HashMap<>();
        sales2.put("estimate", 120);
        sales2.put("actual", 82);
        sales2.put("name", "二月");
        monthlySalesData.add(sales2);
    }

    @Test
    @WithMockUser
    void testGetAnalysisTotal() throws Exception {
        // 模拟服务层返回
        when(sysAnalysisService.getTotal()).thenReturn(totalData);

        // 执行测试并验证
        mockMvc.perform(get("/analysis/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.users").value(1234))
                .andExpect(jsonPath("$.data.messages").value(5678))
                .andExpect(jsonPath("$.data.money").value(98765.43))
                .andExpect(jsonPath("$.data.shoppings").value(432));
    }

    @Test
    @WithMockUser
    void testGetAnalysisTotalEmpty() throws Exception {
        // 模拟空数据
        when(sysAnalysisService.getTotal()).thenReturn(new HashMap<>());

        mockMvc.perform(get("/analysis/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser
    void testGetUserAccessSource() throws Exception {
        // 模拟服务层返回
        when(sysAnalysisService.getUserAccessSource()).thenReturn(userAccessSourceData);

        // 执行测试并验证
        mockMvc.perform(get("/analysis/userAccessSource"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].name").value("直接访问"))
                .andExpect(jsonPath("$.data[0].value").value(335))
                .andExpect(jsonPath("$.data[1].name").value("邮件营销"))
                .andExpect(jsonPath("$.data[1].value").value(310));
    }

    @Test
    @WithMockUser
    void testGetUserAccessSourceEmpty() throws Exception {
        // 模拟空列表
        when(sysAnalysisService.getUserAccessSource()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/analysis/userAccessSource"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @WithMockUser
    void testGetWeeklyUserActivity() throws Exception {
        // 模拟服务层返回
        when(sysAnalysisService.getWeeklyUserActivity()).thenReturn(weeklyUserActivityData);

        // 执行测试并验证
        mockMvc.perform(get("/analysis/weeklyUserActivity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("周一"))
                .andExpect(jsonPath("$.data[0].value").value(13253))
                .andExpect(jsonPath("$.data[1].name").value("周二"))
                .andExpect(jsonPath("$.data[1].value").value(34235));
    }

    @Test
    @WithMockUser
    void testGetWeeklyUserActivityEmpty() throws Exception {
        // 模拟空列表
        when(sysAnalysisService.getWeeklyUserActivity()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/analysis/weeklyUserActivity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @WithMockUser
    void testGetMonthlySales() throws Exception {
        // 模拟服务层返回
        when(sysAnalysisService.getMonthlySales()).thenReturn(monthlySalesData);

        // 执行测试并验证
        mockMvc.perform(get("/analysis/monthlySales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("一月"))
                .andExpect(jsonPath("$.data[0].estimate").value(100))
                .andExpect(jsonPath("$.data[0].actual").value(120))
                .andExpect(jsonPath("$.data[1].name").value("二月"))
                .andExpect(jsonPath("$.data[1].estimate").value(120))
                .andExpect(jsonPath("$.data[1].actual").value(82));
    }

    @Test
    @WithMockUser
    void testGetMonthlySalesEmpty() throws Exception {
        // 模拟空列表
        when(sysAnalysisService.getMonthlySales()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/analysis/monthlySales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void testGetAnalysisTotalWithoutAuth() throws Exception {
        // 测试未认证的情况
        mockMvc.perform(get("/analysis/total"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUserAccessSourceWithoutAuth() throws Exception {
        // 测试未认证的情况
        mockMvc.perform(get("/analysis/userAccessSource"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetAnalysisTotalWithNullValues() throws Exception {
        // 测试包含null值的数据
        Map<String, Object> dataWithNulls = new HashMap<>();
        dataWithNulls.put("users", 100);
        dataWithNulls.put("messages", null);
        dataWithNulls.put("money", 0);

        when(sysAnalysisService.getTotal()).thenReturn(dataWithNulls);

        mockMvc.perform(get("/analysis/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.users").value(100))
                .andExpect(jsonPath("$.data.money").value(0));
    }

    @Test
    @WithMockUser
    void testGetUserAccessSourceWithLargeDataset() throws Exception {
        // 测试大数据集
        List<Map<String, Object>> largeDataset = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> source = new HashMap<>();
            source.put("value", i * 10);
            source.put("name", "来源" + i);
            largeDataset.add(source);
        }

        when(sysAnalysisService.getUserAccessSource()).thenReturn(largeDataset);

        mockMvc.perform(get("/analysis/userAccessSource"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(100));
    }

    @Test
    @WithMockUser
    void testGetWeeklyUserActivityWithZeroValues() throws Exception {
        // 测试包含零值的数据
        List<Map<String, Object>> zeroData = new ArrayList<>();
        Map<String, Object> activity = new HashMap<>();
        activity.put("value", 0);
        activity.put("name", "周日");
        zeroData.add(activity);

        when(sysAnalysisService.getWeeklyUserActivity()).thenReturn(zeroData);

        mockMvc.perform(get("/analysis/weeklyUserActivity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].value").value(0));
    }

    @Test
    @WithMockUser
    void testGetMonthlySalesWithNegativeValues() throws Exception {
        // 测试包含负值的数据（可能表示退款等）
        List<Map<String, Object>> negativeData = new ArrayList<>();
        Map<String, Object> sales = new HashMap<>();
        sales.put("estimate", 100);
        sales.put("actual", -50);
        sales.put("name", "三月");
        negativeData.add(sales);

        when(sysAnalysisService.getMonthlySales()).thenReturn(negativeData);

        mockMvc.perform(get("/analysis/monthlySales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].actual").value(-50));
    }
}
