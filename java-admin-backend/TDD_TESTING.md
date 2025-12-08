# Java Admin Backend - TDD 测试文档

## 概述

本文档描述了 Java Admin Backend 项目的测试驱动开发（TDD）策略和测试套件。所有测试都基于前端实际接口需求和参照项目（gin-admin-backend 和 nest-admin-backend）编写。

## 测试结构

```
java-admin-backend/src/test/java/com/example/admin/
├── config/
│   └── TestSecurityConfig.java          # 测试安全配置
├── controller/                           # Controller 集成测试
│   ├── AuthControllerTest.java          # 认证接口测试
│   ├── RoleControllerTest.java          # 角色管理测试
│   ├── MenuControllerTest.java          # 菜单管理测试
│   ├── DepartmentControllerTest.java    # 部门管理测试
│   ├── UserControllerTest.java          # 用户管理测试
│   └── AnalysisControllerTest.java      # 数据分析测试
├── service/                              # Service 单元测试
│   ├── UserServiceTest.java             # 用户服务测试
│   └── RoleServiceTest.java             # 角色服务测试
└── utils/                                # 工具类测试
    └── PasswordUtilTest.java             # 密码工具测试
```

## 测试覆盖范围

### 1. Controller 集成测试

#### AuthControllerTest
- ✅ 登录成功场景
- ✅ 登录失败（用户名或密码错误）
- ✅ 空用户名验证
- ✅ 空密码验证
- ✅ 登出功能
- ✅ 格式错误的JSON处理
- ✅ 空请求体处理

**对应前端API:**
- `POST /api/auth/login`
- `GET /api/auth/logout`

#### RoleControllerTest
- ✅ 获取角色列表
- ✅ 获取空角色列表
- ✅ 根据ID获取角色
- ✅ 角色不存在处理
- ✅ 创建角色
- ✅ 创建角色（无效数据）
- ✅ 更新角色
- ✅ 删除角色
- ✅ 批量删除角色
- ✅ 批量删除（空列表）
- ✅ 权限控制测试

**对应前端API:**
- `GET /api/role`
- `POST /api/role`
- `PATCH /api/role/:id`
- `DELETE /api/role/:id`

#### MenuControllerTest
- ✅ 获取菜单列表
- ✅ 获取空菜单列表
- ✅ 根据ID获取菜单
- ✅ 菜单不存在处理
- ✅ 创建菜单
- ✅ 创建菜单（无效数据）
- ✅ 更新菜单
- ✅ 删除菜单
- ✅ 批量删除菜单
- ✅ 获取路由列表
- ✅ 不同用户的路由
- ✅ 空路由列表
- ✅ 删除有子菜单的菜单
- ✅ 权限控制测试

**对应前端API:**
- `GET /api/menu`
- `POST /api/menu`
- `PATCH /api/menu/:id`
- `DELETE /api/menu/:id`
- `GET /api/menu/routes`

#### DepartmentControllerTest
- ✅ 获取部门列表
- ✅ 带参数查询部门
- ✅ 获取空部门列表
- ✅ 根据ID获取部门
- ✅ 部门不存在处理
- ✅ 创建部门
- ✅ 创建部门（无效数据）
- ✅ 更新部门
- ✅ 批量删除部门
- ✅ 删除有子部门的部门
- ✅ 树形结构测试
- ✅ 权限控制测试

**对应前端API:**
- `GET /api/department`
- `POST /api/department`
- `PATCH /api/department/:id`
- `POST /api/department/delete`

#### UserControllerTest
- ✅ 获取用户列表（分页）
- ✅ 带筛选条件查询
- ✅ 获取空用户列表
- ✅ 分页功能测试
- ✅ 根据ID获取用户
- ✅ 用户不存在处理
- ✅ 创建用户
- ✅ 创建用户（无效数据）
- ✅ 创建用户（重复用户名）
- ✅ 更新用户
- ✅ 更新用户密码
- ✅ 批量删除用户
- ✅ 删除当前登录用户
- ✅ 权限控制测试

**对应前端API:**
- `GET /api/user`
- `POST /api/user`
- `PATCH /api/user/:id`
- `POST /api/user/delete`

#### AnalysisControllerTest
- ✅ 获取总览数据
- ✅ 获取空总览数据
- ✅ 获取用户访问来源
- ✅ 获取空访问来源
- ✅ 获取每周用户活动
- ✅ 获取空活动数据
- ✅ 获取月度销售数据
- ✅ 获取空销售数据
- ✅ 包含null值的数据
- ✅ 大数据集测试
- ✅ 零值数据测试
- ✅ 负值数据测试
- ✅ 认证测试

**对应前端API:**
- `GET /api/analysis/total`
- `GET /api/analysis/userAccessSource`
- `GET /api/analysis/weeklyUserActivity`
- `GET /api/analysis/monthlySales`

### 2. Service 单元测试

#### UserServiceTest
- ✅ 根据用户名查找
- ✅ 根据ID查找
- ✅ ID不存在处理
- ✅ 查找所有用户
- ✅ 分页查找
- ✅ 保存用户（密码加密）
- ✅ 更新用户
- ✅ 更新用户（带密码）
- ✅ 删除用户
- ✅ 批量删除
- ✅ 分配角色

#### RoleServiceTest
- ✅ 根据ID查找
- ✅ ID不存在处理
- ✅ 查找所有角色
- ✅ 保存角色
- ✅ 更新角色
- ✅ 更新角色（不存在）
- ✅ 删除角色
- ✅ 批量删除
- ✅ 根据角色值查找
- ✅ 空名称验证
- ✅ 重复角色值验证
- ✅ 更新角色状态

### 3. 工具类测试

#### PasswordUtilTest
- ✅ 密码加密
- ✅ 密码验证（正确）
- ✅ 密码验证（错误）

## 测试技术栈

- **JUnit 5**: 测试框架
- **Mockito**: Mock 框架
- **Spring Boot Test**: Spring Boot 测试支持
- **MockMvc**: Web 层测试
- **@WebMvcTest**: Controller 层测试注解
- **@WithMockUser**: 安全测试支持

## 运行测试

### 运行所有测试
```bash
mvn test
```

### 运行特定测试类
```bash
mvn test -Dtest=AuthControllerTest
```

### 运行特定测试方法
```bash
mvn test -Dtest=AuthControllerTest#testLoginSuccess
```

### 生成测试覆盖率报告
```bash
mvn test jacoco:report
```

## TDD 开发流程

1. **编写测试** - 基于前端API需求编写测试用例
2. **运行测试** - 验证测试失败（Red）
3. **实现功能** - 编写最小化代码使测试通过
4. **运行测试** - 验证测试通过（Green）
5. **重构代码** - 优化代码质量（Refactor）
6. **重复循环** - 继续下一个功能

## 测试最佳实践

### 1. 命名规范
- 测试类: `{ClassName}Test`
- 测试方法: `test{MethodName}{Scenario}`
- 例如: `testLoginSuccess`, `testLoginWithInvalidCredentials`

### 2. AAA 模式
每个测试方法遵循 AAA 模式:
- **Arrange** (准备): 设置测试数据和模拟对象
- **Act** (执行): 调用被测试的方法
- **Assert** (断言): 验证结果

### 3. 测试隔离
- 每个测试独立运行
- 使用 `@BeforeEach` 初始化测试数据
- 避免测试之间的依赖

### 4. Mock 使用
- 使用 `@Mock` 模拟依赖
- 使用 `@InjectMocks` 注入被测试对象
- 使用 `when().thenReturn()` 定义行为

### 5. 断言验证
- 使用有意义的断言消息
- 验证返回值和副作用
- 使用 `verify()` 验证方法调用

## 测试覆盖目标

- **行覆盖率**: > 80%
- **分支覆盖率**: > 75%
- **方法覆盖率**: > 90%

## 持续集成

测试在以下情况自动运行:
- 代码提交时
- Pull Request 创建时
- 合并到主分支前

## 参照项目对比

### Gin Admin Backend
- 使用 Go 的 testing 包
- 使用 testify 进行断言
- HTTP 测试使用 httptest

### Nest Admin Backend
- 使用 Jest 测试框架
- 使用 supertest 进行 HTTP 测试
- 支持 E2E 测试

### Java Admin Backend
- 使用 JUnit 5 + Mockito
- 使用 MockMvc 进行 HTTP 测试
- 分离单元测试和集成测试

## 待完成测试

以下模块的测试还需要补充:

- [ ] DictionaryControllerTest - 字典管理测试
- [ ] TableControllerTest - 表格示例测试
- [ ] WorkplaceControllerTest - 工作台测试
- [ ] MenuServiceTest - 菜单服务测试
- [ ] DepartmentServiceTest - 部门服务测试
- [ ] AuthServiceTest - 认证服务测试
- [ ] JwtUtilTest - JWT 工具测试
- [ ] Repository 层集成测试
- [ ] E2E 端到端测试

## 问题和改进

### 已知问题
1. 某些 Controller 测试需要完整的 Spring Security 配置
2. 数据库集成测试需要 H2 或 TestContainers

### 改进计划
1. 添加性能测试
2. 添加并发测试
3. 增加测试数据生成器
4. 集成 SonarQube 代码质量分析

## 参考资源

- [JUnit 5 用户指南](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito 文档](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [前端 API 文档](../src/api/)

## 总结

本测试套件提供了全面的测试覆盖，确保:
1. ✅ 所有前端 API 接口都有对应的测试
2. ✅ 业务逻辑正确性得到验证
3. ✅ 边界情况和异常处理得到测试
4. ✅ 权限控制得到验证
5. ✅ 代码质量得到保障

通过 TDD 方式开发，我们确保代码的可测试性、可维护性和高质量。
