import { Injectable } from '@nestjs/common'
import { InjectRepository } from '@nestjs/typeorm'
import { Repository, Like } from 'typeorm'
import * as bcrypt from 'bcrypt'
import { CreateUserDto } from './dto/create-user.dto'
import { UpdateUserDto } from './dto/update-user.dto'
import { FindUserDto } from './dto/find-user.dto'
import { User } from './entities/user.entity'

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User)
    private userRepository: Repository<User>
  ) {}

  async create(createUserDto: CreateUserDto) {
    // 1. 处理默认密码
    if (!createUserDto.password) {
      createUserDto.password = '123456' // 默认密码
    }

    // 2. 密码加密
    const saltRounds = 10
    createUserDto.password = await bcrypt.hash(createUserDto.password, saltRounds)

    // 3. 映射 department.id 到 deptId
    if (createUserDto.department && createUserDto.department.id) {
      createUserDto.deptId = createUserDto.department.id
    }

    // 4. 创建用户实例
    const user = this.userRepository.create(createUserDto)

    // 5. 处理角色关联
    if (createUserDto.role && createUserDto.role.length > 0) {
      user.roles = createUserDto.role.map((roleId) => ({ id: roleId }) as any)
    }

    return this.userRepository.save(user)
  }

  async findAll(query?: FindUserDto) {
    const { page = 1, pageSize = 10, deptId, username, nickname } = query || {}
    const skip = (page - 1) * pageSize

    const where: any = {}
    if (deptId) {
      where.deptId = deptId
    }
    if (username) {
      where.username = Like(`%${username}%`)
    }
    if (nickname) {
      where.nickname = Like(`%${nickname}%`)
    }

    const [list, total] = await this.userRepository.findAndCount({
      where,
      skip,
      take: pageSize,
      order: { createdAt: 'DESC' },
      relations: ['roles'] // Optional: load roles if needed for list view
    })

    return {
      list,
      total
    }
  }

  findOne(id: string) {
    return this.userRepository.findOne({
      where: { id },
      relations: ['roles', 'roles.menus'] // 加载 roles 和 roles.menus 关系
    })
  }

  findOneByUsername(username: string) {
    return this.userRepository.findOne({
      where: { username },
      relations: ['roles'],
      select: [
        'id',
        'username',
        'password',
        'nickname',
        'email',
        'status',
        'deptId',
        'createdAt',
        'updatedAt'
      ] // 显式选择 password 字段
    })
  }

  async update(id: string, updateUserDto: UpdateUserDto) {
    // 1. 处理 department
    if (updateUserDto.department && updateUserDto.department.id) {
      updateUserDto.deptId = updateUserDto.department.id
    }
    // 删除 department 属性，避免 TypeORM 报错
    if (updateUserDto.department) {
      delete updateUserDto.department
    }

    // 2. 处理 roles
    let roles: any[] | undefined
    // 注意：这里改为检查 updateUserDto.role 是否存在，允许空数组（表示清空角色）
    if (updateUserDto.role) {
      roles = updateUserDto.role.map((roleId) => ({ id: roleId }))
      delete updateUserDto.role // 删除 role 属性
    }

    // 3. 如果有 roles 更新 (包括空数组)，使用 save (preload)
    if (roles !== undefined) {
      // 使用 preload 混合现有实体和新数据
      const user = await this.userRepository.preload({
        id,
        ...updateUserDto,
        roles
      })
      if (!user) {
        throw new Error(`User #${id} not found`)
      }
      return this.userRepository.save(user)
    }

    // 4. 如果没有 roles 更新，使用 update (更高效)
    // 确保没有 undefined 的 roles 属性传入 update
    // 使用 as any 绕过类型检查，确保运行时删除多余属性
    const dto: any = updateUserDto
    if (dto.roles) {
      delete dto.roles
    }
    if (dto.createdAt) delete dto.createdAt
    if (dto.updatedAt) delete dto.updatedAt
    if (dto.createTime) delete dto.createTime
    if (dto.updateTime) delete dto.updateTime

    return this.userRepository.update(id, updateUserDto)
  }

  remove(id: string) {
    return this.userRepository.delete(id)
  }

  async assignRoles(user: User, roles: any[]) {
    user.roles = roles
    return this.userRepository.save(user)
  }

  bulkRemove(ids: string[]) {
    return this.userRepository.delete(ids)
  }
}
