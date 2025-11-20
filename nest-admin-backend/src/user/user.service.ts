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

  update(id: string, updateUserDto: UpdateUserDto) {
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
