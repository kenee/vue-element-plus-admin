import { NestFactory } from '@nestjs/core'
import { AppModule } from '../app.module'
import { UserService } from '../user/user.service'
import { RoleService } from '../role/role.service'
import { DepartmentService } from '../department/department.service'
import { MenuService } from '../menu/menu.service'
import { DictionaryService } from '../dictionary/dictionary.service'
import { CreateUserDto } from '../user/dto/create-user.dto'
import { CreateRoleDto } from '../role/dto/create-role.dto'
import { CreateDepartmentDto } from '../department/dto/create-department.dto'
import { CreateMenuDto } from '../menu/dto/create-menu.dto'
import { CreateDictionaryDto } from '../dictionary/dto/create-dictionary.dto'
import { getRepositoryToken } from '@nestjs/typeorm'
import { Department } from '../department/entities/department.entity'
import { Role } from '../role/entities/role.entity'
import { Menu } from '../menu/entities/menu.entity'
import { Dictionary } from '../dictionary/entities/dictionary.entity'
import { Repository } from 'typeorm'

// Mock 菜单数据接口
interface MockMenu {
  path?: string
  component?: string
  redirect?: string
  name?: string
  title: string
  type: number
  parentId?: number | undefined
  id: number
  status?: number
  meta?: {
    title?: string
    icon?: string
    [key: string]: any
  }
  permissionList?: Array<{ id: number; label: string; value: string }>
  children?: MockMenu[]
}

async function bootstrap() {
  const app = await NestFactory.createApplicationContext(AppModule)
  const userService = app.get(UserService)
  const roleService = app.get(RoleService)
  const departmentService = app.get(DepartmentService)
  const menuService = app.get(MenuService)
  const dictionaryService = app.get(DictionaryService)
  const departmentRepository = app.get<Repository<Department>>(getRepositoryToken(Department))
  const menuRepository = app.get<Repository<Menu>>(getRepositoryToken(Menu))
  const dictionaryRepository = app.get<Repository<Dictionary>>(getRepositoryToken(Dictionary))

  // 1. Seed Roles
  const rolesData: CreateRoleDto[] = [
    {
      roleName: 'Super Admin',
      roleValue: 'admin',
      status: 1,
      remark: 'Super Administrator with full access'
    },
    {
      roleName: 'General User',
      roleValue: 'user',
      status: 1,
      remark: 'Standard user with limited access'
    }
  ]

  for (const roleData of rolesData) {
    const existingRoles = await roleService.findAll() // Returns { list: [], total: number }
    const exists = existingRoles.list.find((r) => r.role === roleData.roleValue) // Use 'role' field from response
    if (!exists) {
      await roleService.create(roleData)
      console.log(`Role created: ${roleData.roleName}`)
    } else {
      console.log(`Role already exists: ${roleData.roleName}`)
    }
  }

  // 2. Seed Departments (参考 mock/department/index.mock.ts)
  const citys = ['厦门总公司', '北京分公司', '上海分公司', '福州分公司', '深圳分公司', '杭州分公司']
  const subDepartments = ['研发部', '产品部', '运营部', '市场部', '销售部', '客服部']

  // 检查已存在的部门名称
  const existingDepartments = await departmentRepository.find()
  const existingDepartmentNames = new Set(existingDepartments.map((dept) => dept.name))

  // 创建主部门（总公司/分公司）
  const parentDepartments: any[] = []
  for (let i = 0; i < Math.min(5, citys.length); i++) {
    const cityName = citys[i]
    if (existingDepartmentNames.has(cityName)) {
      console.log(`Department already exists: ${cityName}`)
      continue
    }

    const parentDept: CreateDepartmentDto = {
      name: cityName,
      parentId: null,
      sort: i + 1,
      status: 1,
      remark: `${cityName}的备注信息`
    }

    const createdParent = await departmentService.create(parentDept)
    parentDepartments.push(createdParent)
    console.log(`Parent department created: ${cityName}`)

    // 创建子部门
    for (let j = 0; j < subDepartments.length; j++) {
      const subDeptName = subDepartments[j]
      const subDept: CreateDepartmentDto = {
        name: subDeptName,
        parentId: createdParent.id,
        sort: j + 1,
        status: Math.floor(Math.random() * 2), // 0 或 1
        remark: `${subDeptName}的备注信息`
      }

      await departmentService.create(subDept)
      console.log(`  └─ Sub department created: ${subDeptName}`)
    }
  }

  // 3. Seed Menus (参考 mock/menu/index.mock.ts)
  const mockMenus: MockMenu[] = [
    {
      path: '/dashboard',
      component: '#',
      redirect: '/dashboard/analysis',
      name: 'Dashboard',
      title: '首页',
      type: 0,
      parentId: undefined,
      id: 1,
      meta: {
        title: '首页',
        icon: 'vi-ant-design:dashboard-filled',
        alwaysShow: true
      },
      children: [
        {
          path: 'analysis',
          component: 'views/Dashboard/Analysis',
          name: 'Analysis',
          title: '分析页',
          type: 1,
          parentId: 1,
          id: 2,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' }
          ],
          meta: {
            title: '分析页',
            noCache: true,
            permission: ['add', 'edit']
          }
        },
        {
          path: 'workplace',
          component: 'views/Dashboard/Workplace',
          name: 'Workplace',
          title: '工作台',
          type: 1,
          parentId: 1,
          id: 3,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' }
          ],
          meta: {
            title: '工作台',
            noCache: true
          }
        }
      ]
    },
    {
      path: '/authorization',
      component: '#',
      redirect: '/authorization/user',
      name: 'Authorization',
      title: '权限管理',
      type: 0,
      parentId: undefined,
      id: 18,
      meta: {
        title: '权限管理',
        icon: 'vi-eos-icons:role-binding',
        alwaysShow: true
      },
      children: [
        {
          path: 'department',
          component: 'views/Authorization/Department/Department',
          name: 'Department',
          title: '部门管理',
          type: 1,
          parentId: 18,
          id: 19,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' }
          ],
          meta: {
            title: '部门管理'
          }
        },
        {
          path: 'user',
          component: 'views/Authorization/User/User',
          name: 'User',
          title: '用户管理',
          type: 1,
          parentId: 18,
          id: 20,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' }
          ],
          meta: {
            title: '用户管理'
          }
        },
        {
          path: 'menu',
          component: 'views/Authorization/Menu/Menu',
          name: 'Menu',
          title: '菜单管理',
          type: 1,
          parentId: 18,
          id: 21,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' }
          ],
          meta: {
            title: '菜单管理'
          }
        },
        {
          path: 'role',
          component: 'views/Authorization/Role/Role',
          name: 'Role',
          title: '角色管理',
          type: 1,
          parentId: 18,
          id: 22,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' }
          ],
          meta: {
            title: '角色管理'
          }
        }
      ]
    },
    {
      path: '/external-link',
      component: '#',
      name: 'ExternalLink',
      title: '文档',
      type: 0,
      parentId: undefined,
      id: 4,
      meta: {
        title: '文档',
        icon: 'vi-clarity:document-solid'
      },
      children: [
        {
          path: 'https://element-plus-admin-doc.cn/',
          name: 'DocumentLink',
          title: '文档',
          type: 1,
          parentId: 4,
          id: 5,
          meta: {
            title: '文档'
          }
        }
      ]
    },
    {
      path: '/level',
      component: '#',
      redirect: '/level/menu1/menu1-1/menu1-1-1',
      name: 'Level',
      title: '菜单',
      type: 0,
      parentId: undefined,
      id: 6,
      meta: {
        title: '菜单',
        icon: 'vi-carbon:skill-level-advanced'
      },
      children: [
        {
          path: 'menu1',
          name: 'Menu1',
          component: '##',
          redirect: '/level/menu1/menu1-1/menu1-1-1',
          title: '菜单1',
          type: 0,
          parentId: 6,
          id: 7,
          meta: {
            title: '菜单1'
          },
          children: [
            {
              path: 'menu1-1',
              name: 'Menu11',
              component: '##',
              redirect: '/level/menu1/menu1-1/menu1-1-1',
              title: '菜单1-1',
              type: 0,
              parentId: 7,
              id: 8,
              meta: {
                title: '菜单1-1',
                alwaysShow: true
              },
              children: [
                {
                  path: 'menu1-1-1',
                  name: 'Menu111',
                  component: 'views/Level/Menu111',
                  title: '菜单1-1-1',
                  type: 1,
                  parentId: 8,
                  id: 9,
                  meta: {
                    title: '菜单1-1-1'
                  }
                }
              ]
            },
            {
              path: 'menu1-2',
              name: 'Menu12',
              component: 'views/Level/Menu12',
              title: '菜单1-2',
              type: 1,
              parentId: 7,
              id: 10,
              meta: {
                title: '菜单1-2'
              }
            }
          ]
        },
        {
          path: 'menu2',
          name: 'Menu2Demo',
          component: 'views/Level/Menu2',
          title: '菜单2',
          type: 1,
          parentId: 6,
          id: 11,
          meta: {
            title: '菜单2'
          }
        }
      ]
    },
    {
      path: '/example',
      component: '#',
      redirect: '/example/example-dialog',
      name: 'Example',
      title: '综合示例',
      type: 0,
      parentId: undefined,
      id: 12,
      meta: {
        title: '综合示例',
        icon: 'vi-ep:management',
        alwaysShow: true
      },
      children: [
        {
          path: 'example-dialog',
          component: 'views/Example/Dialog/ExampleDialog',
          name: 'ExampleDialog',
          title: '综合示例-弹窗',
          type: 1,
          parentId: 12,
          id: 13,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' },
            { id: 4, label: '查看', value: 'view' }
          ],
          meta: {
            title: '综合示例-弹窗'
          }
        },
        {
          path: 'example-page',
          component: 'views/Example/Page/ExamplePage',
          name: 'ExamplePage',
          title: '综合示例-页面',
          type: 1,
          parentId: 12,
          id: 14,
          permissionList: [
            { id: 1, label: '新增', value: 'add' },
            { id: 2, label: '编辑', value: 'edit' },
            { id: 3, label: '删除', value: 'delete' },
            { id: 4, label: '查看', value: 'view' }
          ],
          meta: {
            title: '综合示例-页面'
          }
        },
        {
          path: 'example-add',
          component: 'views/Example/Page/ExampleAdd',
          name: 'ExampleAdd',
          title: '综合示例-新增',
          type: 1,
          parentId: 12,
          id: 15,
          meta: {
            title: '综合示例-新增',
            noTagsView: true,
            noCache: true,
            hidden: true,
            showMainRoute: true,
            activeMenu: '/example/example-page'
          }
        },
        {
          path: 'example-edit',
          component: 'views/Example/Page/ExampleEdit',
          name: 'ExampleEdit',
          title: '综合示例-编辑',
          type: 1,
          parentId: 12,
          id: 16,
          meta: {
            title: '综合示例-编辑',
            noTagsView: true,
            noCache: true,
            hidden: true,
            showMainRoute: true,
            activeMenu: '/example/example-page'
          }
        },
        {
          path: 'example-detail',
          component: 'views/Example/Page/ExampleDetail',
          name: 'ExampleDetail',
          title: '综合示例-详情',
          type: 1,
          parentId: 12,
          id: 17,
          meta: {
            title: '综合示例-详情',
            noTagsView: true,
            noCache: true,
            hidden: true,
            showMainRoute: true,
            activeMenu: '/example/example-page'
          }
        }
      ]
    }
  ]

  // 检查已存在的菜单
  const existingMenus = await menuRepository.find()
  const existingMenuPaths = new Set(existingMenus.map((menu) => menu.path).filter(Boolean))

  // 递归创建菜单
  async function createMenuRecursive(
    mockMenu: MockMenu,
    parentId: string | null = null,
    sort: number = 0
  ): Promise<void> {
    // 检查是否已存在
    if (mockMenu.path && existingMenuPaths.has(mockMenu.path)) {
      console.log(`Menu already exists: ${mockMenu.path}`)
      return
    }

    // 构建权限字符串（如果有 permissionList）
    const permission =
      mockMenu.permissionList && mockMenu.permissionList.length > 0
        ? mockMenu.permissionList.map((p) => p.value).join(',')
        : undefined

    // 构建完整的 meta 对象
    const meta: Record<string, any> = {}
    if (mockMenu.meta) {
      // 复制所有 meta 属性
      Object.assign(meta, mockMenu.meta)
      // 确保 title 存在
      if (!meta.title) {
        meta.title = mockMenu.title
      }
    } else {
      // 如果没有 meta，至少包含 title
      meta.title = mockMenu.title
      if (mockMenu.meta?.icon) {
        meta.icon = mockMenu.meta.icon
      }
    }

    const menuDto: CreateMenuDto = {
      parentId: parentId || undefined,
      path: mockMenu.path,
      component: mockMenu.component,
      redirect: mockMenu.redirect, // 存储 redirect 字段
      title: mockMenu.title,
      name: mockMenu.name, // 存储 name 字段
      icon: mockMenu.meta?.icon,
      meta: Object.keys(meta).length > 0 ? meta : undefined, // 存储完整的 meta 对象
      type: mockMenu.type,
      permission: permission,
      sort: sort,
      status: mockMenu.status !== undefined ? mockMenu.status : 1 // 存储 status 字段
    }

    const createdMenu = await menuService.create(menuDto)
    console.log(`${'  '.repeat(sort)}Menu created: ${mockMenu.title} (${mockMenu.path || 'N/A'})`)

    // 递归创建子菜单
    if (mockMenu.children && mockMenu.children.length > 0) {
      for (let i = 0; i < mockMenu.children.length; i++) {
        await createMenuRecursive(mockMenu.children[i], createdMenu.id, sort + 1)
      }
    }
  }

  // 创建所有菜单
  for (let i = 0; i < mockMenus.length; i++) {
    await createMenuRecursive(mockMenus[i], null, 0)
  }

  // 4. Seed Dictionaries (参考 mock/dict/index.mock.ts)
  const dictData: Array<{ dictName: string; dictCode: string; remark?: string }> = [
    {
      dictName: '重要性',
      dictCode: 'importance',
      remark: '重要性字典：0-普通，1-良好，2-重要'
    }
  ]

  // 检查已存在的字典
  const existingDictionaries = await dictionaryRepository.find()
  const existingDictCodes = new Set(existingDictionaries.map((dict) => dict.dictCode))

  for (const dict of dictData) {
    if (existingDictCodes.has(dict.dictCode)) {
      console.log(`Dictionary already exists: ${dict.dictCode}`)
      continue
    }

    const dictDto: CreateDictionaryDto = {
      dictName: dict.dictName,
      dictCode: dict.dictCode,
      status: 1,
      remark: dict.remark
    }

    await dictionaryService.create(dictDto)
    console.log(`Dictionary created: ${dict.dictName} (${dict.dictCode})`)
  }

  const roleRepository = app.get<Repository<Role>>(getRepositoryToken(Role))

  // ... (existing code)

  // 5. Seed Admin User
  const adminUser: CreateUserDto = {
    username: 'admin',
    password: '123456', // Will be hashed by UserService
    nickname: 'Admin',
    email: 'admin@example.com',
    status: 1,
    deptId: parentDepartments.length > 0 ? parentDepartments[0].id : null
  }

  const existingUser = await userService.findOneByUsername(adminUser.username)
  if (!existingUser) {
    const createdUser = await userService.create(adminUser)
    console.log(`User created: ${adminUser.username}`)

    // Fetch the admin role (need Role entity, not DTO)
    const allRoles = await roleService.findAll()
    const adminRoleDto = allRoles.list.find((r) => r.role === 'admin')

    if (adminRoleDto) {
      // Get the Role entity by ID
      const adminRole = await roleService.findOne(adminRoleDto.id)
      if (adminRole) {
        // Assign all menus to admin role
        const allMenus = await menuRepository.find()
        adminRole.menus = allMenus
        await roleRepository.save(adminRole)
        console.log(`Assigned all ${allMenus.length} menus to role ${adminRole.roleName}`)

        await userService.assignRoles(createdUser, [adminRole])
        console.log(`Assigned role ${adminRole.roleName} to user ${createdUser.username}`)
      }
    }
  } else {
    console.log(`User already exists: ${adminUser.username}`)

    // Even if user exists, ensure admin role has all menus
    const allRoles = await roleService.findAll()
    const adminRoleDto = allRoles.list.find((r) => r.role === 'admin')
    if (adminRoleDto) {
      const adminRole = await roleService.findOne(adminRoleDto.id)
      if (adminRole) {
        const allMenus = await menuRepository.find()
        adminRole.menus = allMenus
        await roleRepository.save(adminRole)
        console.log(`Updated role ${adminRole.roleName} with all ${allMenus.length} menus`)
      }
    }
  }

  await app.close()
}

bootstrap()
