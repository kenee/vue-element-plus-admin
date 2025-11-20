import { Injectable } from '@nestjs/common'
import { InjectRepository } from '@nestjs/typeorm'
import { Repository, In } from 'typeorm'
import { CreateMenuDto } from './dto/create-menu.dto'
import { UpdateMenuDto } from './dto/update-menu.dto'
import { Menu } from './entities/menu.entity'
import {
  MenuItemDto,
  MenuListResponseDto,
  MenuMetaDto,
  PermissionItemDto
} from './dto/menu-list-response.dto'
import { RouteItemDto, RouteMetaDto } from './dto/route-response.dto'
import { UserService } from '../user/user.service'

@Injectable()
export class MenuService {
  constructor(
    @InjectRepository(Menu)
    private menuRepository: Repository<Menu>,
    private userService: UserService
  ) {}

  create(createMenuDto: CreateMenuDto) {
    const menu = this.menuRepository.create(createMenuDto)
    return this.menuRepository.save(menu)
  }

  getRoutes() {
    // 保留此方法用于向后兼容，但实际应该使用 getRoutesByUser
    return [
      {
        path: '/dashboard',
        component: '#',
        redirect: '/dashboard/analysis',
        name: 'Dashboard',
        meta: {
          title: 'router.dashboard',
          icon: 'vi-ant-design:dashboard-filled',
          alwaysShow: true
        },
        children: [
          {
            path: 'analysis',
            component: 'views/Dashboard/Analysis',
            name: 'Analysis',
            meta: {
              title: 'router.analysis',
              noCache: true,
              affix: true
            }
          },
          {
            path: 'workplace',
            component: 'views/Dashboard/Workplace',
            name: 'Workplace',
            meta: {
              title: 'router.workplace',
              noCache: true,
              affix: true
            }
          }
        ]
      },
      {
        path: '/authorization',
        component: '#',
        redirect: '/authorization/user',
        name: 'Authorization',
        meta: {
          title: 'router.authorization',
          icon: 'vi-eos-icons:role-binding',
          alwaysShow: true
        },
        children: [
          {
            path: 'department',
            component: 'views/Authorization/Department/Department',
            name: 'Department',
            meta: {
              title: 'router.department'
            }
          },
          {
            path: 'user',
            component: 'views/Authorization/User/User',
            name: 'User',
            meta: {
              title: 'router.user'
            }
          },
          {
            path: 'menu',
            component: 'views/Authorization/Menu/Menu',
            name: 'Menu',
            meta: {
              title: 'router.menuManagement'
            }
          },
          {
            path: 'role',
            component: 'views/Authorization/Role/Role',
            name: 'Role',
            meta: {
              title: 'router.role'
            }
          }
        ]
      }
    ]
  }

  /**
   * 根据用户ID获取该用户角色关联的菜单路由
   * 返回前端期望的 AppCustomRouteRecordRaw[] 格式
   */
  async getRoutesByUser(userId: string): Promise<RouteItemDto[]> {
    // 1. 获取用户及其角色
    const user = await this.userService.findOne(userId)
    if (!user || !user.roles || user.roles.length === 0) {
      return []
    }

    // 2. 收集所有角色关联的菜单ID
    const menuIdSet = new Set<string>()
    user.roles.forEach((role) => {
      if (role.menus && role.menus.length > 0) {
        role.menus.forEach((menu) => {
          menuIdSet.add(menu.id)
        })
      }
    })

    if (menuIdSet.size === 0) {
      return []
    }

    // 3. 从数据库获取菜单（只获取启用状态的菜单）
    const menus = await this.menuRepository.find({
      where: {
        id: In(Array.from(menuIdSet)),
        status: 1 // 只返回启用状态的菜单
      },
      order: { sort: 'ASC', createdAt: 'ASC' }
    })

    // 4. 转换为前端路由格式并构建树形结构
    return this.convertMenusToRoutes(menus)
  }

  /**
   * 将数据库菜单转换为前端路由格式
   */
  private convertMenusToRoutes(menus: Menu[]): RouteItemDto[] {
    const menuMap = new Map<string, RouteItemDto>()
    const rootRoutes: RouteItemDto[] = []
    const nameSet = new Set<string>() // 用于跟踪已使用的路由名称，确保唯一性

    // 第一遍：创建所有路由节点
    menus.forEach((menu) => {
      // 构建 meta 对象
      let meta: RouteMetaDto = {}
      if (menu.meta && typeof menu.meta === 'object') {
        meta = { ...menu.meta }
        // 确保 title 存在
        if (!meta.title) {
          meta.title = menu.title
        }
      } else {
        meta.title = menu.title
        if (menu.icon) {
          meta.icon = menu.icon
        }
      }

      // 处理权限
      if (menu.permission) {
        const permissions = menu.permission
          .split(',')
          .map((p) => p.trim())
          .filter(Boolean)
        if (permissions.length > 0) {
          meta.permission = permissions
        }
      }

      // 生成 name（如果不存在），并确保唯一性
      let routeName = menu.name || this.generateMenuName(menu.path || '', menu.title)

      // 如果名称已存在，添加后缀确保唯一性
      let uniqueName = routeName
      let counter = 1
      while (nameSet.has(uniqueName)) {
        uniqueName = `${routeName}${counter}`
        counter++
      }
      nameSet.add(uniqueName)
      routeName = uniqueName

      // 处理 redirect：如果菜单有 redirect，使用它；否则对于目录类型(type=0)，需要设置默认 redirect
      let redirect = menu.redirect || ''
      // if (!redirect && menu.type === 0) {
      //   // 目录类型但没有 redirect，暂时使用 path，后续在 filterHiddenMenus 中会根据子菜单设置
      //   redirect = menu.path || '';
      // }

      // 确保必需的字段存在
      const route: RouteItemDto = {
        name: routeName,
        path: menu.path || '',
        component: menu.component || '#',
        redirect: redirect,
        meta: meta,
        children: []
      }

      menuMap.set(menu.id, route)
    })

    // 第二遍：构建树形结构
    menus.forEach((menu) => {
      const route = menuMap.get(menu.id)!
      if (menu.parentId && menuMap.has(menu.parentId)) {
        // 有父节点，添加到父节点的 children
        const parent = menuMap.get(menu.parentId)!
        if (!parent.children) {
          parent.children = []
        }
        parent.children.push(route)
      } else {
        // 根节点
        rootRoutes.push(route)
      }
    })

    // 过滤掉 hidden 的菜单，并处理目录类型的 redirect
    return this.filterHiddenMenus(rootRoutes)
  }

  /**
   * 递归过滤掉 hidden 的菜单，并处理目录类型的 redirect
   */
  private filterHiddenMenus(routes: RouteItemDto[]): RouteItemDto[] {
    return routes
      .filter((route) => !route.meta.hidden)
      .map((route) => {
        let processedRoute = { ...route }

        // 如果有子菜单，递归处理
        if (processedRoute.children && processedRoute.children.length > 0) {
          processedRoute.children = this.filterHiddenMenus(processedRoute.children)

          // 如果目录类型没有 redirect，且有子菜单，设置第一个子菜单的路径作为 redirect
          if (!processedRoute.redirect && processedRoute.children.length > 0) {
            const firstChild = processedRoute.children[0]
            if (firstChild.path) {
              // 构建完整路径
              const parentPath = processedRoute.path || ''
              const childPath = firstChild.path.startsWith('/')
                ? firstChild.path
                : `${parentPath}/${firstChild.path}`
              processedRoute.redirect = childPath
            }
          }
        }

        return processedRoute
      })
  }

  async findAll(): Promise<MenuListResponseDto> {
    const menus = await this.menuRepository.find({
      order: { sort: 'ASC', createdAt: 'ASC' }
    })

    // 转换为前端期望的格式，并构建树形结构
    const menuMap = new Map<string, MenuItemDto>()
    const rootMenus: MenuItemDto[] = []

    // 第一遍：创建所有菜单节点
    menus.forEach((menu) => {
      // 将 permission 字符串转换为 permissionList 数组
      const permissionList: PermissionItemDto[] | undefined = menu.permission
        ? menu.permission.split(',').map((value, index) => ({
            id: index + 1,
            label: this.getPermissionLabel(value),
            value: value.trim()
          }))
        : undefined

      // 构建 meta 对象：优先使用存储的 meta，否则从其他字段构建
      let meta: MenuMetaDto
      if (menu.meta && typeof menu.meta === 'object') {
        // 使用存储的 meta，确保包含 title
        meta = {
          ...menu.meta,
          title: menu.meta.title || menu.title
        }
        // 如果 meta 中没有 icon，但实体有 icon，则添加（兼容旧数据）
        if (!meta.icon && menu.icon) {
          meta.icon = menu.icon
        }
      } else {
        // 从其他字段构建 meta（兼容旧数据）
        meta = {
          title: menu.title
        }
        if (menu.icon) {
          meta.icon = menu.icon
        }
      }

      // 如果有权限，添加到 meta（如果 meta 中没有 permission 字段）
      if (permissionList && permissionList.length > 0) {
        if (!meta.permission || !Array.isArray(meta.permission)) {
          meta.permission = permissionList.map((p) => p.value)
        }
      }

      const item: MenuItemDto = {
        id: menu.id,
        path: menu.path || '',
        component: menu.component || undefined,
        redirect: menu.redirect || undefined, // redirect 字段
        name: menu.name || this.generateMenuName(menu.path || '', menu.title), // 优先使用存储的 name，否则生成
        title: menu.title,
        type: menu.type,
        parentId: menu.parentId || undefined,
        status: menu.status !== undefined ? menu.status : 1, // 使用实体中的 status，默认 1
        meta: Object.keys(meta).length > 0 ? meta : undefined,
        permissionList: permissionList,
        children: []
      }
      menuMap.set(menu.id, item)
    })

    // 第二遍：构建树形结构
    menus.forEach((menu) => {
      const item = menuMap.get(menu.id)!
      if (menu.parentId && menuMap.has(menu.parentId)) {
        // 有父节点，添加到父节点的 children
        const parent = menuMap.get(menu.parentId)!
        if (!parent.children) {
          parent.children = []
        }
        parent.children.push(item)
      } else {
        // 根节点
        rootMenus.push(item)
      }
    })

    return {
      list: rootMenus
    }
  }

  // 从 path 生成 name（用于路由）
  private generateMenuName(path: string, title?: string): string {
    if (!path) {
      // 如果没有 path，从 title 生成
      if (title) {
        return title
          .replace(/[^\w\s]/g, '')
          .split(/\s+/)
          .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
          .join('')
      }
      return ''
    }

    // 如果是外部链接，基于 URL 生成唯一的名称
    if (path.startsWith('http')) {
      try {
        const url = new URL(path)
        // 从域名生成名称，例如：element-plus-admin-doc.cn -> ElementPlusAdminDocCn
        const hostname = url.hostname.replace(/^www\./, '')
        const parts = hostname.split('.')
        const domainPart = parts[0] || 'External'
        return (
          domainPart
            .split(/[\/\-_]/)
            .map((part) => {
              const cleaned = part.replace(/[^\w]/g, '')
              return cleaned.charAt(0).toUpperCase() + cleaned.slice(1)
            })
            .join('') + 'Link'
        )
      } catch {
        // 如果 URL 解析失败，使用哈希值生成唯一名称
        return (
          'ExternalLink' +
          Math.abs(
            path.split('').reduce((a, b) => {
              a = (a << 5) - a + b.charCodeAt(0)
              return a & a
            }, 0)
          ).toString(36)
        )
      }
    }

    // 移除开头的 / 和结尾的 /
    const cleanPath = path.replace(/^\/+|\/+$/g, '')

    // 转换为 PascalCase
    const parts = cleanPath.split(/[\/\-_]/)
    if (parts.length === 0) {
      return title
        ? title
            .replace(/[^\w\s]/g, '')
            .split(/\s+/)
            .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
            .join('')
        : 'Menu'
    }

    return parts
      .map((part) => {
        // 处理特殊字符
        const cleaned = part.replace(/[^\w]/g, '')
        return cleaned.charAt(0).toUpperCase() + cleaned.slice(1)
      })
      .join('')
  }

  // 获取权限标签
  private getPermissionLabel(value: string): string {
    const labelMap: Record<string, string> = {
      add: '新增',
      edit: '编辑',
      delete: '删除',
      view: '查看'
    }
    return labelMap[value.trim()] || value.trim()
  }

  findOne(id: string) {
    return this.menuRepository.findOneBy({ id })
  }

  update(id: string, updateMenuDto: UpdateMenuDto) {
    return this.menuRepository.update(id, updateMenuDto)
  }

  remove(id: string) {
    return this.menuRepository.delete(id)
  }
}
