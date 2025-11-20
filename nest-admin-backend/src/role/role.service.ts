import { Injectable, BadRequestException, NotFoundException } from '@nestjs/common'
import { InjectRepository } from '@nestjs/typeorm'
import { Repository, In } from 'typeorm'
import { CreateRoleDto } from './dto/create-role.dto'
import { UpdateRoleDto } from './dto/update-role.dto'
import { Role } from './entities/role.entity'
import { RoleListResponseDto, RoleItemDto } from './dto/role-list-response.dto'
import { MenuService } from '../menu/menu.service'
import { MenuItemDto } from '../menu/dto/menu-list-response.dto'
import { Menu } from '../menu/entities/menu.entity'
import { User } from '../user/entities/user.entity'

@Injectable()
export class RoleService {
  // 系统默认角色，不允许删除
  private readonly SYSTEM_ROLES = ['admin']

  constructor(
    @InjectRepository(Role)
    private roleRepository: Repository<Role>,
    @InjectRepository(Menu)
    private menuRepository: Repository<Menu>,
    @InjectRepository(User)
    private userRepository: Repository<User>,
    private menuService: MenuService
  ) {}

  async create(createRoleDto: CreateRoleDto) {
    const { menuIds, ...roleData } = createRoleDto
    const role = this.roleRepository.create(roleData)

    // 如果提供了菜单 ID，关联菜单
    if (menuIds && menuIds.length > 0) {
      const menus = await this.menuRepository.findBy({ id: In(menuIds) })
      role.menus = menus
    }

    return this.roleRepository.save(role)
  }

  async findAll(): Promise<RoleListResponseDto> {
    const roles = await this.roleRepository.find({
      relations: ['menus'],
      order: { createdAt: 'ASC' }
    })

    // 获取所有菜单（用于构建菜单树）
    const allMenus = await this.menuService.findAll()

    // 转换为前端期望的格式
    const roleList: RoleItemDto[] = roles.map((role) => {
      // 转换菜单：将关联的菜单 ID 转换为菜单树形结构
      let menuList: MenuItemDto[] | undefined
      if (role.menus && role.menus.length > 0) {
        // 创建菜单 ID 集合
        const menuIdSet = new Set(role.menus.map((m) => m.id))

        // 递归函数：从菜单树中提取匹配的菜单（保持树形结构）
        const extractMenus = (menus: MenuItemDto[]): MenuItemDto[] => {
          const result: MenuItemDto[] = []
          menus.forEach((menu) => {
            if (menuIdSet.has(menu.id)) {
              // 如果菜单匹配，创建副本并递归处理子菜单
              const menuCopy: MenuItemDto = {
                ...menu,
                children:
                  menu.children && menu.children.length > 0
                    ? extractMenus(menu.children)
                    : undefined
              }
              result.push(menuCopy)
            } else if (menu.children && menu.children.length > 0) {
              // 如果当前菜单不匹配，但可能有匹配的子菜单
              const matchedChildren = extractMenus(menu.children)
              if (matchedChildren.length > 0) {
                // 如果有匹配的子菜单，创建父菜单（但不包含在 ID 集合中）
                const menuCopy: MenuItemDto = {
                  ...menu,
                  children: matchedChildren
                }
                result.push(menuCopy)
              }
            }
          })
          return result
        }

        menuList = extractMenus(allMenus.list)
      }

      return {
        id: role.id,
        roleName: role.roleName,
        role: role.roleValue, // 映射 roleValue -> role
        status: role.status,
        remark: role.remark,
        createTime: role.createdAt.toISOString(), // 映射 createdAt -> createTime
        menu: menuList || [] // 确保 menu 始终是数组，而不是 undefined
      }
    })

    return {
      list: roleList,
      total: roleList.length
    }
  }

  findOne(id: string) {
    return this.roleRepository.findOneBy({ id })
  }

  async update(id: string, updateRoleDto: UpdateRoleDto) {
    const { menuIds, ...roleData } = updateRoleDto
    const role = await this.roleRepository.findOne({
      where: { id },
      relations: ['menus']
    })

    if (!role) {
      throw new Error('Role not found')
    }

    // 更新角色基本信息
    Object.assign(role, roleData)

    // 如果提供了菜单 ID，更新菜单关联
    if (menuIds !== undefined) {
      if (menuIds.length > 0) {
        const menus = await this.menuRepository.findBy({ id: In(menuIds) })
        role.menus = menus
      } else {
        role.menus = []
      }
    }

    return this.roleRepository.save(role)
  }

  async remove(id: string) {
    // 1. 检查角色是否存在
    const role = await this.roleRepository.findOne({
      where: { id },
      relations: ['menus']
    })

    if (!role) {
      throw new NotFoundException(`Role with ID ${id} not found`)
    }

    // 2. 检查是否是系统默认角色（不允许删除）
    if (this.SYSTEM_ROLES.includes(role.roleValue)) {
      throw new BadRequestException(`Cannot delete system role: ${role.roleValue}`)
    }

    // 3. 检查是否有用户正在使用这个角色
    const usersWithRole = await this.userRepository
      .createQueryBuilder('user')
      .innerJoin('user.roles', 'role', 'role.id = :roleId', { roleId: id })
      .getCount()

    if (usersWithRole > 0) {
      throw new BadRequestException(
        `Cannot delete role "${role.roleName}" because it is assigned to ${usersWithRole} user(s). Please remove the role from all users first.`
      )
    }

    // 4. 删除角色（TypeORM 会自动处理中间表 sys_role_menu 的删除）
    return this.roleRepository.remove(role)
  }
}
