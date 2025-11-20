import { Injectable } from '@nestjs/common'
import { InjectRepository } from '@nestjs/typeorm'
import { Repository } from 'typeorm'
import { CreateDepartmentDto } from './dto/create-department.dto'
import { UpdateDepartmentDto } from './dto/update-department.dto'
import { Department } from './entities/department.entity'
import { DepartmentItemDto, DepartmentListResponseDto } from './dto/department-list-response.dto'

@Injectable()
export class DepartmentService {
  constructor(
    @InjectRepository(Department)
    private departmentRepository: Repository<Department>
  ) {}

  create(createDepartmentDto: CreateDepartmentDto & { departmentName?: string; children?: any }) {
    if (createDepartmentDto.departmentName) {
      createDepartmentDto.name = createDepartmentDto.departmentName
      delete createDepartmentDto.departmentName
    }
    if (createDepartmentDto.children) {
      delete createDepartmentDto.children
    }
    const department = this.departmentRepository.create(createDepartmentDto)
    return this.departmentRepository.save(department)
  }

  async findAll(): Promise<DepartmentListResponseDto> {
    const departments = await this.departmentRepository.find({
      order: { sort: 'ASC', createdAt: 'ASC' }
    })

    // 转换为前端期望的格式，并构建树形结构
    const departmentMap = new Map<string, DepartmentItemDto>()
    const rootDepartments: DepartmentItemDto[] = []

    // 第一遍：创建所有部门节点
    departments.forEach((dept) => {
      const item: DepartmentItemDto = {
        id: dept.id,
        departmentName: dept.name, // 将 name 映射为 departmentName
        children: []
      }
      departmentMap.set(dept.id, item)
    })

    // 第二遍：构建树形结构
    departments.forEach((dept) => {
      const item = departmentMap.get(dept.id)!
      if (dept.parentId && departmentMap.has(dept.parentId)) {
        // 有父节点，添加到父节点的 children
        const parent = departmentMap.get(dept.parentId)!
        if (!parent.children) {
          parent.children = []
        }
        parent.children.push(item)
      } else {
        // 根节点
        rootDepartments.push(item)
      }
    })

    return {
      list: rootDepartments,
      total: departments.length
    }
  }

  findOne(id: string) {
    return this.departmentRepository.findOneBy({ id })
  }

  update(
    id: string,
    updateDepartmentDto: UpdateDepartmentDto & { departmentName?: string; children?: any }
  ) {
    if (updateDepartmentDto.departmentName) {
      updateDepartmentDto.name = updateDepartmentDto.departmentName
      delete updateDepartmentDto.departmentName
    }
    if (updateDepartmentDto.children) {
      delete updateDepartmentDto.children
    }
    return this.departmentRepository.update(id, updateDepartmentDto)
  }

  remove(id: string) {
    return this.departmentRepository.delete(id)
  }

  bulkRemove(ids: string[]) {
    return this.departmentRepository.delete(ids)
  }
}
